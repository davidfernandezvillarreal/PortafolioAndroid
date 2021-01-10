package com.android.manejodeactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnIniciarSesion;
    private EditText cajaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        cajaUsuario = (EditText) findViewById(R.id.cajaUsuario);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("inicio", cajaUsuario.getText().toString());
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
         * Obtenemos los parametros de la activity que mandamos la información
         */
        if ((resultCode==RESULT_OK) && (requestCode==1)) {
            String cadena = data.getExtras().getCharSequence("sesionCerrada").toString();
            Toast.makeText(this, cadena, Toast.LENGTH_SHORT).show();
        }
    }
}
