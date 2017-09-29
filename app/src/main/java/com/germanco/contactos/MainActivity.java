package com.germanco.contactos;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Contacto> contactoList= new ArrayList<>();
    public Contacto contacto;
    public ListView listaContactos;
    Cursor contactoCursor;
    AdaptadorContacto adaptadorContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaContactos= (ListView)findViewById(R.id.contactsList);
        adaptadorContacto= new AdaptadorContacto(this,contactoList);
        listaContactos.setAdapter(adaptadorContacto);
        obtenerContactos();
    }

    public void obtenerContactos(){
        Uri contactsUri=ContactsContract.Data.CONTENT_URI;
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
                contactoList.add(contacto);
                adaptadorContacto.notifyDataSetChanged();
            }catch (NullPointerException ex){
                Log.d("Estado",contactoCursor.getString(0));
            }
        }
    }
}
