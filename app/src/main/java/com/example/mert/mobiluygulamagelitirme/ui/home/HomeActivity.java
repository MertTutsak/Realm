package com.example.mert.mobiluygulamagelitirme.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mert.mobiluygulamagelitirme.R;
import com.example.mert.mobiluygulamagelitirme.data.db.Realm;
import com.example.mert.mobiluygulamagelitirme.data.model.Lesson;
import com.example.mert.mobiluygulamagelitirme.ui.add.AddActivity;
import com.example.mert.mobiluygulamagelitirme.ui.edit.EditActivity;
import com.example.mert.mobiluygulamagelitirme.ui.home.adapter.ListAdapter;
import com.example.mert.mobiluygulamagelitirme.ui.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends MainActivity {

    /* List */

    //Adapter
    private ListAdapter listAdapter;
    //ListView
    @BindView(R.id.listView_lesson_list)
    ListView listViewRecords;

    //TextView No Record
    @BindView(R.id.textView_no_record_list)
    TextView textViewNoRecord;

    //Add Button
    @BindView(R.id.button_add_list)
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(HomeActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(HomeActivity.this.getClass().getSimpleName(), "onItemClick lessonId => " + getLessonRecords().get(position).getId());

                Bundle bundle = new Bundle();
                bundle.putString("id", getLessonRecords().get(position).getId());

                Intent intent = new Intent(HomeActivity.this, EditActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        listAdapter = new ListAdapter(HomeActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    @OnClick(R.id.button_add_list)
    public void LessonAdd(View view) {
        Intent intent = new Intent(HomeActivity.this, AddActivity.class);
        startActivity(intent);
    }

    private ArrayList<Lesson> getLessonRecords() {
        setLessons(realmHelper.getLessons());

        return lessons;
    }

    public void initList() {
        if (getLessonRecords().size() == 0) {
            textViewNoRecord.setVisibility(View.VISIBLE);
            listViewRecords.setVisibility(View.GONE);
        } else {
            textViewNoRecord.setVisibility(View.GONE);
            listViewRecords.setVisibility(View.VISIBLE);
            listAdapter.setLessons(getLessonRecords());
            listViewRecords.setAdapter(listAdapter);
        }
    }
}
