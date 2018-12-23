package com.vivhp.qlct;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vivhp.qlct.DBHelper.DataBaseHelper;
import com.vivhp.qlct.Model.Model_Phannhom;
import com.vivhp.qlct.Model.Model_Taikhoan;
import com.vivhp.qlct.Model.Model_Thuchi;
import com.vivhp.qlct.adapter.Spinner_Tk_Adapter;
import com.vivhp.qlct.adapter.Spinner_adapter;
import com.vivhp.qlct.dialog.DialogProgressBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created
 * by vivhp on 10/16/2016.
 */

public class Tab1 extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener{

    BroadcastReceiver broadcastReceiver;
    Model_Phannhom model_phannhom_chi, model_phannhom_thu;
    String DateTime;
    EditText et_amount, et_description;
    TextView tvDateTime;
    DataBaseHelper dataBaseHelper;
    View rootView;
    Button btnSave;
    Spinner spinner_account, spinner_transaction, spinner_group;
    //AppCompatSpinner spinner_status;
    ArrayList<Model_Phannhom> phannhomArrayList;
    ArrayList<Model_Taikhoan> taikhoanArrayList;
    private String[] type_tk = {"Khoản Chi", "Khoản Thu"};
    TextView tvSetDate;
    String query_type_tk;
    ArrayAdapter<String> Adapter_transaction;
    Spinner_adapter spinnerAdapter;
    Spinner_Tk_Adapter adapter_tk;

    String temp_manhom, temp_idtk, mes;
    /**
     * Variable for data
     **/
    String lydo;
    int sotien;
    String ngay, loaitc;
    int id_tk, manhom;

    //Tai khoan
    int tien_tk, tid_tk;

    //So Tien
    int tien_tk1;

