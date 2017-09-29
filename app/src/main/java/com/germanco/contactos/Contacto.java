package com.germanco.contactos;

public class Contacto {
    String nombre;
    String telefono;
    Boolean isSelected;

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getSelected() {return isSelected;}

    public void setSelected(Boolean selected) {isSelected = selected;}
}
