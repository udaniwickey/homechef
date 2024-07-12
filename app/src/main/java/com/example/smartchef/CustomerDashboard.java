package com.example.smartchef;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.smartchef.models.Chef;
import com.example.smartchef.models.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CustomerDashboard extends AppCompatActivity {

    String id, name, email, age, gender, number;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_newdashboard);

        imageView1 = findViewById(R.id.imageView5);
        imageView2 = findViewById(R.id.reserve);
        imageView3 = findViewById(R.id.review);
        imageView4 = findViewById(R.id.imageView6);

        SharedPreferences preferences = getSharedPreferences("user_emotions", MODE_PRIVATE);
        String emaill = preferences.getString("email","");
        System.out.println(emaill);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CustomerDetails");
        Query query = databaseReference.orderByChild("emaill").equalTo(emaill);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Data matching the search query exists
                    // You can retrieve the data from the snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Here, you can access the specific entry you've searched for
                        Customer userProfile = snapshot.getValue(Customer.class);
                        id = userProfile.getId();
                        name = userProfile.getNamee();
                        email = userProfile.getEmaill();
                        age = userProfile.getAge();
                        gender = userProfile.getGender();
                        number = userProfile.getNumber();
                        // Do something with the data
                    }
                } else {
                    // No data matching the search query was found
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that may occur during the search
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboard.this, SearchChef.class);
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboard.this, AcceptedBookings.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboard.this, RejectedBookings.class);
                startActivity(intent);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboard.this, UserDetailsScreen.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("age", age);
                intent.putExtra("gender", gender);
                intent.putExtra("number", number);
                startActivity(intent);
            }
        });
    }
}