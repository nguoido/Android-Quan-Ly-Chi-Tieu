package com.vivhp.qlct.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivhp.qlct.Model.Model_Phannhom;
import com.vivhp.qlct.R;

import java.util.ArrayList;

/**
 * Created by vivhp on 10/22/2016.
 */

public class Phannhom_Adapter extends ArrayAdapter<Model_Phannhom> {

    Activity context = null;
    int Layout_id;
    ArrayList<Model_Phannhom> arrayList = null;

    public Phannhom_Adapter(Activity context, int resource, ArrayList<Model_Phannhom> list) {
        super(context, resource, list);
        this.context = context;
        this.Layout_id = resource;
        this.arrayList = list;
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
            LayoutInflater layoutInflater = context.getLayoutInflater();
            convertView = layoutInflater.inflate(Layout_id, null);
        }

        //Lấy đối tượng ở vị trí yêu cầu
        Model_Phannhom phannhom = arrayList.get(position);

        //Định nghĩa các control của 1 item
        ImageView icon = (ImageView) convertView.findViewById(R.id.img_item);
        TextView tv_nhom = (TextView) convertView.findViewById(R.id.tv_tennhom);
        TextView tv_khoan = (TextView) convertView.findViewById(R.id.tv_tenkhoan);

        //Gán value cho control của item
        tv_nhom.setText(phannhom.getTennhom());
        tv_khoan.setText(phannhom.getTenkhoan());

        //Get resource icon
        if (tv_khoan.getText().toString().equals("Khoản Thu")) {
            icon.setImageResource(R.mipmap.ic_trending_up_black_24dp1);
        } else if (tv_khoan.getText().toString().equals("Khoản Chi")) {
            icon.setImageResource(R.mipmap.ic_trending_down_black_24dp1);
        }

        return convertView;
    }
}
