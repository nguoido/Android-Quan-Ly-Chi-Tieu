package com.vivhp.qlct.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivhp.qlct.Model.Model_Taikhoan;
import com.vivhp.qlct.R;

import java.util.ArrayList;

/**
 * Created by vivhp on 10/26/2016.
 */

public class Spinner_Tk_Adapter extends ArrayAdapter<Model_Taikhoan> {
    Activity activity;
    int LayoutId;
    ImageView img_icon;
    ArrayList<Model_Taikhoan> arrayList;
    private boolean tienmat = true;

    public Spinner_Tk_Adapter(Activity context, int resource, ArrayList<Model_Taikhoan> list) {
        super(context, resource, list);
        this.activity = context;
        this.LayoutId = resource;
        this.arrayList = list;
    }

    /**
     * Set layout khi Spinner Xổ xuống
     **/
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        /**
         * position: Vị trí item trong list
         * convertView: lấy về các control của mỗi item
         * parent: datasource được truyền từ Activity
         * **/
        View rootView = convertView;

        //Checl rootView xem co = null khong
        if (rootView == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            rootView = layoutInflater.inflate(LayoutId, null);
        }
        //Lấy đối tượng ở vị trí yêu cầu
        Model_Taikhoan taikhoanList1 = arrayList.get(position);

        //Dinh nghia cac View item
        TextView tvid = (TextView) rootView.findViewById(R.id.acc_id);
        TextView tvname = (TextView) rootView.findViewById(R.id.name_tk);
        TextView tvtype = (TextView) rootView.findViewById(R.id.type_tk);
        img_icon = (ImageView) rootView.findViewById(R.id.img_icon_tk);

        //Gan gia tri cho View Item
        tvid.setText(String.valueOf(taikhoanList1.get_id()));
        tvname.setText(taikhoanList1.getTentk());
        tvtype.setText(taikhoanList1.getLoaihinh());
        tvtype.setVisibility(View.INVISIBLE);
        tvtype.setText(arrayList.get(position).getLoaihinh().toString());

        if (tvtype.getText().toString().equals("Tiền Mặt")) {
            tienmat = true;
        } else
            tienmat = false;

        //Set icon cho spinner khi dropDown

        if (tienmat)
            img_icon.setImageResource(R.mipmap.ic_t05);
        else
            img_icon.setImageResource(R.mipmap.ic_t04);


        return rootView;
    }

    /**
     * Set layout khi Spinner bình thường
     **/
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * position: Vị trí item trong list
         * convertView: lấy về các control của mỗi item
         * parent: datasource được truyền từ Activity
         * **/
        View rootView = convertView;

        //Checl rootView xem co = null khong
        if (rootView == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            rootView = layoutInflater.inflate(LayoutId, null);
        }

        Model_Taikhoan taikhoanList1 = arrayList.get(position);

        //Dinh nghia cac View item
        TextView tvid1 = (TextView) rootView.findViewById(R.id.acc_id);
        TextView tvname1 = (TextView) rootView.findViewById(R.id.name_tk);
        TextView tvtype1 = (TextView) rootView.findViewById(R.id.type_tk);
        //Set icon cho spinner khi view
        img_icon = (ImageView) rootView.findViewById(R.id.img_icon_tk);

        //Gan gia tri cho View Item
        tvid1.setText(String.valueOf(taikhoanList1.get_id()));
        tvname1.setText(taikhoanList1.getTentk());
        tvtype1.setText(taikhoanList1.getLoaihinh());
        tvtype1.setVisibility(View.INVISIBLE);

        if (tvtype1.getText().toString().equals("Tiền Mặt")) {
            img_icon.setImageResource(R.mipmap.ic_t05);
        } else if (tvtype1.getText().toString().equals("Thẻ Tín Dụng")) {
            img_icon.setImageResource(R.mipmap.ic_t04);
        }

        return rootView;
    }


    public void updateIcon(boolean tienmat) {
        this.tienmat = tienmat;
        notifyDataSetChanged();
    }
}
