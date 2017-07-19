package com.advisor.agriot.zeon.agriadvisor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText inputUsername, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin;
    private boolean connected;
    private ConnectivityManager connectivityManager;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }
        //define
        inputUsername=(EditText) findViewById(R.id.username);
        inputPassword=(EditText) findViewById(R.id.password);
        btnSignup=(Button) findViewById(R.id.btn_sign_up);
        btnLogin=(Button) findViewById(R.id.btn_login);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        //set Listener for buttons
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);


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
        private boolean isValidPassword(String password){
            if (password.length()>6){
                return (false);
            }
            return true;
        }



    private void userlogin() {

        String username=inputUsername.getText().toString().trim();
        String password=inputPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) ){
            //email empty
            Toast.makeText(this,"Please enter your user name",Toast.LENGTH_SHORT).show();
            return;

        }
        username=username+"@agriot.com";

        if (isValidPassword(password)){
            //password empty
            Toast.makeText(this,"Password is too short, please enter more than 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Signup Successfly",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Signup Failed, Please Try again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    @Override
    public void onClick(View v) {
        if(isconnected(connectivityManager)){
        if (v==btnLogin){

            userlogin();

        }if (v==btnSignup){

            finish();
            startActivity(new Intent(this,SignupActvity.class));
        }}
        else {
            Toast.makeText(getApplicationContext(), "There is No Internet connection", Toast.LENGTH_LONG).show();

        }
    }
}
