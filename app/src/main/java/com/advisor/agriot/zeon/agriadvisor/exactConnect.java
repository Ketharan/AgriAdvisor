package com.advisor.agriot.zeon.agriadvisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class exactConnect extends AppCompatActivity {

    private Handler handler;

    static WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    ListView list;
    String wifis[];
    private Handler mHandler;
    EditText pass;
    public static String SSID;
    public String password;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exact_connect);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            id = extras.getString("id");

            Log.d("id",id);
        }
        SSID = id.substring(0,8);
        password = id.substring(8,19);
        Log.d("ssid",SSID);
        Log.d("password",password);


        final ProgressDialog progress = new ProgressDialog(exactConnect.this);

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
                    while (!isConnected(exactConnect.this)) {
                        //Wait to connect
                        counter++;
                        if(counter > 25){
                            progress.dismiss();
                            exactConnect.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {



                                    final Toast toast;
                                    CharSequence message = "connection Failed, Retry";
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
                    exactConnect.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {



                            final Toast toast;
                            CharSequence message = "connection Succesful";
                            int duration = Toast.LENGTH_LONG;
                            Context context = getApplicationContext();
                            toast = Toast.makeText(context,message,duration);
                            toast.show();




                        }
                    });


                    Intent i = new Intent(exactConnect.this, confiqureDevice.class);
                    startActivity(i);

                } catch (Exception e) {
                }
            }
        };
        t.start();
        progress.show();

    }

    public static boolean isConnected(Context context) {
        String ssid = "";
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        WifiInfo wifiInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
            wifiInfo = mainWifiObj.getConnectionInfo();
            ssid = wifiInfo.getSSID();
            Log.d("ssidNew",ssid );
        }


        return networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED && ssid.equals(String.format("\"%s\"", SSID));
    }







    protected void onResume() {
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }


    class WifiScanReceiver extends BroadcastReceiver {

//        String SSID;
//        String password;
        @SuppressLint("UseValueOf")
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
            wifis = new String[wifiScanList.size()];

            for (int i = 0; i < wifiScanList.size(); i++) {
                if (Objects.equals(wifiScanList.get(i).SSID, SSID)){

                    finallyConnect(password, SSID);
                    unregisterReceiver(this);

                    break;
                }
            }


        }


    }

    private void finallyConnect(String networkPass, String networkSSID) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", networkSSID);
        wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);

        // remember id
        int netId = mainWifiObj.addNetwork(wifiConfig);
        mainWifiObj.disconnect();
        mainWifiObj.enableNetwork(netId, true);
        mainWifiObj.reconnect();



        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"\"" + networkSSID + "\"\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        mainWifiObj.addNetwork(conf);
    }


}
