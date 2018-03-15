package com.advisor.agriot.zeon.agriadvisor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;


import java.util.ArrayList;

public class imageActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<pestImage> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    private ArrayList<DataSnapshot> snapsArray;

    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private String selectedPart;
    private String selectedPlant;

    TextView txtQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        recyclerView = (RecyclerView) findViewById(R.id.rv_pest_image);
        Log.d("imageActivity", "datashot");

        //get previous data
        Bundle extras = getIntent().getExtras();
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        if(extras !=null) {
            selectedPart = extras.getString("selectedPart");
            selectedPlant = extras.getString("selectedPlant");

            Log.d("selectedPart",selectedPart);

        }



        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //firebase
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        databaseReference = firebaseDatabase.getReference();

        data = new ArrayList<pestImage>();
        databaseReference.child("pestManagement"+"/"+selectedPlant).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> datashot = dataSnapshot.getChildren();
                snapsArray = new ArrayList<DataSnapshot>();
                for (DataSnapshot child:datashot){
                    snapsArray.add(child);
                }

                Log.d("Hello", "datashot");


                Iterable<DataSnapshot> innerData = snapsArray.get(0).getChildren();
                String question = snapsArray.get(0).getKey();
                Log.d("question", question);
                txtQuestion.setText(question);
                Iterable<DataSnapshot> innerImages = snapsArray.get(0).getChildren();
                for (DataSnapshot child:innerImages){
                    if (child.getKey().equals(selectedPart)){
                        Log.d("ques", question);
                        Iterable<DataSnapshot> finalArray = child.getChildren();
                        for (DataSnapshot finChild:finalArray){
                            String imageAddress = finChild.getValue().toString();
                            Log.d("location",imageAddress);
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference islandRef = storageRef.child(imageAddress);


                            final long ONE_MEGABYTE = 1024 * 1024;
                            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {

                                    data.add(new pestImage(
                                            "image",
                                            bytes
                                    ));

                                    removedItems = new ArrayList<Integer>();

                                    adapter = new pestImageAdapter(data);
                                    recyclerView.setAdapter(adapter);




                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }


                    }



                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Button submit = (Button) findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(imageActivity.this,pesticide.class));
            }
        });


    }
}
