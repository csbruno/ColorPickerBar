package br.com.csbruno.library;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ColorPallete extends View {
    public ColorPallete(Context context) {
        this(context, null);
    }

    public ColorPallete(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorPallete(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
