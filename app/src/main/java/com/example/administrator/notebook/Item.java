package com.example.administrator.notebook;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/24 0024.
 */

public class Item extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);
        AssetManager mgr1=getAssets();
        Typeface tf1=Typeface.createFromAsset(mgr1, "fonts/cartoon.ttf");
/*
        AssetManager mgr=getAssets();
        Typeface tf=Typeface.createFromAsset(mgr, "fonts/jianti.TTF");*/
        TextView tv_title = (TextView)findViewById(R.id.ItemTitle);
        tv_title.setTypeface(tf1);
        TextView tv_Item = (TextView)findViewById(R.id.ItemText);
        tv_Item.setTypeface(tf1);
        TextView tv_text = (TextView)findViewById(R.id.textView2);
        tv_text.setTypeface(tf1);
    }

}
