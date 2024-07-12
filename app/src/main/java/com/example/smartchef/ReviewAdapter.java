package com.example.smartchef;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.smartchef.models.Ratings;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {
    private Context context;
    private List<Ratings> dataList;

    public ReviewAdapter(Context context, List<Ratings> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.rating_list_item, parent, false);
        }

        Ratings item = dataList.get(position);

        TextView rating = convertView.findViewById(R.id.rating);
        TextView review = convertView.findViewById(R.id.review);
        TextView date = convertView.findViewById(R.id.date);
        TextView name = convertView.findViewById(R.id.user_id);

        rating.setText(item.getReviewText());
        review.setText(item.getRating());
        date.setText(item.getTimestamp());
        name.setText(item.getUserId());

        return convertView;

    }
}
