package com.advisor.agriot.zeon.agriadvisor;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DeviceDataActivity extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<dataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_temp  );
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        Bundle extras = getIntent().getExtras();

            String value = extras.getString("keydevice");

            Log.d("VALUE",value);



            auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=auth.getCurrentUser();

        databaseReference.child(user.getUid()).child("device").child(value).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DeviceData deviceData=dataSnapshot.getValue(DeviceData.class);
                String[] devicedata=new String[4];
                devicedata[0]=deviceData.Temperature;
                devicedata[1]=deviceData.Humidity;
                devicedata[2]=deviceData.Light;
                devicedata[3]=deviceData.Moisture;
//                Iterable<DataSnapshot> device = dataSnapshot.getChildren();
//                ArrayList<String> loop = new ArrayList<String>();
//
//                int j=0;
//                for (DataSnapshot child : device) {
//                    String value = child.getValue().toString();
//                    loop.add(value);
//                    Log.d("Child", loop.toString());
//                    String temprature=child("Temperature").getValue().toString();
//                    Log.d("Temprature",temprature);
//
//                    devicedata[j]=temprature;
//                    j++;
//                    String humidity=child("Humidity").getValue().toString();
//                    devicedata[j]=humidity;
//                    j++;
//                    String light=child.child("Light").getValue().toString();
//                    devicedata[j]=light;
//                    j++;
//                    String moisture=child.child("Moisture").getValue().toString();
//                    devicedata[j]=moisture;
//                    j++;
//                    Log.d("data",devicedata.toString());
//
//
//
//
//
//
//                }
                layoutManager = new LinearLayoutManager(getParent());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                data = new ArrayList<dataModel>();
                for (int i = 0; i < myData.nameArray.length; i++) {
                    data.add(new dataModel(
                            myData.nameArray[i],
                            devicedata[i],
                            myData.id_[i],
                            myData.drawableArray[i]
                    ));
                }

                removedItems = new ArrayList<Integer>();

                adapter = new sensorDataAdapter(data);
                recyclerView.setAdapter(adapter);
            }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });




    }
}
