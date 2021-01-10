package com.android.pclh_api_19;

public class Productor extends Thread {
    PC pc;

    Productor(PC pc) {
        this.pc = pc;
    }

    public void run() {
        pc.producir();
    }
}
