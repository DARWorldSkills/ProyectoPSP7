package com.aprendiz.ragp.proyectopsp7.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aprendiz.ragp.proyectopsp7.MenuPrincipal;
import com.aprendiz.ragp.proyectopsp7.R;
import com.aprendiz.ragp.proyectopsp7.models.CTimeLog;
import com.aprendiz.ragp.proyectopsp7.models.ManagerDB;

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
    int validar=0;
    int delta = 0;
    ConstraintLayout contenedor;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    inputData();
                    return true;
                case R.id.navigation_notifications:
                    Intent intent = new Intent(TimeLog.this,LTimeLog.class);
                    startActivity(intent);
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

        inicializar();
        llamarOnclick();
        ListarPhase();

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
        validar = 0;

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
        contenedor = findViewById(R.id.container);

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

    public void inputData(){
        validarCampos();
        if (validar>1) {
            final CTimeLog cTimeLog = new CTimeLog();
            cTimeLog.setPhase(spinnerPhase.getSelectedItem().toString());
            cTimeLog.setStart(txtHorainicio.getText().toString());
            cTimeLog.setStop(txtHoraFin.getText().toString());
            cTimeLog.setDelta(delta);
            cTimeLog.setInterrupcion(interrupciones);
            cTimeLog.setComments(txtComments.getText().toString());
            cTimeLog.setProject(MenuPrincipal.project.getId());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.item_mostrar,null);
            builder.setView(view);
            builder.setTitle("¿Desea ingresar este Time log?");
            TextView txtTodo = view.findViewById(R.id.txtTodo);
            String mensaje = "Phase: "+ cTimeLog.getPhase()+"\n" +
                    "Start: "+cTimeLog.getStart()+"\n"+
                    "Interrurcpion: "+cTimeLog.getInterrupcion()+"\n"+
                    "Stop: "+cTimeLog.getStop()+"\n"+
                    "Delta: "+cTimeLog.getDelta()+"\n"+
                    "Comments: "+cTimeLog.getComments()+"\n"+
                    "Project: "+cTimeLog.getProject()+"\n";
            txtTodo.setText(mensaje);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ManagerDB managerDB = new ManagerDB(TimeLog.this);
                    managerDB.insertTimeLog(cTimeLog);
                    Snackbar.make(contenedor,"Se ha guardado correctamente",Snackbar.LENGTH_SHORT).show();
                    limpiarCampos();
                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();

        }else {
            Snackbar.make(contenedor,"Faltan campos por ingresar",Snackbar.LENGTH_SHORT).show();
        }

    }
}
