
package com.aprendiz.ragp.proyectopsp7.controllers;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.aprendiz.ragp.proyectopsp7.MenuPrincipal;
import com.aprendiz.ragp.proyectopsp7.R;
import com.aprendiz.ragp.proyectopsp7.models.AdapterDefect;
import com.aprendiz.ragp.proyectopsp7.models.AdapterTime;
import com.aprendiz.ragp.proyectopsp7.models.CDefectLog;
import com.aprendiz.ragp.proyectopsp7.models.CTimeLog;
import com.aprendiz.ragp.proyectopsp7.models.ManagerDB;

import java.util.List;

public class LDefectLog extends AppCompatActivity {
    RecyclerView recyclerView;
    CDefectLog cDefectLog = new CDefectLog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldefect_log);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inizialite();
        inputAdapter();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void inizialite() {
        recyclerView = findViewById(R.id.recyclerViewD);

    }

    private void inputAdapter() {
        final ManagerDB managerDB = new ManagerDB(this);
        final List<CDefectLog> logList  = managerDB.selectDefectLogs(MenuPrincipal.project.getId());
        AdapterDefect adapterDefect = new AdapterDefect(logList);
        recyclerView.setAdapter(adapterDefect);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        adapterDefect.setMlistener(new AdapterTime.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                cDefectLog = logList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(LDefectLog.this);
                builder.setTitle("¿Qué desea hacer con este DefectLog?");
                builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(LDefectLog.this);
                        builder1.setTitle("Está seguro de eliminar este DefectLog");
                        builder1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                managerDB.deleteDefectLog(cDefectLog);
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
