package com.aprendiz.ragp.proyectopsp7.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aprendiz.ragp.proyectopsp7.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectLog extends AppCompatActivity implements View.OnClickListener{

    EditText txtDate, txtfixtime, txtComments;
    Button btnFecha, btnGo, btnStopD, btnAgain;
    Spinner spinerType, spinnerPhaseInject, spinnerPhaseRemoved;

    Thread thread;
    int [] tiempo = {0,0};

    int validar = 0;
    Date dateFecha;

    boolean bandera = true;
    boolean bandera1 = false;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_log);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        inicializar();
        CallOnclick();
        ListarSpinners();
       // validarCampos();
        cronometro();

    }

    private void cronometro() {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                    while (bandera){
                        try {
                            {
                                Thread.sleep(1000);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    if (bandera1){
                                        tiempo[0]++;
                                        if (tiempo [0] == 60){
                                            tiempo[1]++;
                                            tiempo[0] =0;
                                        }

                                        if (tiempo[0] >= 0 && tiempo[0] <10){
                                            if (tiempo[0] >= 0 && tiempo[0] < 10){
                                                txtfixtime.setText("0" + tiempo[1] + ":" + "0" + tiempo[0]);
                                            }else {

                                                txtfixtime.setText(tiempo[1] + ":" + "0" + tiempo[0]);
                                            }
                                        }


                                        if (tiempo[0] >= 10 && tiempo[0] <60){
                                            if (tiempo[0] >= 0 && tiempo[0] <10){
                                                txtfixtime.setText("0" + tiempo[1] + ":" + tiempo[0]);

                                            }else{

                                                txtfixtime.setText(tiempo[1] + ":" + tiempo[0]);
                                            }
                                        }
                                    }

                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

            }
        });
        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    private void validarCampos() {

        int validar = 0;
        if (txtDate.getText().toString().length()>0){
            validar++;
        }else{
            txtDate.setError("Campo Necesario");
        }
        if (txtfixtime.getText().toString().length()>0){
            validar++;
        }else {

            txtfixtime.setError("No hay tiempo");
        }
    }

    private void ListarSpinners() {

        List<String> type = new ArrayList<>();
        type.add("Documentation");
        type.add("Syntax");
        type.add("Build");
        type.add("Package");
        type.add("Assigment");
        type.add("Interface");
        type.add("Checking");
        type.add("Data");
        type.add("Function");
        type.add("System");
        type.add("Environment");
        
        List<String> phases = new ArrayList<>();
        phases.add("PLAN");
        phases.add("DLD");
        phases.add("CODE");
        phases.add("COMPILE");
        phases.add("UT");
        phases.add("PM");

        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type);
        ArrayAdapter<String> adapterphases = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, phases);
        
        spinerType.setAdapter(adapterType);
        spinnerPhaseRemoved.setAdapter(adapterphases);
        spinnerPhaseInject.setAdapter(adapterphases);


    }

    private void CallOnclick() {

        btnGo.setOnClickListener(this);
        btnStopD.setOnClickListener(this);
        btnAgain.setOnClickListener(this);
        btnFecha.setOnClickListener(this);

    }

    private void inicializar() {

        txtDate = findViewById(R.id.txtFecha);
        txtfixtime = findViewById(R.id.txtFixTime);
        txtComments = findViewById(R.id.txtComments);

        btnGo = findViewById(R.id.btnGo);
        btnStopD = findViewById(R.id.btnStopD);
        btnAgain = findViewById(R.id.btnAgain);
        btnFecha = findViewById(R.id.btnFecha);

        spinerType = findViewById(R.id.spinnerType);
        spinnerPhaseInject = findViewById(R.id.spinnerPhaseInject);
        spinnerPhaseRemoved = findViewById(R.id.spinnerPhaseRemoved);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnFecha:
                obtenerFecha();

                break;


            case R.id.btnGo:

                GoCronometro();
                Snackbar.make(v, "Star", Snackbar.LENGTH_SHORT).show();

                break;

            case R.id.btnStopD:

                stopCronometro();
                Snackbar.make(v, "Stop", Snackbar.LENGTH_SHORT).show();

                break;

            case R.id.btnAgain:
                reiniciarCronometro();
                Snackbar.make(v, "Again", Snackbar.LENGTH_SHORT).show();

                break;
        }


    }

    private void reiniciarCronometro() {
        bandera1 = false;
        tiempo[0]=0;
        tiempo[0]=1;
        txtfixtime.setText("0" + tiempo[1] + ":" + "0" + tiempo[0]);
    }

    private void stopCronometro() {
        bandera1 = false;
    }

    private void GoCronometro() {

        bandera1 = true;

    }

    private void obtenerFecha() {

        dateFecha = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha1 = fecha.format(dateFecha);
        txtDate.setText(fecha1);
    }
}
