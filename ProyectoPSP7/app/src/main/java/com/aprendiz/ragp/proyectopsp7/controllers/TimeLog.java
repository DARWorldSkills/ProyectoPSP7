package com.aprendiz.ragp.proyectopsp7.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class TimeLog extends AppCompatActivity implements View.OnClickListener{


    EditText txtHorainicio,txtInterrupcion, txtHoraFin, txtDelta, txtComments ;
    Spinner spinnerPhase;
    Button btnStart, btnStop;

    Date dateStart, dateStop;
    int interrupciones = 0;

    int delta = 0;

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
        setContentView(R.layout.activity_time_log);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializar();
        llamarOnclick();
        ListarPhase();
        validarCampos();
      //  limpiarCampos();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void limpiarCampos() {
        txtHoraFin.setText("");
        txtHorainicio.setText("");
        txtComments.setText("");
        txtDelta.setText("");
        txtInterrupcion.setText("");
        btnStop.setEnabled(false);

    }

    private void validarCampos() {
        int validar = 0;

        if (txtHorainicio.getText().toString().length()>0){
            validar++;
        }else{
            txtHorainicio.setError("Campo Necesario");
        }
        if (txtHoraFin.getText().toString().length()>0){
            validar++;
        }else {
            txtHoraFin.setError("Campo Necesario");
        }

    }

    private void ListarPhase() {

        List<String> phase = new ArrayList<>();
        phase.add(" PLAN");
        phase.add(" DLD");
        phase.add(" CODE");
        phase.add(" COMPILE");
        phase.add(" UT");
        phase.add(" PM");

        ArrayAdapter<String> adapterPhase = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, phase);
        spinnerPhase.setAdapter(adapterPhase);
    }

    private void llamarOnclick() {
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

    }

    private void inicializar() {

        txtHorainicio = findViewById(R.id.txtHorainicio);
        txtInterrupcion = findViewById(R.id.txtInterrupcion);
        txtHoraFin = findViewById(R.id.txtHoraFin);
        txtDelta = findViewById(R.id.txtDelta);
        txtComments = findViewById(R.id.txtComments);

        spinnerPhase = findViewById(R.id.spinnerPhase);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnStart:
                ObtenerHora();
                btnStop.setEnabled(true);

                break;

            case R.id.btnStop:
                HoraFin();
                CalcularDelta();

                break;
        }

    }

    private void CalcularDelta() {

        calcularInterrupciones();
        double diferencia = dateStop.getTime() - dateStart.getTime();
        delta = ((int) (diferencia/ 60000))- interrupciones;
        txtDelta.setText(Integer.toString(delta));


    }

    private void calcularInterrupciones() {
        try {
            interrupciones = Integer.parseInt(txtInterrupcion.getText().toString());

        }catch (Exception e){
            interrupciones = 0;
        }

    }

    private void HoraFin() {

        dateStop = new Date();
        SimpleDateFormat finFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha1 = finFormat.format(dateStop);
        txtHoraFin.setText(fecha1);
    }

    private void ObtenerHora() {

        dateStart = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = format.format(dateStart);
        txtHorainicio.setText(fecha);

    }
}
