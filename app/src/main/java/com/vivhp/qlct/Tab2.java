package com.vivhp.qlct;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vivhp.qlct.DBHelper.DataBaseHelper;
import com.vivhp.qlct.Model.Model_Thongke;
import com.vivhp.qlct.adapter.AdapterListTab2;
import com.vivhp.qlct.dialog.MonthYearPicker;
import com.vivhp.qlct.dialog.YearPicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vivhp on 10/16/2016.
 */

public class Tab2 extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener{

    View rootView;

    BroadcastReceiver broadcastReceiver;
    String TAG = "Tab 2";
    ListView lv_group_t2;
    ArrayList<Model_Thongke> arrModel;
    Spinner spinner_date;
    private String[] arr_date = {"Ngày", "Tháng", "Năm"};
    DataBaseHelper helper;
    String DateTime;
    Button btn_thu, btn_chi;
    TextView tvSetDateT2, DateTimeT2, tvType;
    ArrayAdapter<String> sp_adapter;
    String Dateqr, time_arg;
    AdapterListTab2 adapterListTab2;
    MonthYearPicker monthYearPicker;
    YearPicker yearPicker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab2, container, false);



        spinner_date = (Spinner) rootView.findViewById(R.id.spinner_date);

        helper = new DataBaseHelper(getActivity());

        initView();
        broadcast();
        btnChiSetClick();
        btnThuSetClick();
//        getTimeHienTai();

        return rootView;
    }

    public void initView(){
        tvType = (TextView) rootView.findViewById(R.id.tvType);
        tvSetDateT2 = (TextView) rootView.findViewById(R.id.SetDateT2);
        DateTimeT2 = (TextView) rootView.findViewById(R.id.DateTimeT2);
        lv_group_t2 = (ListView) rootView.findViewById(R.id.lv_tab2);
        btn_thu = (Button) rootView.findViewById(R.id.btn_thu);
        btn_chi = (Button) rootView.findViewById(R.id.btn_chi);

        arrModel = new ArrayList<>();
        adapterListTab2 = new AdapterListTab2(getActivity(), R.layout.list_item_tab2, arrModel);
        lv_group_t2.setAdapter(adapterListTab2);

        initSpinner();
    }

    public void initSpinner(){
        sp_adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, arr_date);
        sp_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_date.setAdapter(sp_adapter);
        spinner_date.getSelectedItemPosition();
        spinner_date.setOnItemSelectedListener(this);
        initDataListChi();
    }

    public void getTimeHienTai() {
        //Get current Date Time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        DateTime = dateFormat.format(date);

        DateTimeT2.setText(DateTime);
        tvSetDateT2.setTextColor(rootView.getResources().getColor(R.color.white));
        tvSetDateT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
//        Toast.makeText(getActivity(), DateTime, Toast.LENGTH_SHORT).show();
    }

    private void setTime() {
        final Calendar calendar = Calendar.getInstance();

        tvSetDateT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String resultDate = String.valueOf(year + "/" + (month + 1) + "/" + dayOfMonth);
                        DateTimeT2.setText(resultDate);
                        initDataListThuDate();
                        initDataListChiDate();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });
    }


    public void getMonth(){
    //Get current Date Time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        Date date = new Date();
        DateTime = dateFormat.format(date);
        DateTimeT2.setText(DateTime);
        tvSetDateT2.setTextColor(rootView.getResources().getColor(R.color.white));
        tvSetDateT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMonth();
            }
        });
//        Toast.makeText(getActivity(), DateTime, Toast.LENGTH_SHORT).show();
    }

    public void setMonth(){

        tvSetDateT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monthYearPicker = new MonthYearPicker(getActivity());
                monthYearPicker.build(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String month = String.valueOf(monthYearPicker.getSelectedMonthShortName());
                        String year = String.valueOf(monthYearPicker.getSelectedYear());
                        String resultDate = year + "/" + month;
                        DateTimeT2.setText(resultDate);
                        initDataListThu();
                        initDataListChi();
                    }
                }, null);
                showMonthPicker(getView());
            }
        });
    }

    public void getYear(){
        //Get current Date Time
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        DateTime = dateFormat.format(date);
        DateTimeT2.setText(DateTime);
        tvSetDateT2.setTextColor(rootView.getResources().getColor(R.color.white));
        tvSetDateT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setYear();
            }
        });
