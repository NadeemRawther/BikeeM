package com.nads.bikeem;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class BikeNews extends Fragment {

    private ProgressDialog progressDialog;

 List<Textobj> barrayList1;
 List<Picsobj> barrayList2;
 Picsobj picsObj;
Textobj textobj;
Bikeemadapter bikeemadapter;
RecyclerView bikeRecycler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bikeRecycler = (RecyclerView) inflater.inflate(
                R.layout.bike_list, container, false);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("food");
        bikeRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        bikeRecycler.setLayoutManager(layoutManager);
         progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("please Wait while loading");
        progressDialog.show();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                barrayList1 = new ArrayList<Textobj>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String key = data.getKey();
                    String value1 = data.getValue(String.class);
                    textobj = new Textobj(key,value1);
                    barrayList1.add(textobj);
                }

                bikeemadapter = new Bikeemadapter(getContext(),barrayList1);
                bikeRecycler.setAdapter(bikeemadapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Log.w( TAG, "Failed to read value.", databaseError.toException());
            }
        };
        myRef.addListenerForSingleValueEvent(valueEventListener);

        return bikeRecycler;
    }
}