package com.advisor.agriot.zeon.agriadvisor;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class pestInitial extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<currentCrop> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    private ArrayList<DataSnapshot> snapArray;

    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private int pestProgress;
    private TextView txtQuestion;
    private Button btnNext;



    private String selectedPlant;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_initial);
        pestProgress =0;
        myOnClickListener = new MyOnClickListener(this);


        //text for question
        snapArray = new ArrayList<DataSnapshot>();
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);




        //nextButton
        btnNext = (Button) findViewById(R.id.btnSubmit);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),imageActivity.class));
                Intent intent = new Intent(pestInitial.this,pestPart.class);
                intent.putExtra("selectedPlant",selectedPlant);
                startActivity(intent);


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
                    pestInitialAdapter.typeArray.add(1);


                    removedItems = new ArrayList<Integer>();

                    adapter = new pestInitialAdapter(data);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        loadData();
    }

    private class MyOnClickListener implements View.OnClickListener {




        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            int childCount = recyclerView.getAdapter().getItemCount();
            CardView card;
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(0);
            for (int i=0;i < childCount;i++) {
                viewHolder
                        = recyclerView.findViewHolderForPosition(i);
                card = (CardView) viewHolder.itemView.findViewById(R.id.pest_part);
                card.setCardBackgroundColor(getResources().getColor(R.color.pesticide));

            }


            viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            card = (CardView) viewHolder.itemView.findViewById(R.id.pest_part);
            card.setCardBackgroundColor(Color.WHITE);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            selectedPlant = (String) textViewName.getText();
//            TextView textViewName
//                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
//            String cropName = (String) textViewName.getText();
//            Intent intent = new Intent(currentCrops.this,cropSelect.class);
//            intent.putExtra("crop",cropName);
//            startActivity(intent);
        }


    }


    //CODE FOR LOADING PEST CONTROL DETAILS
//    public void loadData(){
//        snapArray.clear();
//        databaseReference.child("pestManagement").child(selectedPlant).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> datashot = dataSnapshot.getChildren();
//
//                for (DataSnapshot child:datashot){
//                    snapArray.add(child);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }

//    public void toNext(){
//        data.clear();
//        currentCropAdapter.counter = 0;
//        DataSnapshot innerData = snapArray.get(pestProgress);
//        String question = innerData.getKey();
//        txtQuestion.setText(question);
//        Iterable<DataSnapshot> answers = innerData.getChildren();
//
//
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        for (DataSnapshot child : answers) {
//
//            String answer = child.getKey();
//
//            int drawableIndex = 0;
//            for (int j = 0; j < cropData.cropArray.length; j++) {
//                if (answer.equals(cropData.cropArray[j])) {
//                    drawableIndex = j;
//                    break;
//                }
//            }
//
//            data.add(new currentCrop(
//                    answer,
//                    cropData.drawableArray[drawableIndex]
//            ));
//            currentCropAdapter.typeArray.add(1);
//
//
//
//        }
//        removedItems = new ArrayList<Integer>();
//
//        adapter = new currentCropAdapter(data);
//
//        recyclerView.setAdapter(adapter);
//    }





}





















