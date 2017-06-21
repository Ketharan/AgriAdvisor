package com.advisor.agriot.zeon.agriadvisor;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActvity extends AppCompatActivity implements View.OnClickListener{

    //creating instances
    private EditText inputUsername,inputPassword,inputConfirmpassword;
    private Button btnSignin,btnSignup;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private boolean connected;
    private ConnectivityManager connectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_actvity);

        // firebase auth instance
        auth = FirebaseAuth.getInstance();
        //btn,text definition
        btnSignin = (Button) findViewById(R.id.sign_in_button);
        btnSignup = (Button) findViewById(R.id.sign_up_button);
        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        inputConfirmpassword = (EditText) findViewById(R.id.confirmpassword);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);



        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);





        if(auth.getCurrentUser()!= null){
            //profileActivity
           finish();
           startActivity(new Intent(getApplicationContext(),UserProfile.class));
        }
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


    private void registeruser(){
        String username=inputUsername.getText().toString().trim();
        String confirmpassword=inputConfirmpassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(),"Please Enter User Name",Toast.LENGTH_SHORT).show();
            return;
        }
        username=username+"@agriot.com";
        String password=inputPassword.getText().toString().trim();
        if (isValidPassword(password)){
            //password empty
            Toast.makeText(this,"Password is too short, Try More than 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(confirmpassword)){
            Toast.makeText(getApplicationContext(),"Please Confirm Your Password",Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.compareTo(confirmpassword)!=0){
            //password empty
            Toast.makeText(this,"Password mismatch.Please Confirm Your Password Correctly",Toast.LENGTH_SHORT).show();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Registered Successfly",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),UserProfile.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Registered Failed, Please Try again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        if(isconnected(connectivityManager)) {
            if (v == btnSignup) {

                    registeruser();



            }
            if (v == btnSignin) {
                finish();
                startActivity(new Intent(this, LoginActivity.class));
            }
        }else{
            Toast.makeText(getApplicationContext(), "There is No Internet connection", Toast.LENGTH_LONG).show();

        }
    }
    }

    // now we have to do LoginActivity class and profile class and Main class

//        //function for signin button to open login activity newest one
//        btnSignin.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                //startActivity(new Intent(SignupActvity.this,LoginActivity.class));
//                finish();
//            }
//        });


//        //function for signup button
//        btnSignup.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                String username=inputUsername.getText().toString().trim();
//                String password=inputPassword.getText().toString().trim();
//                String confirmpassword= inputConfirmpassword.getText().toString().trim();
//
//                if(TextUtils.isEmpty(username)){
//                    Toast.makeText(getApplicationContext(),"Please Enter User Name",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(password)){
//                    Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(confirmpassword)){
//                    Toast.makeText(getApplicationContext(),"Please Confirm Your Password",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(password.length()<6){
//                    Toast.makeText(getApplicationContext(),"Password is short. Please Enter minimum 6 Characters",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(password.compareTo(confirmpassword)!=0){
//                    Toast.makeText(getApplicationContext(),"Password mismatch. Please confirm Your Password correctly",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//create user
//                auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(SignupActvity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(getApplicationContext(),"Create User:on Complete:0 "+ task.isSuccessful(),Toast.LENGTH_SHORT ).show();
//                        progressBar.setVisibility(View.GONE);
//                        //logic for task successful and un successful
//                        if (!task.isSuccessful()){
//                            Toast.makeText(getApplicationContext(),"Authentication Failed!"+task.getException(),Toast.LENGTH_SHORT).show();
//                        }else{
//                            //startActivity(new Intent(SignupActvity.this,UserProfileActivity.class));
//                            //finish();
//                            Toast.makeText(getApplicationContext(),"Done correctly",Toast.LENGTH_SHORT).show();
//
//
//                        }
//
//                    }
//                });


//            }
//        });
//
//    }
//    @Override
//    protected void onResume(){
//       super.onResume();
//       progressBar.setVisibility(View.GONE);

//}