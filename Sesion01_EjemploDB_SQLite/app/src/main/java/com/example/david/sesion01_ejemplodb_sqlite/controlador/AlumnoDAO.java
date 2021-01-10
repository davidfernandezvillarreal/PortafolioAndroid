package com.example.david.sesion01_ejemplodb_sqlite.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.david.sesion01_ejemplodb_sqlite.modelo.Alumno;

import java.util.ArrayList;

/**
 * Created by David on 17/11/2016.
 */
public class AlumnoDAO extends SQLiteOpenHelper
{
    SQLiteDatabase bd = this.getReadableDatabase();

    private static final int VERSION_BD = 1;
    private static final String NOMBRE_BD= "escuela";
    private static final String NOMBRE_TABLA = "alumnos";

    private static final String CAMPO_NO_CONTROL = "noControl";
    private static final String CAMPO_NOMBBRE = "nombre";
    private static final String CAMPO_PRIMER_APELLIDO = "primerApellido";
    private static final String CAMPO_SEGUNDO_APELLIDO = "segundoApellido";
    private static final String CAMPO_EDAD = "edad";
    private static final String CAMPO_SEMESTRE = "semestre";
    private static final String CAMPO_CARRERA = "carrera";

    private static final String CREACION_TABLA = "CREATE TABLE " + NOMBRE_TABLA + " (" +
                                                    CAMPO_NO_CONTROL + " TEXT, " +
                                                    CAMPO_NOMBBRE + " TEXT, " +
                                                    CAMPO_PRIMER_APELLIDO + " TEXT, " +
                                                    CAMPO_SEGUNDO_APELLIDO +3 " TEXT, " +
                                                    CAMPO_EDAD + " TEXT, " +
                                                    CAMPO_SEMESTRE + " TEXT, " +
                                                    CAMPO_CARRERA + " TEXT)";

    // CREATE TABLE

    public AlumnoDAO(Context contexto)
    {
        super(contexto, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST " + NOMBRE_TABLA);
        onCreate(db);
    }

    public boolean agregarAlumno(Alumno alumno)
    {
        ContentValues valores = new ContentValues();
        valores.put(CAMPO_NO_CONTROL, alumno.getNoControl());
        valores.put(CAMPO_NOMBBRE, alumno.getNombre());
        valores.put(CAMPO_PRIMER_APELLIDO, alumno.getPrimerApellido());
        valores.put(CAMPO_SEGUNDO_APELLIDO, alumno.getSegundoApellido());
        valores.put(CAMPO_EDAD, alumno.getEdad());
        valores.put(CAMPO_SEMESTRE, alumno.getSemestre());
        valores.put(CAMPO_CARRERA, alumno.getCarrera());

        return (bd.insert(NOMBRE_TABLA, null, valores) == -1) ? false : true;
    }

    public ArrayList<Alumno> listaTotalAlumno()
    {
        ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno>();
        String sql = "SELECT * FROM " + NOMBRE_TABLA;

        Cursor datos = bd.rawQuery(sql, null);

        if(datos.moveToFirst())
        {
            do {
                listaAlumnos.add(
                        new Alumno(datos.getString(0),
                        datos.getString(1),
                        datos.getString(2),
                        datos.getString(3),
                        (byte)datos.getInt(4),
                        datos.getString(5),
                        datos.getString(6))
                );
            }while(datos.moveToNext());
        }

        return listaAlumnos;
    }

    public boolean eliminarAlumno(String nc)
    {
        if (bd.delete(NOMBRE_TABLA, "No Control = '" + nc + "'", null) > 0)
        {
            bd.close();
            return true;
        }
        else {
            bd.close();
            return false;
        }
    }

    public boolean modificarAlumno(Alumno a)
    {
        ContentValues valores = new ContentValues();
        valores.put(CAMPO_NO_CONTROL, a.getNoControl());
        valores.put(CAMPO_NOMBBRE, a.getNombre());
        valores.put(CAMPO_PRIMER_APELLIDO, a.getPrimerApellido());
        valores.put(CAMPO_SEGUNDO_APELLIDO, a.getSegundoApellido());
        valores.put(CAMPO_EDAD, a.getEdad());
        valores.put(CAMPO_SEMESTRE, a.getSemestre());
        valores.put(CAMPO_CARRERA, a.getCarrera());

        if (bd.update(NOMBRE_TABLA, valores, "No Control = '" + a.getNoControl() + "'", null) <= 0)
        {
            bd.close();
            return false;
        }
        else
        {
            bd.close();
            return true;
        }
    }
}
