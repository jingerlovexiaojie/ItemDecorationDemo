package com.example.nala.itemdecorationdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Nala on 2017/12/13.
 */

public class SimplePaddingDecoradtion extends RecyclerView.ItemDecoration {
    private final Paint paint;
    private int dividerHeight;

    public SimplePaddingDecoradtion(Context context) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#00aabb"));
        this.dividerHeight = 10;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left,top,right,bottom,paint);

        }

    }
}
