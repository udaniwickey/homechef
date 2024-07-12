package com.example.smartchef;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChefDetailsScreen extends AppCompatActivity {

    TextView id, name, email, age, gender, location, language, number, dishes, Experience;
    String Tid, Tname, Temail, Tage, Tgender, Tlocation, Tlanguage, Tnumber, Tdishes, TExperience;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_details_own);

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

    }
}