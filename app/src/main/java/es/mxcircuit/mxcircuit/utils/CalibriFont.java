package es.mxcircuit.mxcircuit.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by gashelopodo on 5/8/17.
 */

public class CalibriFont extends android.support.v7.widget.AppCompatTextView {

    public CalibriFont(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "calibri.ttf");
        this.setTypeface(face);
    }

    public CalibriFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "calibri.ttf");
        this.setTypeface(face);
    }

    public CalibriFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "calibri.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }



}
