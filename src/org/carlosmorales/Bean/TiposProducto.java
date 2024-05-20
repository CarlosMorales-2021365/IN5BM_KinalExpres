package org.carlosmorales.Bean;

public class TiposProducto {

    private int tipoProductoID;
    private String descripcion;

    public TiposProducto() {
    }

    public TiposProducto(int tipoProductoID, String descripcion) {
        this.tipoProductoID = tipoProductoID;
        this.descripcion = descripcion;
    }

    public int getTipoProductoID() {
        return tipoProductoID;
    }

    public void setTipoProductoID(int tipoProductoID) {
        this.tipoProductoID = tipoProductoID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString(){
        return getTipoProductoID() + " | " + getDescripcion();
    }

}
