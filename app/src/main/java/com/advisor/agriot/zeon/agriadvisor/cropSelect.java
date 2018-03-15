package com.advisor.agriot.zeon.agriadvisor;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class cropSelect extends AppCompatActivity {

    //firebase
    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    private FirebaseUser user;


    private TextView data00;
    private TextView data01;
    private TextView data02;
    private TextView data10;
    private TextView data11;
    private TextView data20;
    private TextView data21;
    private TextView data30;
    private String cropName;
    private TextView heading;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropselect);

        //firebase initial
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        ref = firebaseDatabase.getReference();




        databaseReference= firebaseDatabase.getReference();
        data00 = (TextView) findViewById(R.id.data00);
        data01 = (TextView) findViewById(R.id.data01);
        data02 = (TextView) findViewById(R.id.data02);
        data10 = (TextView) findViewById(R.id.data10);
        data11 = (TextView) findViewById(R.id.data11);
        data20 = (TextView) findViewById(R.id.data20);
        data21 = (TextView) findViewById(R.id.data21);
        data30 = (TextView) findViewById(R.id.data30);
        heading = (TextView) findViewById(R.id.txt_headcropselect);


        //get data from intent
//        Bundle extras = getIntent().getExtras();
//
//        String cropName = extras.getString("cropName");
//        Log.d("crop",cropName);



        //publishing data

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            cropName = extras.getString("crop");

            Log.d("cropName",cropName);
            heading.setText(cropName + " -> Crop Selection");
        }
        DatabaseReference cropDetails =  databaseReference.child("cropDetails").child(cropName);
        cropDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cropDet cropdetails = dataSnapshot.getValue(cropDet.class);

                data00.setText(cropdetails.data00);
                data01.setText(cropdetails.data01);
                data02.setText(cropdetails.data02);
                data10.setText(cropdetails.data10);
                data11.setText(cropdetails.data11);
                data20.setText(cropdetails.data20);
                data21.setText(cropdetails.data21);
                data30.setText(cropdetails.data30);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        Button storeBtn = (Button) findViewById(R.id.goStore);
        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cropSelect.this,store.class);
                intent.putExtra("crop",cropName);
                startActivity(intent);
                startActivity(intent);

            }
        });

        final Button btnAdd = (Button) findViewById(R.id.btnAddCrop);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence indicator = btnAdd.getText();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());
                if (indicator.equals("ADD")){

                    ref.child(user.getUid()).child("currentCrops").child(cropName).child("startDate").setValue(formattedDate);

                    btnAdd.setText("REMOVE");


                    btnAdd.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove_from_queue_black_24dp,0,0,0);

                } else {
                    ref.child(user.getUid()).child("currentCrops").child(cropName).child("startDate").setValue(null);
                    btnAdd.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_black_24dp,0,0,0);
                    btnAdd.setText("ADD");
                }

            }
        });
    }






}
