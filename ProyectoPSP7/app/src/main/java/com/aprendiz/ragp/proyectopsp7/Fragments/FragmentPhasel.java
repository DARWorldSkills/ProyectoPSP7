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

import com.aprendiz.ragp.proyectopsp7.MenuPrincipal;
import com.aprendiz.ragp.proyectopsp7.R;
import com.aprendiz.ragp.proyectopsp7.models.AdapterCDY;
import com.aprendiz.ragp.proyectopsp7.models.GestorDB;
import com.aprendiz.ragp.proyectopsp7.models.ManagerDB;
import com.aprendiz.ragp.proyectopsp7.models.Results;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPhasel extends Fragment {


    public FragmentPhasel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_phasel, container, false);
        ManagerDB managerDB = new ManagerDB(getContext());
        GestorDB gestorDB = new GestorDB(getContext());
        int timeP;
        SQLiteDatabase db= gestorDB.getReadableDatabase();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewPI);

        final Cursor cursor = db.rawQuery("SELECT * FROM PPS WHERE PROJECT="+ MenuPrincipal.project.getId()+";",null);
        if (cursor.moveToFirst()){
            do {
                timeP=cursor.getInt(1);
            }while (cursor.moveToNext());
        }else {
            timeP=0;
        }

        if (timeP>0) {
            List<Results> results = managerDB.consultaDeYuli1(timeP, MenuPrincipal.project.getId());
            AdapterCDY adapterCDY = new AdapterCDY(results);
            recyclerView.setAdapter(adapterCDY);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            recyclerView.setHasFixedSize(true);

        }else{
            Snackbar.make(view,"No se ha ingresado el timepo planeado del proyecto",Snackbar.LENGTH_SHORT).show();
        }

        return view;
    }

}
