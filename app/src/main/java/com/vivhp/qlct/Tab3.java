package com.vivhp.qlct;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vivhp.qlct.DBHelper.DataBaseHelper;
import com.vivhp.qlct.Model.Model_Phannhom;
import com.vivhp.qlct.adapter.List_Phannhom_Adapter;
import com.vivhp.qlct.dialog.DialogProgressBar;

import java.util.ArrayList;

/**
 * Created by vivhp on 10/16/2016.
 */

public class Tab3 extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener {
    View rootView;

    String tennhom, tenkhoan, query_type;
    Spinner spinner_transaction_group;
    EditText input_group;
    ArrayAdapter<String> Adapter_transaction, Adapter_query;
    ArrayList<Model_Phannhom> phannhomArrayList;
    List_Phannhom_Adapter phannhom_adapter;
    DataBaseHelper dataBaseHelper;
    Snackbar snackbar;
    ListView lvGroup;
    Spinner spinner_group1;
    ImageButton btnAdd;
    DialogProgressBar progressBar;
    int intBoolean;
    int value_receiver;
    int manhom;
    String tennhom_dialog;
    private String[] loaik = {"Khoản Chi", "Khoản Thu"};
    private String[] query = {"Tất cả", "Khoản Chi", "Khoản Thu"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.tab3, container,
                false);

        dataBaseHelper = new DataBaseHelper(getActivity());

        /**
         * Initialization View Control
         * - MaterialBetterSpinner
         * - Spinner
         * - ListView
         * - EditText
         * - ImageButton**/
        lvGroup = (ListView) rootView.findViewById(R.id.lv_Group);
        btnAdd = (ImageButton) rootView.findViewById(R.id.btnAdd);
        input_group = (EditText) rootView.findViewById(R.id.input_group);
        //Initialization Spinner Library
        spinner_group1 = (Spinner) rootView.findViewById(R.id.spinner_group1);
        spinner_transaction_group = (Spinner) rootView.findViewById(R.id.spinner_transaction_group);
        /**
         * Hàm Thực Thi
         * **/
        //Set Hiển Thi cho LítView
        setListView();
        //SetData cho spinner
        setDataSpinnerLK();
        //ImageButton Click
        imgBtnCliked();
        updateNhom();
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /**
         * Nếu có 2 spinner trong một activity:
         * - Số lượng phần tử của mỗi một spinner khác nhau thì cần
         *   ckeck xem spinner nào đang chọn item**/
        if (parent.getId() == R.id.spinner_group1) {
            query_type = Adapter_query.getItem(position).toString();


            if (query_type == "Khoản Chi") {
                //Gọi Set listview Khoản Chi
                setListViewChi();
                spinner_transaction_group.setSelection(1);
            }
            if (query_type == "Khoản Thu") {
                //Gọi Set listview Khoản Thu
                setListViewThu();
                spinner_transaction_group.setSelection(0);
            }
            if (query_type == "Tất cả") {
                setListView();
            }


        }
        if (parent.getId() == R.id.spinner_transaction_group) {
            tenkhoan = Adapter_transaction.getItem(position).toString();
            if (tenkhoan == "Khoản Chi") {
                intBoolean = 1;
            }
            if (tenkhoan == "Khoản Thu") {
                intBoolean = 2;
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * Hàm dùng để khai báo trong onCreateView
     * ******************************
     * **/

    /**
     * Set ListView Hiển thị bảng phân nhóm
     **/
    public void setListView() {
        //Initialization Adapter to ListView
        phannhomArrayList = new ArrayList<Model_Phannhom>();
        phannhomArrayList = dataBaseHelper.getAllNhoms();
        phannhom_adapter = new List_Phannhom_Adapter(getActivity(), R.layout.list_item_layout, phannhomArrayList);

        //Set Adapter to ListView
        lvGroup.setAdapter(phannhom_adapter);
        phannhom_adapter.notifyDataSetChanged();


    }

    public void setListViewThu() {
        //Initialization Adapter to ListView
        phannhomArrayList = new ArrayList<Model_Phannhom>();
        phannhomArrayList = dataBaseHelper.getKhoanThu();
        phannhom_adapter = new List_Phannhom_Adapter(getActivity(), R.layout.list_item_layout, phannhomArrayList);

        //Set Adapter to ListView
        lvGroup.setAdapter(phannhom_adapter);
        phannhom_adapter.notifyDataSetChanged();
    }

    public void setListViewChi() {
        //Initialization Adapter to ListView
        phannhomArrayList = new ArrayList<Model_Phannhom>();
        phannhomArrayList = dataBaseHelper.getKhoanChi();
        phannhom_adapter = new List_Phannhom_Adapter(getActivity(), R.layout.list_item_layout, phannhomArrayList);

        //Set Adapter to ListView
        lvGroup.setAdapter(phannhom_adapter);
        phannhom_adapter.notifyDataSetChanged();
    }

    /**
     * Set Data cho spinner chọn nhóm
     **/
    public void setDataSpinnerLK() {
        //Initialization Adapter to spinner

        //Query
        Adapter_query = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, query);
        Adapter_query.setDropDownViewResource(R.layout.spinner_item);
        spinner_group1.getSelectedItemPosition();

        //Loại Khoản
        Adapter_transaction = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, loaik);
        Adapter_transaction.setDropDownViewResource(R.layout.spinner_item);
        spinner_transaction_group.getSelectedItemPosition();

        //Set Adapter to Spinner
        spinner_group1.setAdapter(Adapter_query);
        spinner_group1.setOnItemSelectedListener(this);
        spinner_transaction_group.setAdapter(Adapter_transaction);
        spinner_transaction_group.setOnItemSelectedListener(this);


    }

