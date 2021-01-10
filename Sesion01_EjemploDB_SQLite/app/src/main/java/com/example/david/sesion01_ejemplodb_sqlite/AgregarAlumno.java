package com.example.david.sesion01_ejemplodb_sqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.david.sesion01_ejemplodb_sqlite.controlador.AlumnoDAO;
import com.example.david.sesion01_ejemplodb_sqlite.modelo.Alumno;

import java.util.ArrayList;

/**
 * Created by David on 17/11/2016.
 */
public class AgregarAlumno extends AppCompatActivity
{
    EditText cajaNoControl, cajaNombre, cajaPrimerApellido, cajaSegundoApellido,
                cajaEdad, cajaSemestre, cajaCarrera;
    TextView txtPrueba;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_alumno);

        cajaNoControl = (EditText) findViewById(R.id.cajaNoControl);
        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaPrimerApellido = (EditText) findViewById(R.id.cajaPrimerApellido);
        cajaSegundoApellido = (EditText) findViewById(R.id.cajaSegundoApellido);
        cajaEdad = (EditText) findViewById(R.id.cajaEdad);
        cajaSemestre = (EditText) findViewById(R.id.cajaSemestre);
        cajaCarrera = (EditText) findViewById(R.id.cajaCarrera);
        txtPrueba = (TextView) findViewById(R.id.textView2);

    }

    public void agregarAlumno(View v)
    {
        String nc = cajaNoControl.getText().toString();
        String n = cajaNombre.getText().toString();
        String pa = cajaPrimerApellido.getText().toString();
        String sa = cajaSegundoApellido.getText().toString();
        byte e = Byte.parseByte(cajaEdad.getText().toString());
        String s = cajaSemestre.getText().toString();
        String c = cajaCarrera.getText().toString();

        Alumno a = new Alumno(nc, n, pa, sa, e, s, c);

        if(new AlumnoDAO(this).agregarAlumno(a))
        {
            Log.i("MSJ: ", "AGREGADO CON EXITO");
            txtPrueba.setText("Aqui se entra");
        }
    }
}
