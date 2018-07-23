package br.com.csbruno.colorpickerbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.csbruno.library.ColorListHelper;
import br.com.csbruno.library.ColorPickerBar;

public class MainActivity extends AppCompatActivity {

    ColorPickerBar colorPickerBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorPickerBar = (ColorPickerBar) findViewById(R.id.colorPickerBar);
        List<Integer> list = new ArrayList<>();
        list.add(Color.RED);
        list.add(Color.BLUE);
        colorPickerBar.setColors(ColorListHelper.genShadesByHueInRange(8,164,1,30,70));
        colorPickerBar.update();

    }
}
