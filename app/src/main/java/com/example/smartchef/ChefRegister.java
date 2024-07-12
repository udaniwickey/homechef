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

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;
    EditText editText9;

    EditText editText10;

    Button reg;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_register);

        editText1=(EditText)findViewById(R.id.name);
        editText2=(EditText)findViewById(R.id.email);
        editText3=(EditText)findViewById(R.id.age);
        editText4=(EditText)findViewById(R.id.gender);
        editText5=(EditText)findViewById(R.id.location);
        editText6=(EditText)findViewById(R.id.languages);
        editText7=(EditText)findViewById(R.id.number);
        editText8=(EditText)findViewById(R.id.dishes);
        editText9=(EditText)findViewById(R.id.password);
        editText10=(EditText)findViewById(R.id.experience);
        reg=(Button)findViewById(R.id.chefregister);

        firebaseAuth=FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("ChefDetails");
//                reference = rootNode.getReference("Users");

                final String namee = editText1.getText().toString();
                final String emaill = editText2.getText().toString();
                final String age = editText3.getText().toString();
                final String gender = editText4.getText().toString();
                final String location = editText5.getText().toString();
                final String language = editText6.getText().toString();
                final String number = editText7.getText().toString();
                final String dishes = editText8.getText().toString();
                final String password = editText9.getText().toString();
                final String experience = editText10.getText().toString();


                if (!validateUserName() | !validateEmail() | !validateAge() | !validatePhone() | !validateGender() | !validateLocation() | !validateLanguage() | !validatedishes() | !validatePassword()| !validateExperience()) {
                    Toast.makeText(ChefRegister.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                }
                else{

                    firebaseAuth.createUserWithEmailAndPassword(emaill, password)
                            .addOnCompleteListener(ChefRegister.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        String userid = reference.push().getKey();

                                        Chef members = new Chef(userid, namee, emaill, age, gender,location, language, number,dishes, experience, password);
                                        reference.child(userid).setValue(members);

                                        System.out.println(members);
                                        Toast.makeText(ChefRegister.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                        SharedPreferences preferences = getSharedPreferences("user_emotions", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("email", emaill);
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
        String value = editText1.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (value.isEmpty()) {
            editText1.setError("Field cannot be empty");
            return false;
        } else if (value.length() >= 15) {
            editText1.setError("Username too long. limit to 15 characters");
            return false;
        } else if (!value.matches(noWhiteSpace)) {
            editText1.setError("White Spaces are not allowed");
            return false;
        } else {
            editText1.setError(null);
            return true;
        }
    }

    public boolean validateEmail() {
        String value = editText2.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z].+[a-z]+\\.+[a-z]+";        //Regex Expression

        if (value.isEmpty()) {
            editText2.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(emailPattern)) {
            editText2.setError("Invalid email address");
            return false;
        } else {
            editText2.setError(null);
            return true;
        }
    }

    public boolean validateAge() {
        String value = editText3.getText().toString();

        if (value.isEmpty()) {
            editText3.setError("Field cannot be empty");
            return false;
        } else {
            editText3.setError(null);
            return true;
        }
    }

    public boolean validatePhone() {
        String value = editText7.getText().toString();

        if (value.isEmpty()) {
            editText7.setError("Field cannot be empty");
            return false;
        } else {
            editText7.setError(null);
            return true;
        }
    }

    public boolean validateGender() {
        String value = editText4.getText().toString();

        if (value.isEmpty()) {
            editText4.setError("Field cannot be empty");
            return false;
        } else {
            editText4.setError(null);
            return true;
        }
    }

    public boolean validateLocation() {
        String value = editText5.getText().toString();

        if (value.isEmpty()) {
            editText5.setError("Field cannot be empty");
            return false;
        } else {
            editText5.setError(null);
            return true;
        }
    }

    public boolean validateLanguage() {
        String value = editText6.getText().toString();

        if (value.isEmpty()) {
            editText6.setError("Field cannot be empty");
            return false;
        } else {
            editText6.setError(null);
            return true;
        }
    }

    public boolean validatedishes() {
        String value = editText8.getText().toString();

        if (value.isEmpty()) {
            editText8.setError("Field cannot be empty");
            return false;
        } else {
            editText8.setError(null);
            return true;
        }
    }

    public boolean validateExperience() {
        String value = editText10.getText().toString();

        if (value.isEmpty()) {
            editText10.setError("Field cannot be empty");
            return false;
        } else {
            editText10.setError(null);
            return true;
        }
    }


    public boolean validatePassword() {
        String value = editText9.getText().toString();
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
            editText9.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(password)) {
            editText9.setError("Password must have at least 1 special character, 2 numbers and letters");
            return false;
        } else {
            editText9.setError(null);
            return true;
        }
    }
}