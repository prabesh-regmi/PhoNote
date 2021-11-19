package com.prabesh.phonote.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabesh.phonote.Modal.DataModel;
import com.prabesh.phonote.R;
import com.prabesh.phonote.databinding.DataListDesignBinding;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.viewHolder>{

    ArrayList<DataModel> list;
    Context context;
    int i=0;

    public DataAdapter(ArrayList<DataModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_list_design,parent,false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        if ( i%2 == 0 ){
            holder.sn.setBackgroundResource(R.color.green);
            holder.date.setBackgroundResource(R.color.green);
            holder.name.setBackgroundResource(R.color.green);
            holder.weight.setBackgroundResource(R.color.green);
            holder.rate.setBackgroundResource(R.color.green);
            holder.total.setBackgroundResource(R.color.green);

        }else {
            holder.sn.setBackgroundResource(R.color.lightgreen);
            holder.date.setBackgroundResource(R.color.lightgreen);
            holder.name.setBackgroundResource(R.color.lightgreen);
            holder.weight.setBackgroundResource(R.color.lightgreen);
            holder.rate.setBackgroundResource(R.color.lightgreen);
            holder.total.setBackgroundResource(R.color.lightgreen);
        }
        DataModel model = list.get(position);
        holder.date.setText(model.getDate());
        holder.name.setText(model.getName());
        holder.weight.setText(model.getWeight().toString());
        holder.rate.setText(model.getRate().toString());
        holder.total.setText(model.getTotal().toString());
        String sn1 = Integer.toString(i);
        holder.sn.setText(sn1);
        i++;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends  RecyclerView.ViewHolder {

        DataListDesignBinding binding;
        TextView sn,date,name,weight,rate,total;
        public viewHolder(@NonNull View itemView) {

            super(itemView);
            binding = DataListDesignBinding.bind(itemView);

            sn = itemView.findViewById(R.id.snTitle);
            date = itemView.findViewById(R.id.dateTitle);
            name = itemView.findViewById(R.id.nameTitle);
            weight = itemView.findViewById(R.id.weightTitle);
            rate = itemView.findViewById(R.id.rateTitle);
            total = itemView.findViewById(R.id.totalTitle);
        }
    }

}
