package com.geekbrains.cases;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddWork extends AppCompatActivity {
    private TextInputEditText editText;
    private Button btnAdd;
    final static String KEY_TO_DATA = "key_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        initView();
        addOnClick();
    }

    private void initView() {
        editText = findViewById(R.id.editText);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private ArrayList<String> dataTime() {
        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        // Форматирование времени как "часы:минуты:секунды"
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);

        ArrayList<String> al = new ArrayList<>();
        al.add(0, dateText);
        al.add(1, timeText);
        return al;
    }

    private void addOnClick() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                ArrayList<String> al = dataTime();
                al.add(2, text);

                Intent intent = new Intent(AddWork.this, MainActivity.class);
                intent.putExtra(KEY_TO_DATA, al);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
