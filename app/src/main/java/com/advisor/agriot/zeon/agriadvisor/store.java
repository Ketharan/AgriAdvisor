package com.advisor.agriot.zeon.agriadvisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class store extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<storeStructure> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private  String cropName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);

        //myOnClickListener = new MyOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            cropName = extras.getString("crop");

            Log.d("cropName",cropName);

        }

        recyclerView = (RecyclerView) findViewById(R.id.rv_store);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<storeStructure>();
        for (int i = 0; i < storeData.shopArray.length; i++) {
            data.add(new storeStructure(
                    cropName,
                    storeData.shopArray[i],
                    storeData.describtionArray[i],
                    storeData.starCount_[i],
                    storeData.imageArray[i]
            ));
        }

        removedItems = new ArrayList<Integer>();

        adapter = new storeAdapter(data);
        recyclerView.setAdapter(adapter);
        storeAdapter.counter =0;
    }
}
