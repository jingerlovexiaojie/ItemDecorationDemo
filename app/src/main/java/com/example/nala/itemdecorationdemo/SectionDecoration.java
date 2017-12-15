package com.example.nala.itemdecorationdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by Nala on 2017/12/13.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {
    private final Paint paint;
    private DecorationCallback callback;
    private TextPaint textPaint;
    private final Paint.FontMetrics fontMetrics;
    private final int topGap;

    public SectionDecoration(Context context,DecorationCallback callback) {
        Resources res = context.getResources();
        this.callback = callback;

        paint = new Paint();
        paint.setColor(Color.BLUE);

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(80);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = new Paint.FontMetrics();
        topGap =70;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        long groupId = callback.getGroupId(pos);
        if(groupId < 0){
            return;
        }
        if(pos == 0 || isFirstInGroup(pos)){//同组的第一个才添加padding
            outRect.top = topGap;
        }else{
            outRect.top = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            long groupId = callback.getGroupId(position);
            if(groupId < 0){
                return;
            }
            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if(position == 0 || isFirstInGroup(position)){
                int top = view.getTop() - topGap;
                int bottom = view.getTop();
                c.drawRect(left,top,right,bottom,paint);
                c.drawText(textLine,left,bottom ,textPaint);
            }

        }
    }

    private boolean isFirstInGroup(int pos){
       if(pos == 0){
           return true;
       }else{
           long preGroupId = callback.getGroupId(pos -1);
           long groupId = callback.getGroupId(pos);
           return preGroupId != groupId;
       }
   }
    public interface DecorationCallback{
        long getGroupId(int position);
        String getGroupFirstLine(int position);
    }
}


