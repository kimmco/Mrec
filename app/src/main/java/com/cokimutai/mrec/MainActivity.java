package com.cokimutai.mrec;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Listner {
    RecyclerView recyclerView;
    DbHelper dbHelper;
    RecyclerAdapter adapter;

    private long bpTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new RecyclerAdapter(this, dbHelper.getAllsupplias());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Intent myntent = new Intent(getApplicationContext(), Register.class);
                startActivity(myntent);
                finish();  }
        });
    }


    @Override
    public void onBackPressed(){
        //to prevent irritating accidental logouts
        long t = System.currentTimeMillis();
        if (t - bpTime > 2000) {
            //2 secs
            bpTime = t;
            Toast.makeText(this, "Press back again to exit",
                    Toast.LENGTH_SHORT).show();
        } else {
            //this guy is serious
            super.onBackPressed();
            //bye
        }
    }

    @Override
    public void nameToChange(String name){

        // dbHelper.deleteSupplia(name);

       /* adapter = new RecyclerAdapter(this, dbHelper.getAllSupplia());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.price_tab:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                Intent myIntent = new Intent(getApplicationContext(), PaymentView.class);
                startActivity(myIntent);
                finish();
                return true;

            case R.id.action_settings:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
               /* Intent myjIntent = new Intent(getApplicationContext(), PaymentView.class);
                startActivity(myjIntent);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
