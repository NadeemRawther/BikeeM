package com.nads.bikeem;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BikeNewsDetailed extends FragmentActivity{
    private ProgressDialog progressDialog;
    public static final int bikeno =1;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef2 = database.child("url");
    DatabaseReference myRef3 = database.child("bikedetailed");
    List<Textobj> arrayList1;
    List<Picsobj> barrayList2;
    Picsobj picsobj;
    Textobj textobj;
    protected void onCreate(Bundle savedInstanceState) {
    //Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bike_news_detailed);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait while loading");
        progressDialog.show();
   Intent intent = getIntent();
   final int valur = intent.getIntExtra(String.valueOf(bikeno),bikeno);
        final TextView textView = (TextView)findViewById(R.id.textView);
        //final TextView textView1 = (TextView)findViewById(R.id.textView4);
        final WebView myWebView = (WebView) findViewById(R.id.webview);
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                barrayList2 = new ArrayList<Picsobj>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String value1 = data.getValue(String.class);
                    picsobj = new Picsobj(value1);
                    barrayList2.add(picsobj);
                }
                ImageView imageView = (ImageView)findViewById(R.id.imageView);
                Glide.with(getApplicationContext()).load(barrayList2.get(valur).getUrl()).into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        arrayList1 = new ArrayList<Textobj>();
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            String key = data.getKey();
            String value1 = data.getValue(String.class);
            textobj = new Textobj(key,value1);
            arrayList1.add(textobj);
        }
        textView.setText(arrayList1.get(valur).getMtext());
       // textView1.setText(arrayList1.get(valur).getMtext3());

        myWebView.loadUrl(arrayList1.get(valur).getMtext3());
        progressDialog.dismiss();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
     progressDialog.dismiss();
    }
});


    }

}
