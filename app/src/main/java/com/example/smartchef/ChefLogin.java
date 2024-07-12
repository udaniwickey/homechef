package com.example.smartchef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ChefLogin extends AppCompatActivity {

    Button reg;
    EditText editText1;
    EditText editText2;
    Button log;
    FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login);

        editText1=(EditText)findViewById(R.id.Lemail);
        editText2=(EditText)findViewById(R.id.Lpass);
        log=(Button)findViewById(R.id.login);
        reg=(Button)findViewById(R.id.register);

        firebaseAuth=FirebaseAuth.getInstance();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChefLogin.this, ChefRegister.class);
                startActivity(intent);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emaill = editText1.getText().toString();
                final String passwordd = editText2.getText().toString();

                if (emaill.isEmpty()) {
                    editText1.setError("Correct email required");
                } if (passwordd.isEmpty()) {
                    editText2.setError("Correct Password required");
                }else{



                    firebaseAuth.signInWithEmailAndPassword(emaill, passwordd)
                            .addOnCompleteListener(ChefLogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChefLogin.this, "Successfully sign in", Toast.LENGTH_SHORT).show();
                                        SharedPreferences preferences = getSharedPreferences("user_emotions", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("email", emaill);
                                        editor.apply();
                                        Intent intent = new Intent(ChefLogin.this, ChefDashboard.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(ChefLogin.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });
    }

}
