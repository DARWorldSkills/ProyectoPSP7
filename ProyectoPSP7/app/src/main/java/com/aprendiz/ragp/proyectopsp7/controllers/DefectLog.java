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
import com.aprendiz.ragp.proyectopsp7.models.CDefectLog;
import com.aprendiz.ragp.proyectopsp7.models.CTimeLog;
import com.aprendiz.ragp.proyectopsp7.models.ManagerDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectLog extends AppCompatActivity implements View.OnClickListener{

    EditText txtDate, txtfixtime, txtComments;
    Button btnFecha, btnGo, btnStopD, btnAgain;
    Spinner spinerType, spinnerPhaseInject, spinnerPhaseRemoved;
    public static int modo =0;
    public static CDefectLog defectLogC = new CDefectLog();
    Thread thread;
    int [] tiempo = {0,0};
    List<String> typelist= new ArrayList<>();
    List<String> phases = new ArrayList<>();
    int validar = 0;
    Date dateFecha;


    boolean bandera = true;
    boolean bandera1 = false;

    private TextView mTextMessage;
    ConstraintLayout contenedor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    limpiarCampo();
                    return true;
                case R.id.navigation_dashboard:
                    if (modo==0) {
                        inputData();
                    }else {
                        updateData();
                    }
                    return true;
                case R.id.navigation_notifications:
                    Intent intent = new Intent(DefectLog.this,LDefectLog.class);
                    startActivity(intent);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        inicializar();
        CallOnclick();
        ListarSpinners();
        cronometro();
        limpiarCampo();
        if (modo==1){
            inputVales();
        }

    }

    private void inputVales() {
        for (int i =0; i<typelist.size();i++) {
            try {
                if (defectLogC.getType().equals(typelist.get(i))) {
                    spinerType.setSelection(i);

                }
            }catch (Exception e){

            }
        }


        for (int i =0; i<phases.size();i++) {
            try {
                if (defectLogC.getPhaseI().equals(phases.get(i))) {
                    spinnerPhaseInject.setSelection(i);

                }
            }catch (Exception e){

            }
        }

        for (int i =0; i<phases.size();i++) {
            try {
                if (defectLogC.getPhaseR().equals(phases.get(i))) {
                    spinnerPhaseRemoved.setSelection(i);

                }
            }catch (Exception e){

            }
        }

        txtfixtime.setText(defectLogC.getFixtime());
        txtDate.setText(defectLogC.getDate());
        txtComments.setText(defectLogC.getComments());
    }

    private void limpiarCampo() {
        txtfixtime.setText("");
        txtDate.setText("");
        txtComments.setText("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
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

        validar = 0;
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

        typelist = new ArrayList<>();
        typelist.add("Documentation");
        typelist.add("Syntax");
        typelist.add("Build");
        typelist.add("Package");
        typelist.add("Assigment");
        typelist.add("Interface");
        typelist.add("Checking");
        typelist.add("Data");
        typelist.add("Function");
        typelist.add("System");
        typelist.add("Environment");
        
        List<String> phases = new ArrayList<>();
        phases.add("PLAN");
        phases.add("DLD");
        phases.add("CODE");
        phases.add("COMPILE");
        phases.add("UT");
        phases.add("PM");

        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typelist);
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
        contenedor = findViewById(R.id.container);

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
        tiempo[1]=0;
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

    public void inputData(){
        validarCampos();
        if (validar>1) {
            bandera1=false;
            final CDefectLog cDefectLog = new CDefectLog();
            cDefectLog.setDate(txtDate.getText().toString());
            cDefectLog.setType(spinerType.getSelectedItem().toString());
            cDefectLog.setFixtime(txtfixtime.getText().toString());
            cDefectLog.setPhaseI(spinnerPhaseInject.getSelectedItem().toString());
            cDefectLog.setPhaseR(spinnerPhaseRemoved.getSelectedItem().toString());
            cDefectLog.setComments(txtComments.getText().toString());
            cDefectLog.setProject(MenuPrincipal.project.getId());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.item_mostrar,null);
            builder.setView(view);
            builder.setTitle("¿Desea ingresar este Defect log?");
            TextView txtTodo = view.findViewById(R.id.txtTodo);
            String mensaje = "Date: "+ cDefectLog.getDate()+"\n" +
                    "Type: "+cDefectLog.getType()+"\n"+
                    "Fixtime: "+cDefectLog.getFixtime()+"\n"+
                    "Phase Inject: "+cDefectLog.getPhaseI()+"\n"+
                    "Phase Removed: "+cDefectLog.getPhaseR()+"\n"+
                    "Comments: "+cDefectLog.getComments()+"\n"+
                    "Project: "+cDefectLog.getProject()+"\n";
            txtTodo.setText(mensaje);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ManagerDB managerDB = new ManagerDB(DefectLog.this);
                    managerDB.insertDefectLog(cDefectLog);
                    Snackbar.make(contenedor,"Se ha guardado correctamente",Snackbar.LENGTH_SHORT).show();
                    limpiarCampo();
                    reiniciarCronometro();

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


    private void updateData() {
        validarCampos();
        if (validar>1) {
            bandera1=false;
            final CDefectLog cDefectLog = new CDefectLog();
            cDefectLog.setId(defectLogC.getId());
            cDefectLog.setDate(txtDate.getText().toString());
            cDefectLog.setType(spinerType.getSelectedItem().toString());
            cDefectLog.setFixtime(txtfixtime.getText().toString());
            cDefectLog.setPhaseI(spinnerPhaseInject.getSelectedItem().toString());
            cDefectLog.setPhaseR(spinnerPhaseRemoved.getSelectedItem().toString());
            cDefectLog.setComments(txtComments.getText().toString());
            cDefectLog.setProject(MenuPrincipal.project.getId());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.item_mostrar,null);
            builder.setView(view);
            builder.setTitle("¿Desea ingresar este Defect log?");
            TextView txtTodo = view.findViewById(R.id.txtTodo);
            String mensaje = "Date: "+ cDefectLog.getDate()+"\n" +
                    "Type: "+cDefectLog.getType()+"\n"+
                    "Fixtime: "+cDefectLog.getFixtime()+"\n"+
                    "Phase Inject: "+cDefectLog.getPhaseI()+"\n"+
                    "Phase Removed: "+cDefectLog.getPhaseR()+"\n"+
                    "Comments: "+cDefectLog.getComments()+"\n"+
                    "Project: "+cDefectLog.getProject()+"\n";
            txtTodo.setText(mensaje);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ManagerDB managerDB = new ManagerDB(DefectLog.this);
                    managerDB.updateDefectLog(cDefectLog);
                    Snackbar.make(contenedor,"Se ha editado correctamente",Snackbar.LENGTH_SHORT).show();
                    limpiarCampo();
                    reiniciarCronometro();
                    modo=0;
                    Intent intent = new Intent(DefectLog.this, LDefectLog.class);
                    startActivity(intent);
                    finish();

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