    /**
     * Sự kiện click Image button
     **/
    public void imgBtnCliked() {
        /**
         * Sự kiện click button thêm nhóm**/
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intBoolean == 1){
                    addGroupC();
                }
                if (intBoolean == 2){
                    addGroupT();
                }
                setListView();
                spinner_group1.setSelection(0);
            }
        });
    }

    public void updateNhom() {
        lvGroup.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                manhom = phannhomArrayList.get(position).getManhom();
                tennhom_dialog = phannhomArrayList.get(position).getTennhom();
                showDialog();
                return false;
            }
        });
    }

    /**
     * Hàm không khai báo trong onCreateView
     * ******************************
     * **/


    /**
     * Sự kiên thêm value cua nhóm vào database
     **/
//    public void addGroup() {
//        if (TextUtils.isEmpty(tennhom = input_group.getText().toString())) {
//            addGroupErr();
//        } else {
//            Model_Phannhom phannhom = new Model_Phannhom(tennhom, tenkhoan);
//            if (dataBaseHelper.addNhom(phannhom)){
//                Constants.model_phannhom_chi = dataBaseHelper.getLastTenNhomChi();
//                Constants.model_phannhom_thu = dataBaseHelper.getLastTenNhomThu();
//            }
//
//            addGroupSucc();
//            if (spinner_transaction_group.getSelectedItemPosition() == 0) {
//                spinner_group1.setSelection(1);
//            } else if (spinner_transaction_group.getSelectedItemPosition() == 1) {
//                spinner_group1.setSelection(2);
//            }
//        }
//
//        input_group.setText(null);
//    }

    public void addGroupT() {
        if (TextUtils.isEmpty(tennhom = input_group.getText().toString())) {
            addGroupErr();
        } else {
            Model_Phannhom phannhom = new Model_Phannhom(tennhom, tenkhoan);
            if (dataBaseHelper.addNhom(phannhom)){
                Constants.model_phannhom_thu = dataBaseHelper.getLastTenNhomThu();
                Log.d("Model: ", Constants.model_phannhom_thu.getTennhom());

                Intent intent = new Intent("p");
                intent.putExtra("p", phannhom);
                intent.putExtra("b", 2);
                getActivity().sendBroadcast(intent);
            }

            addGroupSucc();
            if (spinner_transaction_group.getSelectedItemPosition() == 0) {
                spinner_group1.setSelection(1);
            } else if (spinner_transaction_group.getSelectedItemPosition() == 1) {
                spinner_group1.setSelection(2);
            }
        }

        input_group.setText(null);
    }

    public void addGroupC() {
        if (TextUtils.isEmpty(tennhom = input_group.getText().toString())) {
            addGroupErr();
        } else {
            Model_Phannhom phannhom = new Model_Phannhom(tennhom, tenkhoan);
            if (dataBaseHelper.addNhom(phannhom)){
                Constants.model_phannhom_chi = dataBaseHelper.getLastTenNhomChi();
                Log.d("Model: ", Constants.model_phannhom_chi.getTennhom());

                Intent intent = new Intent("p");
                intent.putExtra("p", phannhom);
                intent.putExtra("b", 1);
                getActivity().sendBroadcast(intent);
            }

            addGroupSucc();
            if (spinner_transaction_group.getSelectedItemPosition() == 0) {
                spinner_group1.setSelection(1);
            } else if (spinner_transaction_group.getSelectedItemPosition() == 1) {
                spinner_group1.setSelection(2);
            }
        }

        input_group.setText(null);
    }




    /**
     * Sự kiên thêm value cua nhóm Thu vào database
     **/
