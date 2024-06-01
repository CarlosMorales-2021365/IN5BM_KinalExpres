
package org.carlosmorales.Bean;


public class Productos {
    
    private String productoID;
    private String descripcionProducto;
    private double precionUnitario;
    private double precioDocena;
    private double precioMayor;
    private int existencia;
    private int tipoProductoID;
    private int proveedorID;
    
    public Productos(){
}

    public Productos(String productoID, String descripcionProducto, double precionUnitario, double precioDocena, double precioMayor, int existencia, int tipoProductoID, int proveedorID) {
        this.productoID = productoID;
        this.descripcionProducto = descripcionProducto;
        this.precionUnitario = precionUnitario;
        this.precioDocena = precioDocena;
        this.precioMayor = precioMayor;
        this.existencia = existencia;
        this.tipoProductoID = tipoProductoID;
        this.proveedorID = proveedorID;
    }

    

    public String getProductoID() {
        return productoID;
    }

    public void setProductoID(String productoID) {
        this.productoID = productoID;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public double getPrecionUnitario() {
        return precionUnitario;
    }

    public void setPrecionUnitario(double precionUnitario) {
        this.precionUnitario = precionUnitario;
    }

    public double getPrecioDocena() {
        return precioDocena;
    }

    public void setPrecioDocena(double precioDocena) {
        this.precioDocena = precioDocena;
    }

    public double getPrecioMayor() {
        return precioMayor;
    }

    public void setPrecioMayor(double precioMayor) {
        this.precioMayor = precioMayor;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getTipoProductoID() {
        return tipoProductoID;
    }

    public void setTipoProductoID(int tipoProductoID) {
        this.tipoProductoID = tipoProductoID;
    }

    public int getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(int proveedorID) {
        this.proveedorID = proveedorID;
    }

    @Override
    public String toString(){
        return getProductoID()+ " | " + getDescripcionProducto();
    }

    
}
