package com.prabesh.phonote.Adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prabesh.phonote.AddDataActivity;
import com.prabesh.phonote.Modal.DataModel;
import com.prabesh.phonote.R;
import com.prabesh.phonote.databinding.DetailDataDesignBinding;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.viewHolder> {

    ArrayList<DataModel> list;
    Context context;
    int i = 1;

    public DataAdapter(ArrayList<DataModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_data_design, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        if (i % 2 == 1) {
            holder.binding.snTitle.setBackgroundResource(R.color.green);
            holder.binding.dateTitle.setBackgroundResource(R.color.green);
            holder.binding.nameTitle.setBackgroundResource(R.color.green);
            holder.binding.weightTitle.setBackgroundResource(R.color.chocolate);
            holder.binding.rateTitle.setBackgroundResource(R.color.chocolate);
            holder.binding.totalTitle.setBackgroundResource(R.color.chocolate);
            holder.binding.dropdownView.setBackgroundResource(R.color.green);
            holder.binding.lowerConstraint.setBackgroundResource(R.color.chocolate);

        } else {
            holder.binding.snTitle.setBackgroundResource(R.color.gray);
            holder.binding.dateTitle.setBackgroundResource(R.color.gray);
            holder.binding.nameTitle.setBackgroundResource(R.color.gray);
            holder.binding.weightTitle.setBackgroundResource(R.color.chocolate);
            holder.binding.rateTitle.setBackgroundResource(R.color.chocolate);
            holder.binding.totalTitle.setBackgroundResource(R.color.chocolate);
            holder.binding.dropdownView.setBackgroundResource(R.color.gray);
            holder.binding.lowerConstraint.setBackgroundResource(R.color.chocolate);


        }

        DataModel model = list.get(position);
        holder.binding.dateTitle.setText(model.getDate());
        holder.binding.nameTitle.setText(model.getName());
        holder.binding.weightTitle.setText(model.getWeight().toString());
        holder.binding.rateTitle.setText(model.getRate().toString());
        holder.binding.totalTitle.setText(model.getTotal().toString());
        if (model.isSell())
            holder.binding.buySellView.setText("Sell");
        else
            holder.binding.buySellView.setText("Buy");
        holder.binding.snTitle.setText(Integer.toString(i));
        String timeAgo = TimeAgo.getTimeAgo(model.getAddedTime());
        String timeAgo1 = TimeAgo.getTimeAgo(model.getModifiedTime());
        holder.binding.uploadTime.setText(timeAgo);
        if (model.getModifiedTime()==0){
            holder.binding.modifiedTimeLayout.setVisibility(View.GONE);

        } else {
            holder.binding.modifiedTimeLayout.setVisibility(View.VISIBLE);
            holder.binding.modifiedTime.setText(timeAgo1);
        }


        i++;
        holder.binding.dataListLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.binding.dropdown.getRotationX() == 0)
                    holder.binding.dropdown.setRotationX(180);
                else
                    holder.binding.dropdown.setRotationX(0);

                if (holder.binding.lowerConstraint.getVisibility() == View.VISIBLE)
                    holder.binding.lowerConstraint.setVisibility(View.GONE);
                else
                    holder.binding.lowerConstraint.setVisibility(View.VISIBLE);
            }
        });

        holder.binding.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddDataActivity.class);
                intent.putExtra("activity", "list");
                intent.putExtra("date",model.getDate()+"");
                intent.putExtra("name", model.getName()+"");
                intent.putExtra("weight", model.getWeight()+"");
                intent.putExtra("rate", model.getRate()+"");
                intent.putExtra("id", model.getDataId()+"");
                context.startActivity(intent);
            }
        });
        holder.binding.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Conform Delete")
                        .setMessage("Do you really want to delete?")
                        .setPositiveButton("Ok",null)
                        .setNeutralButton("Cancel",null)
                        .show();

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dataId = model.getDataId();


                        /*FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("data_added_by_user")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds :dataSnapshot.getChildren()){
                                    DataModel model = ds.getValue(DataModel.class);
                                    if(model.getDataId() == dataId){
                                        ds.getRef().removeValue();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });*/

                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("data_added_by_user").child(dataId).removeValue();
                        holder.binding.lowerConstraint.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        DetailDataDesignBinding binding;

        public viewHolder(@NonNull View itemView) {

            super(itemView);
            binding = DetailDataDesignBinding.bind(itemView);

        }
    }


    public static class TimeAgo {
        private static final int SECOND_MILLIS = 1000;
        private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

        public static String getTimeAgo(long time) {
            if (time < 1000000000000L) {
                time *= 1000;
            }

            long now = System.currentTimeMillis();
            if (time > now || time <= 0) {
                return null;
            }


            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " minutes ago";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " hours ago";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " days ago";
            }
        }

    }
}
