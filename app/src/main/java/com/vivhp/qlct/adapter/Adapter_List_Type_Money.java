package com.vivhp.qlct.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivhp.qlct.Model.Model_List_Type_Money;
import com.vivhp.qlct.R;

import java.util.ArrayList;

/**
 * Created by vivhp on 10/26/2016.
 */

public class Adapter_List_Type_Money extends ArrayAdapter<Model_List_Type_Money> {


    Activity context;
    int Layout_Id;
    ArrayList<Model_List_Type_Money> arList = null;

    public Adapter_List_Type_Money(Activity context, int resource, ArrayList<Model_List_Type_Money> list) {
        super(context, resource, list);
        this.context = context;
        this.Layout_Id = resource;
        this.arList = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * position: Vị trí item trong list
         * convertView: lấy về các control của mỗi item
         * parent: datasource được truyền từ Activity
         **/

        //Check View if View == null
        if (convertView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            convertView = layoutInflater.inflate(Layout_Id, null);
        }

        //Định nghĩa các control của 1 item
        ImageView icon_type = (ImageView) convertView.findViewById(R.id.icon_type_money);
        TextView tv_name_type_money = (TextView) convertView.findViewById(R.id.name_type_money);

        //Gán giá trị
        tv_name_type_money.setText(String.valueOf(arList.get(position).getName()));


        //Gán iCon
        /**
         * Nếu Loại tiền là Tiền Mặt
         * - Gán Icon 1
         * Nếu Loại tiền là Tiền Thẻ
         * - Gán Icon 2
         **/
        if (tv_name_type_money.getText().toString() == "Thẻ Tín Dụng") {
            icon_type.setImageResource(R.mipmap.ic_t04);
        }
        if (tv_name_type_money.getText().toString() == "Tiền Mặt") {
            icon_type.setImageResource(R.mipmap.ic_t05);
        }

        return convertView;
    }
}
