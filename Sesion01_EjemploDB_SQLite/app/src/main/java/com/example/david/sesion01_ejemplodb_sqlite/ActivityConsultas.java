package com.example.david.sesion01_ejemplodb_sqlite;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.david.sesion01_ejemplodb_sqlite.controlador.AlumnoDAO;
import com.example.david.sesion01_ejemplodb_sqlite.modelo.Alumno;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by David on 24/11/2016.
 */
public class ActivityConsultas extends AppCompatActivity
{
    ListView listaViewAlumnos;
    TextView txtTituloConsultaAlumnos;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        txtTituloConsultaAlumnos = (TextView) findViewById(R.id.textView);
        listaViewAlumnos = (ListView) findViewById(R.id.listView);

        AlumnoDAO aDAO = new AlumnoDAO(this);
        ArrayList<Alumno> listadoAlumnos = aDAO.listaTotalAlumno();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        if(!listadoAlumnos.isEmpty())
        {
            for (Alumno a: listadoAlumnos)
            {
                adaptador.add("" + a.getNoControl() + " - " +
                        a.getNombre() + " - " +
                        a.getPrimerApellido() + " - " +
                        a.getSegundoApellido() + " - " +
                        a.getEdad() + " - " +
                        a.getSemestre() + " - " +
                        a.getCarrera() + " - "
                );
            }
            adaptador.add(listaViewAlumnos);
            listaViewAlumnos.setAdapter(adaptador);
        }
        else
        {
            txtTituloConsultaAlumnos.setText("NO SE ENCONTRARON REGISTROS");
            txtTituloConsultaAlumnos.setBackgroundColor(Color.rgb(255, 0, 0));
        }
    }
}
