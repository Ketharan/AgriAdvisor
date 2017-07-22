package com.advisor.agriot.zeon.agriadvisor;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserProfile extends AppCompatActivity implements View.OnClickListener{

    private Button btnsave,btnlogout,btntest;
    private EditText textfirstname,textlastname,textphonenumber,textstreetaddress,textstrretoptional,textcity,textprovince;
    private boolean connected;
    private ConnectivityManager connectivityManager;
    private FirebaseAuth auth;
    private TextView profilename;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        btntest=(Button) findViewById(R.id.btnTest);
        btnlogout=(Button) findViewById(R.id.btnUserprofile_Logout);
        btnsave=(Button) findViewById(R.id.btnSave);
        textfirstname=(EditText) findViewById(R.id.editTextfirstname);
        textlastname=(EditText) findViewById(R.id.editTextlastname);
        textphonenumber=(EditText) findViewById(R.id.editTextphonenumber);
        textstreetaddress=(EditText) findViewById(R.id.editTextstreetaddress);
        textstrretoptional=(EditText) findViewById(R.id.editTextstreetaddressoptional);
        textcity=(EditText) findViewById(R.id.editTextcity);
        textprovince=(EditText) findViewById(R.id.editTextprovince);

        profilename=(TextView)findViewById(R.id.textprofilename);
        auth = FirebaseAuth.getInstance();
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        btnsave.setOnClickListener(this);
        btnlogout.setOnClickListener(this);
        btntest.setOnClickListener(this);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        //check current user
        if(auth.getCurrentUser()== null){
            //LoginActivity
             finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        FirebaseUser user=auth.getCurrentUser();
        profilename.setText(auth.getCurrentUser().getEmail());
        //check internet connection
        connected = false;
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else{
            connected = false;
            Toast.makeText(getApplicationContext(),"There is No Internet connection",Toast.LENGTH_LONG).show();

        }

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
    private void savedata(){
        String firstname=textfirstname.getText().toString().trim();
        String lastname=textlastname.getText().toString().trim();
        String phonenumber=textphonenumber.getText().toString().trim();
        String streetaddress=textstreetaddress.getText().toString().trim();
        String optionalstreetaddress=textstrretoptional.getText().toString().trim();
        String city=textcity.getText().toString().trim();
        String province=textprovince.getText().toString().trim();
        if(TextUtils.isEmpty(firstname)){
            Toast.makeText(getApplicationContext(),"Please Enter First Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(lastname)){
            Toast.makeText(getApplicationContext(),"Please Enter Last Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phonenumber)){
            Toast.makeText(getApplicationContext(),"Please Enter Your Phone Number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(streetaddress)){
            Toast.makeText(getApplicationContext(),"Please Enter Street Address",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(optionalstreetaddress)){
            optionalstreetaddress="NONE";

        }
        if(TextUtils.isEmpty(city)){
            Toast.makeText(getApplicationContext(),"Please Enter Your City",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(province)){
            Toast.makeText(getApplicationContext(),"Please Enter Your Province",Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfileDatabase Userinformation=new UserProfileDatabase(firstname,lastname,phonenumber,streetaddress,optionalstreetaddress,city,province);
        FirebaseUser user=auth.getCurrentUser();


        databaseReference.child(user.getUid()).setValue(Userinformation).addOnCompleteListener(this, new OnCompleteListener<Void>() {
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
            if(v==btntest){
                finish();
                startActivity(new Intent(this,EditUserProfileActivity.class));

            }

        }else{
            Toast.makeText(getApplicationContext(),"Internet conncetion lost.Please CHeck Your Internet connection.",Toast.LENGTH_SHORT).show();
        }

    }
}
