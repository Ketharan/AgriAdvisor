package com.advisor.agriot.zeon.agriadvisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MydevicesActivitty extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private ListView listView;
    private ArrayList<String> mydevices;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydevices);

//        textView=(TextView)findViewById(R.id.textView_mydevices);
        mydevices=new ArrayList<>();
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        listView=(ListView) findViewById(R.id.listview_mydevices);
        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this,R.layout.activity_mydevices,mydevices);
        listView.setAdapter(arrayAdapter);
        if(auth.getCurrentUser()== null){
            //LoginActivity
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        FirebaseUser user=auth.getCurrentUser();




        databaseReference.child(user.getUid()).child("device").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> device= dataSnapshot.getChildren();
                ArrayList<String> loop = new ArrayList<String>();
                for (DataSnapshot child: device){
                    String value=child.getValue().toString();
                    loop.add(value);
                    Log.d("Child",loop.toString());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child(user.getUid()).child("crop").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> device= dataSnapshot.getChildren();
                ArrayList<String> loop = new ArrayList<String>();
                for (DataSnapshot child: device){
                    String key=child.getKey().toString();
                    String value=child.child("period").getValue().toString();
                    loop.add(key);
                    loop.add(value);
                    Log.d("Crop",loop.toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        databaseReference.child(user.getUid()).child("device").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String value=dataSnapshot.getValue(String.class);
//                mydevices.add(value);
//                arrayAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }
}
