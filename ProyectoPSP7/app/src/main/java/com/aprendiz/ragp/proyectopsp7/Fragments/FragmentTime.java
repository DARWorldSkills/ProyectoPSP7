package com.aprendiz.ragp.proyectopsp7.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aprendiz.ragp.proyectopsp7.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTime extends Fragment {


    public FragmentTime() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_fragment_time, container, false);

        EditText txtTiempo;
        Button btnGuardarTiempo;
        RecyclerView recyclerView;

        txtTiempo = view.findViewById(R.id.txtTiempo);
        btnGuardarTiempo = view.findViewById(R.id.btnGuardarTime);
        recyclerView = view.findViewById(R.id.recyTime);

        return view;
    }


}
