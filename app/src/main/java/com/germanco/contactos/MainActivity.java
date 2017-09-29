package com.germanco.contactos;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Contacto> contactoList= new ArrayList<>();
    public Contacto contacto;
    public ListView listaContactos;
    Cursor contactoCursor;
    AdaptadorContacto adaptadorContacto;
    Button botonGuardar;
    public List<Contacto> contactosGuardados= new ArrayList<>();
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaContactos= (ListView)findViewById(R.id.contactsList);
        botonGuardar=(Button)findViewById(R.id.botonGuardar);
        adaptadorContacto= new AdaptadorContacto(this,contactoList);
        listaContactos.setAdapter(adaptadorContacto);
        obtenerContactos();
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Estado","Guardar contactos");
                guardarContactos();
            }
        });
    }

    public void obtenerContactos(){
        String[] projection= new String[]{ContactsContract.Data._ID,ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE};
        String where= ContactsContract.Data.MIMETYPE+"='"+ ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE+"' AND "+ ContactsContract.CommonDataKinds.Phone.NUMBER+ " IS NOT NULL";
        String orden= ContactsContract.Data.DISPLAY_NAME+" ASC";
        contactoCursor=getContentResolver().query(ContactsContract.Data.CONTENT_URI,projection,where,null,orden);
        while (contactoCursor.moveToNext()){
            try {
                contactoCursor.moveToNext();
                contacto= new Contacto();
                contacto.setNombre(contactoCursor.getString(1));
                contacto.setTelefono(contactoCursor.getString(2));
                contacto.setSelected(false);
                contactoList.add(contacto);
                adaptadorContacto.notifyDataSetChanged();
            }catch (NullPointerException ex){
                Log.d("Estado",contactoCursor.getString(0));
            }
        }
    }

    public void guardarContactos(){
        for(int i=0; i<AdaptadorContacto.contactoList.size();i++){
            if(AdaptadorContacto.contactoList.get(i).getSelected()){
                contactosGuardados.add(contactoList.get(i));
                Log.d("Contacto guardado",contactoList.get(i).getNombre());
            }
        }
        gson= new GsonBuilder().setPrettyPrinting().create();
        String representacionJsonContactos=gson.toJson(contactosGuardados);
        System.out.println("JSON:\n"+representacionJsonContactos);
    }
}
