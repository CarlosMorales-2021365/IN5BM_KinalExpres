
package org.carlosmorales.Bean;


public class DetallesCompras {
    
    private int detalleCompraID;
    private double costoUnitario;
    private int cantidad;
    private String productoID;
    private int numeroDocumento;
    
    public DetallesCompras(){
    
}

    public DetallesCompras(int detalleCompraID, double costoUnitario, int cantidad, String productoID, int numeroDocumento) {
        this.detalleCompraID = detalleCompraID;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.productoID = productoID;
        this.numeroDocumento = numeroDocumento;
    }

    public int getDetalleCompraID() {
        return detalleCompraID;
    }

    public void setDetalleCompraID(int detalleCompraID) {
        this.detalleCompraID = detalleCompraID;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getProductoID() {
        return productoID;
    }

    public void setProductoID(String productoID) {
        this.productoID = productoID;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    
    
    
}
