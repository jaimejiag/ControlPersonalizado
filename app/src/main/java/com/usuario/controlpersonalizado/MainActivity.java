package com.usuario.controlpersonalizado;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    private ColorMixer colorMixer;
    private TextView txvColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorMixer = (ColorMixer) findViewById(R.id.mixer);
        txvColor = (TextView) findViewById(R.id.txv_color);
        txvColor.setText(Integer.toHexString(colorMixer.getColor()));

        colorMixer.setOnColorChangedListener(new ColorMixer.OnColorChangedListener() {
            @Override
            public void OnColorChanged(int color) {
                txvColor.setText(Integer.toHexString(color));
            }
        });
    }
}
