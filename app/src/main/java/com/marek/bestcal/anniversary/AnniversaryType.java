package com.marek.bestcal.anniversary;

import android.content.Context;

import com.marek.bestcal.R;

public enum AnniversaryType {

    BIRTH("*", R.string.birth),
    DEATH("+", R.string.death),
    WEDDING("!", R.string.wedding);

    private String sign;
    private int descriptionId;
    private Context context;


    private AnniversaryType(String sign, int descriptionId) {
        this.sign = sign;
        this.descriptionId = descriptionId;
    }

    public String getSign() {
        return sign;
    }

    public String getDescription(Context context) {
        this.context = context;
        return context.getString(descriptionId);
    }

    @Override
    public String toString() {
        String result;
        if (context != null) {
            result = context.getString(descriptionId);
        }
        else {
            result = sign;
        }
        return result;
    }

    private void setContext(Context context) {
        this.context = context;
    }

    public static void setContexts(Context context) {
        for (AnniversaryType anniversaryType : AnniversaryType.values()) {
            anniversaryType.setContext(context);
        }
    }


}
