package br.com.csbruno.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorPickerBar extends ScrollableBar{

    private GestureDetector _gestureScanner;
    private Scroller _scroller;
    private int _viewPortX, _viewPortY;
    private List<Integer> _colorList;
    private ColorTouchListener _colorTouchListener;
    private int _colorBorderSize;
    private float _radius;
    private float _size;

    @ColorInt
    private int _borderColor;

    public ColorPickerBar(Context context) {
        this(context, null);
    }

    public ColorPickerBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPickerBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(getContext(), attrs, defStyleAttr);

    }

    private void init() {
        final Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);

        _colorList = new ArrayList<>();
        _scroller = new Scroller(this.getContext());

    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerBar, defStyleAttr, 0);
        int padding = 0;
        if (attributes != null) {
            _colorBorderSize = attributes.getDimensionPixelSize(R.styleable.ColorPickerBar_colorBorderSize, 0);
            _borderColor = attributes.getColor(R.styleable.ColorPickerBar_borderColor, Color.WHITE);
            _radius = attributes.getDimensionPixelSize(R.styleable.ColorPickerBar_colorRadius, 0);
            _size = attributes.getDimension(R.styleable.ColorPickerBar_colorSize, 16);

            padding = (int) attributes.getDimensionPixelSize(R.styleable.ColorPickerBar_colorPadding, 0);

            if (padding == 0) {
                setPadding(  attributes.getDimensionPixelSize(R.styleable.ColorPickerBar_colorPaddingLeft, 0),
                attributes.getDimensionPixelSize(R.styleable.ColorPickerBar_colorPaddingRight, 0),
                 attributes.getDimensionPixelSize(R.styleable.ColorPickerBar_colorPaddingTop, 0),
                 attributes.getDimensionPixelSize(R.styleable.ColorPickerBar_colorPaddingBottom, 0));
            } else {
                setPadding(padding);
            }

            attributes.recycle();
        }
    }

    public void update() {

        //  if (getChildCount() <= 0 | _colorList.size() <= 0) return;

        final int count = _colorList.size();

        for (int i = 0; i < count; i++) {

            final int curColor = _colorList.get(i);
            final int curPos = i;
            final View child = generateColorView(curColor, _borderColor, _radius, _colorBorderSize);

            child.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            child.getLayoutParams().width = (int) (_size);
            child.getLayoutParams().height = (int) child.getLayoutParams().width;

            if (_colorTouchListener != null) {
                child.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        _colorTouchListener.onLongTouch(curPos, curColor);
                        return false;
                    }
                });
                child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _colorTouchListener.onSingleTouch(curPos, curColor);
                    }
                });
            }
            addView(child);
        }
        requestLayout();

    }

    public View generateColorView(int backgroundColor, int borderColor, float borderRadius, int borderWidth) {

        View v = new View(getContext());
        GradientDrawable shape = new GradientDrawable();

        shape.setShape(GradientDrawable.OVAL);
        float[] array = new float[8];
        Arrays.fill(array, borderRadius);
        shape.setCornerRadii(array);

        shape.setColor(backgroundColor);
        shape.setStroke(borderWidth, borderColor);

        //API < 16 Support
        int sdk = android.os.Build.VERSION.SDK_INT;

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

            v.setBackgroundDrawable(shape);
        } else {

            v.setBackground(shape);
        }

        return v;
    }


    public void setColorTouchListener(ColorTouchListener colorTouchListener) {

        this._colorTouchListener = colorTouchListener;

    }

    public void setColors(List<Integer> colorList) {

        this._colorList = colorList;

    }

    public void addColor(@ColorInt int color) {

        this._colorList.add(color);

    }
}
