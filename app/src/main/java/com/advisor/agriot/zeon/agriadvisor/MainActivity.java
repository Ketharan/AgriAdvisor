package com.advisor.agriot.zeon.agriadvisor;

import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.Space;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

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


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<notification> data;
    static View.OnClickListener notificationlistener;
    private static ArrayList<Integer> removedItems;


    //mini sensor dara
    private static RecyclerView.Adapter adapterMini;
    private RecyclerView.LayoutManager layoutManagerMini;
    private static RecyclerView recyclerViewMini;
    private static ArrayList<dataModel> dataMini;

    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItemsMini;
    private ArrayList<String> KEY ;
    private ArrayList<String > DATAS ;

    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private String agroZone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //recycler view for notification
        notificationlistener = new notificationListener(getParent());
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getParent());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());






        databaseReference= firebaseDatabase.getReference();

//        KEY =new ArrayList<String>();
//        DATAS =new ArrayList<String>();

        //firebaseDatabase.setPersistenceEnabled(tr
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        final TextView username =(TextView) findViewById(R.id.username);
        DatabaseReference name =  databaseReference.child(user.getUid()).child("userDetails").child("firstName");
        name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                username.setText("Hi," + name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        DatabaseReference ref =  databaseReference.child(user.getUid()).child("userDetails").child("agroZone");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                agroZone = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

//        DatabaseReference notificationRef = databaseReference.child(user.getUid()).child("notifications");
//        notificationRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot notification: dataSnapshot.getChildren()) {
//                    username.setText(notification.getValue(String.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });

       databaseReference.child(user.getUid()).child("notification").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Iterable<DataSnapshot> datashot= dataSnapshot.getChildren();

//               ArrayList<String> KEY =new ArrayList<String>();
//               ArrayList<String > DATA =new ArrayList<String>();
               Log.d("Hello","datashot");
               int i = 0;
               data = new ArrayList<notification>();
               for(DataSnapshot child:datashot){

                   String key=child.getKey();
                   String datas=child.getValue().toString();



                   data.add(new notification(datas,myNotifications.imageArray[Integer.valueOf(key)],Integer.valueOf(key)));

                   notificationAdapter.typeArray[i] = Integer.valueOf(key);
                   i+=1;
               }
               removedItems = new ArrayList<Integer>();
//
               adapter = new notificationAdapter(data);
               recyclerView.setAdapter(adapter);



           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });




//        String[] Datastring =new String[DATA.size()];
//        for(int j=0;j<DATA.size();j++){
//            Datastring[j]=DATA.get(j+1);
//        }
//        String[] Keystring =new String[KEY.size()];
//        for(int j=0;j<KEY.size();j++){
//            Datastring[j]=KEY.get(j+1);
//        }
//        Log.d("and",Datastring[0]);
//        Log.d("aaaand",Keystring[1]);


//        for (int i = 0; i < myNotifications.notificationArray.length; i++) {
//            data.add(new notification(DATA.get(i+1),myNotifications.imageArray[i],Integer.valueOf(KEY.get(i+1))
//            ));
//            notificationAdapter.typeArray[i] = myNotifications.idArray[i];
//
//        }
//
//        removedItems = new ArrayList<Integer>();
////
//        adapter = new notificationAdapter(data);
//        recyclerView.setAdapter(adapter);






       // setSupportActionBar(toolbar);
//



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        myOnClickListener = new MyOnClickListener(this);

        recyclerViewMini = (RecyclerView) findViewById(R.id.rvbtnviewdevice);



//        check whether a device already added add_device
        databaseReference.child(user.getUid()).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("device")){
                    recyclerViewMini.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Device add onclick listener
        ImageView addDevice;
        addDevice = (ImageView)findViewById(R.id.add_device);
        addDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent myIntent = new Intent(MainActivity.this, hspotConnect.class);
                    startActivity(myIntent);
                    overridePendingTransition(R.anim.slide_in,R.anim.silde_out);

                }
        });



        //initialization mini sensor data
        //recycle view for mini sensor data


        recyclerViewMini.setHasFixedSize(true);


        layoutManagerMini = new LinearLayoutManager(this);
        recyclerViewMini.setLayoutManager(layoutManagerMini);
        recyclerViewMini.setItemAnimator(new DefaultItemAnimator());


        //firebase commectivity for minisensor data
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

                }



                dataMini = new ArrayList<dataModel>();
                for (int j = 0; j < myData.nameArray.length; j++) {
                    dataMini.add(new dataModel(
                            devicedata[j],
                            miniSensorData.versionArray[j],
                            miniSensorData.id_[j],
                            miniSensorData.drawableArray[j]
                    ));
                }
                removedItemsMini = new ArrayList<Integer>();
