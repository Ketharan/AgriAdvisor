//package com.advisor.agriot.zeon.agriadvisor;
//
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.content.Context;
//
//import android.support.v7.widget.RecyclerView;
//
//import android.widget.TextView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//
//public class DeviceDataActivity extends AppCompatActivity {
//    private static RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private static RecyclerView recyclerView;
//    private static ArrayList<dataModel> data;
//    static View.OnClickListener myOnClickListener;
//    private static ArrayList<Integer> removedItems;
//    private FirebaseAuth auth;
//    private DatabaseReference databaseReference;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_temp  );
//        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
//
//        Bundle extras = getIntent().getExtras();
//
//            String value = extras.getString("keydevice");
//
//            Log.d("VALUE",value);
//
//
//
//            auth=FirebaseAuth.getInstance();
//        databaseReference= FirebaseDatabase.getInstance().getReference();
//        FirebaseUser user=auth.getCurrentUser();
//
//        databaseReference.child(user.getUid()).child("device").child(value).addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                DeviceData deviceData=dataSnapshot.getValue(DeviceData.class);
//                String[] devicedata=new String[4];
//                devicedata[0]=deviceData.Temperature;
//                devicedata[1]=deviceData.Humidity;
//                devicedata[2]=deviceData.Light;
//                devicedata[3]=deviceData.Moisture;
//
//
//            }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//
//            });
//
//
//
//
//    }
//}
