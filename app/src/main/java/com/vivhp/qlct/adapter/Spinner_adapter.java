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
 * Created by vivhp on 10/24/2016.
 */

public class Spinner_adapter extends ArrayAdapter<Model_Phannhom> {
    Activity context;
    int Layout_id;
    ImageView icon_sp;
    ArrayList<Model_Phannhom> list;

    private boolean chi = true;

    public Spinner_adapter(Activity context, int resource, ArrayList<Model_Phannhom> list) {
        super(context, resource, list);
        this.context = context;
        this.Layout_id = resource;
        this.list = list;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            row = layoutInflater.inflate(Layout_id, null);
        }
        //Lấy đối tượng ở vị trí yêu cầu
        Model_Phannhom list_item = list.get(position);

        TextView tv_id = (TextView) row.findViewById(R.id.id);
        TextView tv_name = (TextView) row.findViewById(R.id.name);
        icon_sp = (ImageView) row.findViewById(R.id.icon_sp);

        //Gán value cho control của item
        tv_id.setText(String.valueOf(list_item.getManhom()));
        tv_id.setVisibility(View.INVISIBLE);
        tv_name.setText(list_item.getTennhom());

        if (chi) {
            icon_sp.setImageResource(R.mipmap.ic_trending_down_black_24dp1);
            tv_name.setTextColor(row.getResources().getColor(R.color.color_red));
        } else {
            icon_sp.setImageResource(R.mipmap.ic_trending_up_black_24dp1);
            tv_name.setTextColor(row.getResources().getColor(R.color.child_hb));
        }

        return row;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * position: Vị trí item trong list
         * convertView: lấy về các control của mỗi item
         * parent: datasource được truyền từ Activity
         * **/

        View row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            row = layoutInflater.inflate(Layout_id, null);
        }
        //Lấy đối tượng ở vị trí yêu cầu
        Model_Phannhom list_item = list.get(position);

        TextView tv_id = (TextView) row.findViewById(R.id.id);
        TextView tv_name = (TextView) row.findViewById(R.id.name);
        icon_sp = (ImageView) row.findViewById(R.id.icon_sp);
        //Gán value cho control của item
        tv_id.setText(String.valueOf(list_item.getManhom()));
        tv_id.setVisibility(View.INVISIBLE);
        tv_name.setText(list_item.getTennhom());

        if (chi) {
            icon_sp.setImageResource(R.mipmap.ic_trending_down_black_24dp1);
            tv_name.setTextColor(row.getResources().getColor(R.color.color_red));
        } else {
            icon_sp.setImageResource(R.mipmap.ic_trending_up_black_24dp1);
            tv_name.setTextColor(row.getResources().getColor(R.color.child_hb));
        }

        return row;
    }

    public void updateIcon(boolean chi) {
        this.chi = chi;
        notifyDataSetChanged();
    }
}
