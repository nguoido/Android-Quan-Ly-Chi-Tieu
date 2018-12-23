package com.vivhp.qlct;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vivhp.qlct.Model.Model_List_Type_Money;
import com.vivhp.qlct.adapter.Adapter_List_Type_Money;

import java.util.ArrayList;

public class TypeMoney extends AppCompatActivity {

    //Khai báo các control cần dùng
    private String[] arr_type_money = {"Tiền Mặt", "Thẻ Tín Dụng"};
    ArrayList<Model_List_Type_Money> arrayList;
    String type_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_money);


        final ListView lv_type = (ListView) findViewById(R.id.lv_type_money);

        //Khởi Tạo mới ArrList
        arrayList = new ArrayList<Model_List_Type_Money>();
        //Lặp để check số phần tử trong mảng rồi thêm vào arList

        for (int i = 0; i < arr_type_money.length; i++) {
            Model_List_Type_Money type_money = new Model_List_Type_Money(arr_type_money[i]);
            arrayList.add(type_money);
        }

        //Tạo đối tượng Adapter
        Adapter_List_Type_Money adapter_list_type_money = new Adapter_List_Type_Money(this, R.layout.row_type_money, arrayList);
        //Set Adapter
        lv_type.setAdapter(adapter_list_type_money);
        lv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type_txt = String.valueOf(arrayList.get(position).getName());
                Toast.makeText(getApplicationContext(), type_txt, Toast.LENGTH_SHORT).show();
                //Khoi tao intent
                Intent intent = new Intent();
                //Gui Du lieu sang ben activity AddAcc
                intent.putExtra("Type", type_txt);
                //Set request code = voi request code cua intent result ben activity AddaAcc
                setResult(1, intent);
                //An activity nay
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
