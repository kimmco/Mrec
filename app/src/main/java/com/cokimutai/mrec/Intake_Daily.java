package com.cokimutai.mrec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Intake_Daily extends AppCompatActivity {
    EditText InBox;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake__daily);


    }

    public void addDailyMilk(View view){

        InBox = (EditText)findViewById(R.id.d_Litres);
        dbHelper = DbHelper.getInstance(getApplicationContext());
        Intent myIntent = getIntent();
        String sIdValue = myIntent.getStringExtra("suppliaId");

        SuppliaData suppliaData = new SuppliaData();
        //suppliaData.suppliaId = sIdValue;
        suppliaData.setSuppliaId((sIdValue));
        if (!InBox.getText().toString().isEmpty()){
            suppliaData.litres = Integer.parseInt(InBox.getText().toString());
        }else {
            suppliaData.litres = Integer.parseInt("");
        }
            dbHelper.insertMilk(suppliaData);

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

    }
}
