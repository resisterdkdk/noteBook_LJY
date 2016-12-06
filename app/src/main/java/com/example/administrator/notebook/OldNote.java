package com.example.administrator.notebook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/9/25 0025.
 */

public class OldNote extends Activity {
    String Date[]={"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
    String Month[]={
            "January ","February","March","April","May","June ","July ","Aguest","September","October","November","December"
    };
    private Button bt_del;
    private ImageButton bt6;
    private TextView tv;
    private Button bt_done;
    private EditText ev;
    private String content ;
    private Calendar cal;
    private  int year;
    private String month;
    private int day;
    private String date;
    String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oldnote);
        //字体
        AssetManager mgr=getAssets();
        Typeface tf=Typeface.createFromAsset(mgr, "fonts/dishu.TTF");

        AssetManager mgr1=getAssets();
        Typeface tf1=Typeface.createFromAsset(mgr1, "fonts/cartoon.ttf");

        bt6 = (ImageButton)findViewById(R.id.imageButton1_1);
        final NewNote change = new NewNote();
        tv = (TextView)findViewById(R.id.textView_1);
        tv.setTypeface(tf1);
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        day = cal.get(Calendar.DATE);
        month = Month[cal.get(Calendar.MONTH)];
        date = Date[cal.get(Calendar.DAY_OF_WEEK)-1];
        tv.setText(date+"/"+month+" "+day+"/"+year);

        bt_del = (Button)findViewById(R.id.button_del);
        bt_del.setTypeface(tf1);
        bt_done = (Button)findViewById(R.id.button6_1);
        bt_done.setTypeface(tf1);
        ev = (EditText) findViewById(R.id.editView1_1);
        ev.setTypeface(tf);
        MainActivity get = new MainActivity();
        num = String.valueOf(get.getPosition_1());
        String content_1 = num+"_content";
        content = change.pref.getString(content_1,null);
        ev.setText(content);

        bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = change.pref.edit();
                String day_1 =num+"_day";
                editor.remove(day_1);
                String date_1 = num+"_date";
                editor.remove(date_1);
                String content_1 = num+"_content";
                editor.remove(content_1);
                String month_1 = num+"_month";
                editor.remove(month_1);
                String year_1 = num+"_year";
                editor.remove(year_1);
                editor.commit();
                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


                bt_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent data = new Intent();
                        content = ev.getText().toString();
                        //sharepreference存数据,day,date,content
                        SharedPreferences.Editor editor = change.pref.edit();
                        String content_1 = num + "_content";
                        editor.putString(content_1, content);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "修改日记成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date1 = date+"/"+month+" "+day+"/"+year;
                ev.append(date1);
            }
        });

    }
}
