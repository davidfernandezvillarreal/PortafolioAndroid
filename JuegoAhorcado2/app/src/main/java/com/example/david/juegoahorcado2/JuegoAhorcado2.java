package com.example.david.juegoahorcado2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class JuegoAhorcado2 extends AppCompatActivity
{
    EditText cajaLetra;
    TextView txtLongitudPalabra, txtOportunidades, txtLetrasDisponibles, txtLetrasIngresadas,
            txtMensaje, txtGuiones, txtGanadorPerdedor;
    Button btnEnviarLetra;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_ahorcado2);

        cajaLetra = (EditText) findViewById(R.id.cajaLetra);
        txtLongitudPalabra = (TextView) findViewById(R.id.txtLongitudPalabra);
        txtOportunidades = (TextView) findViewById(R.id.txtOportunidades);
        txtLetrasDisponibles = (TextView) findViewById(R.id.txtLetrasDisponibles);
        txtLetrasIngresadas = (TextView) findViewById(R.id.txtLetrasIngresadas);
        txtMensaje = (TextView) findViewById(R.id.txtMensaje);
        txtGuiones = (TextView) findViewById(R.id.txtGuiones);
        txtGanadorPerdedor = (TextView) findViewById(R.id.txtGanadorPerdedor);
        btnEnviarLetra = (Button) findViewById(R.id.btnEnviarLetra);
    }

    boolean boleano=false;
    String[] letrasDisponibles = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","ñ","o","p","q","r","s","t","u","v","w","x","y","z"},
            guiones, letrasIngresadas = {"","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    int contador=0, ganador=0, oportunidades=10;
    String palabra="";
    String palabras[] = {"DAVID", "FERNANDEZ", "VILLARREAL"};

    public String elegirPalabra(String palabrasEspañol[])
    {
        int azar = (int) (Math.random()*palabrasEspañol.length);
        return palabrasEspañol[azar];
    }

    public boolean seAdivinoLaPalabra(String palabraSecreta, String[] letrasIngresadas)
    {
        int contador=0;
        boolean retorno=false;
        for (int a=0; a<palabraSecreta.length(); a++)
        {
            for (int b=0; b<letrasIngresadas.length; b++)
            {
                if (String.valueOf(palabraSecreta.charAt(a)).equalsIgnoreCase(letrasIngresadas[b]))
                {
                    contador++;
                    break;
                }

            }
        }

        if (contador==palabraSecreta.length())
            retorno=true;

        return retorno;
    }

    public String obtenerPalabraAdivinada(String palabraSecreta, String letrasIngresadas[], String guiones[], String letra)
    {
        int siHayMasDeUnaLetraEnLaPalabra=0;
        contador=0;
        for (int a=0; a<palabraSecreta.length(); a++)
        {
            if(letra.equalsIgnoreCase(String.valueOf(palabraSecreta.charAt(a))))
            {
                if (contador!=1)
                {
                    if (siHayMasDeUnaLetraEnLaPalabra==1)
                        ganador++;

                    if (siHayMasDeUnaLetraEnLaPalabra==0)
                    {
                        for (int b=0; b<letrasIngresadas.length; b++)
                        {
                            if (letra.equalsIgnoreCase(letrasIngresadas[b]))
                            {
                                txtMensaje.setText("Ya habías ingresado esa letra");
                                break;
                            }

                            if(letrasIngresadas[b].equalsIgnoreCase(""))
                            {
                                txtMensaje.setText("Bien hecho");
                                ganador++;
                                letrasIngresadas[b] = letra.toLowerCase();
                                siHayMasDeUnaLetraEnLaPalabra=1;
                                break;
                            }
                        }
                    }

                    txtLetrasIngresadas.setText("");
                    for (int b=0; b<letrasIngresadas.length; b++)
                        txtLetrasIngresadas.setText(txtLetrasIngresadas.getText().toString() + letrasIngresadas[b] + " ");

                    guiones[a] = guiones[a].replaceAll("_", letra);
                    contador=2;
                }
            }
        }

        if (contador!=2)
        {
            if(letra.length() > 1)
                txtMensaje.setText("Ingrese solo una letra");
            else if (!letra.equalsIgnoreCase("a") && !letra.equalsIgnoreCase("b") && !letra.equalsIgnoreCase("c")
                    && !letra.equalsIgnoreCase("d") && !letra.equalsIgnoreCase("e") && !letra.equalsIgnoreCase("f")
                    && !letra.equalsIgnoreCase("g") && !letra.equalsIgnoreCase("h") && !letra.equalsIgnoreCase("i")
                    && !letra.equalsIgnoreCase("j") && !letra.equalsIgnoreCase("k") && !letra.equalsIgnoreCase("l")
                    && !letra.equalsIgnoreCase("m") && !letra.equalsIgnoreCase("n") && !letra.equalsIgnoreCase("ñ")
                    && !letra.equalsIgnoreCase("o") && !letra.equalsIgnoreCase("p") && !letra.equalsIgnoreCase("q")
                    && !letra.equalsIgnoreCase("r") && !letra.equalsIgnoreCase("s") && !letra.equalsIgnoreCase("t")
                    && !letra.equalsIgnoreCase("u") && !letra.equalsIgnoreCase("v") && !letra.equalsIgnoreCase("w")
                    && !letra.equalsIgnoreCase("x") && !letra.equalsIgnoreCase("y") && !letra.equalsIgnoreCase("z"))
                txtMensaje.setText("Este caracter no esta contemplado para este juego");
            else {
                for (int b=0; b<letrasIngresadas.length; b++)
                {
                    if (letra.equalsIgnoreCase(letrasIngresadas[b]))
                    {
                        txtMensaje.setText("Ya habías ingresado esa letra");
                        break;
                    }

                    if(letrasIngresadas[b].equalsIgnoreCase(""))
                    {
                        txtMensaje.setText("Esa letra no esta en la palabra");
                        oportunidades--;
                        letrasIngresadas[b] = letra.toLowerCase();
                        break;
                    }
                }
            }

            txtLetrasIngresadas.setText("");
            for (int b=0; b<letrasIngresadas.length; b++)
                txtLetrasIngresadas.setText(txtLetrasIngresadas.getText().toString() + letrasIngresadas[b] + " ");
        }

        txtOportunidades.setText(oportunidades + "");

        String palabra="";
        for (int a=0; a<guiones.length; a++)
            palabra = palabra + guiones[a];

        return palabra;
    }

    public String[] obtenerLetrasDisponibles(String letrasIngresadas[], String letrasDisponibles[])
    {
        boolean boleano=false;
        for (int a=0; a<letrasIngresadas.length; a++)
        {
            for (int b=0; b<letrasDisponibles.length; b++)
            {
                if (letrasIngresadas[a].equalsIgnoreCase(letrasDisponibles[b]))
                {
                    letrasDisponibles[b] = letrasDisponibles[b].replaceAll(letrasDisponibles[b], "-");
                    boleano=true;
                    break;
                }
            }

            if (letrasIngresadas[a].equalsIgnoreCase("") || boleano)
                break;
        }

        return letrasDisponibles;
    }

    public void inicioAhorcado(String palabraSecreta)
    {
        try {
            if (!boleano)
            {
                guiones = new String[palabraSecreta.length()];
                palabra = palabraSecreta;

                for (int a=0; a<guiones.length; a++)
                    guiones[a] = "_ ";

            }

            String letra = cajaLetra.getText().toString();
            letra = letra.toUpperCase();
            txtLongitudPalabra.setText(palabra.length() + "");

            txtGuiones.setText(obtenerPalabraAdivinada(palabra,letrasIngresadas,guiones,letra));

            String[] letrasDisp = obtenerLetrasDisponibles(letrasIngresadas, letrasDisponibles);
            txtLetrasDisponibles.setText("");
            for (int a=0; a<letrasDisponibles.length; a++)
                txtLetrasDisponibles.setText(txtLetrasDisponibles.getText() + letrasDisp[a]);

            if(ganador==palabra.length())
            {
                btnEnviarLetra.setEnabled(false);
                txtGanadorPerdedor.setText("!Felicidades has, GANADO!\n" +
                        "¿Se adivino la palabra? " + seAdivinoLaPalabra(palabra, letrasIngresadas));
            }

            if (oportunidades==0)
            {
                btnEnviarLetra.setEnabled(false);
                txtGanadorPerdedor.setText("Te has quedado sin oportunidades para adivinar.\n" +
                        "NO HAS ADIVINADO LA PALABRA. La palabra secreta era: \"" + palabra.toUpperCase() + "\"\n" +
                        "¿Se adivino la palabra? " + seAdivinoLaPalabra(palabraSecreta, letrasIngresadas));
            }

            boleano = true;
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void comienzoJuego(View v)
    {
        inicioAhorcado(elegirPalabra(palabras));
        cajaLetra.setText("");
    }
}
