package com.android.pclh_api_19;

public class Consumidor extends Thread {
    PC pc;

    Consumidor(PC pc) {
        this.pc = pc;
    }

    public void run() {
        pc.consumir();
    }
}
