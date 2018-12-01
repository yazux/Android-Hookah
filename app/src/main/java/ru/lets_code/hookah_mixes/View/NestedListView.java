package ru.lets_code.hookah_mixes.View;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

public class NestedListView extends ListView {
    private android.view.ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public NestedListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (getCount() != oldCount)
        {
            int height = getChildAt(0).getHeight() + 1 ;
            oldCount = getCount();
            params = getLayoutParams();
            params.height = getCount() * height;
            setLayoutParams(params);
        }
        super.onDraw(canvas);
    }
}
