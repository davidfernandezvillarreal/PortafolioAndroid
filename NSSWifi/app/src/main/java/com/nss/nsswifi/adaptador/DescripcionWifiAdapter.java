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

import java.util.List;
import java.util.Map;

public class DescripcionWifiAdapter extends BaseAdapter {

    protected Activity activity;
    protected List<String> descripcion;
    protected List<String> valor;

    public DescripcionWifiAdapter(Activity activity, List<String> descripcion, List<String> valor) {
        this.activity = activity;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    @Override
    public int getCount() {
        return valor.size();
    }

    @Override
    public Object getItem(int position) {
        return valor.get(position);
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
            view = inflater.inflate(R.layout.list_item_layout2, null, true );
        }

        String key = descripcion.get(position);
        String value = valor.get(position);

        TextView nombre = view.findViewById(R.id.nombre);
        nombre.setText(key);

        TextView tipo = view.findViewById(R.id.caracteristica);
        tipo.setText(value);

        return view;
    }
}
