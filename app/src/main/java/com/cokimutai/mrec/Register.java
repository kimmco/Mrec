package com.cokimutai.mrec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    EditText IdView, FnameBox, SnameBox, ResBox, CallBox;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void createSupplia(View view) {

        dbHelper = DbHelper.getInstance(getApplicationContext());

        IdView = (EditText)findViewById(R.id.s_id);
        FnameBox = (EditText)findViewById(R.id.f_name);
        SnameBox = (EditText)findViewById(R.id.l_name);
        ResBox = (EditText)findViewById(R.id.r_dnc);
        CallBox = (EditText)findViewById(R.id.call_no);

        SuppliaData suppliaData = new SuppliaData();

        if (!IdView.getText().toString().isEmpty()){
            suppliaData.suppliaId = IdView.getText().toString();
            //suppliaData.setSuppliaId(IdView.getText().toString());
        }else {
            // suppliaData.setSuppliaId("");
        }
        if (!FnameBox.getText().toString().isEmpty()){
            suppliaData.fname = FnameBox.getText().toString();
        }else {
            //suppliaData.fname = "";
        }

        if (!SnameBox.getText().toString().isEmpty()){
            suppliaData.lname = SnameBox.getText().toString();
        }else {
            //suppliaData.lname = "";
        }

        if (!ResBox.getText().toString().isEmpty()){
            suppliaData.resdnc = ResBox.getText().toString();
        }else {
            //suppliaData.resdnc = "";
        }

        if (!CallBox.getText().toString().isEmpty()){
            suppliaData.phone = CallBox.getText().toString();
        }else {
            //suppliaData.phone = "";
        }

        dbHelper.createSupplia(suppliaData);

        Intent myIntent = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        finish();



    }

    public void cancelCreate(View view) {
    }
}
