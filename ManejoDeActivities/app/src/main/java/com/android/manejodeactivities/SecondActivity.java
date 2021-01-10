package com.android.manejodeactivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private TextView txtUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        Intent i = getIntent();
        String user = i.getExtras().getCharSequence("inicio").toString();
        txtUsuario.setText(user);
        txtUsuario.setTextColor(Color.GREEN);
        txtUsuario.setTextSize(20);
    }

    public void cerrarSesion(View view) {
        Intent i = new Intent();
        /*
         * Guardamos la información que mandaremos a la otra activity
         */
        i.putExtra("sesionCerrada", "Sesión cerrada: " + txtUsuario.getText().toString());

        setResult(RESULT_OK, i); //Definimos el result con OK y le pasamos el objeto Intent

        finish();
    }
}
