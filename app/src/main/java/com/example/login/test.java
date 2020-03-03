package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class test extends AppCompatActivity {

    private NavigationView navigationview;
    private FirebaseAuth firebaseauth;
    private ListView drawerlist;
    private Switch darklight;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.darktheme);
        }
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        navigationview = findViewById(R.id.navigation);

        Menu menu = navigationview.getMenu();
        MenuItem menuItem = menu.findItem(R.id.switch_item);
        View action = MenuItemCompat.getActionView(menuItem);
        darklight = action.findViewById(R.id.app_bar_switch);


        firebaseauth = FirebaseAuth.getInstance();


        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), test.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.logout:
                        firebaseauth.signOut();
                        finish();
                        Intent intent = new Intent(test.this, login.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                }

                return false;
            }
        });

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        {
            darklight.setChecked(true);
        }

        darklight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    startActivity(new Intent(getApplicationContext(), test.class));
                    finish();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    startActivity(new Intent(getApplicationContext(), test.class));
                    finish();
                }
            }
        });
    }
}
