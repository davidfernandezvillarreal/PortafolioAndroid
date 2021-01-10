package com.android.pclh_api_19;

import android.util.Log;
import java.util.LinkedList;

public class ProductoresConsumidores {
    LinkedList<Integer> contenedor;

    ProductoresConsumidores(LinkedList<Integer> contenedor) {
        this.contenedor = contenedor;
    }

    synchronized void producir() {
        if (contenedor.size()<3) {
            contenedor.add((int) (Math.random()*100));
            Log.i("programa", "Producido: " + Thread.currentThread().getName() +
                    " numero: " + contenedor.getLast());
            //MainActivity.txtProductor.setText(MainActivity.txtProductor.getText().toString() + contenedor.getLast() + ", ");
            notify();
        } else {
            try {
                Log.i("programa","Lista llena. Esperando " + Thread.currentThread().getName());
                wait();
                contenedor.add((int) (Math.random()*100));
                Log.i("programa","Continuando de Producir: " + Thread.currentThread().getName() +
                        " numero: " + contenedor.getLast());
                //MainActivity.txtProductor.setText(MainActivity.txtProductor.getText().toString() + contenedor.getLast() + ", ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void consumir() {
        if (!contenedor.isEmpty()) {
            if (contenedor.getFirst()%2==0) {
                Log.i("programa","PAR: " + contenedor.getFirst());
                //MainActivity.cajaPares.setText(MainActivity.cajaPares.getText().toString() + contenedor.getFirst() + ", ");
            } else {
                Log.i("programa","IMPAR: " + contenedor.getFirst());
                //MainActivity.cajaImpares.setText(MainActivity.cajaImpares.getText().toString() + contenedor.getFirst() + ", ");
            }
            contenedor.removeFirst();
            notify();
        } else {
            try {
                Log.i("programa","Lista vacia. Esperando " + Thread.currentThread().getName());
                wait();
                if (contenedor.getFirst()%2==0) {
                    Log.i("programa","Continuando PAR: " + contenedor.getFirst());
                    //MainActivity.cajaPares.setText(MainActivity.cajaPares.getText().toString() + contenedor.getFirst() + ", ");
                } else {
                    Log.i("programa","Continuando IMPAR: " + contenedor.getFirst());
                    //MainActivity.cajaImpares.setText(MainActivity.cajaImpares.getText().toString() +
                            //contenedor.getFirst() + ", ");
                }
                contenedor.removeFirst();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
