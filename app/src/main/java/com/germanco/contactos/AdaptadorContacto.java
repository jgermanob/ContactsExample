package com.germanco.contactos;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;


public class AdaptadorContacto extends BaseAdapter {
    Activity activity;
   public static List<Contacto> contactoList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(inflater==null){
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=inflater.inflate(R.layout.contacts_list_item,null);
        }

        TextView nombreContacto=(TextView)convertView.findViewById(R.id.nombreContacto);
        TextView telefonoContacto=(TextView)convertView.findViewById(R.id.telefonoContacto);
        CheckBox seleccionContacto=(CheckBox)convertView.findViewById(R.id.checkBoxContacto);
        Contacto contacto= contactoList.get(position);
        nombreContacto.setText(contacto.getNombre());
        telefonoContacto.setText(contacto.getTelefono());
        seleccionContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(contactoList.get(position).getSelected()){
                        contactoList.get(position).setSelected(false);
                        Log.d("Estado","Quitaste seleción de "+contactoList.get(position).getNombre());
                    }else{
                        contactoList.get(position).setSelected(true);
                        Log.d("Estado","Selecionaste a "+contactoList.get(position).getNombre());
                    }
                }catch (NullPointerException ex){
                    Log.d("Estado Exception",contactoList.get(position).getNombre());
                }

            }
        });
        return convertView;
    }
}
