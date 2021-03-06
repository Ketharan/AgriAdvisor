package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 21/07/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class pestPartsAdapter extends RecyclerView.Adapter<pestPartsAdapter.MyViewHolder> {

    private ArrayList<currentCrop> crops;
    static int counter = 0;
    static ArrayList<Integer> typeArray = new ArrayList<Integer>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        //TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public pestPartsAdapter(ArrayList<currentCrop> data) {
        this.crops = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {

        View view;
        MyViewHolder myViewHolder;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pestpart_cart, parent, false);

        view.setOnClickListener(pestPart.myOnClickListener);

        myViewHolder = new MyViewHolder(view);

//        if (currentCropAdapter.typeArray.get(currentCropAdapter.counter)== 1 ) {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.cart_crop_g1, parent, false);
//
//            myViewHolder = new MyViewHolder(view);
//            currentCropAdapter.counter++;
//            view.setOnClickListener(currentCrops.myOnClickListener);
//            return myViewHolder;
//
//        }else if (currentCropAdapter.typeArray.get(currentCropAdapter.counter)== 2) {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.carts_crop_g2, parent, false);
//            myViewHolder = new MyViewHolder(view);
//            currentCropAdapter.counter++;
//            view.setOnClickListener(currentCrops.myOnClickListener);
//            return myViewHolder;
//        }else if (currentCropAdapter.typeArray.get(currentCropAdapter.counter)== 3) {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.carts_crop_g3, parent, false);
//            myViewHolder = new MyViewHolder(view);
//            currentCropAdapter.counter++;
//            view.setOnClickListener(currentCrops.myOnClickListener);
//            return myViewHolder;
//        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        //TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(crops.get(listPosition).getName());
        //textViewVersion.setText(crop.get(listPosition).getVersion());
        imageView.setImageResource(crops.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return crops.size();
    }

    public void clear() {
        int size = this.crops.size();
        this.crops.clear();
        notifyItemRangeRemoved(0, size);
    }
}