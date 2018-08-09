package com.aprendiz.ragp.proyectopsp7.models;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aprendiz.ragp.proyectopsp7.R;

import java.util.List;

public class AdapterCDY extends RecyclerView.Adapter<AdapterCDY.Holder>{
    List<Results> results;

    public AdapterCDY(List<Results> results) {
        this.results = results;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_results,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.connectData(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtPhase= itemView.findViewById(R.id.txtPhaseItem);
        TextView txtTime=itemView.findViewById(R.id.txtTime);
        TextView txtP=itemView.findViewById(R.id.txtRealTime);
        public Holder(View itemView) {
            super(itemView);
        }

        public void connectData(Results results){
            txtPhase.setText(results.getPhase());
            txtTime.setText(Integer.toString(results.getTime()));
            txtP.setText(Float.toString(results.getPercent())+"%");
        }
    }
}
