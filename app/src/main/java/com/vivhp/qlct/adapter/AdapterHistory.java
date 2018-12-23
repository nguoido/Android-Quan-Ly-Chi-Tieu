package com.vivhp.qlct.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vivhp.qlct.HistoryFragment;

import java.util.ArrayList;

/**
 * Created by vivhp on 10/29/2016.
 */

public class AdapterHistory extends FragmentPagerAdapter {

    ArrayList<String> arr;

    //Constructor
    public AdapterHistory(FragmentManager fm, ArrayList<String> arr) {
        super(fm);
        this.arr = arr;
    }

    //Get Item
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new HistoryFragment(arr.get(position));
        return fragment;
    }

    //Get So Luong
    @Override
    public int getCount() {
        return arr.size();
    }

    //Get Title
    @Override
    public CharSequence getPageTitle(int position) {
        return arr.get(position);
    }
}
