package com.example.david.juegoahorcado2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by David on 05/06/2016.
 */
public class Menu extends AppCompatActivity
{
    JuegoAhorcado2 juegoAhorcado2 = new JuegoAhorcado2();
    EditText cajaPalabras;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent b = getIntent();
        String cad = b.getExtras().getCharSequence("inicio").toString();
        Toast.makeText(this, cad, Toast.LENGTH_SHORT).show();

        cajaPalabras = (EditText) findViewById(R.id.cajaPalabras);

    }

    public void verificarArchivo(View v)
    {
        Toast.makeText(this, "Numero de palabras: " + cargarPalabras().length, Toast.LENGTH_SHORT).show();
    }

    public void llenar(View v)
    {
        llenarArchivo();
    }

    public void borrar(View v)
    {
        borrarArchivo();
        Toast.makeText(this, "Palabras borradas", Toast.LENGTH_SHORT).show();
    }

    public void jugar(View v)
    {
        switch (v.getId())
        {
            case R.id.btnJugar:
                intent = new Intent(this, JuegoAhorcado2.class);
                startActivity(intent);
                break;
        }
    }

    public void borrarArchivo()
    {
        try {
            OutputStreamWriter bw = new OutputStreamWriter( openFileOutput("archivo.txt", Context.MODE_PRIVATE));
            bw.write("");
            bw.close();
        }
        catch (Exception e) {}

    }

    public void llenarArchivo()
    {
        try
        {
            OutputStreamWriter bw = new OutputStreamWriter( openFileOutput("archivo.txt", Context.MODE_PRIVATE));
            bw.write(cajaPalabras.getText().toString());
            bw.close();
            Toast.makeText(this, "Archivo llenado", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public String[] cargarPalabras()
    {
        int variable=0;
        String palabras[] = new String [variable];

        try {
            BufferedReader br = new BufferedReader( new InputStreamReader(openFileInput("archivo.txt")));

            String temp="";

            try {
                while (temp!=null)
                {
                    temp = br.readLine();

                    String []arreglo = temp.split(" ");

                    variable=arreglo.length;
                    palabras = new String [variable];
                    palabras = arreglo;
                }
            } catch (NullPointerException npe1){}
        }
        catch (Exception e) {}

        return palabras;
    }
}
