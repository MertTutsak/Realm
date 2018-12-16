package com.example.mert.mobiluygulamagelitirme.ui.add;

import android.os.Build;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mert.mobiluygulamagelitirme.R;
import com.example.mert.mobiluygulamagelitirme.Util;
import com.example.mert.mobiluygulamagelitirme.data.db.Realm;
import com.example.mert.mobiluygulamagelitirme.data.model.Lesson;
import com.example.mert.mobiluygulamagelitirme.ui.MainActivity;
import com.example.mert.mobiluygulamagelitirme.ui.home.HomeActivity;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddActivity extends MainActivity implements Realm.Add {

    //Name
    @BindView(R.id.editText_lesson_name_add)
    EditText editTextName;

    /* SeekBars */

    //Day
    @BindView(R.id.seekBar_day_add)
    IndicatorSeekBar seekBarDay;
    @BindView(R.id.textView_day_add)
    TextView textViewDay;

    //Hour
    @BindView(R.id.seekBar_hour_add)
    IndicatorSeekBar seekBarHour;
    @BindView(R.id.textView_hour_add)
    TextView textViewHour;

    //During
    @BindView(R.id.seekBar_during_add)
    IndicatorSeekBar seekBarDuring;
    @BindView(R.id.textView_during_add)
    TextView textViewDuring;

    //Add
    @BindView(R.id.button_add_add)
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(AddActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        editTextName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        seekBarDay.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                textViewDay.setText(Util.TIME.getDay(seekParams.progress));
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

        seekBarHour.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if (seekParams.progress < 10) {
                    textViewHour.setText("0" + String.valueOf(seekParams.progress));
                } else {
                    textViewHour.setText(String.valueOf(seekParams.progress));
                }

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

        seekBarDuring.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                textViewDuring.setText("0" + String.valueOf(seekParams.progress));
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }

    @OnClick(R.id.button_add_add)
    public void LessonAdd() {
        if (isFieldFull()) {
            //Lesson
            Lesson lesson = new Lesson();
            lesson.setId(UUID.randomUUID().toString());
            lesson.setName(editTextName.getText().toString());

            //Lesson Time
            Log.i(this.getClass().getSimpleName(), "Lesson Time Day => " + seekBarDay.getProgress() + " Hour => " + seekBarHour.getProgress() + " During => " + seekBarDuring.getProgress());
            lesson.setDay(seekBarDay.getProgress());
            lesson.setHour(seekBarHour.getProgress());
            lesson.setDuring(seekBarDuring.getProgress());

            realmHelper.addLesson(lesson, AddActivity.this);

        } else {
            Toast.makeText(AddActivity.this, "Fill the empty fields!", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isFieldFull() {
        if (editTextName == null) {
            return false;
        } else if (editTextName.getText() == null) {
            return false;
        } else if (editTextName.getText().toString().matches("")) {
            return false;
        }

        return true;
    }

    @Override
    public void OnAddSuccess() {
        Toast.makeText(AddActivity.this, "Lesson added!", Toast.LENGTH_LONG).show();
        AddActivity.this.onBackPressed();
    }

    @Override
    public void OnAddFailed() {
        Toast.makeText(AddActivity.this, "Lesson can't add!", Toast.LENGTH_LONG).show();
    }
}
