package com.cokimutai.mrec;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class StoreView extends AppCompatActivity {

    TextView idStoreView;
    TextView nameStoreView;
    TextView weekView;
    TextView lastSupplyBox;
    TextView monthView;

    DbHelper dbHelper = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_view);

        nameStoreView = (TextView)findViewById(R.id.storeName);
        idStoreView = (TextView)findViewById(R.id.storeId);
        weekView = (TextView)findViewById(R.id.weeksView);
        lastSupplyBox = (TextView)findViewById(R.id.last_Supply);
        monthView = (TextView)findViewById(R.id.monthsView);

        Intent myIntent = getIntent();
        String sIdValue = myIntent.getStringExtra("suppliaId");

        HashMap<String, String>nameTag = dbHelper.getPersonInfo(sIdValue);
        if (nameTag.size() != 0){
            nameStoreView.setText(nameTag.get("fname") + " " +
            nameTag.get("lname"));
        }
        int dailyList = dbHelper.getDailyTotal(sIdValue);
        lastSupplyBox.setText(Integer.toString(dailyList) + " " +
        " Litres");

        int weeklyList = dbHelper.getWeeklyTotal(sIdValue);
        weekView.setText(Integer.toString(weeklyList) + " " +
                " Litres");

        int monthlyList = dbHelper.getMonthlyTotal(sIdValue);
        monthView.setText(Integer.toString(monthlyList) + " " +
                " Litres");
    }
    public void open(View view){
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String suppliaIdValue = getIntent().getStringExtra("suppliaId");
                Intent myIntent = new Intent(getApplicationContext(), Deduct_View.class);
                myIntent.putExtra("suppliaId", suppliaIdValue);
                startActivity(myIntent);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                this.getClass();
            }
        });
        alertDialogBuilder.setTitle("Are you sure you want to DEDUCT?");
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void deductBtn(View view) {
        this.open(view);
    }

    public void dSupplyBtn(View view) {

        String sIdValue = getIntent().getStringExtra("suppliaId");

        Intent myIntent = new Intent(getApplicationContext(), Store_Room.class);
        myIntent.putExtra("suppliaId", sIdValue);
        startActivity(myIntent);
        finish();
    }
}
