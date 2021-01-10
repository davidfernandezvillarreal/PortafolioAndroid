package com.example.david.consultadmin.Objetos;

/**
 * Created by David on 14/09/2017.
 */
public class Chat
{
    String correoElectronicoEmisor;
    String mensajeEnviado;
    String correoElectronicoReceptor;
    int numeroDeMensaje;

    public Chat(String correoElectronicoEmisor, String mensajeEnviado, String correoElectronicoReceptor, int numeroDeMensaje) {
        this.correoElectronicoEmisor = correoElectronicoEmisor;
        this.mensajeEnviado = mensajeEnviado;
        this.correoElectronicoReceptor = correoElectronicoReceptor;
        this.numeroDeMensaje = numeroDeMensaje;
    }

    public Chat(){}

    public String getCorreoElectronicoEmisor() {
        return correoElectronicoEmisor;
    }

    public String getMensajeEnviado() {
        return mensajeEnviado;
    }

    public String getCorreoElectronicoReceptor() {
        return correoElectronicoReceptor;
    }

    public int getNumeroDeMensaje() {
        return numeroDeMensaje;
    }

    public void setNumeroDeMensaje(int numeroDeMensaje) {
        this.numeroDeMensaje = numeroDeMensaje;
    }
}
