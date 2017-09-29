package com.germanco.contactos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class AdaptadorContacto extends BaseAdapter {
    Activity activity;
    List<Contacto> contactoList;
    LayoutInflater inflater;
    public AdaptadorContacto(Activity activity, List<Contacto> contactoList){
        this.activity=activity;
        this.contactoList=contactoList;
    }
    @Override
    public int getCount() {
        return contactoList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=inflater.inflate(R.layout.contacts_list_item,null);
        }

        TextView nombreContacto=(TextView)convertView.findViewById(R.id.nombreContacto);
        TextView telefonoContacto=(TextView)convertView.findViewById(R.id.telefonoContacto);

        Contacto contacto= contactoList.get(position);
        nombreContacto.setText(contacto.getNombre());
        telefonoContacto.setText(contacto.getTelefono());
        return convertView;
    }
}