//    public void addGroupThu() {
//        spinner_transaction_group.setSelection(0);
//        if (TextUtils.isEmpty(tennhom = input_group.getText().toString())) {
//            addGroupErr();
//        } else {
//            dataBaseHelper.addNhom(new Model_Phannhom(tennhom, tenkhoan));
//            addGroupSucc();
//            setListViewThu();
//        }
//        spinner_group1.setSelection(1);
//        input_group.setText(null);
//    }

    /**
     * Sự kiên thêm value cua nhóm Thu vào database
     **/
//    public void addGroupChi() {
//        spinner_transaction_group.setSelection(1);
//        if (TextUtils.isEmpty(tennhom = input_group.getText().toString())) {
//            addGroupErr();
//        } else {
//            dataBaseHelper.addNhom(new Model_Phannhom(tennhom, tenkhoan));
//            addGroupSucc();
//        }
//        spinner_group1.setSelection(1);
//        input_group.setText(null);
//    }

    /**
     * Sự kiện thêm nhóm bị lỗi
     **/
    public void addGroupErr() {
        snackbar = Snackbar.make(rootView, R.string.mes_add_group_err, Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();

            }
        }).setActionTextColor(rootView.getResources().getColor(R.color.colorAccent)).setDuration(3000);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(rootView.getResources().getColor(R.color.rectage_btn));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(rootView.getResources().getColor(R.color.white));
        snackbar.show();
    }

    /**
     * Sự kiện thêm Nhóm thành công
     **/
    public void addGroupSucc() {
        snackbar = Snackbar.make(rootView, "Đã thêm nhóm: " + tennhom + "!", Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.done, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();

            }
        }).setActionTextColor(rootView.getResources().getColor(R.color.colorAccent)).setDuration(3000);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(rootView.getResources().getColor(R.color.rectage_btn));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(rootView.getResources().getColor(R.color.white));
        snackbar.show();
    }

    /**
     * Sự kiện Sửa nhóm theo Mã Nhóm
     **/

    public void showDialog() {
        LayoutInflater layoutInflater = getLayoutInflater(getArguments());
        View alertLayout = layoutInflater.inflate(R.layout.custom_dialog, null);
        final RadioButton rdo_chi = (RadioButton) alertLayout.findViewById(R.id.radio_chi);
        final RadioButton rdo_thu = (RadioButton) alertLayout.findViewById(R.id.radio_thu);
        final RadioGroup radioGroup = (RadioGroup) alertLayout.findViewById(R.id.radio_group);
        final EditText edt_nhom = (EditText) alertLayout.findViewById(R.id.edt_nhom);
        edt_nhom.setText(tennhom_dialog);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(alertLayout);
        dialog.setCancelable(false);
        dialog.setTitle("Cập nhật nhóm");
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Hủy bỏ cập nhật!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBar = new DialogProgressBar(getContext(), false, false, null, getString(R.string.saving));
                progressBar.showProgressBar();
                final String st_khoan, st_nhom;
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.radio_chi) {
                    st_khoan = rdo_chi.getText().toString();
                    value_receiver = 1;
                } else {
                    st_khoan = rdo_thu.getText().toString();
                    value_receiver = 2;
                }
                st_nhom = edt_nhom.getText().toString();

                if (st_nhom != null && st_khoan != null) {
                    updateData(st_khoan, st_nhom);
                } else {
                    Toast.makeText(getActivity(), "Ten nhóm: " + st_nhom + ", ten khoan: " + st_khoan, Toast.LENGTH_SHORT).show();
                }

            }

            private void updateData(final String st_khoan, final String st_nhom) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataBaseHelper.updateNhom(manhom, st_nhom, st_khoan);
                        progressBar.hideProgressBar();
                        Toast.makeText(getActivity(), "Đã cập nhật!", Toast.LENGTH_SHORT).show();
                        setListView();
                        if (value_receiver == 1) {
                            Intent intent = new Intent("2");
                            intent.putExtra("b", 3);
                            getActivity().sendBroadcast(intent);
                        }
                        if (value_receiver == 2) {
                            Intent intent = new Intent("2");
                            intent.putExtra("b", 4);
                            getActivity().sendBroadcast(intent);
                        }
                    }
                }, 1000);
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

}
