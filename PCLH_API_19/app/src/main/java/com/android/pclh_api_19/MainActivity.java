package com.android.pclh_api_19;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    static TextView txtProductor;
    static EditText cajaPares;
    static EditText cajaImpares;
    Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtProductor = (TextView) findViewById(R.id.txtProductor);
        cajaPares = (EditText) findViewById(R.id.cajaPares);
        cajaImpares = (EditText) findViewById(R.id.cajaImpares);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LinkedList<Integer> contenedor = new LinkedList<>();
                    PC pc = new PC(contenedor);
                    Consumidor c = new Consumidor(pc);
                    c.start();
                    Thread.sleep(500);

                    Productor p1 = new Productor(pc);
                    p1.start();

                    Productor p2 = new Productor(pc);
                    p2.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

