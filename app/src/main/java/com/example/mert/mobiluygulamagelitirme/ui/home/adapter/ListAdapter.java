package com.example.mert.mobiluygulamagelitirme.ui.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mert.mobiluygulamagelitirme.R;
import com.example.mert.mobiluygulamagelitirme.Util;
import com.example.mert.mobiluygulamagelitirme.data.model.Lesson;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    //Context
    private Context context;

    //List
    private ArrayList<Lesson> lessons;

    public ListAdapter(Context context) {
        this.context = context;

        this.lessons = new ArrayList<>();
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public Object getItem(int position) {
        return lessons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_listview, null);
        }

        TextView textViewName = (TextView) convertView.findViewById(R.id.textView_name_item_listview);
        TextView textViewTime = (TextView) convertView.findViewById(R.id.textView_lesson_time_item_listview);

        textViewName.setText(lessons.get(position).getName());

        if (lessons.get(position) != null) {
            Log.i(this.getClass().getSimpleName(), "lessonTime Day => " + lessons.get(position).getDay() + " Hour =>" + lessons.get(position).getHour() + " During =>" + lessons.get(position).getDuring());
            textViewTime.setText(Util.TIME.getTimebyString(lessons.get(position).getDay(), lessons.get(position).getHour(), lessons.get(position).getDuring()));
        } else {
            Log.i(this.getClass().getSimpleName(), "lessonTime is null ");
        }

        return convertView;
    }
}
