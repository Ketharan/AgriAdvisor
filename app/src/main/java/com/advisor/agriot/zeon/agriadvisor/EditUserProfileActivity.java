package com.advisor.agriot.zeon.agriadvisor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.location.Location;

public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnsave,btnlogout;
    private EditText textfirstname,textlastname,textphonenumber,textstreetaddress,textstrretoptional,textcity,textprovince;
    private boolean connected;
    private ConnectivityManager connectivityManager;
    private FirebaseAuth auth;
    private TextView profilename;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private ArrayList<String> arrayList;
    public double longtitude = 0.0;
    public double latitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        final ProgressDialog progress = new ProgressDialog(EditUserProfileActivity.this);
        progress.setTitle("Location finding....");
        progress.setMessage("Trying to get the location");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        //getting gps location
        gpsGetter.LocationResult locationResult;
        gpsGetter myGps = new gpsGetter();

        locationResult = new gpsGetter.LocationResult(){
            @Override
            public void gotLocation(Location location){
                longtitude = location.getLongitude();
                latitude = location.getLatitude();
            }

        };
        myGps.getLocation(this, locationResult);




        //waiting for GPS location
        final Thread t = new Thread() {
            Object lock = new Object();

            int counter = 1;
            @Override
            public void run() {
                try {
                    //check if connected!
                    while (!isLocationDerived()) {
                        //Wait to connect
                        counter++;
                        if(counter > 25){
                            progress.dismiss();
                            EditUserProfileActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {



                                    final Toast toast;
                                    CharSequence message = "Connection Failed, Retry";
                                    int duration = Toast.LENGTH_LONG;
                                    Context context = getApplicationContext();
                                    toast = Toast.makeText(context,message,duration);
                                    toast.show();






                                }
                            });
                            this.destroy();
                        }
                        Thread.sleep(1000);
                    }

                    //progress.setMessage("Connected!!");
                    progress.dismiss();
                    EditUserProfileActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Location derived",Toast.LENGTH_SHORT).show();
                        }
                    });




                } catch (Exception e) {
                }
            }
        };
        t.start();
        progress.show();


        btnlogout=(Button) findViewById(R.id.btnedituserprofile__Logout);
        btnsave=(Button) findViewById(R.id.btnedituserprofile_Save);
        textfirstname=(EditText) findViewById(R.id.editTextedituserprofile_firstname);
        textlastname=(EditText) findViewById(R.id.editTextedituserprofile_lastname);
        textphonenumber=(EditText) findViewById(R.id.editTextedituserprofile_phonenumber);
        textstreetaddress=(EditText) findViewById(R.id.editTextedituserprofile_streetaddress);
        textstrretoptional=(EditText) findViewById(R.id.editTextedituserprofile_streetaddressoptional);
        textcity=(EditText) findViewById(R.id.editTextedituserprofile_city);
        textprovince=(EditText) findViewById(R.id.editTextedituserprofile_province);


        profilename=(TextView)findViewById(R.id.edituserprofile_textprofilename);
        auth = FirebaseAuth.getInstance();
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        btnsave.setOnClickListener(this);
        btnlogout.setOnClickListener(this);

        arrayList=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference();

        //check current user
        if(auth.getCurrentUser()== null){
            //LoginActivity
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        FirebaseUser user=auth.getCurrentUser();
        profilename.setText(auth.getCurrentUser().getEmail());

        databaseReference.child(user.getUid()).child("userDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfileDatabase userProfileDatabase=dataSnapshot.getValue(UserProfileDatabase.class);
                textfirstname.setText(userProfileDatabase.firstName);
                textlastname.setText(userProfileDatabase.lastName);
                textcity.setText(userProfileDatabase.city);
                textphonenumber.setText(userProfileDatabase.phoneNumber);
                textprovince.setText(userProfileDatabase.province);
                textstreetaddress.setText(userProfileDatabase.streetAddress);
                textstrretoptional.setText(userProfileDatabase.streetAddress2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public boolean isLocationDerived(){
        return ((longtitude != 0) && (latitude != 0));
    }


    private void savedata(){
        String firstName=textfirstname.getText().toString().trim();
        String lastName=textlastname.getText().toString().trim();
        String phoneNumber=textphonenumber.getText().toString().trim();
        String streetAddress=textstreetaddress.getText().toString().trim();
        String streetAddress2=textstrretoptional.getText().toString().trim();
        String city=textcity.getText().toString().trim();
        String province=textprovince.getText().toString().trim();
        if(TextUtils.isEmpty(firstName)){
            Toast.makeText(getApplicationContext(),"Please Enter First Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(lastName)){
            Toast.makeText(getApplicationContext(),"Please Enter Last Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(getApplicationContext(),"Please Enter Your Phone Number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(streetAddress)){
            Toast.makeText(getApplicationContext(),"Please Enter Street Address",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(streetAddress2)){
            streetAddress2="NONE";

        }
        if(TextUtils.isEmpty(city)){
            Toast.makeText(getApplicationContext(),"Please Enter Your City",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(province)){
            Toast.makeText(getApplicationContext(),"Please Enter Your Province",Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfileDatabase Userinformation=new UserProfileDatabase(firstName,lastName,phoneNumber,streetAddress,streetAddress2,city,province,latitude,longtitude,"WL3","NULL");
        FirebaseUser user=auth.getCurrentUser();


        databaseReference.child(user.getUid()).child("userDetails").setValue(Userinformation).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Thank You.Information saved Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }else {
                    Toast.makeText(getApplicationContext(),"Sorry.Unable to save Your Profile.Please Try Again.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private boolean isconnected(ConnectivityManager connectivityManager){
        connected = false;
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else{
            connected = false;

        }
        return  connected;

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }

    @Override
    public void onClick(View v) {
        if(isconnected(connectivityManager)){
            if (v==btnsave){
                savedata();
            }
            if(v==btnlogout){
                auth.signOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
            }

        }else{
            Toast.makeText(getApplicationContext(),"Internet conncetion lost.Please CHeck Your Internet connection.",Toast.LENGTH_SHORT).show();
        }
    }
}
