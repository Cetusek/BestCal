package com.marek.bestcal.month;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.marek.bestcal.R;
import com.marek.bestcal.main.model.DayListItemEvent;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DialogCell extends Dialog {

    private TextView eventsView;
    private TextView dateView;

    private Date cellDate;

    public DialogCell(@NonNull Context context, Date cellDate) {
        super(context);
        this.cellDate = cellDate;
        setContentView(R.layout.dialog_month_cell);
        mapGUI();
        setDateView();

    }

    private void mapGUI() {
        eventsView = (TextView) findViewById(R.id.DialogCellEvents);
        dateView = (TextView) findViewById(R.id.DialogCellDate);
    }

    private void setDateView(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM yyyy");
        dateView.setText(sdf.format(cellDate));
    }

    public void deployEvents(List<DayListItemEvent> events) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < events.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(events.get(i).getEventLabel());
        }
        eventsView.setText(sb.toString());
    }
}
