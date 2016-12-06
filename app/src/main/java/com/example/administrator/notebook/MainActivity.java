package com.example.administrator.notebook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    //维护一个key的列表
    int key[] = new int[65535];
    private static boolean mode =false;
    /************************显示时间*************/
    private final static String TAG="TimeDate";
    //获取日期格式器对象
    DateFormat fmtDate = new java.text.SimpleDateFormat("yyyy-MM-dd");
    //定义一个TextView控件对象
    TextView txtDate = null;
    //获取一个日历对象
    java.util.Calendar dateAndTime = java.util.Calendar.getInstance(Locale.CHINA);


    //当点击DatePickerDialog控件的设置按钮时，调用该方法
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            //修改日历控件的年，月，日
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(java.util.Calendar.YEAR, year);
            dateAndTime.set(java.util.Calendar.MONTH, monthOfYear);
            dateAndTime.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
            //将页面TextView的显示更新为最新时间
            upDateDate();

        }
    };
    /***********************************显示时间*************************/

    private ImageButton bt_all;
    public static int Item_num = 0;
    private static int position_1;
    private ListView lv;
    private ImageButton bt_add;
    private TextView et;
    private Button bt_year;
    private Button bt_month;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//字体


        AssetManager mgr1=getAssets();
        Typeface tf1=Typeface.createFromAsset(mgr1, "fonts/cartoon.ttf");

        //添加ADD日志，消息回传
        bt_add = (ImageButton)findViewById(R.id.button_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,NewNote.class);
                startActivity(intent);
            }
        });
        bt_all = (ImageButton)findViewById(R.id.toggleButton);
        bt_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode==true)
                {
                    mode = false;
                    Toast.makeText(getApplicationContext(),"单月日记模式",Toast.LENGTH_SHORT).show();
                    onResume();
                }else{
                    mode = true;
                    Toast.makeText(getApplicationContext(),"显示所有日记",Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });

/************************时间***********/
        Log.d(TAG,"onCreate");
        txtDate =(Button)findViewById(R.id.button_time);
        txtDate.setTypeface(tf1);
        txtDate.setClickable(true);
        txtDate.setFocusable(true);
        txtDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG,"txtDate click start");
                DatePickerDialog  dateDlg = new DatePickerDialog(MainActivity.this,
                        d,
                        dateAndTime.get(java.util.Calendar.YEAR),
                        dateAndTime.get(java.util.Calendar.MONTH),
                        dateAndTime.get(java.util.Calendar.DAY_OF_MONTH));

                dateDlg.show();

                Log.d(TAG,"Date show");
            }
        });
        upDateDate();

    }


    @Override
    protected void onResume() {
        super.onResume();

        lv = (ListView) findViewById(R.id.lv);
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();
        /*在数组中存放数据*/
        int num=0;
        for(int i=0;i<Item_num;i++)
        {
            NewNote note = new NewNote();
            HashMap<String, Object> map = new HashMap<String, Object>();
            //map.put("ItemImage", R.drawable.class);//加入图片
            String a =note.getDate(i);
            String getCurrentTime = txtDate.getText().toString();
            String[] Time  = getCurrentTime.split("\\-");
            int year_num = Integer.parseInt(Time[0]);
            int month_num = Integer.parseInt(Time[1]);
            //显示当月/全部的日记
            if((year_num == note.getYear(i))&&(month_num ==note.getMonth_num(i) )|| mode) {
                String b =note.getDate(i);
                if(note.getDate(i)==null) continue;
                map.put("ItemTitle", note.getDate(i));
                map.put("ItemText", note.getDay(i));
                map.put("textView2", note.getContent(i));
                map.put("textView4",String.valueOf(i));
                key[num++] = i;
                listItem.add(map);
            }
        }

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据
                R.layout.item,//每一行的布局//动态数组中的数据源的键对应到定义布局的View中
                new String[]{"ItemImage","ItemTitle", "ItemText","textView2","textView4"},
                new int[]{R.id.ItemImage,R.id.ItemTitle,R.id.ItemText,R.id.textView2,R.id.textView4}
        );

        lv.setAdapter(mSimpleAdapter);//为ListView绑定适配器
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //setTitle("你点击了第"+position+"行");//设置标题栏显示点击的行
                position_1 = key[position];
                Intent intent =new Intent(MainActivity.this,OldNote.class);
                startActivityForResult(intent,position);
            }
        });
    }
    int getPosition_1(){return position_1;}
    int getItem_num(){
        return Item_num;
    }
    void setItem_num(int num){
        Item_num = num;
    }
    private void upDateDate() {
        if(mode==false)
            Toast.makeText(getApplicationContext(),"单月日记模式",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"显示所有日记",Toast.LENGTH_SHORT).show();
        txtDate.setText(fmtDate.format(dateAndTime.getTime()));
        onResume();
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


    }
}
