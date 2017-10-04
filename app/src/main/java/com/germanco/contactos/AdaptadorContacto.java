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
    Context context;

    public AdaptadorContacto(Activity activity, List<Contacto> contactoList, Context context){
        this.activity=activity;
        this.contactoList=contactoList;
        this.context=context;
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
        final ContactHolder holder;

        if(convertView==null){
            holder= new ContactHolder();
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.contacts_list_item,null,true);
            holder.contactCheckBox=(CheckBox)convertView.findViewById(R.id.checkBoxContacto);
            holder.contactTextView=(TextView)convertView.findViewById(R.id.nombreContacto);
            holder.telContactTextView=(TextView)convertView.findViewById(R.id.telefonoContacto);
            convertView.setTag(holder);
        }else{
            holder=(ContactHolder)convertView.getTag();
        }

        holder.contactTextView.setText(contactoList.get(position).getNombre());
        holder.telContactTextView.setText(contactoList.get(position).getTelefono());
        holder.contactCheckBox.setChecked(contactoList.get(position).getSelected());
        holder.contactCheckBox.setTag(position);
        holder.contactCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos=(Integer)holder.contactCheckBox.getTag();
                try{
                    if(contactoList.get(pos).getSelected()==true){
                        contactoList.get(pos).setSelected(false);
                        Log.d("Estado","Quitaste seleción de "+contactoList.get(pos).getNombre());
                    }else{
                        contactoList.get(pos).setSelected(true);
                        Log.d("Estado","Selecionaste a "+contactoList.get(pos).getNombre());
                    }
                }catch (NullPointerException ex){
                    Log.d("Estado Exception",contactoList.get(position).getNombre());
                }
            }
        });
        return convertView;
    }

    //Guarda referencia de los controles a utilizar en el adaptador
    public class ContactHolder{
        TextView contactTextView;
        TextView telContactTextView;
        CheckBox contactCheckBox;
    }
}
