package com.advisor.agriot.zeon.agriadvisor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class currentCrops extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<currentCrop> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_crops);

        myOnClickListener = new MyOnClickListener(this);


        ImageView addCrop = (ImageView) findViewById(R.id.add_crop);
        addCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),cropSuitability.class));
            }
        });

        //firebase
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        databaseReference = firebaseDatabase.getReference();


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();





        recyclerView = (RecyclerView) findViewById(R.id.rv_current_crops);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        databaseReference.child(user.getUid()).child("currentCrops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> datashot = dataSnapshot.getChildren();

//               ArrayList<String> KEY =new ArrayList<String>();
//               ArrayList<String > DATA =new ArrayList<String>();
                Log.d("Hello", "datashot");

                data = new ArrayList<currentCrop>();
                for (DataSnapshot child : datashot) {

                    String cropName = child.getKey();

                    int drawableIndex = 0;
                    for (int j = 0; j < cropData.cropArray.length; j++) {
                        if (cropName.equals(cropData.cropArray[j])) {
                            drawableIndex = j;
                            break;
                        }
                    }

                    data.add(new currentCrop(
                            cropName,
                            cropData.drawableArray[drawableIndex]
                    ));
                    currentCropAdapter.typeArray.add(1);


                    removedItems = new ArrayList<Integer>();

                    adapter = new currentCropAdapter(data);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private class MyOnClickListener implements View.OnClickListener {




        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String cropName = (String) textViewName.getText();
            Intent intent = new Intent(currentCrops.this,cropSelect.class);
            intent.putExtra("crop",cropName);
            startActivity(intent);
        }


    }


}
