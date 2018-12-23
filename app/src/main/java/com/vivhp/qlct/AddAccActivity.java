package com.vivhp.qlct;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vivhp.qlct.DBHelper.DataBaseHelper;
import com.vivhp.qlct.Model.Model_Taikhoan;
import com.vivhp.qlct.dialog.DialogProgressBar;

public class AddAccActivity extends AppCompatActivity {

    EditText edit_money, edit_name, edit_type_money;
    DataBaseHelper dataBaseHelper;
    Drawable img, img1;
    String result;
    View view;
    Button btnSave;
    DialogProgressBar progressDialog;

    private boolean tienmat = true;

    //Var for Database
    String ten, loai, tien;
    int sotien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        view = findViewById(R.id.rootView);


        dataBaseHelper = new DataBaseHelper(this);

        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAccActivity.super.onBackPressed();
            }
        });

        //định nghĩa View control
        edit_name = (EditText) findViewById(R.id.name_acc);

        //Doi tien
        edit_money = (EditText) findViewById(R.id.money_acc);
        edit_money.addTextChangedListener(new DoiSoTien(edit_money));


        edit_type_money = (EditText) findViewById(R.id.btn_type_acc);
        btnSave = (Button) findViewById(R.id.btnSaveType);

        //Lấy ảnh
        img = getApplicationContext().getResources().getDrawable(R.mipmap.ic_t04);
        img1 = getApplicationContext().getResources().getDrawable(R.mipmap.ic_t05);

        //Set sự kiện click
        edit_type_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TypeMoney.class);
                //Khởi tạo Activity chờ nhận dữ liệu
                startActivityForResult(intent, 1);
                //Set icon cho button
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaiKhoan();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check request code == 1
        if (resultCode == 1) {
            result = data.getStringExtra("Type");
            //Set title cho button
            edit_type_money.setText(result);
            if (edit_type_money.getText().toString().equals("Tiền Mặt")) {
                checkTien(true);
            } else
                checkTien(false);

            if (tienmat) {
                edit_type_money.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_t05, 0);
            } else {
                edit_type_money.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_t04, 0);
            }
        } else
            finish();
    }

    public void addTaiKhoan() {

        String mes;

        if (TextUtils.isEmpty(ten = edit_name.getText().toString())) {
            mes = "Bạn cần nhập tên tài khoản.";
            Snackbar snackbar = Snackbar.make(view, mes, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            sbView.setBackgroundColor(view.getResources().getColor(R.color.rectage_btn));
            textView.setTextColor(view.getResources().getColor(R.color.white));
            snackbar.show();
        } else if (TextUtils.isEmpty(loai = edit_type_money.getText().toString())) {
            mes = "Bạn cần chọn loại tiền.";
            Snackbar snackbar = Snackbar.make(view, mes, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            sbView.setBackgroundColor(view.getResources().getColor(R.color.rectage_btn));
            textView.setTextColor(view.getResources().getColor(R.color.white));
            snackbar.show();
        } else if (TextUtils.isEmpty(tien = edit_money.getText().toString())) {
            mes = "Bạn cần nhập số tiền.";
            Snackbar snackbar = Snackbar.make(view, mes, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            sbView.setBackgroundColor(view.getResources().getColor(R.color.rectage_btn));
            textView.setTextColor(view.getResources().getColor(R.color.white));
            snackbar.show();
        } else {
            ten = edit_name.getText().toString();
            loai = edit_type_money.getText().toString();

            //Quy doi tien ve int
            String tiennhap = edit_money.getText().toString();
            int sotienint = Integer.parseInt(tiennhap.replaceAll("[\\D]", ""));
            sotien = sotienint;

            Model_Taikhoan model_taikhoan = new Model_Taikhoan(ten, loai, sotien);
            if (dataBaseHelper.addTaikhoan(model_taikhoan)) {
                Constants.model_taikhoan = dataBaseHelper.getFinalTenTaiKhoan();
            }

            edit_money.setText(null);
            edit_type_money.setHint(R.string.des_acc);
            edit_name.setText(null);
            progressDialog = new DialogProgressBar(AddAccActivity.this, false, false, null, getString(R.string.saving));
            progressDialog.showProgressBar();

            timeOut(2000);

        }

    }


    public int timeOut(int milisecond) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.hideProgressBar();

                Snackbar snackbar = Snackbar.make(view, ten + ": " + loai + ": " + sotien, Snackbar.LENGTH_SHORT);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                sbView.setBackgroundColor(view.getResources().getColor(R.color.rectage_btn));
                textView.setTextColor(view.getResources().getColor(R.color.white));
                snackbar.show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);

                //Send Broadcast
                int reques_int = 1;
                Intent intent = new Intent("2");
                intent.putExtra("2", reques_int);
                intent.putExtra("a", 2);

                int request_int = 2;
                intent.putExtra("c", request_int);

                getApplicationContext().sendBroadcast(intent);

            }
        }, milisecond);
        return milisecond;
    }

    public void checkTien(boolean tienmat) {
        this.tienmat = tienmat;
    }

}


