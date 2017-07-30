package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 20/07/2017.
 */

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class sensorDataAdapter extends RecyclerView.Adapter<sensorDataAdapter.MyViewHolder> {

    private ArrayList<dataModel> dataSet;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }

    public sensorDataAdapter(ArrayList<dataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {


            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);

            return myViewHolder;






        //view.setOnClickListener(sensorData.myOnClickListener);

        //view.setOnClickListener(DeviceDataActivity.myOnClickListener);




    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;

        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getName());

        imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}