package br.com.csbruno.library;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;


public class ScrollableBar extends ViewGroup implements GestureDetector.OnGestureListener {


    private GestureDetector _gestureScanner;
    private Scroller _scroller;
    private int _viewPortX, _viewPortY;
    private int _paddingLeft;
    private int _paddingRight;
    private int _paddingBottom;
    private int _paddingTop;


    @ColorInt
    private int _borderColor;

    public ScrollableBar(Context context) {
        this(context, null);
    }

    public ScrollableBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollableBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        _scroller = new Scroller(this.getContext());

    }
    public void setPadding(int l , int r, int b, int t){

        _paddingLeft = l;
        _paddingRight = r;
        _paddingBottom = b;
        _paddingTop = t;
    }
    public void setPadding(int all){

        _paddingLeft = all;
        _paddingRight = all;
        _paddingBottom = all;
        _paddingTop = all;
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    public void scrollNow(float x) {
        _viewPortX = (int) x;

        requestLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int count = getChildCount();

        int maxHeight = 0;
        int childState = 0;
        int maxChildWidth = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
            if (child.getVisibility() == GONE)
                continue;
            maxChildWidth = Math.max(maxChildWidth, child.getMeasuredWidth());
        }

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                continue;

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());


        setMeasuredDimension(getMeasuredWidth(), maxHeight + getPaddingBottom() + getPaddingTop() );

    }


    @Override
    public int getPaddingBottom() {
        return super.getPaddingBottom() + _paddingBottom;
    }

    @Override
    public int getPaddingTop() {
        return super.getPaddingTop() +  _paddingTop;
    }

    @Override
    public int getPaddingLeft() {
        return super.getPaddingLeft() + _paddingLeft;
    }

    @Override
    public int getPaddingRight() {
        return super.getPaddingRight() + _paddingRight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentLeft = getPaddingLeft();
        final int parentRight = r - l - getPaddingRight();
        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();
        final int count = getChildCount();

        int childLeft = parentLeft;
        int childTop = parentTop;

        int currentPosX = getPaddingLeft();

        if (_scroller != null) {
            if (_scroller.computeScrollOffset()) {
                scrollNow(_scroller.getCurrX());

                /* This is necessary to request layout while in layout loop */
                post(new Runnable() {
                    public void run() {
                        requestLayout();
                    }
                });
            }
        }

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            if (child.getVisibility() != GONE) {


                setChildFrame(child, Math.min(currentPosX + _viewPortX, currentPosX), childTop, width, height);
                currentPosX += width + _paddingRight + _paddingLeft;
            }
        }


    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }


    private boolean canScroll() {

        int widthSum = 0;

        for (int i = 0; i < getChildCount(); i++) {
            widthSum += getChildAt(i).getMeasuredWidth();
        }
        return widthSum < getMeasuredWidth();
    }



}
