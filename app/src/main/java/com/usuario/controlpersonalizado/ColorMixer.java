package com.usuario.controlpersonalizado;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 * Created by usuario on 19/01/17.
 */

public class ColorMixer extends RelativeLayout {
    private SeekBar red;
    private SeekBar green;
    private SeekBar blue;
    private View swatch;
    private SeekBar.OnSeekBarChangeListener listener;
    private OnColorChangedListener onColorChangedListener;
    private int color;


    public interface OnColorChangedListener {
        void OnColorChanged(int color);
    }


    public ColorMixer(Context context) {
        super(context);
    }


    public ColorMixer(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 1. Se tiene que inflar la vista del componente. Mixer va dentro de este RelativeLayout, por eso 'true'.
        ((Activity)context).getLayoutInflater().inflate(R.layout.mixer, this, true);

        // 2. Recoger las referencias.
        swatch = findViewById(R.id.swatch);

        red = (SeekBar) findViewById(R.id.red);
        red.setMax(0xFF);   //Valor hexadecimal máximo de un color, es 255.

        green = (SeekBar) findViewById(R.id.green);
        green.setMax(0xFF);

        blue = (SeekBar) findViewById(R.id.blue);
        blue.setMax(0xFF);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ColorMixer);
            int color = typedArray.getColor(R.styleable.ColorMixer_initColor, 0x345675);
            setColorSeekBar(color);
            typedArray.recycle();
        }

        // Se inicializa el listener que escuchará los cambios en los tres SeekBar.
        initSeekBarListener();
    }


    private void setColorSeekBar(int color) {
        red.setProgress(Color.red(color));
        blue.setProgress(Color.blue(color));
        green.setProgress(Color.green(color));
        swatch.setBackgroundColor(color);
    }


    public int getColor() {
        return (Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress()));
    }


    private void initSeekBarListener() {

        listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // 1. Se obtiene el valor de los tres SeekBar
                color = Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress());

                // 2. Modificar el componente swatch.
                swatch.setBackgroundColor(color);

                // 3. Y pasar el evento a los listener.
                onColorChangedListener.OnColorChanged(color);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //No se usa;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //No se usa;
            }
        };

        red.setOnSeekBarChangeListener(listener);
        green.setOnSeekBarChangeListener(listener);
        blue.setOnSeekBarChangeListener(listener);
    }

    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener) {
        this.onColorChangedListener = onColorChangedListener;
    }
}
