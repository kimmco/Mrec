package com.cokimutai.mrec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.ListActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cokimutai on 1/9/2017.
 */
public class Store_Room extends ListActivity {
    TextView dateView, litresBox, iDBox;
    DbHelper dbHelper = new DbHelper(this);

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store__room);

        dateView = (TextView)findViewById(R.id.callender_View);
        litresBox = (TextView)findViewById(R.id.daily_Litres);
        iDBox = (TextView)findViewById(R.id.suup_Id);

        String suppliaId = getIntent().getStringExtra("suppliaId");
        //iDBox.setText(String.valueOf(suppliaId));
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        ArrayList<HashMap<String, String>>dailyList =
                dbHelper.getDailySupplly(suppliaId);


        if (dailyList.size()  != 0){
            ListAdapter listAdapter = new SimpleAdapter(
                    Store_Room.this, dailyList, R.layout.storerow,
                    new String[]{ "cdate", "litres", "suppliaId"},
                    new int[]{ R.id.callender_View, R.id.daily_Litres, R.id.suup_Id});
            setListAdapter(listAdapter);
        }

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        iDBox = (TextView)findViewById(R.id.suup_Id);
        Bundle extra = getIntent().getExtras();
        if (extra == null){
            return;
        }
        String idString = extra.getString("suppliaId");

        //String  suppliaIdValue = iDBox.getText().toString();
        Intent myIntent = new Intent(getApplicationContext(), StoreView.class);
        myIntent.putExtra("suppliaId", idString);
        startActivity(myIntent);
        finish();

    }
}
