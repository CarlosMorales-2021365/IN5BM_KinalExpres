
package org.carlosmorales.Bean;


public class TelefonosProveedores {
    private int telefonoID;
    private String numeroPrincipal;
    private String numeroSecundario;
    private String observaciones;
    private int proveedorID;
    
    public TelefonosProveedores(){
}

    public TelefonosProveedores(int telefonoID, String numeroPrincipal, String numeroSecundario, String observaciones, int proveedorID) {
        this.telefonoID = telefonoID;
        this.numeroPrincipal = numeroPrincipal;
        this.numeroSecundario = numeroSecundario;
        this.observaciones = observaciones;
        this.proveedorID = proveedorID;
    }

    public int getTelefonoID() {
        return telefonoID;
    }

    public void setTelefonoID(int telefonoID) {
        this.telefonoID = telefonoID;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public String getNumeroSecundario() {
        return numeroSecundario;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numeroSecundario = numeroSecundario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(int proveedorID) {
        this.proveedorID = proveedorID;
    }
    
    
    
}


