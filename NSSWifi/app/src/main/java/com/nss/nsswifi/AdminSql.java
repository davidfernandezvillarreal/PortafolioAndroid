package com.nss.nsswifi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


public class AdminSql extends SQLiteOpenHelper {

    private String REDES_WIFI = "CREATE TABLE redes_wifi (" +
            "bssid TEXT NOT NULL PRIMARY KEY," +
            "ssid TEXT NOT NULL," +
            "frecuencia TEXT NOT NULL," +
            "seguridad TEXT NOT NULL" +
            ")";

    private String DISPOSITIVOS = "CREATE TABLE dispositivos (" +
            "ip TEXT NOT NULL PRIMARY KEY," +
            "mac TEXT NOT NULL," +
            "bssid TEXT NOT NULL," +
            "FOREIGN KEY(bssid) REFERENCES redes_wifi(bssid)" +
            ")";

    private String HISTORICOS_WIFI = "CREATE TABLE historicos_wifi (" +
            "id_historico INTEGER PRIMARY KEY AUTOINCREMENT," +
            "intensidad TEXT," +
            "fecha_hora TEXT," +
            "mac_dispositivo INTEGER," +
            "FOREIGN KEY(mac_dispositivo) REFERENCES dispositivos(mac)" +
            ")";
    private String insertar = "insert into historicosRedesMoviles (fecha,dbm,asu,pais,tipo_de_red,tipo_de_red_telefonica)" +
            " values ('22/10/19',-113,23,'mx','4G','UMTS')";

    public AdminSql(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "wifi", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REDES_WIFI);
        db.execSQL(DISPOSITIVOS);
        db.execSQL(HISTORICOS_WIFI);
        //db.execSQL(insertar);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS redes_wifi");
        //db.execSQL("DROP TABLE IF EXISTS dispositivos");
        //db.execSQL("DROP TABLE IF EXISTS historicos_wifi");
        db.execSQL(REDES_WIFI);
        db.execSQL(DISPOSITIVOS);
        db.execSQL(HISTORICOS_WIFI);
        //db.execSQL(insertar);
    }
}
