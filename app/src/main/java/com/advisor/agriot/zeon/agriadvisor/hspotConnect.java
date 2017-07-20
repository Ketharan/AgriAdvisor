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

public class hspotConnect extends ListActivity{
    private Handler handler;

    static WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    ListView list;
    String wifis[];
    private Handler mHandler;
    EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
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
                    hspotConnect.this.runOnUiThread(new Runnable() {

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


                    Intent i = new Intent(hspotConnect.this, confiqureDevice.class);
                    startActivity(i);

                } catch (Exception e) {
                }
            }
        };
        t.start();
        progress.show();



        list=getListView();


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