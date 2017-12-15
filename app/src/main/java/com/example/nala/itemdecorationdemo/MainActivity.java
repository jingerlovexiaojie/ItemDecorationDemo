package com.example.nala.itemdecorationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<String> data = new ArrayList();
    private DividerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data.clear();
        for (int i = 0; i < 18; i++) {
            data.add("aa");
            data.add("ab");
            data.add("ad");
            data.add("ac");
            data.add("ba");
            data.add("ca");
            data.add("da");
            data.add("ae");
            data.add("ea");
            data.add("fa");
            data.add("gb");
            data.add("hd");
            data.add("ic");
            data.add("ga");
            data.add("ka");
            data.add("ma");
            data.add("zd");
            data.add("ra");
        }
        adapter = new DividerAdapter(this, data);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapter);
        //左右标签效果
      //  recycler.addItemDecoration(new LeftAndRightTagDecoration(this));
        //简单的分割线
      //  recycler.addItemDecoration(new SimplePaddingDecoradtion(this));
     /*   recycler.addItemDecoration(new SectionDecoration(this, new SectionDecoration.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return Character.toUpperCase(data.get(position).charAt(0));
            }

            @Override
            public String getGroupFirstLine(int position) {
                return data.get(position).substring(0,1).toUpperCase();
            }
        }));*/
     recycler.addItemDecoration(new PinnedSectionDecoration(this, new PinnedSectionDecoration.DecorationCallback() {
         @Override
         public long getGroupId(int position) {
             return Character.toUpperCase(data.get(position).charAt(0));
         }

         @Override
         public String getGroupFirstLine(int position) {
             return data.get(position).substring(0,1).toUpperCase();
         }
     }));
    }
}
