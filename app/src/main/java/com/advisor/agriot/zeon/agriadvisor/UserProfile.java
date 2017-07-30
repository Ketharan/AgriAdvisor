package com.advisor.agriot.zeon.agriadvisor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;


public class UserProfile extends AppCompatActivity implements View.OnClickListener{

    private Button btnsave,btnlogout,btntest;
    private EditText textfirstname,textlastname,textphonenumber,textstreetaddress,textstrretoptional,textcity,textprovince;
    private boolean connected;
    private ConnectivityManager connectivityManager;
    private FirebaseAuth auth;
    private TextView profilename;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    public double longtitude = 0.0;
    public double latitude = 0.0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        final ProgressDialog progress = new ProgressDialog(UserProfile.this);
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
                            UserProfile.this.runOnUiThread(new Runnable() {

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
                    UserProfile.this.runOnUiThread(new Runnable() {

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


    public boolean isLocationDerived(){
        return ((longtitude != 0) && (latitude != 0));
    }


    public class SendRequest extends AsyncTask<String, Void, String> {
        JSONObject postDataParams = new JSONObject();
        protected void onPreExecute() {
            String firstname=textfirstname.getText().toString().trim();
            String lastname=textlastname.getText().toString().trim();
            String phonenumber=textphonenumber.getText().toString().trim();
            String streetaddress=textstreetaddress.getText().toString().trim();
            String optionalstreetaddress=textstrretoptional.getText().toString().trim();
            String city=textcity.getText().toString().trim();
            String province=textprovince.getText().toString().trim();
            gpsGetter.LocationResult locationResult;






            try {
                postDataParams.put("save", "ok");
                postDataParams.put("firstName", firstname);
                postDataParams.put("lastName", lastname);
                postDataParams.put("phoneNumber",phonenumber);
                postDataParams.put("streetAddress", streetaddress);
                postDataParams.put("streetAddress2",optionalstreetaddress);
                postDataParams.put("city",city);
                postDataParams.put("province",province);
                postDataParams.put("latitude", latitude);
                postDataParams.put("longtitude", longtitude);

            } catch (Exception e) {
                //String("Exception: " + e.getMessage());
            }
        }

        public void setParams(){

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://35.187.239.118/Agriot/cropSelection/index.php");

//                JSONObject postDataParams = new JSONObject();
//
//
//
//                postDataParams.put("save", textcity.getText().toString().trim());
//                postDataParams.put("zone", "100");
//                postDataParams.put("season", "abc");
//                postDataParams.put("group", "100");
//                postDataParams.put("crop", "ketha");

                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //Context context = getApplicationC
            //Toast.makeText(getApplicationContext(), result,
            //Toast.LENGTH_LONG).show();

        }


        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
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
//        String firstname=textfirstname.getText().toString().trim();
//        String lastname=textlastname.getText().toString().trim();
//        String phonenumber=textphonenumber.getText().toString().trim();
//        String streetaddress=textstreetaddress.getText().toString().trim();
//        String optionalstreetaddress=textstrretoptional.getText().toString().trim();
//        String city=textcity.getText().toString().trim();
//        String province=textprovince.getText().toString().trim();
//        if(TextUtils.isEmpty(firstname)){
//            Toast.makeText(getApplicationContext(),"Please Enter First Name",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(lastname)){
//            Toast.makeText(getApplicationContext(),"Please Enter Last Name",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(phonenumber)){
//            Toast.makeText(getApplicationContext(),"Please Enter Your Phone Number",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(streetaddress)){
//            Toast.makeText(getApplicationContext(),"Please Enter Street Address",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(optionalstreetaddress)){
//            optionalstreetaddress="NONE";
//
//        }
//        if(TextUtils.isEmpty(city)){
//            Toast.makeText(getApplicationContext(),"Please Enter Your City",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(province)){
//            Toast.makeText(getApplicationContext(),"Please Enter Your Province",Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        UserProfileDatabase Userinformation=new UserProfileDatabase(firstname,lastname,phonenumber,streetaddress,optionalstreetaddress,city,province);
//        FirebaseUser user=auth.getCurrentUser();
//
//
//        databaseReference.child(user.getUid()).setValue(Userinformation).addOnCompleteListener(this, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),"Thank You.Information saved Successfully",Toast.LENGTH_SHORT).show();
//                    finish();
//                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
//
//                }else {
//                    Toast.makeText(getApplicationContext(),"Sorry.Unable to save Your Profile.Please Try Again.",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        new SendRequest().execute();



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
