package com.vivhp.qlct;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vivhp.qlct.DBHelper.DataBaseHelper;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //This is our tab layout
    TabLayout tabLayout;
    //This is our viewPage
    ViewPager viewPager;
    //This is Toolbar
    Toolbar toolbar;
    //Broadcast
    BroadcastReceiver broadcastReceiver;
    DataBaseHelper dataBaseHelper;

    int currentPage;

    private int[] tabIcons = {
            R.mipmap.ic_mode_edit_white_24dp,
            R.mipmap.ic_content_paste_white_24dp,
            R.mipmap.ic_supervisor_account_white_24dp,
            R.mipmap.ic_more_white_24dp
    };
    private int[] tabIconsSelected = {
            R.mipmap.ic_mode_edit_white_24dp,
            R.mipmap.ic_content_paste_white_24dp,
            R.mipmap.ic_supervisor_account_white_24dp,
            R.mipmap.ic_more_white_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.Tab1);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        setUpViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);

        //Initializing the tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setupTabIcons();
        broadcast();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPage = tab.getPosition();

                if (currentPage == 0) {
                    toolbar.setTitle(R.string.Tab1);
                }
                if (currentPage == 1) {
                    toolbar.setTitle(R.string.Tab2);
                }
                if (currentPage == 2) {
                    toolbar.setTitle(R.string.Tab3);
                }
                if (currentPage == 3) {
                    toolbar.setTitle(R.string.Tab4);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    void setUpViewPager(ViewPager viewPager) {
        //Creating our pager Adapter
        pageAdapter adapter = new pageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1(), "");
        adapter.addFragment(new Tab2(), "");
        adapter.addFragment(new Tab3(), "");
        adapter.addFragment(new Tab4(), "");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIconsSelected[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void broadcast() {
        //Khai bao ve thuc hien lang nghe
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getExtras().getInt("c") == 2) {
                    viewPager.setCurrentItem(3);
                }
            }
        };
        // đăng ký
        getApplicationContext().registerReceiver(broadcastReceiver, new IntentFilter("2"));
    }

    class pageAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        //Constructor

        public pageAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_add_acc) {
            // Intent -> Activity History
            startActivity(new Intent(this, AddAccActivity.class));
        } else if (id == R.id.nav_history) {
            // Intent -> Activity History
            startActivity(new Intent(this, ActivityHistory.class));

        } else if (id == R.id.nav_setting) {
            // Intent -> Activity Setting
            startActivity(new Intent(this, SettingActivity.class));
        } else if (id == R.id.nav_exit) {
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
