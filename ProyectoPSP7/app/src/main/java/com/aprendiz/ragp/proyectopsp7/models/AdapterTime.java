package com.aprendiz.ragp.proyectopsp7.models;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aprendiz.ragp.proyectopsp7.R;

import java.util.List;

public class AdapterTime extends RecyclerView.Adapter<AdapterTime.Holder> {
    List<CTimeLog> timeLogList;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void itemClick(int position);
    }

    public AdapterTime(List<CTimeLog> timeLogList) {
        this.timeLogList = timeLogList;
    }

    public void setMlistener(OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo,parent,false);
        Holder holder = new Holder(view,mlistener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.connectData(timeLogList.get(position));
    }

    @Override
    public int getItemCount() {
        return timeLogList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtID = itemView.findViewById(R.id.txtIdItem);
        TextView txtName= itemView.findViewById(R.id.txtNameItem);
        TextView txtDes = itemView.findViewById(R.id.txtDesItem);
        public Holder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getLayoutPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.itemClick(position);
                        }
                    }
                }
            });
        }

        public void connectData(CTimeLog cTimeLog){
            txtID.setText(Integer.toString(cTimeLog.getId()));
            txtName.setText(cTimeLog.getPhase());
            txtDes.setText(cTimeLog.getStart());

        }


    }
}
