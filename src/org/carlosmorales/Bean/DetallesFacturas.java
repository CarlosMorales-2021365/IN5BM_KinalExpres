
package org.carlosmorales.Bean;


public class DetallesFacturas {
    
    private int detalleFacturaID;
    private double precioUnitario;
    private int cantidad;
    private int facturaID;
    private String productoID;
    
    public DetallesFacturas(){
    }

    public DetallesFacturas(int detalleFacturaID, double precioUnitario, int cantidad, int facturaID, String productoID) {
        this.detalleFacturaID = detalleFacturaID;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.facturaID = facturaID;
        this.productoID = productoID;
    }

    public int getDetalleFacturaID() {
        return detalleFacturaID;
    }

    public void setDetalleFacturaID(int detalleFacturaID) {
        this.detalleFacturaID = detalleFacturaID;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getFacturaID() {
        return facturaID;
    }

    public void setFacturaID(int facturaID) {
        this.facturaID = facturaID;
    }

    public String getProductoID() {
        return productoID;
    }

    public void setProductoID(String productoID) {
        this.productoID = productoID;
    }

    
 
}
