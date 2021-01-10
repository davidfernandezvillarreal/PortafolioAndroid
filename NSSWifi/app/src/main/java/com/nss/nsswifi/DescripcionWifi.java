package com.nss.nsswifi;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nss.nsswifi.adaptador.DescripcionWifiAdapter;
import com.nss.nsswifi.receiver.WifiReceiver;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DescripcionWifi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DescripcionWifi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescripcionWifi extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listViewDescripcion;
    private List<String> listaDescripcion;
    private List<String> listaDatos;
    private DescripcionWifiAdapter adaptador;
    private WifiManager wifiManager;
    private WifiReceiver wifiReceiver;
    private WifiInfo wifiInfo;
    private Handler handler = new Handler();

    public DescripcionWifi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DescripcionWifi.
     */
    // TODO: Rename and change types and number of parameters
    public static DescripcionWifi newInstance(String param1, String param2) {
        DescripcionWifi fragment = new DescripcionWifi();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descripcion_wifi, container, false);
        listViewDescripcion = view.findViewById(R.id.listaDescripcion);
        actualizarDescripcion();
        return view;
    }

    private void actualizarDescripcion(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new ListaDescripcion().execute();
                actualizarDescripcion();
            }
        },500);
    }

    class ListaDescripcion extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... objects) {
            wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiReceiver = new WifiReceiver();
            getActivity().getApplicationContext().registerReceiver(wifiReceiver,
                    new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

            wifiInfo = wifiManager.getConnectionInfo();
            listaDescripcion = new LinkedList<String>();
            listaDatos = new LinkedList<String>();

            listaDescripcion.add(getString(R.string.ssid));
            listaDatos.add(wifiInfo.getSSID());

            listaDescripcion.add(getString(R.string.intensidad));
            listaDatos.add(wifiInfo.getRssi() + " dBm");

            DecimalFormat formato = new DecimalFormat("#.#");
            listaDescripcion.add(getString(R.string.frecuancia));
            listaDatos.add(formato.format((double)wifiInfo.getFrequency()/1000.0) + " GHz");

            listaDescripcion.add(getString(R.string.velocidad));
            listaDatos.add(wifiInfo.getLinkSpeed() + " Mbps");

            int ip = wifiInfo.getIpAddress();
            String ipString = String.format("%d.%d.%d.%d",
                    (ip & 0xff),
                    (ip >> 8 & 0xff),
                    (ip >> 16 & 0xff),
                    (ip >> 24 & 0xff));
            listaDescripcion.add(getString(R.string.ip));
            listaDatos.add(ipString);

            listaDescripcion.add(getString(R.string.mac));
            listaDatos.add(wifiInfo.getMacAddress().toUpperCase());

            /*if (wifiManager.isWifiEnabled()) { // Wi-Fi adapter is ON
                if( wifiInfo.getNetworkId() == -1 ) {
                    return false; // Not connected to an access point
                }
                return true; // Connected to an access point
            } else {
                return false; // Wi-Fi adapter is OFF
            }*/

            listaDescripcion.add(getString(R.string.estado));
            if (wifiManager.isWifiEnabled()) {
                if (wifiInfo.getNetworkId() == -1) {
                    listaDatos.add(getString(R.string.estadoD));
                } else {
                    listaDatos.add(getString(R.string.estadoC));
                }
            } else {
                listaDatos.add(getString(R.string.estadoDisable));
            }

            Log.i("listaDesc", listaDescripcion.toString());
            Log.i("listaDatos", listaDatos.toString());
            adaptador = new DescripcionWifiAdapter(getActivity(), listaDescripcion, listaDatos);
            publishProgress();

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            listViewDescripcion.setAdapter(adaptador);
            Log.i("descRssi", wifiInfo.getRssi()+"");
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
