package com.example.smartchef;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartchef.models.Ratings;

import java.util.ArrayList;
import java.util.List;

public class RatingsScreen extends AppCompatActivity {

    private ListView listView;
    List<Ratings> list=new ArrayList<Ratings>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_list);

        list = (ArrayList<Ratings>) getIntent().getSerializableExtra("ratingsList");

        listView = findViewById(R.id.ratings);

        ReviewAdapter adp = new ReviewAdapter(this, list);

        listView.setAdapter(adp);

    }
}
