package com.example.smartchef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartchef.models.Chef;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChefRegister extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextAge;
    EditText editTextGender;
    EditText editTextLocation;
    EditText editTextLanguages;
    EditText editTextNumber;
    EditText editTextDishes;
    EditText editTextPassword;

    EditText editTextExperience;

    Button reg;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_register);

        editTextName = (EditText)findViewById(R.id.name);
        editTextEmail = (EditText)findViewById(R.id.email);
        editTextAge = (EditText)findViewById(R.id.age);
        editTextGender = (EditText)findViewById(R.id.gender);
        editTextLocation = (EditText)findViewById(R.id.location);
        editTextLanguages = (EditText)findViewById(R.id.languages);
        editTextNumber = (EditText)findViewById(R.id.number);
        editTextDishes = (EditText)findViewById(R.id.dishes);
        editTextPassword = (EditText)findViewById(R.id.password);
        editTextExperience = (EditText)findViewById(R.id.experience);
        reg=(Button)findViewById(R.id.chefregister);

        firebaseAuth=FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("ChefDetails");
//                reference = rootNode.getReference("Users");

                final String name = editTextName.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String age = editTextAge.getText().toString();
                final String gender = editTextGender.getText().toString();
                final String location = editTextLocation.getText().toString();
                final String language = editTextLanguages.getText().toString();
                final String number = editTextNumber.getText().toString();
                final String dishes = editTextDishes.getText().toString();
                final String password = editTextPassword.getText().toString();
                final String experience = editTextExperience.getText().toString();


                if (!validateUserName() | !validateEmail() | !validateAge() | !validatePhone() | !validateGender() | !validateLocation() | !validateLanguage() | !validatedishes() | !validatePassword()| !validateExperience()) {
                    Toast.makeText(ChefRegister.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                }
                else{

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(ChefRegister.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        String userid = reference.push().getKey();

                                        Chef members = new Chef(userid, name, email, age, gender,location, language, number,dishes, experience, password);
                                        reference.child(userid).setValue(members);

                                        System.out.println(members);
                                        Toast.makeText(ChefRegister.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                        SharedPreferences preferences = getSharedPreferences("user_emotions", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("email", email);
                                        editor.apply();
                                        Intent intent = new Intent(ChefRegister.this, ChefDashboard.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(ChefRegister.this, "Sign up failed", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                }
            }
        });
    }

    public boolean validateUserName() {
        String value = editTextName.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (value.isEmpty()) {
            editTextName.setError("Field cannot be empty");
            return false;
        } else if (value.length() >= 15) {
            editTextName.setError("Username too long. limit to 15 characters");
            return false;
        } else if (!value.matches(noWhiteSpace)) {
            editTextName.setError("White Spaces are not allowed");
            return false;
        } else {
            editTextName.setError(null);
            return true;
        }
    }

    public boolean validateEmail() {
        String value = editTextEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z].+[a-z]+\\.+[a-z]+";        //Regex Expression

        if (value.isEmpty()) {
            editTextEmail.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(emailPattern)) {
            editTextEmail.setError("Invalid email address");
            return false;
        } else {
            editTextEmail.setError(null);
            return true;
        }
    }

    public boolean validateAge() {
        String value = editTextAge.getText().toString();

        if (value.isEmpty()) {
            editTextAge.setError("Field cannot be empty");
            return false;
        } else {
            editTextAge.setError(null);
            return true;
        }
    }

    public boolean validatePhone() {
        String value = editTextNumber.getText().toString();

        if (value.isEmpty()) {
            editTextNumber.setError("Field cannot be empty");
            return false;
        } else {
            editTextNumber.setError(null);
            return true;
        }
    }

    public boolean validateGender() {
        String value = editTextGender.getText().toString();

        if (value.isEmpty()) {
            editTextGender.setError("Field cannot be empty");
            return false;
        } else {
            editTextGender.setError(null);
            return true;
        }
    }

    public boolean validateLocation() {
        String value = editTextLocation.getText().toString();

        if (value.isEmpty()) {
            editTextLocation.setError("Field cannot be empty");
            return false;
        } else {
            editTextLocation.setError(null);
            return true;
        }
    }

    public boolean validateLanguage() {
        String value = editTextLanguages.getText().toString();

        if (value.isEmpty()) {
            editTextLanguages.setError("Field cannot be empty");
            return false;
        } else {
            editTextLanguages.setError(null);
            return true;
        }
    }

    public boolean validatedishes() {
        String value = editTextDishes.getText().toString();

        if (value.isEmpty()) {
            editTextDishes.setError("Field cannot be empty");
            return false;
        } else {
            editTextDishes.setError(null);
            return true;
        }
    }

    public boolean validateExperience() {
        String value = editTextExperience.getText().toString();

        if (value.isEmpty()) {
            editTextExperience.setError("Field cannot be empty");
            return false;
        } else {
            editTextExperience.setError(null);
            return true;
        }
    }


    public boolean validatePassword() {
        String value = editTextPassword.getText().toString();
        String password = "^" +
                //"(?=.*[0-9])"     +           //at least 1 digit
                //"(?=.*[a-z])"     +           //at least 1 lower case letter
                //"(?=.*[A-Z])"    +            //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +              //any letter
                "(?=.*[@#$%^&+=])" +            //at least 1 special character
                "(?=\\S+$)" +                   //no white spaces
                ".{2,}" +                      //at least 2 characters
                "$";

        if (value.isEmpty()) {
            editTextPassword.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(password)) {
            editTextPassword.setError("Password must have at least 1 special character, 2 numbers and letters");
            return false;
        } else {
            editTextPassword.setError(null);
            return true;
        }
    }
}