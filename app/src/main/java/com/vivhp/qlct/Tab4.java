package com.vivhp.qlct;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vivhp.qlct.DBHelper.DataBaseHelper;
import com.vivhp.qlct.Model.Model_Taikhoan;
import com.vivhp.qlct.adapter.RecycleViewAdapterTaiKhoan;

import java.util.ArrayList;

/**
 * Created by vivhp on 10/16/2016.
 */

public class Tab4 extends android.support.v4.app.Fragment {

    BroadcastReceiver broadcastReceiver;
    RecyclerView lv_taikhoan;
    ArrayList<Model_Taikhoan> arrayList;
    DataBaseHelper dataBaseHelper;
    RecycleViewAdapterTaiKhoan adapterTaiKhoan;
    TextView tvTotalMoney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4_status, container, false);
        broadcast();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Khởi tạo db
        dataBaseHelper = new DataBaseHelper(getActivity());

        //TV Total Money
        tvTotalMoney = (TextView) view.findViewById(R.id.tv_total);
        //Hien so doi tien
        tvTotalMoney.addTextChangedListener(new HienDoiTien(tvTotalMoney));


        initSumMoney();
        initRecyclerview(view);
    }

    private void initRecyclerview(View view) {
        /**
         * LinearLayoutManager bắt buộc phải có
         * Khi sử dụng RecycleView vì nó hỗ trợ sắp
         * xếp các View theo hướng ngang hoặc dọc...**/
        lv_taikhoan = (RecyclerView) view.findViewById(R.id.lv_taikhoan);
        lv_taikhoan.setHasFixedSize(true);

        /**
         * Tham số chính:
         * - Context
         * - Hướng Chỉ định
         * - Nếu tham số này được set là true thì các item views sẽ hiển thị theo thứ tự ngược lại so với mảng các items được đưa vào trong adapter
         * **/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lv_taikhoan.setLayoutManager(linearLayoutManager);

        //Set Animation cho Item,
        lv_taikhoan.setItemAnimator(new DefaultItemAnimator());
        //Set Adapter cho RecycleView

        arrayList = dataBaseHelper.getAllTaiKhoan();
        Log.e("all_líst_first", "total " + arrayList.size());
        //Set ArrayList cho dapter
        adapterTaiKhoan = new RecycleViewAdapterTaiKhoan(arrayList);

        lv_taikhoan.setAdapter(adapterTaiKhoan);
    }

    private void initSumMoney() {
        //Tạo biến lưu tổng số tiền của Tất cả các tài khoản
        int total = dataBaseHelper.getTotalMoney();
        //Set Tổng số tiền các tài khoản ra TextView
        tvTotalMoney.setText(String.valueOf(total));
        tvTotalMoney.addTextChangedListener(new HienDoiTien(tvTotalMoney));

    }

    private void broadcast(){
        //Khai bao ve thuc hien lang nghe
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getExtras().getInt("a") == 2){
                    new LoadData().execute();
                }
                if (intent.getExtras().getInt("c") == 2) {
                    new LoadData().execute();
                }
            }
        };
        // đăng ký
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("2"));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //Xử lý theo tiến trình
    private class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            arrayList.clear();
            arrayList = dataBaseHelper.getAllTaiKhoan();
            Log.e("all_taikhoan", "total: " + arrayList.size());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterTaiKhoan.updateData(arrayList);
            initSumMoney();
        }
    }
}
