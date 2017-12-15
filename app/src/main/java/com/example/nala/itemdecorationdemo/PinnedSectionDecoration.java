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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

/**
 * Created by Nala on 2017/12/13.
 */

public class PinnedSectionDecoration extends RecyclerView.ItemDecoration {

    private final Paint paint;
    private final TextPaint textPaint;
    private final Paint.FontMetrics fontMetrics;
    private final int topGap;
    private DecorationCallback callback;

    public PinnedSectionDecoration(Context context,DecorationCallback decorationCallback) {
        this.callback = decorationCallback;
        Resources res = context.getResources();

        paint = new Paint();
        paint.setColor(Color.parseColor("#00aa00"));

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(80);
        textPaint.setColor(Color.BLACK);
       // textPaint.getFontMetrics(fontMetrics);
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = new Paint.FontMetrics();
        topGap = 70;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        long groupId = callback.getGroupId(pos);
        if(groupId < 0){
            return;
        }
        if(pos == 0 || isFirstInGroup(pos)){
            outRect.top = topGap;
        }else{
            outRect.top = 0;
        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = textPaint.getTextSize() + fontMetrics.descent;

        long preGroupId ,groupId = -1;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = callback.getGroupId(position);
            if(groupId < 0 || groupId == preGroupId){
                continue;
            }
            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if(TextUtils.isEmpty(textLine)){
                continue;
            }
            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            if (position + 1 < itemCount) { //下一个和当前不一样移动当前
                long nextGroupId = callback.getGroupId(position + 1);
                if (nextGroupId != groupId && viewBottom < textY ) {//组内最后一个view进入了header
                  //分割线的bottom坐标不再是固定值，变成viewbottom的值向上滑时 慢慢变小
                    textY = viewBottom;
                }
            }

            c.drawRect(left, textY - topGap, right, textY, paint);
            c.drawText(textLine, left, textY, textPaint);

        }
    }

    private boolean isFirstInGroup(int pos){
        if(pos == 0){
            return true;
        }else{
            long preGroupId = callback.getGroupId(pos - 1);
            long groupId = callback.getGroupId(pos);
            return preGroupId != groupId;
        }
    }

    public interface  DecorationCallback{
        long getGroupId(int position);

        String getGroupFirstLine(int pposition);
    }
}
