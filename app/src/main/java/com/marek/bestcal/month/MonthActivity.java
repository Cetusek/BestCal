package com.marek.bestcal.month;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.marek.bestcal.R;

public class MonthActivity extends AppCompatActivity {

    GridLayout cellLayout;
    MonthCell[] cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        mapGUI();
    }

    private void mapGUI() {
        mapCells();
    }

    private void mapCells() {
        cellLayout = (GridLayout) findViewById(R.id.MonthActivityMonthLayout);
        cells = new MonthCell[cellLayout.getRowCount()*cellLayout.getColumnCount()];
        for (byte i = 0; i < cells.length; i++) {
            cells[i] = new MonthCell(this, i);
            cellLayout.addView(cells[i]);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            deployCells();
        }
    }

    private void deployCells() {
        int cellWidth = cellLayout.getWidth() / cellLayout.getColumnCount();
        int cellHeight = cellLayout.getHeight() / cellLayout.getRowCount();
        Log.i("MY_APP", "cellWidth = "+cellLayout.getWidth());
        for (int i = 0; i < cells.length; i++) {
            cells[i].setDimensions(cellWidth, cellHeight);
        }
    }


}
