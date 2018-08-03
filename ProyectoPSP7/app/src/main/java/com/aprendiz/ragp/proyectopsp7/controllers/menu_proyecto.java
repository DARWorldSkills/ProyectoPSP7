package com.aprendiz.ragp.proyectopsp7.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aprendiz.ragp.proyectopsp7.R;

public class menu_proyecto extends AppCompatActivity implements View.OnClickListener{

    Button btnTime, btnDefect, btnPPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_proyecto);
        inicializar();
        llamarOnclick();

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

                Intent intent = new Intent(menu_proyecto.this, TimeLog.class);
                startActivity(intent);

                break;

            case R.id.btnDefectLog:

                Intent intent1 = new Intent(menu_proyecto.this, DefectLog.class);
                startActivity(intent1);

                break;

            case R.id.btnPlanSummary:

                Intent intent2 = new Intent(menu_proyecto.this, ProjectPlanSummary.class);
                startActivity(intent2);


                break;
        }
    }
}
