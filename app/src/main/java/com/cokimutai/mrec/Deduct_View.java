package com.cokimutai.mrec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Deduct_View extends AppCompatActivity {
    TextView idView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deduct__view);
        idView = (TextView)findViewById(R.id.identity);
        String sIdentity = getIntent().getStringExtra("suppliaId");
        idView.setText(sIdentity);

    }


    public void reduceBtn(View view) {
        idView = (TextView)findViewById(R.id.identity);
        String suppliaIdVal = idView.getText().toString();
        Intent myIntent = new Intent(getApplication(), StoreView.class);
        myIntent.putExtra("suppliaId", suppliaIdVal);
        startActivity(myIntent);
        finish();
    }
}
