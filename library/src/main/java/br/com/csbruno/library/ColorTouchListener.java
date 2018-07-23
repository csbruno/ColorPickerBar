package br.com.csbruno.library;

import android.graphics.Color;
import android.support.annotation.ColorInt;

public interface ColorTouchListener {

    public void onSingleTouch(int position,@ColorInt int color);

    public void onLongTouch(int position,@ColorInt int color);

}
