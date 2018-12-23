package com.vivhp.qlct.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vivhp.qlct.DoiTienLS;
import com.vivhp.qlct.Model.ModelHistory;
import com.vivhp.qlct.R;

import java.util.ArrayList;

/**
 * Created by vivhp on 10/29/2016.
 */

public class AdapterHistoryItem extends ArrayAdapter<ModelHistory> {

    Activity context = null;
    int LayoutId;
    ArrayList<ModelHistory> arrayList;
    TextView txtTenKhoan, txtSotien, txtLydo, txtTenTK, txtTenNhom;
    private boolean chi = false;
    public AdapterHistoryItem(Activity context, int resource, ArrayList<ModelHistory> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.LayoutId = resource;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check convert view == null
        if (convertView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            convertView = layoutInflater.inflate(LayoutId, null);
            if (arrayList.size() > 0) {
                //Lay vi tri hien tai cua item
                ModelHistory history = arrayList.get(position);

                txtTenNhom = (TextView) convertView.findViewById(R.id.txtTennhom);
                txtTenKhoan = (TextView) convertView.findViewById(R.id.txtTenKhoan);



                txtSotien = (TextView) convertView.findViewById(R.id.txtMoney);
                txtSotien.addTextChangedListener(new DoiTienLS(txtSotien));



                txtLydo = (TextView) convertView.findViewById(R.id.txtLydo);
                txtTenTK = (TextView) convertView.findViewById(R.id.txtTenTK);

                //Gán Giá trị cho các View
                txtTenNhom.setText(history.getTenkhoan());
                txtTenKhoan.setText(history.getTennhom());
                txtSotien.setText(String.valueOf(history.getSotien()));
                txtLydo.setText(history.getLydo());
                txtTenTK.setText(history.getTentk());
                /**
                 * Check xem trạng thái chi hay thu để đổi màu cho textView
                 * Thu: màu xanh
                 * Chi nợ: màu đỏ
                 **/

                if (txtTenKhoan.getText().toString().equals("Khoản Chi")) {
                    chi = true;
                } else
                    chi = false;

                if (chi) {
                    txtTenKhoan.setTextColor(convertView.getResources().getColor(R.color.color_chi));

                    //Ông thực hiện sửa cái dòng này
                    txtSotien.setText(" - " + String.valueOf(history.getSotien()));
                    //history.getSotien() =  history.getSotien().replace("-.", "-");

                } else
                    txtTenKhoan.setTextColor(convertView.getResources().getColor(R.color.colorPrimary));


            } else {
            }
        }
        return convertView;
    }
}
