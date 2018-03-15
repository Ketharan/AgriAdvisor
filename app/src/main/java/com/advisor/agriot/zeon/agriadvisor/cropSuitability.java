package com.advisor.agriot.zeon.agriadvisor;

import android.graphics.Typeface;
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
    private static RecyclerView.Adapter adapterCrop;
    private RecyclerView.LayoutManager layoutManagerCrop;
    private static RecyclerView recyclerViewCrop;
    private static ArrayList<crop> dataCrop;
    static View.OnClickListener myOnClickListenerCrop;
    private static ArrayList<Integer> removedItemsCrop;


    private FirebaseAuth authCrop;

    private DatabaseReference databaseReferenceCrop;
    private FirebaseUser userCrop;
    private DatabaseReference cropSuitCrop;
    private ValueEventListener getCropSuitCrop;

    private FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_suitability);


        //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        databaseReferenceCrop = firebaseDatabase.getReference();


        authCrop = FirebaseAuth.getInstance();
        userCrop = authCrop.getCurrentUser();

        //crop Suitability
        recyclerViewCrop = (RecyclerView) findViewById(R.id.rv_crop_suit);

        recyclerViewCrop.setHasFixedSize(true);

        layoutManagerCrop = new LinearLayoutManager(this);
        recyclerViewCrop.setLayoutManager(layoutManagerCrop);
        recyclerViewCrop.setItemAnimator(new DefaultItemAnimator());
        myOnClickListenerCrop = new MyOnClickListener(this);

        cropSuitCrop = databaseReferenceCrop.child(userCrop.getUid()).child("suitableCrops");
        getCropSuitCrop = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> datashot = dataSnapshot.getChildren();


               // Log.d("Hello", "datashot");

                dataCrop = new ArrayList<crop>();
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

                    dataCrop.add(new crop(
                            crop,
                            cropData.drawableArray[drawableIndex]
                    ));
                    cropDataAdapter.typeArray.add(Integer.valueOf(group));


                    removedItemsCrop = new ArrayList<Integer>();

                    adapterCrop = new cropDataAdapter(dataCrop);
                    recyclerViewCrop.setAdapter(adapterCrop);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        cropSuitCrop.addValueEventListener(getCropSuitCrop);
        cropDataAdapter.typeArray.clear();
        cropDataAdapter.counter = 0;
    }




        protected void onStop(){
            super.onStop();
            cropSuitCrop.removeEventListener(getCropSuitCrop);
        }


        private class MyOnClickListener implements View.OnClickListener {




            private final Context context;

            private MyOnClickListener(Context context) {
                this.context = context;
            }

            @Override
            public void onClick(View v) {
                int selectedItemPosition = recyclerViewCrop.getChildPosition(v);
                RecyclerView.ViewHolder viewHolder
                        = recyclerViewCrop.findViewHolderForPosition(selectedItemPosition);
                TextView textViewName
                        = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
                String cropName = (String) textViewName.getText();

                Intent intent = new Intent(cropSuitability.this,cropSelect.class);
                intent.putExtra("crop",cropName);
                startActivity(intent);
            }


        }


}
