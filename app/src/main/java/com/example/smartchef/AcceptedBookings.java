package com.example.smartchef;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.smartchef.models.Bookings;
import com.example.smartchef.models.Ratings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcceptedBookings extends AppCompatActivity {
    List<Bookings> bookingsList = new ArrayList<>();
    private ListView listView;
    private List<Bookings> bookings;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_bookings);

        listView = findViewById(R.id.listviesnew);

        SharedPreferences preferences = getSharedPreferences("user_emotions", MODE_PRIVATE);
        String emaill = preferences.getString("email","");
        System.out.println(emaill);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

        // Create a query to find bookings by chefEmail
        Query query = databaseReference.orderByChild("customerEmail").equalTo(emaill);

        // Attach a listener to the query
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Bookings booking = snapshot.getValue(Bookings.class);
                        if (booking != null && booking.getStatus().equals("accepted")) {
                            bookingsList.add(booking);
                        }
                    }

                    // Assign bookingsList to the class-level bookings variable
                    bookings = bookingsList;

                    if(bookingsList.isEmpty()){
                        System.out.println("No bookings found for the specified chef email.");
                        Bookings bookings1 = new Bookings();
                        bookingsList.add(bookings1);

                        MyAdapter2 adapter = new MyAdapter2(AcceptedBookings.this, R.layout.accepted_booking_not_found, (ArrayList<Bookings>) bookingsList);
                        listView.setAdapter(adapter);
                    }else {
                        // Update the ListView with the bookingsList
                        MyAdapter adapter = new MyAdapter(AcceptedBookings.this, R.layout.custom_accepted_booking, (ArrayList<Bookings>) bookings);
                        listView.setAdapter(adapter);
                    }
                } else {
                    // Handle case when no bookings are found
                    System.out.println("No bookings found for the specified chef email.");
                    Bookings bookings1 = new Bookings();
                    bookingsList.add(bookings1);

                    MyAdapter2 adapter = new MyAdapter2(AcceptedBookings.this, R.layout.accepted_booking_not_found, (ArrayList<Bookings>) bookingsList);
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
            final ViewBookingsChef.ViewHolder holder;
            if (view == null) {
                holder = new ViewBookingsChef.ViewHolder();
                view = inflater.inflate(R.layout.accepted_booking_not_found, null);

                view.setTag(holder);
            }

            return view;

        }
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
                view = inflater.inflate(R.layout.custom_accepted_booking, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.location);
                holder.COL3 = (TextView) view.findViewById(R.id.date);
                holder.COL4 = (TextView) view.findViewById(R.id.timee);
                holder.COL5 = (TextView) view.findViewById(R.id.description);
                holder.button1 = (Button) view.findViewById(R.id.complete);

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
                                    snapshot.getRef().child("status").setValue("completed");
                                    notifyDataSetChanged();
                                    showRatingDialog(user.get(position).getChefId(), user.get(position).getCustomerId(), user.get(position).getChefEmail());// Notify the adapter to refresh the view

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

    private void showRatingDialog(String chefId, String customerId, String chefEmail) {
        // Create a custom dialog
        Dialog ratingDialog = new Dialog(this);
        ratingDialog.setContentView(R.layout.rating_dialog);
        ratingDialog.setTitle("Rate the Chef");

        RatingBar ratingBar = ratingDialog.findViewById(R.id.ratingBar);
        TextView ratingText = ratingDialog.findViewById(R.id.editTextTextMultiLine);
        Button submitButton = ratingDialog.findViewById(R.id.submitRating);

        // Handle the submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();

                // Update the chef's rating in the database
                updateChefRating(chefId, customerId, String.valueOf(rating), ratingText, chefEmail);

                // Close the dialog
                ratingDialog.dismiss();
            }
        });

        // Show the dialog
        ratingDialog.show();
    }

    private void updateChefRating(String chefId, String customerId, String rating, TextView ratingText, String chefEmail) {
        // Update the chef's rating in the database
        DatabaseReference chefRef = FirebaseDatabase.getInstance().getReference("Ratings").child(chefId);
        String key = chefRef.push().getKey();

        Ratings ratings = null;

        Date dateTime = new Date(); // Replace this with your date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(dateTime);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ratings = new Ratings(customerId, rating, ratingText.getText().toString(), formattedDate, chefEmail);
        }
        chefRef.child(key).setValue(ratings);

        Intent intent = new Intent(AcceptedBookings.this, AcceptedBookings.class);
        startActivity(intent);
    }

}