package com.advisor.agriot.zeon.agriadvisor;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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




public class sensorData extends AppCompatActivity implements android.view.View.OnClickListener {


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<dataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;


    //firebase related fields
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.sensor_data);


        //firebase implementation
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();

        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        myOnClickListener = new MyOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        //firebase connectivity for sensor data

        databaseReference.child(user.getUid()).child("device").addValueEventListener(new ValueEventListener() {
            String[] devicedata = new String[5];
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot innerNode : dataSnapshot.getChildren()) {
                    deviceNode devicenode = innerNode.getValue(deviceNode.class);
                    DeviceData deviceData = devicenode.data;

                    devicedata[0] = deviceData.temperature;
                    devicedata[1] = deviceData.humidity;
                    devicedata[2] = deviceData.light;
                    devicedata[3] = deviceData.moisture;
                    devicedata[4] = deviceData.ec;
                    String batteryLevel = devicenode.battery;
                    String lastUpdate = devicenode.lastBroadcast;
                    TextView txtBattery  = (TextView) findViewById(R.id.battery_life);
                    TextView txtLastupdate = (TextView)  findViewById(R.id.time);
                    txtBattery.setText(batteryLevel);
                    txtLastupdate.setText(lastUpdate);
                }





                data = new ArrayList<dataModel>();
                for (int j = 0; j < myData.nameArray.length; j++) {
                    data.add(new dataModel(
                            devicedata[j],
                            myData.versionArray[j],
                            myData.id_[j],
                            myData.drawableArray[j]
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

//        data = new ArrayList<dataModel>();
//        for (int i = 0; i < myData.nameArray.length; i++) {
//            data.add(new dataModel(
//                    myData.nameArray[i],
//                    myData.versionArray[i],
//                    myData.id_[i],
//                    myData.drawableArray[i]
//            ));
//        }
//
//        removedItems = new ArrayList<Integer>();
//
//        adapter = new sensorDataAdapter(data);
//        recyclerView.setAdapter(adapter);

    }




    public void viewButton(stateRepo repo){
        RelativeLayout mainRelativeLayout = new RelativeLayout(this);

        // Defining the RelativeLayout layout parameters with Fill_Parent
        RelativeLayout.LayoutParams relativeLayoutParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        stateVariable stateVar1 = repo.getstateVariableById(0);
        String strButtonCount = stateVar1.state;
        int buttonCount = Integer.parseInt(strButtonCount);
        for (int i = 0; i < buttonCount+1;i++){

            int btnId = i;
            Button btn = new Button(this);
            btn.setId(btnId);
            btn.setOnClickListener(this);
            AddButtonLayout(btn, RelativeLayout.ALIGN_PARENT_BOTTOM);
            mainRelativeLayout.addView(btn);

            // Setting the RelativeLayout as our content view
            setContentView(mainRelativeLayout, relativeLayoutParameters);
        }
    }


    private void AddButtonLayout(Button button, int centerInParent, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        // Defining the layout parameters of the Button
        RelativeLayout.LayoutParams buttonLayoutParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Add Margin to the LayoutParameters
        buttonLayoutParameters.setMargins(marginLeft, marginTop, marginRight, marginBottom);

        // Add Rule to Layout
        buttonLayoutParameters.addRule(centerInParent);

        // Setting the parameters on the Button
        button.setLayoutParams(buttonLayoutParameters);
    }

    private void AddButtonLayout(Button button, int centerInParent) {
        // Just call the other AddButtonLayout Method with Margin 0
        AddButtonLayout(button, centerInParent, 0 ,0 ,0 ,0);
    }
    @Override
    public void onClick(View view){

    }


    private static class  MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeItem(v);
        }

        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < myData.nameArray.length; i++) {
                if (selectedName.equals(myData.nameArray[i])) {
                    selectedItemId = myData.id_[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }
    }


}
