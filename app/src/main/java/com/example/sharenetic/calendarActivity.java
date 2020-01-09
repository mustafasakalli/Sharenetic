package com.example.sharenetic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.EventLog;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class calendarActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    static final int progressBarType = 0;
    private ImageView icon;
    private TextView month;
    private TextView exp;
    Button downloadButton;
    static String pictureURL = "https://image.slidesharecdn.com/8d5ce9b8-c700-4709-81b9-3c68d5246de0-151128045200-lva1-app6892/95/ms-in-mis-course-curriculum-1-638.jpg";

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        downloadButton = (Button)findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File syllabusFile = new File(Environment.getExternalStorageDirectory().getPath()+"/syllabus.jpg");

                if(syllabusFile.exists()) {
                    Toast.makeText(calendarActivity.this, "File Already Exist", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(calendarActivity.this, "File Downloading", Toast.LENGTH_SHORT).show();
                    new Downloader().execute(pictureURL);
                }


            }
        });



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

    // THREAD CODE BEGINS


    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id){

            case progressBarType:
                pDialog = new ProgressDialog(calendarActivity.this);
                pDialog.setMessage("File Downloading , please wait");
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(false);
                return pDialog;
            default:
                return null;
        }

    }

    public class Downloader extends AsyncTask<String, String, Object>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progressBarType);
        }

        @Override
        protected Object doInBackground(String[] objects) {
            int number;
            try {
                URL url = new URL(objects[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int fileLength = connection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream(), 10 * 1024);
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()
                        +"/syllabus.jpg");

                byte data[] = new byte[1024]; //Buffer name

                int total = 0;


                while ((number = input.read(data)) > 0){

                    // > 0 is equal to != -1

                    output.write(data, 0 , number);

                    total += number;

                    publishProgress(String.valueOf((total * 100 ) / fileLength));
                }

                output.flush();
                output.close();
                input.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            dismissDialog(progressBarType);
            Toast.makeText(calendarActivity.this, "Download finished !", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String[] values) {
            pDialog.setProgress(Integer.parseInt(values[0]));
        }
    }

}
