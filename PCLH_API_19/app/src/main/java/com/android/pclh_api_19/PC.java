package com.android.pclh_api_19;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.LinkedList;

public class PC {
    LinkedList<Integer> contenedor;
    Handler handler;
    Looper looper;
    boolean send;

    PC(LinkedList<Integer> contenedor) {
        this.contenedor = contenedor;
        handler = new Handler();
    }

    synchronized void producir() {
        if (contenedor.size()<2) {
            contenedor.add((int) (Math.random()*100));

            Message mensaje = Message.obtain();
            mensaje.arg1 = contenedor.getLast();

            send = handler.sendMessage(mensaje);
            Log.i("programa", contenedor.size() + ", msg: " + mensaje.arg1 + ", send: " + send);

            Log.i("programa", "Producido: " + Thread.currentThread().getName() +
                    " numero: " + contenedor.getLast());
            notify();
        } else {
            try {
                Log.i("programa","Lista llena. Esperando " + Thread.currentThread().getName());
                wait();
                contenedor.add((int) (Math.random()*100));


                Message mensaje = Message.obtain();
                mensaje.arg1 = contenedor.getLast();

                send = handler.sendMessage(mensaje);
                Log.i("programa", contenedor.size() + ", msg: " + mensaje.arg1 + ", send: " + send);



                Log.i("programa","Continuando de Producir: " + Thread.currentThread().getName() +
                        " numero: " + contenedor.getLast());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void consumir() {
        looper.prepare();
        Log.i("programa", "consumidor + " + contenedor.size());

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i("programa", contenedor.size() + ", msg: " + msg.arg1);
                if (!contenedor.isEmpty()) {
                    //MainActivity.txtProductor.setText(MainActivity.txtProductor.getText().toString() + msg.arg1 + ", ");
                    if (msg.arg1%2==0) {
                        Log.i("programa","PAR: " + msg.arg1);
                        //MainActivity.cajaPares.setText(MainActivity.cajaPares.getText().toString() + msg.arg1 + ", ");
                    } else {
                        Log.i("programa","IMPAR: " + msg.arg1);
                        //MainActivity.cajaImpares.setText(MainActivity.cajaImpares.getText().toString() + msg.arg1 + ", ");
                    }
                    contenedor.removeLast();
                    notify();
                } else {
                    try {
                        Log.i("programa","Lista vacia. Esperando " + Thread.currentThread().getName());

                        wait();
                        //MainActivity.txtProductor.setText(MainActivity.txtProductor.getText().toString() + msg.arg1 + ", ");
                        if (contenedor.getLast()%2==0) {
                            Log.i("programa","Continuando PAR: " + msg.arg1);
                            //MainActivity.cajaPares.setText(MainActivity.cajaPares.getText().toString() + msg.arg1 + ", ");
                        } else {
                            Log.i("programa","Continuando IMPAR: " + msg.arg1);
                            //MainActivity.cajaImpares.setText(MainActivity.cajaImpares.getText().toString() + msg.arg1 + ", ");
                        }
                        contenedor.removeLast();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        looper.loop();
        looper.quit();
    }
}
