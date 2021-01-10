package com.example.david.sesion01_ejemplodb_sqlite.modelo;

/**
 * Created by David on 17/11/2016.
 */
public class Alumno
{
    private String noControl;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private byte edad;
    private String semestre;
    private String carrera;

    public Alumno() {
    }

    public Alumno(String noControl, String nombre, String primerApellido, String segundoApellido, byte edad, String semestre, String carrera) {
        this.noControl = noControl;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.semestre = semestre;
        this.carrera = carrera;
    }

    public String getNoControl() {
        return noControl;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public byte getEdad() {
        return edad;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getCarrera() {
        return carrera;
    }
}
