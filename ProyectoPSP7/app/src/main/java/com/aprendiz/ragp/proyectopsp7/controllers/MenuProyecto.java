package com.aprendiz.ragp.proyectopsp7.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.aprendiz.ragp.proyectopsp7.R;

public class MenuProyecto extends AppCompatActivity implements View.OnClickListener{

    Button btnTime, btnDefect, btnPPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_proyecto);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializar();
        llamarOnclick();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:

                Log.i("Action bar", "Atras");
                finish();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void llamarOnclick() {
        btnTime.setOnClickListener(this);
        btnDefect.setOnClickListener(this);
        btnPPS.setOnClickListener(this);
    }

    private void inicializar() {

        btnTime = findViewById(R.id.btnTimeLog);
        btnDefect = findViewById(R.id.btnDefectLog);
        btnPPS = findViewById(R.id.btnPlanSummary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnTimeLog:

                Intent intent = new Intent(MenuProyecto.this, TimeLog.class);
                startActivity(intent);

                break;

            case R.id.btnDefectLog:
                DefectLog.modo=0;
                Intent intent1 = new Intent(MenuProyecto.this, DefectLog.class);
                startActivity(intent1);

                break;

            case R.id.btnPlanSummary:
                TimeLog.modo=0;
                Intent intent2 = new Intent(MenuProyecto.this, ProjectPlanSummary.class);
                startActivity(intent2);


                break;
        }
    }
}
