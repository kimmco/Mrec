package com.cokimutai.mrec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.ListActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cokimutai on 1/9/2017.
 */
public class PaymentView extends ListActivity{

    TextView namesView, LnamesView, payIDBox;
    DbHelper dbHelper = new DbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_view);

        namesView = (TextView)findViewById(R.id.payFName);
        LnamesView = (TextView)findViewById(R.id.payLName);
        payIDBox = (TextView)findViewById(R.id.pay_Id);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        ArrayList<HashMap<String, String>> payList =
                dbHelper.getPaysupplias();
        if (payList.size()  != 0){
            ListAdapter listAdapter = new SimpleAdapter(
                    PaymentView.this, payList, R.layout.payrow,
                    new String[]{ "fname ", "lname", "suppliaId"},
                    new int[]{ R.id.payFName, R.id.payLName, R.id.pay_Id});
            setListAdapter(listAdapter);
        }

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        finish();

    }
}
