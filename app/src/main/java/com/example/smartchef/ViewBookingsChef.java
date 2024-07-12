package com.example.smartchef;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartchef.models.Bookings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingsChef extends AppCompatActivity {

    private ListView listView;
    private List<Bookings> bookings;
    private DatabaseReference ref;
    MyAdapter adapter;

    List<Bookings> bookingsList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings_chef);

        listView = findViewById(R.id.listviesnew);

        SharedPreferences preferences = getSharedPreferences("user_emotions", MODE_PRIVATE);
        String emaill = preferences.getString("email","");
        System.out.println(emaill);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

        // Create a query to find bookings by chefEmail
        Query query = databaseReference.orderByChild("chefEmail").equalTo(emaill);

        // Attach a listener to the query
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Bookings booking = snapshot.getValue(Bookings.class);
                        if (booking != null && booking.getStatus().equals("requested")) {
                            bookingsList.add(booking);
                        }
                    }

                    // Assign bookingsList to the class-level bookings variable
                    bookings = bookingsList;

                    if(bookingsList.isEmpty()){
                        System.out.println("No bookings found for the specified chef email.");
                        Bookings bookings1 = new Bookings();
                        bookingsList.add(bookings1);

                        MyAdapter2 adapter = new MyAdapter2(ViewBookingsChef.this, R.layout.custom_booking_not_found, (ArrayList<Bookings>) bookingsList);
                        listView.setAdapter(adapter);
                    }else{
                        // Update the ListView with the bookingsList
                        MyAdapter adapter = new MyAdapter(ViewBookingsChef.this, R.layout.custom_booking, (ArrayList<Bookings>) bookings);
                        listView.setAdapter(adapter);
                    }

                } else {
                    // Handle case when no bookings are found
                    System.out.println("No bookings found for the specified chef email.");
                    Bookings bookings1 = new Bookings();
                    bookingsList.add(bookings1);

                    MyAdapter2 adapter = new MyAdapter2(ViewBookingsChef.this, R.layout.custom_booking_not_found, (ArrayList<Bookings>) bookingsList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });

    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;

        Button button1;
        Button button2;
    }

    class MyAdapter extends ArrayAdapter<Bookings> {
        LayoutInflater inflater;
        Context myContext;
        List<Bookings> user;


        public MyAdapter(Context context, int resource, ArrayList<Bookings> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_booking, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.location);
                holder.COL3 = (TextView) view.findViewById(R.id.date);
                holder.COL4 = (TextView) view.findViewById(R.id.timee);
                holder.COL5 = (TextView) view.findViewById(R.id.description);
                holder.button1 = (Button) view.findViewById(R.id.accept);
                holder.button2 = (Button) view.findViewById(R.id.reject);

                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getCustomerName());
            holder.COL2.setText("Request Status :- "+user.get(position).getStatus());
            holder.COL3.setText(user.get(position).getDate());
            holder.COL4.setText(user.get(position).getTime());
            holder.COL5.setText(user.get(position).getDescription());
            System.out.println(holder);

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

                    // Create a query to find the booking by ID
                    Query query = databaseReference.orderByChild("bookingId").equalTo(user.get(position).getBookingId());

                    // Attach a listener to the query
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    // Update the status field
                                    snapshot.getRef().child("status").setValue("accepted");
                                    notifyDataSetChanged();  // Notify the adapter to refresh the view
                                    Intent intent = new Intent(ViewBookingsChef.this, ChefDashboard.class);
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("Error: " + databaseError.getMessage());
                        }
                    });
                }
            });

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

                    // Create a query to find the booking by ID
                    Query query = databaseReference.orderByChild("bookingId").equalTo(user.get(position).getBookingId());

                    // Attach a listener to the query
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    // Update the status field
                                    snapshot.getRef().child("status").setValue("rejected");
                                    notifyDataSetChanged();  // Notify the adapter to refresh the view
                                    Intent intent = new Intent(ViewBookingsChef.this, ChefDashboard.class);
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("Error: " + databaseError.getMessage());
                        }
                    });
                }
            });


            return view;

        }
    }

    class MyAdapter2 extends ArrayAdapter<Bookings> {
        LayoutInflater inflater;
        Context myContext;
        List<Bookings> user;


        public MyAdapter2(Context context, int resource, ArrayList<Bookings> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_booking_not_found, null);

                view.setTag(holder);
            }

            return view;

        }
    }

}