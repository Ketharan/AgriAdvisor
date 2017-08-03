package com.advisor.agriot.zeon.agriadvisor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Message;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import android.net.NetworkInfo;
import android.widget.ProgressBar;
import android.net.wifi.WifiInfo;
import 	android.net.wifi.SupplicantState;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;







public class hspotConnect extends AppCompatActivity{
    private Handler handler;


    ListView list;
    String wifis[];
    private Handler mHandler;
    EditText pass;
    TextView deviceName;
    TextView deviceId;

    private FirebaseAuth auth;

    private DatabaseReference ref;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Button connect = (Button) findViewById(R.id.btn_connect);

        //firebase connection
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


        deviceName = (EditText) findViewById(R.id.device_name);
        deviceId = (EditText) findViewById(R.id.device_id);

        connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String id = deviceId.getText().toString().trim();
                String name = deviceName.getText().toString().trim();
                if(validateId(id) && validateName(name)){
                    DeviceData devdata = new DeviceData("28 C","87 %","1500L","48 %","21 S");
                    deviceNode dn = new deviceNode(devdata,"Just now","","Null","70%","Null","Null");
                    ref = firebaseDatabase.getReference();
                    auth = FirebaseAuth.getInstance();
                    user=auth.getCurrentUser();
                    DatabaseReference devRef =  ref.child(user.getUid()).child("device").child(id);
                    devRef.setValue(dn);
                    Intent i = new Intent(hspotConnect.this, exactConnect.class);
                    i.putExtra("id",id);
                    startActivity(i);



                }else if(!validateName(name)){

                    Toast.makeText(getApplicationContext(),"Name should contain more than four letters",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"Invalid Id",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private  boolean validateId(String id){
        return  (id.length() == 19);

    }
    private boolean validateName(String name){
        return  !(name.length()==0);
    }

}