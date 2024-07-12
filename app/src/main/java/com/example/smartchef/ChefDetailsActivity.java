package com.example.smartchef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartchef.models.Ratings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChefDetailsActivity extends AppCompatActivity {

    TextView id, name, email, age, gender, location, language, number, dishes, Experience;
    String Tid, Tname, Temail, Tage, Tgender, Tlocation, Tlanguage, Tnumber, Tdishes, TExperience;

    ImageView button, button2, button3, button4;

    Button booknow, ratings;

    ArrayList<Ratings> ratingsList = new ArrayList<>();

    int count;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_details);

        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        age = (TextView) findViewById(R.id.age);
        gender = (TextView) findViewById(R.id.gender);
        location = (TextView) findViewById(R.id.location);
        language = (TextView) findViewById(R.id.language);
        number = (TextView) findViewById(R.id.number);
        dishes = (TextView) findViewById(R.id.dishes);
        Experience = (TextView) findViewById(R.id.experience);
        button = (ImageView)findViewById(R.id.button);
        button2 = (ImageView)findViewById(R.id.button2);
        button3 = (ImageView)findViewById(R.id.button3);
        button4 = (ImageView)findViewById(R.id.button4);
        booknow = findViewById(R.id.booknow);
        ratings = findViewById(R.id.review_button);

        Intent intent = getIntent();
        Tid = intent.getStringExtra("id");
        Tname = intent.getStringExtra("name");
        Temail = intent.getStringExtra("email");
        Tage = intent.getStringExtra("age");
        Tgender = intent.getStringExtra("gender");
        Tlocation = intent.getStringExtra("location");
        Tlanguage = intent.getStringExtra("language");
        Tnumber = intent.getStringExtra("number");
        Tdishes = intent.getStringExtra("dishes");
        TExperience = intent.getStringExtra("experience");


        id.setText(Tid);
        name.setText(Tname);
        email.setText(Temail);
        age.setText(Tage);
        gender.setText(Tgender);
        location.setText(Tlocation);
        language.setText(Tlanguage);
        number.setText(Tnumber);
        dishes.setText(Tdishes);
        Experience.setText(TExperience);

        DatabaseReference chefRef = FirebaseDatabase.getInstance().getReference("Ratings").child(Tid);

        chefRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Ratings ratings1 = snapshot.getValue(Ratings.class);
                        ratingsList.add(ratings1);
                    }
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(number.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer(number.getText().toString());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMessagingApp(number.getText().toString());
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGmailApp(email.getText().toString());
            }
        });

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChefDetailsActivity.this, AddBookingRequest.class);
                intent.putExtra("id", Tid);
                intent.putExtra("name", Tname);
                intent.putExtra("email", Temail);
                intent.putExtra("age", Tage);
                intent.putExtra("gender", Tgender);
                intent.putExtra("location", Tlocation);
                intent.putExtra("language", Tlanguage);
                intent.putExtra("number", Tnumber);
                intent.putExtra("dishes", Tdishes);
                intent.putExtra("experience", TExperience);
                startActivity(intent);
            }
        });

        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChefDetailsActivity.this, RatingsScreen.class);
                intent.putExtra("ratingsList", ratingsList);

                startActivity(intent);
            }

        });
    }

    private void openWhatsApp(String phoneNumber) {
        try {
            // Use the "smsto" and not "sms" to open WhatsApp directly
            Uri uri = Uri.parse("smsto:" + phoneNumber);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");

            // You can include a message if you want
            // i.putExtra("sms_body", "Hello, this is a message!");

            startActivity(i);
        } catch (Exception e) {
            // If WhatsApp is not installed or an error occurs, handle it here
            // For example, open the Play Store to download WhatsApp
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.whatsapp"));
            startActivity(intent);
        }
    }

    private void openDialer(String phoneNumber) {
        // Create an Intent with the ACTION_DIAL action
        Intent intent = new Intent(Intent.ACTION_DIAL);

        // Set the data URI with the phone number
        Uri uri = Uri.parse("tel:" + phoneNumber);
        intent.setData(uri);

        // Check if there's a dialer application available before starting the activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void openMessagingApp(String phoneNumber) {
        // Create an Intent with the ACTION_VIEW action
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // Set the data URI with the "smsto" scheme and the phone number
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        intent.setData(uri);

        // Check if there's a messaging app available before starting the activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void openGmailApp(String emailAddress) {
        // Create an Intent with the ACTION_SENDTO action
        Intent intent = new Intent(Intent.ACTION_SENDTO);

        // Set the data URI with the "mailto" scheme and the email address
        Uri uri = Uri.parse("mailto:" + emailAddress);
        intent.setData(uri);

        // Check if there's an email app available before starting the activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}