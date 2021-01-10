package com.nss.nsswifi;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.nss.nsswifi.adaptador.RedesWifiAdapter;
import com.nss.nsswifi.receiver.WifiReceiver;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RedesWifi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RedesWifi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RedesWifi extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listViewRedes;
    private List<ScanResult> listaRedes;
    private RedesWifiAdapter adaptador;
    private WifiManager wifiManager;
    private WifiReceiver wifiReceiver;
    private Button btnRefrescarRedes;
    DecimalFormat formato = new DecimalFormat("#.#");

    public RedesWifi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RedesWifi.
     */
    // TODO: Rename and change types and number of parameters
    public static RedesWifi newInstance(String param1, String param2) {
        RedesWifi fragment = new RedesWifi();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new ListaRedes().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_redes_wifi, container, false);
        listViewRedes = view.findViewById(R.id.listaRedes);
        btnRefrescarRedes = view.findViewById(R.id.btnRefrescarRedes);
        btnRefrescarRedes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListaRedes().execute();
            }
        });

        listViewRedes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(listaRedes.get(position).SSID);
                    builder.setMessage("SSID: " + listaRedes.get(position).SSID + "\n\n" +
                    "Frecuencia: " + formato.format((double)listaRedes.get(position).frequency/1000.0) + " Ghz\n\n" +
                    "Seguridad: " + obtenerSeguridadRed(listaRedes.get(position)) + "\n\n" +
                    "Nivel: " + listaRedes.get(position).level + " dBm");
                    builder.setPositiveButton(R.string.txt_cerrar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                });

                Dialog dialogo = builder.create();
                dialogo.show();

            }
        });

        return view;
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

    class ListaRedes extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... objects) {
            wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiReceiver = new WifiReceiver();
            getActivity().getApplicationContext().registerReceiver(wifiReceiver,
                    new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

            listaRedes = new LinkedList<ScanResult>();
            boolean redDetectada = false;

            for (ScanResult redWifi : wifiManager.getScanResults()) {
                for (int i=0; i<listaRedes.size(); i++) {
                    if (redWifi.SSID.equals(listaRedes.get(i).SSID)) {
                        redDetectada = true;
                        break;
                    }
                }
                if (!redDetectada)
                    listaRedes.add(redWifi);
                redDetectada = false;
            }


            adaptador = new RedesWifiAdapter(getActivity(), listaRedes);
            publishProgress();

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            listViewRedes.setAdapter(adaptador);
        }
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
