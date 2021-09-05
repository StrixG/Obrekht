package me.obrecht.developerslife;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.developerslife.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
    }
}