//        Toast.makeText(getActivity(), DateTime, Toast.LENGTH_SHORT).show();
    }

    public void setYear(){
        final Calendar calendar = Calendar.getInstance();

        tvSetDateT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearPicker = new YearPicker(getActivity());
                yearPicker.build(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String year = String.valueOf(yearPicker.getSelectedYear());
                        String resultDate = year;
                        DateTimeT2.setText(resultDate);
                        initDataListThu();
                        initDataListChi();
                    }
                }, null);
                showYearPicker(getView());
            }
        });
    }

    private void btnChiSetClick() {
        btn_chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getString(R.string.total_chi), Toast.LENGTH_SHORT).show();
                initDataListChi();
            }
        });
    }

    private void btnThuSetClick() {
        btn_thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getString(R.string.total_thu), Toast.LENGTH_SHORT).show();
                initDataListThu();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner_date){
            Dateqr = sp_adapter.getItem(position).toString();
            if (Dateqr == "Ngày"){
                tvType.setText("Ngày: ");
                tvSetDateT2.setText(rootView.getResources().getString(R.string.set_date));
                getTimeHienTai();
                time_arg = DateTimeT2.getText().toString();
                Toast.makeText(getActivity(),"Date: " + time_arg, Toast.LENGTH_SHORT).show();
                initDataListThu();
                initDataListChi();
            } if (Dateqr == "Tháng"){
                tvType.setText("Tháng: ");
                tvSetDateT2.setText(rootView.getResources().getString(R.string.set_month));
                getMonth();
                time_arg = DateTimeT2.getText().toString();
                Toast.makeText(getActivity(),"Month: " + time_arg, Toast.LENGTH_SHORT).show();
                initDataListThu();
                initDataListChi();
            } if (Dateqr == "Năm"){
                tvType.setText("Năm: ");
                tvSetDateT2.setText(rootView.getResources().getString(R.string.set_year));
                getYear();
                time_arg = DateTimeT2.getText().toString();
                Toast.makeText(getActivity(),"Year: " + time_arg, Toast.LENGTH_SHORT).show();
                initDataListThu();
                initDataListChi();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initDataListChi() {
        arrModel.clear();
        arrModel = helper.getGroupChi(DateTimeT2.getText().toString());

        for (int i = 0; i < arrModel.size(); i++) {
            Log.e("cv_", "null " + arrModel.get(i).getSoTien());
        }

        adapterListTab2 = new AdapterListTab2(getActivity(), R.layout.list_item_tab2, arrModel);
        lv_group_t2.setAdapter(adapterListTab2);
        adapterListTab2.updateKhoan(true);
//        adapterListTab2.notifyDataSetChanged();
    }

    private void initDataListThu() {
        arrModel.clear();
        arrModel = helper.getGroupThu(DateTimeT2.getText().toString());
        adapterListTab2 = new AdapterListTab2(getActivity(), R.layout.list_item_tab2, arrModel);
        lv_group_t2.setAdapter(adapterListTab2);
        adapterListTab2.updateKhoan(false);
//        adapterListTab2.notifyDataSetChanged();
    }

    private void initDataListChiDate() {
        arrModel.clear();
        arrModel = helper.getGroupChiNgay(DateTimeT2.getText().toString());
        adapterListTab2 = new AdapterListTab2(getActivity(), R.layout.list_item_tab2, arrModel);
        lv_group_t2.setAdapter(adapterListTab2);
        adapterListTab2.updateKhoan(true);
    }

    private void initDataListThuDate() {
        arrModel.clear();
        arrModel = helper.getGroupThuNgay(DateTimeT2.getText().toString());
        adapterListTab2 = new AdapterListTab2(getActivity(), R.layout.list_item_tab2, arrModel);
        lv_group_t2.setAdapter(adapterListTab2);
        adapterListTab2.updateKhoan(false);
    }

    private void broadcast(){
        //Khai bao ve thuc hien lang nghe
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getExtras().getInt("a") == 2){
                    initDataListThu();
                    initDataListChi();
                }
                if (intent.getExtras().getInt("b") == 3) {
                    initDataListChi();
                    Toast.makeText(context, "Lọc: " + btn_chi.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                if (intent.getExtras().getInt("b") == 4) {
                    initDataListThu();
                    Toast.makeText(context, "Lọc: " + btn_thu.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        // đăng ký
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("2"));
    }

    public void showMonthPicker(View view) {
        monthYearPicker.show();
    }

    public void showYearPicker(View view) {
        yearPicker.show();
    }
}
