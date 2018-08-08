package com.aprendiz.ragp.proyectopsp7;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.aprendiz.ragp.proyectopsp7.controllers.MenuProyecto;
import com.aprendiz.ragp.proyectopsp7.models.AdapterP;
import com.aprendiz.ragp.proyectopsp7.models.ManagerDB;
import com.aprendiz.ragp.proyectopsp7.models.Project;

import java.util.List;

public class MenuPrincipal extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static Project project;
    private FloatingActionButton btnInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        inizialite();
        inputAdaper();
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
                builder.setTitle("Agregar Proyecto");
                View view = LayoutInflater.from(MenuPrincipal.this).inflate(R.layout.alertd_agregarp,null);
                builder.setView(view);
                final EditText txtNombre = view.findViewById(R.id.txtNombrePI);
                builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = txtNombre.getText().toString();
                        if (name.length()>0){
                            ManagerDB managerDB = new ManagerDB(MenuPrincipal.this);
                            Project project = new Project();
                            project.setName(name);
                            managerDB.insertProject(project);
                            Snackbar.make(v,"Se ha agregado correctamente el proyecto",Snackbar.LENGTH_SHORT).show();
                            inputAdaper();
                        }else{
                            Snackbar.make(v,"Para agregar un proyecto no puede dejar el campo vacio",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }


    private void inizialite() {
        recyclerView= findViewById(R.id.recyclerviewP);
        btnInput = findViewById(R.id.btnIngresar);
    }

    private void inputAdaper() {
        ManagerDB managerDB = new ManagerDB(this);
        final List<Project> projectList = managerDB.selectProjects();
        AdapterP adapterP = new AdapterP(projectList);
        recyclerView.setAdapter(adapterP);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        adapterP.setMlistener(new AdapterP.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                project = projectList.get(position);
                Intent intent = new Intent(MenuPrincipal.this,MenuProyecto.class);
                startActivity(intent);
            }
        });
    }
}