//
                adapterMini = new miniSensorDataAdapter(dataMini);
                recyclerViewMini.setAdapter(adapterMini);




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });





        //recycle view for mini sensor data
//        myOnClickListener = new MyOnClickListener(this);
//
//        recyclerViewMini = (RecyclerView) findViewById(R.id.rvbtnviewdevice);
//
//        recyclerViewMini.setHasFixedSize(true);
//
//
//        layoutManagerMini = new LinearLayoutManager(this);
//        recyclerViewMini.setLayoutManager(layoutManagerMini);
//        recyclerViewMini.setItemAnimator(new DefaultItemAnimator());
//
//        dataMini = new ArrayList<dataModel>();
//        for (int j = 0; j < myData.nameArray.length; j++) {
//            dataMini.add(new dataModel(
//                    miniSensorData.nameArray[j],
//                    miniSensorData.versionArray[j],
//                    miniSensorData.id_[j],
//                    miniSensorData.drawableArray[j]
//            ));
//        }
//
//        removedItemsMini = new ArrayList<Integer>();
//
//        adapterMini = new miniSensorDataAdapter(dataMini);
//        recyclerViewMini.setAdapter(adapterMini);


    }

    //click listener class for recyclerview
    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,sensorData.class));
        }


    }

    //here the code for posting for cropSuitability goes on
    public class SendRequest extends AsyncTask<String, Void, String> {
        JSONObject postDataParams = new JSONObject();
        protected void onPreExecute() {







            try {

                String uid = auth.getCurrentUser().getUid();
                postDataParams.put("save", "ok");
                postDataParams.put("uid", uid);
                postDataParams.put("agroZone", agroZone);


            } catch (Exception e) {
                //String("Exception: " + e.getMessage());
            }
        }

        public void setParams(){

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://35.187.239.118/Agriot/cropSuitability/index.php");

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

//            Toast.makeText(getApplicationContext(), result,
//            Toast.LENGTH_LONG).show();

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




    //click listener for notifications
    private class notificationListener implements View.OnClickListener {

        private final Context context;

        private notificationListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textIndicator);
            String indicator = (String) textViewName.getText();
            int inTindicator = Integer.valueOf(indicator);
            if (inTindicator == 1){

                databaseReference.child(user.getUid()).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("suitableCrops")){
                            startActivity(new Intent(MainActivity.this,cropSuitability.class));
                        }else {
                            new SendRequest().execute();
                            //startActivity(new Intent(MainActivity.this,cropSuitability.class));
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }


    }

    @Override
    public void onClick(View v){
        if(v == recyclerViewMini){

        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager=getFragmentManager();

        if (id == R.id.go_to_store) {
            finish();
            startActivity(new Intent(getApplicationContext(),store.class));
            // Handle the camera action
        } else if (id == R.id.current_crop) {

        } else if (id == R.id.suitable_crop) {
            finish();
            startActivity(new Intent(getApplicationContext(),cropSuitability.class));
        } else if (id == R.id.field_data) {
            finish();
            startActivity(new Intent(getApplicationContext(),sensorData.class));

        } else if (id == R.id.nav_mydevices) {
            finish();
            startActivity(new Intent(getApplicationContext(),sensorData.class));

        } else if (id == R.id.nav_adddevice) {
            finish();
            Intent intent = new Intent(getApplicationContext(),hspotConnect.class);

        }
        else if (id == R.id.nav_profile){
            finish();
            startActivity(new Intent(getApplicationContext(),EditUserProfileActivity.class));
        }else if(id == R.id.nav_logout){
            auth.signOut();;
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
