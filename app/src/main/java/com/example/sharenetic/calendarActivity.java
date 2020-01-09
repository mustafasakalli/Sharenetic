package com.example.sharenetic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class calendarActivity extends AppCompatActivity {

    private ImageView icon;
    private TextView month;
    private TextView exp;

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        icon = (ImageView) findViewById(R.id.icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(calendarActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        month = (TextView) findViewById(R.id.monthTitle);
        exp = (TextView) findViewById(R.id.exp);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactCalendarView);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        //Set an event for GUI final exam on 09.01.2020 10:00

        Event ev1 = new Event(Color.BLUE, 1578582000000L, "GUI Final Exam" );
        compactCalendar.addEvent(ev1);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                exp.setText(null);
                exp.setBackgroundColor(Color.TRANSPARENT);
                Context context = getApplicationContext();

                if (dateClicked.toString().compareTo("Thu Jan 09 00:00:00 GMT+03:00 2020") == 0){
                    int myColorValue = Color.parseColor("#80E4E4E4");

                    exp.setBackgroundColor(myColorValue);
                    exp.setText("GUI Final Exam\n10:00\n\nGrading Policy for Phase III:\n-Predemonstration (%10)" +
                                "\n-Demonstration (%20)\n-Implementation ");
                }
                else {
                    Toast.makeText(context, "No event!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                month.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

    }
}
