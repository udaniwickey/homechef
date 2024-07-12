package com.example.smartchef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smartchef.models.Chef;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchChef extends AppCompatActivity {

    private ListView listView;
    private List<Chef> chefList;

    List<Chef> filteredList = new ArrayList<>();

    private DatabaseReference ref;
    private MyAdapter adapter;

    private Spinner filterSpinner;
    private EditText filterValueEditText;
    private ImageView searchButton;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chef);

        listView = findViewById(R.id.listviewsnew);
        filterSpinner = findViewById(R.id.filterSpinner);
        filterValueEditText = findViewById(R.id.filterValueEditText);
        searchButton = findViewById(R.id.searchButton);

        chefList = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("ChefDetails");

        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(
                this, R.array.filter_options, android.R.layout.simple_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(filterAdapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chefList.clear();

                for (DataSnapshot chefSnapshot : dataSnapshot.getChildren()) {
                    Chef chef = chefSnapshot.getValue(Chef.class);
                    chefList.add(chef);
                }

                updateListView(chefList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchButtonClick();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                if(!filteredList.isEmpty()){
                    showChefDetails(filteredList.get(position));
                    filteredList.clear();
                }else{
                    showChefDetails(chefList.get(position));
                }
            }
        });
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
    }

    class MyAdapter extends ArrayAdapter<Chef> {
        LayoutInflater inflater;
        Context myContext;
        List<Chef> user;


        public MyAdapter(Context context, int resource, ArrayList<Chef> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_chef, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.location);
                holder.COL3 = (TextView) view.findViewById(R.id.experience);
                holder.COL4 = (TextView) view.findViewById(R.id.speak);

                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getName());
            holder.COL2.setText(user.get(position).getLocation());
            holder.COL3.setText(user.get(position).getExperience());
            holder.COL4.setText(user.get(position).getLanguage());
            System.out.println(holder);


            return view;

        }
    }

    private void showChefDetails(Chef chef) {
        // Create an Intent to start a new activity to display chef details
        Intent intent = new Intent(this, ChefDetailsActivity.class);
        intent.putExtra("id", chef.getId());
        intent.putExtra("name", chef.getName());
        intent.putExtra("email", chef.getEmail());
        intent.putExtra("age", chef.getAge());
        intent.putExtra("gender", chef.getGender());
        intent.putExtra("location", chef.getLocation());
        intent.putExtra("language", chef.getLanguage());
        intent.putExtra("number", chef.getNumber());
        intent.putExtra("dishes", chef.getDishes());
        intent.putExtra("experience", chef.getExperience());
        startActivity(intent);
    }

    private void updateListView(List<Chef> chefs) {
        adapter = new MyAdapter(SearchChef.this, R.layout.custom_chef, (ArrayList<Chef>) chefs);
        listView.setAdapter(adapter);
    }

    private void onSearchButtonClick() {
        String selectedFilter = filterSpinner.getSelectedItem().toString();
        String searchQuery = filterValueEditText.getText().toString();

        if (!searchQuery.isEmpty()) {
            List<Chef> filteredList = filterData(selectedFilter, searchQuery);
            updateListView(filteredList);
        } else {
            updateListView(chefList);
        }
    }

    private List<Chef> filterData(String selectedFilter, String searchQuery) {

        for (Chef chef : chefList) {
            switch (selectedFilter) {
                case "Name":
                    if (chef.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                        filteredList.add(chef);
                    }
                    break;
                case "Location":
                    if (chef.getLocation().toLowerCase().contains(searchQuery.toLowerCase())) {
                        filteredList.add(chef);
                    }
                    break;
                case "Skilled Dish":
                    if (chef.getDishes().toLowerCase().contains(searchQuery.toLowerCase())) {
                        filteredList.add(chef);
                    }
                    break;
                // Add more cases for other fields if needed

            }
        }

        return filteredList;
    }
}