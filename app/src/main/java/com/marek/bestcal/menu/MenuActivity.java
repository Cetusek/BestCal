package com.marek.bestcal.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.marek.bestcal.R;
import com.marek.bestcal.anniversary.AnniversaryActivity;
import com.marek.bestcal.config.calendarlist.CalendarListActivity;

public class MenuActivity extends AppCompatActivity {

    ImageButton addAnniversaryButton;
    ImageButton settingsButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mapGUI();
    }

    private void mapGUI() {
        addAnniversaryButton = (ImageButton) findViewById(R.id.ActivityMenuAddAnniversaryButton);
        addAnniversaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddAnniversaryButtonPressed();
            }
        });
        settingsButton = (ImageButton) findViewById(R.id.ActivityMenuSettingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSettingsButtonPressed();
            }
        });
    }

    private void onAddAnniversaryButtonPressed() {
        Intent intent = new Intent(this, AnniversaryActivity.class);
        startActivity(intent);
    }

    private void onSettingsButtonPressed() {
        Intent intent = new Intent(this, CalendarListActivity.class);
        startActivity(intent);
    }
}
