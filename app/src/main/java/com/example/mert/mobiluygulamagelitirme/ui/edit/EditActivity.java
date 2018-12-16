package com.example.mert.mobiluygulamagelitirme.ui.edit;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mert.mobiluygulamagelitirme.R;
import com.example.mert.mobiluygulamagelitirme.Util;
import com.example.mert.mobiluygulamagelitirme.data.db.Realm;
import com.example.mert.mobiluygulamagelitirme.data.model.Lesson;
import com.example.mert.mobiluygulamagelitirme.ui.MainActivity;
import com.example.mert.mobiluygulamagelitirme.ui.add.AddActivity;
import com.example.mert.mobiluygulamagelitirme.ui.home.HomeActivity;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends MainActivity implements Realm.Edit, Realm.Delete {

    //View
    @BindView(R.id.relativeLayout_view_edit)
    RelativeLayout relativeLayoutView;

    //Name
    @BindView(R.id.editText_lesson_name_edit)
    EditText editTextName;

    /* SeekBars */

    //Day
    @BindView(R.id.seekBar_day_edit)
    IndicatorSeekBar seekBarDay;
    @BindView(R.id.textView_day_edit)
    TextView textViewDay;

    //Hour
    @BindView(R.id.seekBar_hour_edit)
    IndicatorSeekBar seekBarHour;
    @BindView(R.id.textView_hour_edit)
    TextView textViewHour;

    //During
    @BindView(R.id.seekBar_during_edit)
    IndicatorSeekBar seekBarDuring;
    @BindView(R.id.textView_during_edit)
    TextView textViewDuring;

    //Edit
    @BindView(R.id.button_edit_edit)
    Button buttonAdd;

    //Delete
    @BindView(R.id.button_delete_edit)
    Button buttonDelete;

    private Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(EditActivity.this);

        Bundle bundle = getIntent().getExtras();

        lesson = new Lesson();

        lesson.setId(bundle.getString("id"));

        ArrayList<Lesson> lessonArrayList = new ArrayList<>();

        lessonArrayList.addAll(realmHelper.getLessons());

        for (int i = 0; i < lessonArrayList.size(); i++) {
            Log.i(EditActivity.this.getClass().getSimpleName(), "onItemLongClick lessonId => " + lessonArrayList.get(i).getId());

            if (lessonArrayList.get(i).getId().equals(lesson.getId())) {
                lesson = lessonArrayList.get(i);
            }
        }

        if (lessonArrayList.size() <= 0) {
            Toast.makeText(EditActivity.this, "Lesson can't find.", Toast.LENGTH_LONG).show();
            EditActivity.this.onBackPressed();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        relativeLayoutView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (relativeLayoutView != null) {
                    int heightDiff = relativeLayoutView.getRootView().getHeight() - relativeLayoutView.getHeight();
                    if (heightDiff > Util.dpToPx(EditActivity.this, 200)) { // if more than 200 dp, it's probably a keyboard...
                        buttonAdd.setVisibility(View.GONE);
                        buttonDelete.setVisibility(View.GONE);
                    } else {
                        buttonAdd.setVisibility(View.VISIBLE);
                        buttonDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        editTextName.setText(lesson.getName().toUpperCase());

        editTextName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        seekBarDay.setProgress(lesson.getDay());
        textViewDay.setText(Util.TIME.getDay(lesson.getDay()));

        seekBarHour.setProgress(lesson.getHour());

        if (lesson.getHour() < 10) {
            textViewHour.setText("0" + String.valueOf(lesson.getHour()));
        } else {
            textViewHour.setText(String.valueOf(lesson.getHour()));
        }

        seekBarDuring.setProgress(lesson.getDuring());

        seekBarHour.setProgress(lesson.getHour());
        if (lesson.getDuring() < 10) {
            textViewDuring.setText("0" + String.valueOf(lesson.getDuring()));
        } else {
            textViewDuring.setText(String.valueOf(lesson.getDuring()));
        }

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

    @OnClick(R.id.button_edit_edit)
    public void LessonUpdate() {
        if (isFieldFull()) {
            //Lesson
            Lesson object = new Lesson();
            object.setId(lesson.getId());
            object.setName(editTextName.getText().toString());

            //Lesson Time
            Log.i(this.getClass().getSimpleName(), "Lesson Time Day => " + seekBarDay.getProgress() + " Hour => " + seekBarHour.getProgress() + " During => " + seekBarDuring.getProgress());
            object.setDay(seekBarDay.getProgress());
            object.setHour(seekBarHour.getProgress());
            object.setDuring(seekBarDuring.getProgress());

            realmHelper.updateLesson(object, EditActivity.this);

        } else {
            Toast.makeText(EditActivity.this, "Fill the empty fields!", Toast.LENGTH_LONG).show();
        }

    }

    @OnClick(R.id.button_delete_edit)
    public void LessonDelete() {
        if (isFieldFull()) {
            realmHelper.deleteLesson(lesson.getId(), EditActivity.this);
        } else {
            Toast.makeText(EditActivity.this, "Fill the empty fields!", Toast.LENGTH_LONG).show();
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
    public void OnEditSuccess() {
        Toast.makeText(EditActivity.this, "Lesson updated!", Toast.LENGTH_LONG).show();
        EditActivity.this.onBackPressed();
    }

    @Override
    public void OnEditFailed() {
        Toast.makeText(EditActivity.this, "Lesson can't update!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnDeleteSuccess() {
        Toast.makeText(EditActivity.this, "Lesson deleted!", Toast.LENGTH_LONG).show();
        EditActivity.this.onBackPressed();
    }

    @Override
    public void OnDeleteFailed() {
        Toast.makeText(EditActivity.this, "Lesson can't delete!", Toast.LENGTH_LONG).show();
    }
}
