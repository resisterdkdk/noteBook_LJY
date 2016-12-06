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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
/**
 * Created by Administrator on 2016/9/22 0022.
 */

public class NewNote extends Activity{
    String Date[]={"SUN","MON","TUES","WED","THURS","FRI","SAT"};
    String Month[]={
            "January ","February","March","April","May","June ","July ","Aguest","September","October","November","December"
    };
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
    private int month_num;
    protected static SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnote);
        bt6 = (ImageButton)findViewById(R.id.imageButton1);
        //字体
        AssetManager mgr=getAssets();
        Typeface tf=Typeface.createFromAsset(mgr, "fonts/dishu.TTF");
        AssetManager mgr1=getAssets();
        Typeface tf1=Typeface.createFromAsset(mgr1, "fonts/cartoon.ttf");

        tv = (TextView)findViewById(R.id.textView);
        tv.setTypeface(tf1);
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        day = cal.get(Calendar.DATE);
        month_num=cal.get(Calendar.MONTH)+1;
        month = Month[month_num-1];
        date = Date[cal.get(Calendar.DAY_OF_WEEK)-1];
        tv.setText(date+"/"+month+" "+day+"/"+year);

        bt_done = (Button)findViewById(R.id.button6);
        bt_done.setTypeface(tf1);
        ev = (EditText) findViewById(R.id.editView1);
        ev.setTypeface(tf);


        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                content = ev.getText().toString();
                String date1 = date+"/"+month+" "+day+"/"+year;
                MainActivity get = new MainActivity();
                //文件编号
                String num = String.valueOf(get.getItem_num());

                //sharepreference存数据,day,date,content
                pref =getSharedPreferences("mypref",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                String day_1 =num+"_day";
                String date_1 = num+"_date";
                String content_1 = num+"_content";
                String month_1 = num+"_month";
                String year_1 = num+"_year";

                editor.putInt(year_1,year);
                editor.putInt(month_1,month_num);
                editor.putString(day_1,String.valueOf(day));
                editor.putString(date_1,date);
                editor.putString(content_1,content);
                editor.commit();
                get.setItem_num(get.getItem_num()+1);
                Toast.makeText(getApplicationContext(),"新建日记成功",Toast.LENGTH_SHORT).show();
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
    public int getYear(int num)
    {
        String year_1 =String.valueOf(num)+"_year";
        return pref.getInt(year_1,0);
    }
    public int getMonth_num(int num)
    {
        String month_1 =String.valueOf(num)+"_month";
        return pref.getInt(month_1,0);
    }
    public String getDay(int num){
         String day_1 =String.valueOf(num)+"_day";
         return pref.getString(day_1,null);
    }
    public String getDate(int num){
        String date_1 =String.valueOf(num)+"_date";
        return pref.getString(date_1,null);
    }
    public String getContent(int num){
        String content_1 =String.valueOf(num)+"_content";
        return pref.getString(content_1,null);
    }
}
