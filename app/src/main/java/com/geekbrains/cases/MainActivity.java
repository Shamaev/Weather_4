package com.geekbrains.cases;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.geekbrains.cases.model.Cases;
import com.geekbrains.cases.model.EducationDao;
import com.geekbrains.cases.model.EducationSource;
import com.geekbrains.cases.model.WorksRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorksRecyclerAdapter adapter;
    private EducationSource educationSource;

    private int CODE_KEY = 1;
    final static String KEY_TO_DATA = "key_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initView();

        initRecyclerView();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddWork.class);
                startActivityForResult(intent, CODE_KEY);
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.list);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        EducationDao educationDao = App
                .getInstance()
                .getEducationDao();
        educationSource = new EducationSource(educationDao);

        adapter = new WorksRecyclerAdapter(educationSource,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_KEY && resultCode == Activity.RESULT_OK) {
//            String text = data.getStringExtra(AddWork.KEY_TO_DATA);

            ArrayList<String> al = data.getStringArrayListExtra(AddWork.KEY_TO_DATA);
            String text = al.get(2);
            String dataCase = al.get(0);
            String timeCase = al.get(1);

            Cases cases = new Cases();
            cases.textWork = text;
            cases.checkWork = 0;
            cases.data = dataCase;
            cases.time = timeCase;
            // Добавляем студента
            educationSource.addStudent(cases);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.clear_list:
                clearList();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.help:
                return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearList() {
        educationSource.deleteStudent();
        adapter.notifyDataSetChanged();

    }


}