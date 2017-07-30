package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 23/07/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.MyViewHolder> {

    private ArrayList<notification> notificationSet;

    static int counter = 0;
    static Integer[] typeArray = {0,1,2,3,4,5,6,7};

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNotification;

        ImageView imageViewIcon;

        TextView textViewIndicator;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewNotification = (TextView) itemView.findViewById(R.id.notification);

            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.notification_image);
            this.textViewIndicator = (TextView) itemView.findViewById(R.id.textIndicator);
        }
    }

    public notificationAdapter(ArrayList<notification> data) {
        this.notificationSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        MyViewHolder myViewHolder;
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout_main, parent, false);

        myViewHolder = new MyViewHolder(view);
        if (notificationAdapter.typeArray[notificationAdapter.counter]== 0 ) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout_main, parent, false);

            myViewHolder = new MyViewHolder(view);
            notificationAdapter.counter++;
            view.setOnClickListener(MainActivity.notificationlistener);
            return myViewHolder;

        }else if (notificationAdapter.typeArray[notificationAdapter.counter]== 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irrigation, parent, false);
            myViewHolder = new MyViewHolder(view);
            notificationAdapter.counter++;
            view.setOnClickListener(MainActivity.notificationlistener);

            return myViewHolder;
        }else if (notificationAdapter.typeArray[notificationAdapter.counter]== 2) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout_main, parent, false);
            notificationAdapter.counter++;
        }else if (notificationAdapter.typeArray[notificationAdapter.counter]== 3) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout_main, parent, false);
            notificationAdapter.counter++;
        }else if (notificationAdapter.typeArray[notificationAdapter.counter]== 4) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout_main, parent, false);
            notificationAdapter.counter++;
        }else if (notificationAdapter.typeArray[notificationAdapter.counter]== 5) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout_main, parent, false);
            notificationAdapter.counter++;
        }else if (notificationAdapter.typeArray[notificationAdapter.counter]== 6) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout_main, parent, false);
            notificationAdapter.counter++;
        }else if (notificationAdapter.typeArray[notificationAdapter.counter]== 7) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout_main, parent, false);
            notificationAdapter.counter++;
        }else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout_main, parent, false);
            notificationAdapter.counter++;
        }

        return myViewHolder;




        //view.setOnClickListener(DeviceDataActivity.myOnClickListener);




    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewNotification;

        ImageView imageView = holder.imageViewIcon;

        TextView textIndicator = holder.textViewIndicator;


        textViewName.setText(notificationSet.get(listPosition).getNotification());

        imageView.setImageResource(notificationSet.get(listPosition).getImage());

        textIndicator.setText(Integer.toString(notificationSet.get(listPosition).getIndicator()));
    }

    @Override
    public int getItemCount() {
        return notificationSet.size();
    }
}