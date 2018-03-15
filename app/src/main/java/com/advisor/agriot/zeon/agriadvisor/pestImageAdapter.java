package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 27/08/2017.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class pestImageAdapter extends RecyclerView.Adapter<pestImageAdapter.MyViewHolder> {

    private ArrayList<pestImage> images;
    static int counter = 0;
    static ArrayList<Integer> typeArray = new ArrayList<Integer>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.pestImage);
        }
    }

    public pestImageAdapter(ArrayList<pestImage> data) {
        this.images = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view;
        MyViewHolder myViewHolder;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imag_cart, parent, false);

        //view.setOnClickListener(currentCrops.myOnClickListener);

        myViewHolder = new MyViewHolder(view);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        ImageView pestImage = holder.imageViewIcon;

//        textViewName.setText(crops.get(listPosition).getName());
//        //textViewVersion.setText(crop.get(listPosition).getVersion());
        Bitmap bmp = BitmapFactory.decodeByteArray(images.get(listPosition).getImage(), 0, images.get(listPosition).getImage().length);


        pestImage.setImageBitmap(Bitmap.createScaledBitmap(bmp,700,
                400, false));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

}