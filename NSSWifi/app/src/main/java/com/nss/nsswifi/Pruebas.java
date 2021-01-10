package com.nss.nsswifi;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.anastr.speedviewlib.DeluxeSpeedView;
import com.github.anastr.speedviewlib.SpeedView;
import com.nss.nsswifi.receiver.WifiReceiver;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Pruebas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Pruebas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pruebas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DecimalFormat formato = new DecimalFormat("#.#");

    private OnFragmentInteractionListener mListener;

    private SpeedView speedometer;
    private Handler handler = new Handler();

    //private Toast mensaje;
    private boolean permitirGirar = false;
    private Button btnIniciarPrueba;

    private AdminSql sql;
    private SQLiteDatabase db;
    ContentValues registro;
    Cursor cursor;

    private String tituloMensajeNotificacion="La red cambio";
    private NotificationHelpener notificacionHelpe; /**objeto el cual usaremos para enviar notificacion */

    private WifiManager wifiManager;
    private WifiReceiver wifiReceiver;
    private WifiInfo wifiInfo;

    public Pruebas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pruebas.
     */
    // TODO: Rename and change types and number of parameters
    public static Pruebas newInstance(String param1, String param2) {
        Pruebas fragment = new Pruebas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void btnPrueba() {
        if (btnIniciarPrueba.getText().toString().equalsIgnoreCase("Detener"))
            btnIniciarPrueba.setText("Iniciar prueba");
        else
            btnIniciarPrueba.setText("Detener");
    }

    public void ponerMedidaSpeed(int intensidad) {
        if (permitirGirar)
            speedometer.speedTo(intensidad);
    }

    public void insertarRedes() {
        try {
            wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiReceiver = new WifiReceiver();
            getActivity().getApplicationContext().registerReceiver(wifiReceiver,
                    new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiInfo = wifiManager.getConnectionInfo();
            ScanResult redWifi = null;

            List<String[]> redes_wifi = new LinkedList<>();

            sql = new AdminSql(getActivity(), "wifi", null, 1);
            db = sql.getWritableDatabase();
            cursor = db.rawQuery("SELECT bssid, seguridad FROM redes_wifi WHERE bssid = ?", new String[]{wifiInfo.getBSSID()});
            //cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                Log.i("bssid-origin", cursor.getString(0));
                cursor = db.rawQuery("SELECT * FROM redes_wifi", null);
                cursor.moveToFirst();

                while (cursor.moveToNext()) {
                    String registro[] = new String[cursor.getColumnCount()];

                    for (int i=0; i<cursor.getColumnCount(); i++) {
                        registro[i] = cursor.getString(i)+"";
                    }
                    redes_wifi.add(registro);
                }

                Log.i("redes_wifi", redes_wifi+"");

                cursor.close();
                db.close();
            } else {
                for (ScanResult scanResult : wifiManager.getScanResults())
                    if (wifiInfo.getBSSID().equals(scanResult.BSSID))
                        redWifi = scanResult;

                registro = new ContentValues();
                registro.put("bssid", wifiInfo.getBSSID());
                registro.put("ssid", wifiInfo.getSSID());
                registro.put("frecuencia", formato.format((double)wifiInfo.getFrequency()/1000.0) + " GHz");
                registro.put("seguridad", obtenerSeguridadRed(redWifi));
                Log.i("registroRedes", "bssid: " + registro.getAsString("bssid"));
                Log.i("registroRedes", "ssid: " + registro.getAsString("ssid"));
                Log.i("registroRedes", "seguridad: " + registro.getAsString("seguridad"));
                Log.i("seguridad", obtenerSeguridadRed(redWifi));
                db.insert("redes_wifi", null, registro);
                //Toast.makeText(getActivity(), "Se cargaron los registros correctamente REDES", Toast.LENGTH_SHORT).show();
                cursor.close();
                db.close();
            }
        } catch (Exception e) {
            Log.i("exception", e.getMessage());
            //***Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void insertarDispositivos() {
        try {
            wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiReceiver = new WifiReceiver();
            getActivity().getApplicationContext().registerReceiver(wifiReceiver,
                    new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiInfo = wifiManager.getConnectionInfo();

            sql = new AdminSql(getActivity(), "wifi", null, 1);
            db = sql.getWritableDatabase();
            cursor = db.rawQuery("SELECT mac FROM dispositivos WHERE mac = ?", new String[]{wifiInfo.getMacAddress()});
           //cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                Log.i("mac_dispositivo", cursor.getString(0));
                cursor.close();
                db.close();
            } else {
                int ip = wifiInfo.getIpAddress();
                String ipString = String.format("%d.%d.%d.%d",
                        (ip & 0xff),
                        (ip >> 8 & 0xff),
                        (ip >> 16 & 0xff),
                        (ip >> 24 & 0xff));

                registro = new ContentValues();
                registro.put("ip", ipString);
                registro.put("mac", wifiInfo.getMacAddress());
                registro.put("bssid", wifiInfo.getBSSID());
                Log.i("registroDispositivos", "ip: " + registro.getAsString("ip"));
                Log.i("registroDispositivos", "mac: " + registro.getAsString("mac"));
                Log.i("registroDispositivos", "bssid: " + registro.getAsString("bssid"));
                db.insert("dispositivos", null, registro);
                Toast.makeText(getActivity(), "Se cargaron los registros correctamente DISPOSITIVOS", Toast.LENGTH_SHORT).show();
                cursor.close();
                db.close();
            }
        } catch (Exception e) {
            Log.i("exception", e.getMessage());
            //***Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void insertarHistoricos() {
        try {
            wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiReceiver = new WifiReceiver();
            getActivity().getApplicationContext().registerReceiver(wifiReceiver,
                    new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiInfo = wifiManager.getConnectionInfo();

            sql = new AdminSql(getActivity(), "wifi", null, 1);
            db = sql.getWritableDatabase();
            registro = new ContentValues();
            cursor = db.rawQuery("SELECT DATETIME('now')", null);
            cursor.moveToFirst();
            registro.put("intensidad", wifiInfo.getRssi());
            registro.put("fecha_hora", cursor.getString(0));
            registro.put("mac_dispositivo", wifiInfo.getMacAddress());
            Log.i("registroHistoricos", "intensidad: " + registro.getAsString("intensidad"));
            Log.i("registroHistoricos", "fecha_hora: " + registro.getAsString("fecha_hora"));
            Log.i("registroHistoricos", "mac_dispositivo: " + registro.getAsString("mac_dispositivo"));
            db.insert("historicos_wifi", null, registro);
            Toast.makeText(getActivity(), "Se cargaron los registros correctamente HISTORICOS", Toast.LENGTH_SHORT).show();
            cursor.close();
            db.close();
        } catch (Exception e) {
            //***Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String obtenerSeguridadRed(ScanResult redWifi) {
        final String cap = redWifi.capabilities;
        final String[] securityModes = { "WEP", "WPA", "WPA2", "WPA_EAP", "IEEE8021X" };
        for (int i = securityModes.length - 1; i >= 0; i--) {
            if (cap.contains(securityModes[i])) {
                return securityModes[i];
            }
        }
        return "Abierta";
    }

    public void ejecutarPrueba() {
        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new WifiReceiver();
        getActivity().getApplicationContext().registerReceiver(wifiReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiInfo = wifiManager.getConnectionInfo();

        ponerMedidaSpeed(wifiInfo.getRssi());

        if(btnIniciarPrueba.getText().toString().equalsIgnoreCase("DETENER")) {
            insertarRedes();
            insertarDispositivos();
            insertarHistoricos();
        } else {
        }
    }

    private void actualizarSpeed(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifiReceiver = new WifiReceiver();
                getActivity().getApplicationContext().registerReceiver(wifiReceiver,
                        new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                wifiInfo = wifiManager.getConnectionInfo();

                ponerMedidaSpeed(wifiInfo.getRssi());
                actualizarSpeed();
            }
        },100);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pruebas, container, false);
        notificacionHelpe = new NotificationHelpener(getActivity());
        speedometer = view.findViewById(R.id.speedView);
        btnIniciarPrueba = view.findViewById(R.id.btnIniciarPrueba);
        btnIniciarPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permitirGirar = true;
                btnPrueba();
                ejecutarPrueba();
            }
        });
        speedometer.setWithTremble(false);
        speedometer.setUnitUnderSpeedText(true);
        speedometer.setUnit("dBm");
        speedometer.setMinSpeed(-130);
        speedometer.setMaxSpeed(0);
        actualizarSpeed();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
