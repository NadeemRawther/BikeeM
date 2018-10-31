package com.nads.bikeem;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class Bikeemadapter extends RecyclerView.Adapter<Bikeemadapter.ViewHolder> {
    private Listener listener;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef2 = database.child("url");
     List<Textobj> arrayList1;
     List<Picsobj> barrayList2;
     Picsobj textobj;
    public Bikeemadapter(Context context, List<Textobj> arrayList1) {
        this.arrayList1 = arrayList1;
        this.context = context;
    }
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
    @Override
    public Bikeemadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bike_news,parent,false);
        return new ViewHolder(view);
    }
    public static interface Listener {
        public void onClick(int position);
    }
    public void setListener(Listener listener){
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(final Bikeemadapter.ViewHolder holder, final int position) {
      final CardView cardView = holder.cardView;
      final  TextView textView = (TextView) cardView.findViewById(R.id.textView2);
      final TextView textView1 = (TextView) cardView.findViewById(R.id.textView3);
      final ImageView imageView = (ImageView) cardView.findViewById(R.id.pics);
      myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              barrayList2 = new ArrayList<Picsobj>();
              for (DataSnapshot data : dataSnapshot.getChildren()) {
                  String value1 = data.getValue(String.class);
                  textobj = new Picsobj(value1);
                  barrayList2.add(textobj);
              }
              Glide.with(context).load(barrayList2.get(position).getUrl()).into(imageView);
          }
          @Override
          public void onCancelled(DatabaseError databaseError) {
          }
      });
      final Context context = cardView.getContext();
      textView.setText(arrayList1.get(position).getMtext());
      textView1.setText(arrayList1.get(position).getMtext3().toString());
      cardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(context.getApplicationContext(),BikeNewsDetailed.class);
              intent.putExtra(String.valueOf(BikeNewsDetailed.bikeno),position);
              context.startActivity(intent);
          }
      });
    }
    @Override
    public int getItemCount() {
    int size = arrayList1.size();
    return size;
    }
}