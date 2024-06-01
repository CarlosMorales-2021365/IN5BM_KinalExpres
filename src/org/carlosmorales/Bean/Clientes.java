package org.carlosmorales.Bean;

public class Clientes {

    private int clienteID;
    private String nombreCliente;
    private String apellidoCliente;
    private String clienteNit;
    private String telefonoCliente;
    private String direccionCliente;
    private String correoCliente;

    public Clientes() {
    }

    public Clientes(int clienteID, String nombreCliente, String apellidoCliente, String clienteNit, String telefonoCliente, String direccionCliente, String correoCliente) {
        this.clienteID = clienteID;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.clienteNit = clienteNit;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.correoCliente = correoCliente;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getClienteNit() {
        return clienteNit;
    }

    public void setClienteNit(String clienteNit) {
        this.clienteNit = clienteNit;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }
    
    @Override
    public String toString(){
        return getClienteID()+ " | " + getNombreCliente();
    }

}
