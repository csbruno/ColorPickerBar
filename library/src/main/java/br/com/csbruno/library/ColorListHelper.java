package br.com.csbruno.library;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorListHelper {
    public static List<Integer> genGrayShades(int num){
        ArrayList<Integer> list = new ArrayList<>();
        if(num <= 0) return list;

        float steps  = Math.round( 256/num );

        for (float i = 0; i<256; i+= steps){
            list.add(Color.rgb((int)i,(int)i,(int)i));
        }
        return list;
    }
    public static List<Integer> genShadesByHueInRange(int nSteps,float hue, float saturation, int min, int max){

        if(hue > 360) throw new IllegalArgumentException("Hue value > 360");

        if(nSteps <= 0) throw new ArithmeticException();

        ArrayList<Integer> list = new ArrayList<>();
        float step = max/nSteps ;
        for (float i = min; i<100; i+= step){
            list.add(Color.HSVToColor(new float[]{hue,saturation,i/max}));
        }
        return list;

    }
    public static List<Integer> genShadesByHue(int nSteps,float hue, float saturation){

        if(hue > 360) throw new IllegalArgumentException("Hue value > 360");

        if(nSteps <= 0) throw new ArithmeticException();

        ArrayList<Integer> list = new ArrayList<>();
        float step = 100/nSteps ;
        for (float i = 0; i<100; i+= step){
            list.add(Color.HSVToColor(new float[]{hue,saturation,i/100}));
        }
        return list;

    }
}
