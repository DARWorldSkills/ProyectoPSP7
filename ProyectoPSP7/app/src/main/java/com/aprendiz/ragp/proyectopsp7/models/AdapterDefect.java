package com.aprendiz.ragp.proyectopsp7.models;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aprendiz.ragp.proyectopsp7.R;

import java.util.List;

public class AdapterDefect extends RecyclerView.Adapter<AdapterDefect.Holder> {
    List<CDefectLog> cDefectLogs;
    private AdapterTime.OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void itemClick(int position);
    }

    public AdapterDefect(List<CDefectLog> cDefectLogs) {
        this.cDefectLogs = cDefectLogs;
    }

    public void setMlistener(AdapterTime.OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo,parent);
        Holder holder = new Holder(view,mlistener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.connectData(cDefectLogs.get(position));
    }


    @Override
    public int getItemCount() {
        return cDefectLogs.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtID = itemView.findViewById(R.id.txtIdItem);
        TextView txtName= itemView.findViewById(R.id.txtNameItem);
        TextView txtDes = itemView.findViewById(R.id.txtDesItem);
        public Holder(View itemView, final AdapterTime.OnItemClickListener listener) {
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

        public void connectData(CDefectLog cDefectLog){
            txtID.setText(Integer.toString(cDefectLog.getId()));
            txtName.setText(cDefectLog.getDate());
            txtDes.setText(cDefectLog.getType());

        }


    }
}
