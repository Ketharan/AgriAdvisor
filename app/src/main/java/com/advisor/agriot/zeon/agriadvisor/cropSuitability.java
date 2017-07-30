package com.advisor.agriot.zeon.agriadvisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class cropSuitability extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<crop> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;


    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_suitability);


        //firebase
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        databaseReference = firebaseDatabase.getReference();


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //crop Suitability
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myOnClickListener = new MyOnClickListener(this);

        databaseReference.child(user.getUid()).child("suitableCrops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> datashot = dataSnapshot.getChildren();

//               ArrayList<String> KEY =new ArrayList<String>();
//               ArrayList<String > DATA =new ArrayList<String>();
                Log.d("Hello", "datashot");
                int i = 0;
                data = new ArrayList<crop>();
                for (DataSnapshot child : datashot) {

                    String crop = child.getKey();
                    String group = child.getValue().toString();
                    int drawableIndex = 0;
                    for (int j = 0; j < cropData.cropArray.length; j++) {
                        if (crop.equals(cropData.cropArray[j])) {
                            drawableIndex = j;
                            break;
                        }
                    }

                    data.add(new crop(
                            crop,
                            cropData.drawableArray[drawableIndex]
                    ));
                    cropDataAdapter.typeArray[i] = Integer.valueOf(group);

                    removedItems = new ArrayList<Integer>();

                    adapter = new cropDataAdapter(data);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


        @Override
        public void onResume() {
            super.onResume();

        }


        private class MyOnClickListener implements View.OnClickListener {

            private final Context context;

            private MyOnClickListener(Context context) {
                this.context = context;
            }

            @Override
            public void onClick(View v) {
                startActivity(new Intent(cropSuitability.this, cropsDetails.class));
            }


        }


}