    //DialogProgressbar
    DialogProgressBar progressBar;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.test, container,
                false);


        dataBaseHelper = new DataBaseHelper(getActivity());
        //Init TextView
        tvDateTime = (TextView) rootView.findViewById(R.id.DateTime);
        tvSetDate = (TextView) rootView.findViewById(R.id.SetDate);
        //Init Edittext
        et_amount = (EditText) rootView.findViewById(R.id.input_amount);
        et_amount.addTextChangedListener(new DoiSoTien(et_amount));
        et_description = (EditText) rootView.findViewById(R.id.input_description);
        //Init Spinner Library
        spinner_account = (Spinner) rootView.findViewById(R.id.spinner_account);
        spinner_transaction = (Spinner) rootView.findViewById(R.id.spinner_transaction);
        spinner_group = (Spinner) rootView.findViewById(R.id.spinner_group);
        /**
         * Method
         **/
        //Get Date
        getTimeHienTai();
        //Set Spinner
        set_Spinner_account();
        set_Spinner_transaction();
        setBtnSave();
        setForcusAble();

        //Khai bao ve thuc hien lang nghe
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getExtras().getInt("b") == 1){
                    spinner_transaction.setSelection(0);
                    model_phannhom_chi = (Model_Phannhom) intent.getSerializableExtra("p");
                    Constants.model_phannhom_chi = model_phannhom_chi;
                    Log.e("Model phannhom c ten: ", model_phannhom_chi.getTennhom());
                    phannhomArrayList.add(Constants.model_phannhom_chi);
                    Constants.model_phannhom_chi = null;
                }
                if (intent.getExtras().getInt("b") == 2){
                    spinner_transaction.setSelection(1);
                    model_phannhom_thu = (Model_Phannhom) intent.getSerializableExtra("p");
                    Log.e("Model phannhom t ten: ", model_phannhom_thu.getTennhom());
                    Constants.model_phannhom_thu = model_phannhom_thu;
                    phannhomArrayList.add(Constants.model_phannhom_thu);
                    Constants.model_phannhom_thu = null;
                }
                if (intent.getExtras().getInt("b") == 3) {
                    receiverIntentChi();
                }
                if (intent.getExtras().getInt("b") == 4) {
                    receiverIntentThu();
                }
            }
        };
        // đăng ký
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("p"));


        return rootView;
    }

    public void setForcusAble() {
        et_amount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        et_description.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
    }

    public void setDate() {
        Calendar calendar = Calendar.getInstance();
        android.app.DatePickerDialog pickerDialog = new android.app.DatePickerDialog(getActivity(), new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvDateTime.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }

    public void getData() {
        progressBar = new DialogProgressBar(getActivity(), false, false, null, getString(R.string.saving));
        /**
         * ThuChi
         * - sotien
         * - ngay
         * - lydo
         * - id_tk
         * - manhom
         * =>
         *  - Tính Toán Thu Chi
         *  - Add Data
         **/
            String length_edt;
        length_edt = et_amount.getText().toString().trim();
        if (length_edt.isEmpty() || length_edt.length()==0 || length_edt == null) {
            mes = "Bạn chưa nhập số tiền.";
            Snackbar snackbar = Snackbar.make(rootView, mes, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            sbView.setBackgroundColor(rootView.getResources().getColor(R.color.rectage_btn));
            textView.setTextColor(rootView.getResources().getColor(R.color.white));
            snackbar.show();
        } else if (TextUtils.isEmpty(et_description.getText().toString())) {
            mes = "Bạn chưa nhập lý do.";
            Snackbar snackbar = Snackbar.make(rootView, mes, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            sbView.setBackgroundColor(rootView.getResources().getColor(R.color.rectage_btn));
            textView.setTextColor(rootView.getResources().getColor(R.color.white));
            snackbar.show();
        } else if (taikhoanArrayList.size() == 0){
            mes = "Bạn cần thêm ví tiền.";
            Snackbar snackbar = Snackbar.make(rootView, mes, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            sbView.setBackgroundColor(rootView.getResources().getColor(R.color.rectage_btn));
            textView.setTextColor(rootView.getResources().getColor(R.color.white));
            snackbar.show();
        } else if (phannhomArrayList.size() == 0){
            mes = "Bạn chưa thêm nhóm.";
            Snackbar snackbar = Snackbar.make(rootView, mes, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            sbView.setBackgroundColor(rootView.getResources().getColor(R.color.rectage_btn));
            textView.setTextColor(rootView.getResources().getColor(R.color.white));
            snackbar.show();
        } else {
            String sotiennhap = et_amount.getText().toString();
            int sotien_int=Integer.parseInt(sotiennhap.replaceAll("[\\D]", ""));
            sotien = sotien_int;


            ngay = tvDateTime.getText().toString();
            lydo = et_description.getText().toString();
            id_tk = Integer.parseInt(temp_idtk);
            manhom = Integer.parseInt(temp_manhom);
            //Tính Toán Thu Chi
            /**
             * Nếu Thu thì tính tổng
             **/

            /**
             * Nếu Chi thì tính hiệu
             **/
            if (loaitc == "Khoản Chi") {
                tien_tk = dataBaseHelper.getTienTk(id_tk);
                tien_tk1 = tien_tk - sotien;
                dataBaseHelper.updateTienTk(new Model_Taikhoan(id_tk, tien_tk1));
            }
            if (loaitc == "Khoản Thu") {
                tien_tk = dataBaseHelper.getTienTk(id_tk);
                tien_tk1 = tien_tk + sotien;
                dataBaseHelper.updateTienTk(new Model_Taikhoan(id_tk, tien_tk1));
            }
            //Add Thu Chi
            dataBaseHelper.addThuChi(new Model_Thuchi(sotien, ngay, lydo, id_tk, manhom));
            et_amount.setText("");
            et_description.setText("");
            //Send Broadcast
            int reques_int = 1;
            Intent intent = new Intent("2");
            intent.putExtra("2", reques_int);
            intent.putExtra("a", 2);
            getActivity().sendBroadcast(intent);

            progressBar.showProgressBar();
            TimeOut(1000);
        }


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /**
         * Nếu có 2 spinner trong một activity:
         * - Số lượng phần tử của mỗi một spinner khác nhau thì cần
         *   ckeck xem spinner nào đang chọn item**/

        //Spinner Loai khoan
        if (parent.getId() == R.id.spinner_transaction) {
            query_type_tk = Adapter_transaction.getItem(position).toString();
            //Nếu chuỗi String = Khoản Thu

            //Nếu chuỗi String = Khoản Chi
            if (query_type_tk == "Khoản Chi") {
                //Gọi Set spinner Khoản Chi
                setSpinnerChi();
            }
            if (query_type_tk == "Khoản Thu") {
                //Gọi Set spinner Khoản Thu
                setSpinnerThu();
            }
            loaitc = query_type_tk;
            Toast.makeText(getActivity(), loaitc, Toast.LENGTH_SHORT).show();
        }

        //Spinner nhom
        if (parent.getId() == R.id.spinner_group) {
            String txt = "";

            temp_manhom = String.valueOf(phannhomArrayList.get(position).getManhom());

//            txt += "Cách 1: " + phannhomArrayList.get(position).getManhom();
////            or
//            txt += ";  Cách 2: " +  + phannhomArrayList.get(spinner_group.getSelectedItemPosition()).getManhom();
        }
        //Spinner Tai khoan
        if (parent.getId() == R.id.spinner_account) {
            tid_tk = taikhoanArrayList.get(position).get_id();
            temp_idtk = String.valueOf(taikhoanArrayList.get(position).get_id());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Set Button Lưu Click
     **/
    public void setBtnSave() {
        btnSave = (Button) rootView.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });


    }


    /**
     * Get thời gian hiện tại
     **/
    public void getTimeHienTai() {
        //Get current Date Time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        DateTime = dateFormat.format(date);

        tvDateTime.setText(DateTime);
        tvSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDateTime.setText(null);
                setDate();
            }
        });
    }

    /**
     * Set Spinner Account
     **/
    public void set_Spinner_account() {
        //Initialization Adapter to spinner
        taikhoanArrayList = dataBaseHelper.getAllTenTaiKhoan();
        Log.d("Size: ", String.valueOf(taikhoanArrayList.size()));
        if (taikhoanArrayList.size() == 0){
            spinner_account.setVisibility(View.GONE);
        } else {
            adapter_tk = new Spinner_Tk_Adapter(getActivity(), R.layout.taikkhoan_spinner_row, taikhoanArrayList);
            adapter_tk.setDropDownViewResource(R.layout.taikkhoan_spinner_row);
            //Set Adapter Spinner
            spinner_account.setAdapter(adapter_tk);
            spinner_account.getSelectedItemPosition();
            spinner_account.setOnItemSelectedListener(this);
            adapter_tk.notifyDataSetChanged();
        }
    }

    /**
     * Set Spinner Transaction
     **/
    public void set_Spinner_transaction() {
        //Adapter Transaction
        Adapter_transaction = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, type_tk);
        Adapter_transaction.setDropDownViewResource(R.layout.spinner_item);
        //Transaction
        spinner_transaction.setAdapter(Adapter_transaction);
        spinner_transaction.getSelectedItemPosition();
        spinner_transaction.setOnItemSelectedListener(this);
    }

    /**
     * Nếu Item Spinner Giao dịch get đc là khoản Thu
     **/
    public void setSpinnerThu() {
        //Adapter Group
        phannhomArrayList = new ArrayList<Model_Phannhom>();
        phannhomArrayList = dataBaseHelper.getTenNhomThu();

        if (phannhomArrayList.size() == 0){
            spinner_group.setVisibility(View.GONE);
        }else {
            spinnerAdapter = new Spinner_adapter(getActivity(), R.layout.spinner_row, phannhomArrayList);
            spinnerAdapter.setDropDownViewResource(R.layout.spinner_row);
            spinnerAdapter.updateIcon(false);
            //Group
            spinner_group.setAdapter(spinnerAdapter);
            spinner_group.getSelectedItemPosition();
            spinner_group.setOnItemSelectedListener(this);
        }

    }

    /**
     * Nếu Item Spinner Giao dịch get đc là khoản Chi
     **/
    public void setSpinnerChi() {
        //Adapter Group
        phannhomArrayList = new ArrayList<Model_Phannhom>();
        phannhomArrayList = dataBaseHelper.getTenNhomChi();

        if (phannhomArrayList.size() == 0){
            spinner_group.setVisibility(View.GONE);
        }else {
            spinnerAdapter = new Spinner_adapter(getActivity(), R.layout.spinner_row, phannhomArrayList);
            spinnerAdapter.setDropDownViewResource(R.layout.spinner_row);
            spinnerAdapter.updateIcon(true);
            //Group
            spinner_group.setAdapter(spinnerAdapter);
            spinner_group.getSelectedItemPosition();
            spinner_group.setOnItemSelectedListener(this);
        }
    }

    public void receiverIntentChi() {
        phannhomArrayList.clear();
        setSpinnerChi();
    }

    public void receiverIntentThu() {
        phannhomArrayList.clear();
        setSpinnerThu();
    }

    @Override
    public void onResume() {
        if (Constants.model_taikhoan != null) {
            Log.d("Model ID:", String.valueOf(Constants.model_taikhoan.get_id()));
            Log.d("Model Name:", String.valueOf(Constants.model_taikhoan.getTentk()));
            taikhoanArrayList.add(Constants.model_taikhoan);
            adapter_tk.notifyDataSetChanged();
            Constants.model_taikhoan = null;
        }

        if (Constants.model_phannhom_chi != null){
            Log.d("Ma nhom1: ", String.valueOf(Constants.model_phannhom_chi.getManhom()));
            Log.d("Ten nhom1: ", String.valueOf(Constants.model_phannhom_chi.getTennhom()));
            phannhomArrayList.add(Constants.model_phannhom_chi);
            spinnerAdapter.notifyDataSetChanged();
            Constants.model_phannhom_chi = null;
        }

        if (Constants.model_phannhom_thu != null){
            Log.d("Ma nhom2: ", String.valueOf(Constants.model_phannhom_thu.getManhom()));
            Log.d("Ten nhom2: ", String.valueOf(Constants.model_phannhom_thu.getTennhom()));
            phannhomArrayList.add(Constants.model_phannhom_chi);
            spinnerAdapter.notifyDataSetChanged();
            Constants.model_phannhom_thu = null;
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        try {
            getActivity().unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private int TimeOut(int milisecond) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.hideProgressBar();
                //Snackbar Done
                mes = "Thêm Giao Dịch Thành Công";
                Snackbar snackbar = Snackbar.make(rootView, mes, Snackbar.LENGTH_SHORT);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                sbView.setBackgroundColor(rootView.getResources().getColor(R.color.rectage_btn));
                textView.setTextColor(rootView.getResources().getColor(R.color.white));
                snackbar.show();

                //Get current Date Time
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                DateTime = dateFormat.format(date);

                tvDateTime.setText(DateTime);
            }
        }, milisecond);
        return milisecond;
    }
}
