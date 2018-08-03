package com.aprendiz.ragp.proyectopsp7.Fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aprendiz.ragp.proyectopsp7.MenuPrincipal;
import com.aprendiz.ragp.proyectopsp7.R;
import com.aprendiz.ragp.proyectopsp7.models.AdapterCDY;
import com.aprendiz.ragp.proyectopsp7.models.CPPS;
import com.aprendiz.ragp.proyectopsp7.models.GestorDB;
import com.aprendiz.ragp.proyectopsp7.models.ManagerDB;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTime extends Fragment {


    public FragmentTime() {
        // Required empty public constructor
    }

    int timeP;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.fragment_fragment_time, container, false);

        final EditText txtTiempo;
        Button btnGuardarTiempo;


        txtTiempo = view.findViewById(R.id.txtTiempo);
        btnGuardarTiempo = view.findViewById(R.id.btnGuardarTime);
        recyclerView = view.findViewById(R.id.recyTime);
        GestorDB gestorDB = new GestorDB(getContext());
        SQLiteDatabase db = gestorDB.getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM PPS WHERE PROJECT="+ MenuPrincipal.project.getId()+";",null);
        if (cursor.moveToFirst()){
            do {
                timeP=cursor.getInt(1);
            }while (cursor.moveToNext());
        }else {
            timeP=0;
        }

        btnGuardarTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = txtTiempo.getText().toString();
                if (s.length()>0) {
                    if (timeP<=0) {
                        ManagerDB managerDB = new ManagerDB(getActivity());
                        CPPS cpps = new CPPS();
                        cpps.setTime(Integer.parseInt(s));
                        cpps.setProject(MenuPrincipal.project.getId());
                        managerDB.insertPPS(cpps);
                        timeP=cpps.getTime();

                    }else {
                        ManagerDB managerDB = new ManagerDB(getActivity());
                        CPPS cpps = new CPPS();
                        cpps.setTime(Integer.parseInt(s));
                        cpps.setProject(MenuPrincipal.project.getId());
                        managerDB.updatePPS(cpps);
                        timeP=cpps.getTime();
                        Toast.makeText(getContext(), ""+timeP, Toast.LENGTH_SHORT).show();
                    }

                    inputAdaper();
                }else {
                    Snackbar.make(v,"Por favor no deje el campo vacio ",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        db.close();
        cursor.close();
        inputAdaper();
        return view;
    }

    private void inputAdaper() {
        ManagerDB managerDB = new ManagerDB(getContext());
        if (timeP>0) {
            AdapterCDY adapterCDY = new AdapterCDY(managerDB.consultaDeYuli(timeP, MenuPrincipal.project.getId()));
            recyclerView.setAdapter(adapterCDY);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            recyclerView.setHasFixedSize(true);

        }
    }


}
