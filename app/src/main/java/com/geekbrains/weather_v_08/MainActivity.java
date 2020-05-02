package com.geekbrains.weather_v_08;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;

import com.geekbrains.weather_v_08.ui.home.HomeFragment;
import com.geekbrains.weather_v_08.ui.search.SearchFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

        CoordinatorLayout coordinatorLayout = findViewById(R.id.fonapp);
        Date currentDate = new Date();
        if (UpdateBackground(currentDate)) {
            coordinatorLayout.setBackgroundResource(R.drawable.fon_day);
        } else {
            coordinatorLayout.setBackgroundResource(R.drawable.fon_night);
        }
    }


    private boolean UpdateBackground(Date currentDate) {
        DateFormat timeFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        DateFormat formatE = new SimpleDateFormat("EEE", Locale.ENGLISH);
        DateFormat formatM = new SimpleDateFormat("MMM", Locale.ENGLISH);
        DateFormat formatD = new SimpleDateFormat("dd", Locale.ENGLISH);
        DateFormat formatZ = new SimpleDateFormat("zzz", Locale.ENGLISH);
        DateFormat formatY = new SimpleDateFormat("yyyy", Locale.ENGLISH);

        String textE = formatE.format(currentDate);
        String textM = formatM.format(currentDate);
        String textD = formatD.format(currentDate);
        String textZ = formatZ.format(currentDate);
        String textY = formatY.format(currentDate);

        String timeNight = textE + " " + textM + " " + textD + " "
                + "21:00:00" + " " + textZ + " " + textY;
        String timeDay = textE + " " + textM + " " + textD + " "
                + "06:00:00" + " " + textZ + " " + textY;

        try {
            Date dateNight = timeFormat.parse(timeNight);
            Date dateDay = timeFormat.parse(timeDay);

            return currentDate.after(dateDay) && currentDate.before(dateNight);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        return toolbar;
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_search:
//                HomeFragment homeFragment = new HomeFragment();
//                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//                navController.navigate(R.id.action_nav_home_to_nav_search);
//                return true;
            case R.id.action_star:
                Log.i(TAG, "star");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void replaceFragments(SearchFragment fragmentClass) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragmentClass).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
