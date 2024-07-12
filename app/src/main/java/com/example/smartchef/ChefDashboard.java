package com.example.smartchef;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.smartchef.models.Chef;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChefDashboard extends AppCompatActivity {

    String name, email, age, dishes, experience, gender, language, location, number;
    ImageView imageView1;

    ImageView imageView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_newdashboard);

        imageView1 = findViewById(R.id.appointment);
        imageView2 = findViewById(R.id.search);


        SharedPreferences preferences = getSharedPreferences("user_emotions", MODE_PRIVATE);
        String emaill = preferences.getString("email","");
        System.out.println(emaill);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ChefDetails");
        Query query = databaseReference.orderByChild("email").equalTo(emaill);

        // Attach a ValueEventListener to fetch the user's profile

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Data matching the search query exists
                    // You can retrieve the data from the snapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Here, you can access the specific entry you've searched for
                        Chef userProfile = snapshot.getValue(Chef.class);
                        name = userProfile.getName();
                        email = userProfile.getEmail();
                        age = userProfile.getAge();
                        dishes = userProfile.getDishes();
                        gender = userProfile.getGender();
                        language = userProfile.getLanguage();
                        location = userProfile.getLocation();
                        number = userProfile.getNumber();
                        experience = userProfile.getExperience();
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
                Intent intent = new Intent(ChefDashboard.this, ViewBookingsChef.class);
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChefDashboard.this, ChefDetailsScreen.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("age", age);
                intent.putExtra("gender", gender);
                intent.putExtra("location", location);
                intent.putExtra("language", language);
                intent.putExtra("number", number);
                intent.putExtra("dishes", dishes);
                intent.putExtra("experience", experience);
                startActivity(intent);
            }
        });
    }
}