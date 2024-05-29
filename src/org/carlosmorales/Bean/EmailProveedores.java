
package org.carlosmorales.Bean;


public class EmailProveedores {
    
    private int emailID;
    private String emailProveedor;
    private String descripcion;
    private int proveedorID;
    
    public EmailProveedores(){
    }

    public EmailProveedores(int emailID, String emailProveedor, String descripcion, int proveedorID) {
        this.emailID = emailID;
        this.emailProveedor = emailProveedor;
        this.descripcion = descripcion;
        this.proveedorID = proveedorID;
    }

    public int getEmailID() {
        return emailID;
    }

    public void setEmailID(int emailID) {
        this.emailID = emailID;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(int proveedorID) {
        this.proveedorID = proveedorID;
    }
    
    
}
