package com.aprendiz.ragp.proyectopsp7.controllers;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aprendiz.ragp.proyectopsp7.MenuPrincipal;
import com.aprendiz.ragp.proyectopsp7.R;
import com.aprendiz.ragp.proyectopsp7.models.AdapterTime;
import com.aprendiz.ragp.proyectopsp7.models.CTimeLog;
import com.aprendiz.ragp.proyectopsp7.models.ManagerDB;

import java.util.List;

public class LTimeLog extends AppCompatActivity {
    RecyclerView recyclerView;
    CTimeLog cTimeLog = new CTimeLog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltime_log);
        inizialite();
        inputAdapter();
    }

    private void inizialite() {
        recyclerView = findViewById(R.id.recyclerViewT);

    }

    private void inputAdapter() {
        final ManagerDB managerDB = new ManagerDB(this);
        final List<CTimeLog> logList  = managerDB.selectTimeLogs(MenuPrincipal.project.getId());
        AdapterTime adapterTime = new AdapterTime(logList);
        recyclerView.setAdapter(adapterTime);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        adapterTime.setMlistener(new AdapterTime.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                cTimeLog = logList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(LTimeLog.this);
                builder.setTitle("¿Qué desea hacer con este TimeLog?");
                builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(LTimeLog.this);
                        builder1.setTitle("Está seguro de eliminar este TimeLog");
                        builder1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                managerDB.deleteTimeLog(cTimeLog);
                                inputAdapter();
                            }
                        });

                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder1.show();
                    }
                });

                builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }

        });


    }
}
