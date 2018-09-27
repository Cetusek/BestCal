package com.marek.bestcal.config;


import android.graphics.Color;

public class Configuration {

    private static Configuration instance;

    private Configuration() {

    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public int getHolidayColor() {
        return Color.RED;
    }

}
