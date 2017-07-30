package com.advisor.agriot.zeon.agriadvisor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import android.util.Log;
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
import java.util.List;

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







public class hspotConnect extends AppCompatActivity implements View.OnClickListener{
    private Handler handler;

    static WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    ListView list;
    String wifis[];
    private Handler mHandler;
    EditText pass;
    EditText deviceName;
    EditText deviceId;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_connect);

        deviceName = (EditText) findViewById(R.id.device_name);
        deviceId = (EditText) findViewById(R.id.device_id);


        Button btnConnect;
        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnConnect.setOnClickListener(this);
// list=getListView();


    }



    public class SendRequest extends AsyncTask<String, Void, String> {
        JSONObject postDataParams = new JSONObject();
        protected void onPreExecute() {

            String id = deviceId.getText().toString().trim();
            String name = deviceName.getText().toString().trim();


            try {
                postDataParams.put("save", "ok");
                postDataParams.put("deviceId", id);
                postDataParams.put("deviceName", name);


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


    @Override
    public  void onClick(View v){

        String id = deviceId.getText().toString().trim();
        String name = deviceName.getText().toString().trim();

        if(validateId(id) && validateName(name)){
            new SendRequest().execute();
            String SSID = getSSID(id);
            String password = getPassword(id);
            final ProgressDialog progress = new ProgressDialog(hspotConnect.this);

            progress.setTitle("Trying to Connect");
            progress.setMessage("Searching for device.... Be patient this may take some time.....");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            mainWifiObj = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    mainWifiObj.setWifiEnabled(true);
                    wifiReciever = new WifiScanReceiver();
                    mainWifiObj.startScan();
                }
            }).start();


            final Thread t = new Thread() {
                Object lock = new Object();

                int counter = 1;
                @Override
                public void run() {
                    try {
                        //check if connected!
                        while (!isConnected(hspotConnect.this)) {
                            //Wait to connect
                            counter++;
                            if(counter > 25){
                                progress.dismiss();
                                hspotConnect.this.runOnUiThread(new Runnable() {

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
                        hspotConnect.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {



                                final Toast toast;
                                CharSequence message = "connection Successful";
                                int duration = Toast.LENGTH_LONG;
                                Context context = getApplicationContext();
                                toast = Toast.makeText(context,message,duration);
                                toast.show();




                            }
                        });


                        Intent i = new Intent(hspotConnect.this, confiqureDevice.class);
                        startActivity(i);

                    } catch (Exception e) {
                    }
                }
            };
            t.start();
            progress.show();


        }else if(!validateName(name)){

            Toast.makeText(getApplicationContext(),"Name should contain more than four letters",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(),"Invalid Id",Toast.LENGTH_SHORT).show();
        }


    }
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }





    protected void onPause() {
        unregisterReceiver(wifiReciever);
        super.onPause();
    }

    protected void onResume() {
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }


    class WifiScanReceiver extends BroadcastReceiver {

        String SSID;
        String password;
        @SuppressLint("UseValueOf")
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
            wifis = new String[wifiScanList.size()];

            for (int i = 0; i < wifiScanList.size(); i++) {
                if (Objects.equals(wifiScanList.get(i).SSID, "Dialog4G")){
                    SSID = "Dialog4G";
                    password = "8GRD8MD7Y0H";
                    finallyConnect(password, SSID);
                    unregisterReceiver(this);

                    break;
                }
            }


        }


    }
    public void onBackPressed(){
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        overridePendingTransition(R.anim.slide_in,R.anim.silde_out);

    }

    private void finallyConnect(String networkPass, String networkSSID) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", networkSSID);
        wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);

        //remember id
        int netId = mainWifiObj.addNetwork(wifiConfig);
        mainWifiObj.disconnect();
        mainWifiObj.enableNetwork(netId, true);
        mainWifiObj.reconnect();



        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"\"" + networkSSID + "\"\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        mainWifiObj.addNetwork(conf);
    }

    private  boolean validateId(String id){
        if (id.length() == 15){
            return true;
        }
        else return false;

    }
    private boolean validateName(String name){
        if (name.length()==0){
            return false;
        }else{
            return true;
        }
    }
    private String getSSID(String id){
        return id.substring(0,10);
    }

    private String getPassword(String id){
        return id.substring(7,14);
    }
}