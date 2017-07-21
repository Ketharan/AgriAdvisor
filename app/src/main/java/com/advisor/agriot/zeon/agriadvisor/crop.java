package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 21/07/2017.
 */

public class crop {
    String name;
    int image;

    public crop(String name,int image) {
        this.name = name;

        this.image=image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

}


/*
     working code for recycler view
     crop cuitabilty


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<crop> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    */


 /*


                ********
                * working code recycler view
                * for crop suitability

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<crop>();
        for (int i = 0; i < cropData.selectArray.length; i++) {
            String cropName = cropData.cropArray[i];
            int drawableIndex =0;
            for (int j = 0; j < cropData.cropArray.length; j++){
                if (cropData.selectArray[i].equals(cropData.cropArray[j])){
                    drawableIndex = j;
                    break;
                }
            }

            data.add(new crop(
                    cropData.selectArray[i],
                    cropData.drawableArray[drawableIndex]
            ));
        }

        removedItems = new ArrayList<Integer>();

        adapter = new cropDataAdapter(data);
        recyclerView.setAdapter(adapter);

        */
