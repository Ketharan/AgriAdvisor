package com.advisor.agriot.zeon.agriadvisor;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.widget.TextView;

import java.util.ArrayList;




public class agriotMain extends AppCompatActivity implements android.view.View.OnClickListener {

    /* working code for sensor data recycler view

            **********
            *
            *
            *
            *  */
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<dataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        setContentView(R.layout.activity_main);

        //myOnClickListener = new MyOnClickListener(this);


        /*
            *********working recycler view for sensorData*/


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<dataModel>();
        for (int i = 0; i < myData.nameArray.length; i++) {
            data.add(new dataModel(
                    myData.nameArray[i],
                    myData.versionArray[i],
                    myData.id_[i],
                    myData.drawableArray[i]
            ));
        }

        removedItems = new ArrayList<Integer>();

        adapter = new sensorDataAdapter(data);
        recyclerView.setAdapter(adapter);









        //stateRepo repo = new stateRepo(context);
        //stateVariable stateVar = new stateVariable();
        //stateVar.state = "0";
        //stateVar.state_id = 0;
        //repo.insert(stateVar);
        //stateVariable stateVar1 = repo.getstateVariableById(0);


        //viewButton(repo);






    }




    public void viewButton(stateRepo repo){
        RelativeLayout mainRelativeLayout = new RelativeLayout(this);

        // Defining the RelativeLayout layout parameters with Fill_Parent
        RelativeLayout.LayoutParams relativeLayoutParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        stateVariable stateVar1 = repo.getstateVariableById(0);
        String strButtonCount = stateVar1.state;
        int buttonCount = Integer.parseInt(strButtonCount);
        for (int i = 0; i < buttonCount+1;i++){

            int btnId = i;
            Button btn = new Button(this);
            btn.setId(btnId);
            btn.setOnClickListener(this);
            AddButtonLayout(btn, RelativeLayout.ALIGN_PARENT_BOTTOM);
            mainRelativeLayout.addView(btn);

            // Setting the RelativeLayout as our content view
            setContentView(mainRelativeLayout, relativeLayoutParameters);
        }
    }


    private void AddButtonLayout(Button button, int centerInParent, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        // Defining the layout parameters of the Button
        RelativeLayout.LayoutParams buttonLayoutParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Add Margin to the LayoutParameters
        buttonLayoutParameters.setMargins(marginLeft, marginTop, marginRight, marginBottom);

        // Add Rule to Layout
        buttonLayoutParameters.addRule(centerInParent);

        // Setting the parameters on the Button
        button.setLayoutParams(buttonLayoutParameters);
    }

    private void AddButtonLayout(Button button, int centerInParent) {
        // Just call the other AddButtonLayout Method with Margin 0
        AddButtonLayout(button, centerInParent, 0 ,0 ,0 ,0);
    }
    @Override
    public void onClick(View view){

    }


    private static class  MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeItem(v);
        }

        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < myData.nameArray.length; i++) {
                if (selectedName.equals(myData.nameArray[i])) {
                    selectedItemId = myData.id_[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }
    }


}
