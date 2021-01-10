package com.nss.nsswifi.adaptador;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.nss.nsswifi.R;

import java.text.DecimalFormat;
import java.util.List;

public class RedesWifiAdapter extends BaseAdapter {
    protected Activity activity;
    protected List<ScanResult> items;

    public RedesWifiAdapter(Activity activity, List<ScanResult> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_layout, null, true );
        }


        // Obtiene cada una de las redes detectadas
        ScanResult redWifi = items.get(position);

        /*
        * Obtiene el SSID y la frecuencia de la red en cuestión
        * */

        TextView nombre = view.findViewById(R.id.nombre);
        nombre.setText(redWifi.SSID);

        DecimalFormat formato = new DecimalFormat("#.#");
        TextView tipo = view.findViewById(R.id.caracteristica);
        tipo.setText(formato.format((double)redWifi.frequency/1000.0) + " GHz");

        return view;
    }
}
