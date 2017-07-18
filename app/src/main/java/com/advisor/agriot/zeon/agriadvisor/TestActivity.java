package com.advisor.agriot.zeon.agriadvisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
        private TextView txtview;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        txtview=(TextView)findViewById(R.id.test);
        auth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        FirebaseUser user=auth.getCurrentUser();
        arrayList=new ArrayList<>();
        //databaseReference.child(user.getUid()).child("province").setValue("KAndermadam");
        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserProfileDatabase userProfileDatabase =dataSnapshot.getValue(UserProfileDatabase.class);
                txtview.setText(userProfileDatabase.city);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
