package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 20/07/2017.
 */

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import static com.advisor.agriot.zeon.agriadvisor.R.id.parent;

public class storeAdapter extends RecyclerView.Adapter<storeAdapter.MyViewHolder> {

    private ArrayList<storeStructure> dataSet;
    static  int counter = 0;
    static Integer[] typeArray = {0,1,2,3,4};


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCrop;
        TextView textViewShop;
        ImageView imageViewIcon;
        TextView textViewDescribtion;
        RatingBar ratingBar;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewCrop = (TextView) itemView.findViewById(R.id.txtCrop);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.textViewDescribtion = (TextView) itemView.findViewById(R.id.txtDescribtion);
            this.textViewShop = (TextView) itemView.findViewById(R.id.txtName);
            this.ratingBar = (RatingBar) itemView.findViewById(R.id.barRating);
        }
    }

    public storeAdapter(ArrayList<storeStructure> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_card, parent, false);
        if (storeAdapter.counter == 2){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.store_card_fert, parent, false);
            storeAdapter.counter++;
        }else if(storeAdapter.counter==3){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.store_cart_pest, parent, false);
            storeAdapter.counter++;
        }else {
            storeAdapter.counter++;
        }
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;






        //view.setOnClickListener(sensorData.myOnClickListener);

        //view.setOnClickListener(DeviceDataActivity.myOnClickListener);




    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewCrop = holder.textViewCrop;

        ImageView imageView = holder.imageViewIcon;

        TextView textViewDescribtion = holder.textViewDescribtion;

        TextView textViewShop = holder.textViewShop;

        RatingBar ratingBar = holder.ratingBar;

        textViewCrop.setText(dataSet.get(listPosition).getCrop());



        imageView.setImageResource(dataSet.get(listPosition).getImage());

        textViewDescribtion.setText(dataSet.get(listPosition).getDescribtion());
        textViewShop.setText(dataSet.get(listPosition).getShop());
        ratingBar.setRating(dataSet.get(listPosition).getRating());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}