package com.example.smartchef;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsScreen extends AppCompatActivity {

    TextView id, name, email, age, gender, language, number;
    String Tid, Tname, Temail, Tage, Tgender, Tlanguage, Tnumber;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details_own);

        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        age = (TextView) findViewById(R.id.age);
        gender = (TextView) findViewById(R.id.gender);
        number = (TextView) findViewById(R.id.number);

        Intent intent = getIntent();
        Tid = intent.getStringExtra("id");
        Tname = intent.getStringExtra("name");
        Temail = intent.getStringExtra("email");
        Tage = intent.getStringExtra("age");
        Tgender = intent.getStringExtra("gender");
        Tnumber = intent.getStringExtra("number");

        id.setText(Tid);
        name.setText(Tname);
        email.setText(Temail);
        age.setText(Tage);
        gender.setText(Tgender);
        number.setText(Tnumber);

    }
}