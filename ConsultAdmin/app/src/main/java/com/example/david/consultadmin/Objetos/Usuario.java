package com.example.david.consultadmin.Objetos;

/**
 * Created by David on 14/09/2017.
 */
public class Usuario
{
    String nombre;
    String apellido;
    String correoElectronico;
    String contrasena;
    String nombreDelNegocio;
    String domicilio;
    String tiempoDelNegocio;
    String giroDelNecocio;

    public Usuario(String nombre,
                   String apellido,
                   String correoElectronico,
                   String contrasena,
                   String nombreDelNegocio,
                   String domicilio,
                   String tiempoDelNegocio,
                   String giroDelNecocio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombreDelNegocio = nombreDelNegocio;
        this.domicilio = domicilio;
        this.tiempoDelNegocio = tiempoDelNegocio;
        this.giroDelNecocio = giroDelNecocio;
    }

    public Usuario(String correoElectronico)
    {
        this.correoElectronico = correoElectronico;
    }

    public Usuario(){}

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombreDelNegocio() {
        return nombreDelNegocio;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getTiempoDelNegocio() {
        return tiempoDelNegocio;
    }

    public String getGiroDelNecocio() {
        return giroDelNecocio;
    }
}
