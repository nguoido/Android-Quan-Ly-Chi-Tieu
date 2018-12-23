package com.vivhp.qlct.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivhp.qlct.HienDoiTien;
import com.vivhp.qlct.Model.Model_Thongke;
import com.vivhp.qlct.R;

import java.util.ArrayList;

/**
 * Created by vivhp on 11/18/2016.
 */

public class AdapterListTab2 extends ArrayAdapter<Model_Thongke> {
    Activity activity;
    int LayoutId;
    ArrayList<Model_Thongke> arr;
    private boolean chi = true;

    public AdapterListTab2(Activity context, int resource, ArrayList<Model_Thongke> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.LayoutId = resource;
        this.arr = objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * position: Vị trí item trong list
         * convertView: lấy về các control của mỗi item
         * parent: datasource được truyền từ Activity
         * **/

        //Check convert view == null
        if (convertView == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            convertView = layoutInflater.inflate(LayoutId, null);
        }

        Model_Thongke thongke = arr.get(position);

        //Định nghĩa các control của 1 item
        ImageView icon = (ImageView) convertView.findViewById(R.id.img_item);
        TextView tv_nhom = (TextView) convertView.findViewById(R.id.tv_t2_tennhom);
        TextView tv_money = (TextView) convertView.findViewById(R.id.tv_t2_sotien);
        tv_money.addTextChangedListener(new HienDoiTien(tv_money));

        if (chi)
            icon.setImageResource(R.mipmap.ic_trending_down_black_24dp1);
        else
            icon.setImageResource(R.mipmap.ic_trending_up_black_24dp1);

        //Gán value cho control của item
        tv_nhom.setText(thongke.getTenNhom());
        tv_money.setText(String.valueOf(thongke.getSoTien()));

        return convertView;
    }

    public void updateKhoan(boolean chi) {
        this.chi = chi;
        notifyDataSetChanged();
    }
}
