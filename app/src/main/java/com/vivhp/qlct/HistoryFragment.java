package com.vivhp.qlct;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vivhp.qlct.DBHelper.DataBaseHelper;
import com.vivhp.qlct.Model.ModelHistory;
import com.vivhp.qlct.adapter.AdapterHistoryItem;

import java.util.ArrayList;

/**
 * Created by vivhp on 10/29/2016.
 */

@SuppressLint("ValidFragment")
public class HistoryFragment extends Fragment {

    String ngay;
    DataBaseHelper dataBaseHelper;
    ArrayList<ModelHistory> historyArrayList;
    ListView lv_history;
    public Activity activity;
    AdapterHistoryItem adapterHistoryItem;


    public HistoryFragment(String ngay) {
        this.ngay = ngay;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, null, false);

        dataBaseHelper = new DataBaseHelper(getActivity());
        lv_history = (ListView) view.findViewById(R.id.lvThongtin);
        getThuChi();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        historyArrayList = new ArrayList<ModelHistory>();
        historyArrayList= dataBaseHelper.getThuChi(ngay);
        adapterHistoryItem = new AdapterHistoryItem(getActivity(), R.layout.history_item, historyArrayList);
        lv_history.setAdapter(adapterHistoryItem);
        adapterHistoryItem.notifyDataSetChanged();

    }

    //Get Thu Chi LOG
    public void getThuChi() {
        historyArrayList = new ArrayList<ModelHistory>();
        historyArrayList = dataBaseHelper.getThuChi(ngay);
        Log.d("Time: ", ngay);
        for (ModelHistory history : historyArrayList) {
            String log = "So tien: " + history.getSotien()
                    + " Ten Khoan: " + history.getTenkhoan()
                    + " Ly do: " + history.getLydo()

                    + " Ten TK: " + history.getTentk();
            Log.d("Inner: ", log);
        }
        Log.d("Get All: ", "...");
    }
}
