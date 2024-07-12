package com.example.smartchef;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartchef.models.Bookings;
import com.example.smartchef.models.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddBookingRequest extends AppCompatActivity {

    TextView id, name, email, age, gender, location, language, number, dishes, Experience, bookingDate, bookingTime;
    String Tid;
    String Tname;
    String Temail;
    String Tage;
    String Tgender;
    String Tlocation;
    String Tlanguage;
    String Tnumber;
    String Tdishes;
    String TExperience;
    String timeTonotify;
    String date;
    String time;
    static String Cusid;
    static String Cname;
    static String Cemail;
    Button button1, button2;
    List<Customer> filteredEmotions = new ArrayList<>();
    EditText editText;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking_request);

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
        button1 = findViewById(R.id.booknow);
        bookingDate = (TextView) findViewById(R.id.date);
        bookingTime = (TextView) findViewById(R.id.time);
        button2 = findViewById(R.id.confirm);
        editText =findViewById(R.id.description);

        SharedPreferences preferences = getSharedPreferences("user_emotions", MODE_PRIVATE);
        String emaill = preferences.getString("email","");
        System.out.println(emaill);
        findCustomerByEmail(emaill);

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

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBookingToFirebase();
            }
        });
    }
    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;
                time = FormatTime(i, i1);
                bookingTime.setText(time);
            }
        }, hour, minute, false);
        timePickerDialog.show();

    }

    public static String FormatTime(int hour, int minute) {

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = day + "-" + (month + 1) + "-" + year;
                bookingDate.setText(date);
                selectTime();
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public static void findCustomerByEmail(String email) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CustomerDetails");

        // Create a query to find a customer by email
        Query query = databaseReference.orderByChild("emaill").equalTo(email);

        // Attach a listener to the query
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if there's a match for the email
                if (dataSnapshot.exists()) {
                    // Loop through the matching customers
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Get customer data and print it
                        Customer customer = snapshot.getValue(Customer.class);
                        if (customer != null) {
                            System.out.println("Customer found:");
                            System.out.println("ID: " + customer.getId());
                            System.out.println("Name: " + customer.getNamee());
                            System.out.println("Email: " + customer.getEmaill());
                            Cusid = customer.getId();
                            Cname = customer.getNamee();
                            Cemail = customer.getEmaill();
                        }
                    }
                } else {
                    System.out.println("No customer found with the specified email.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    private void saveBookingToFirebase() {

        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("Bookings");
        String bookingKey = bookingsRef.push().getKey();
        Bookings booking = new Bookings(bookingKey,Cusid,Tid,Cname,Tname,editText.getText().toString(),date,time,"requested",Cemail,Temail);
        bookingsRef.child(bookingKey).setValue(booking);

        Toast.makeText(this, "Booking Request Sent", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AddBookingRequest.this, CustomerDashboard.class);
        startActivity(intent);
    }

}