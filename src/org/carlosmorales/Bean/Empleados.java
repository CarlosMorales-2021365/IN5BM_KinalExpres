
package org.carlosmorales.Bean;


public class Empleados {
    
    private int empleadoID;
    private String nombresEmpleado;
    private String apellidosEmpleado;
    private double sueldo;
    private String direccion;
    private String turno;
    private int cargoEmpleadoID;
    
    public Empleados(){
    }

    public Empleados(int empleadoID, String nombresEmpleado, String apellidosEmpleado, double sueldo, String direccion, String turno, int cargoEmpleadoID) {
        this.empleadoID = empleadoID;
        this.nombresEmpleado = nombresEmpleado;
        this.apellidosEmpleado = apellidosEmpleado;
        this.sueldo = sueldo;
        this.direccion = direccion;
        this.turno = turno;
        this.cargoEmpleadoID = cargoEmpleadoID;
    }

    public int getEmpleadoID() {
        return empleadoID;
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID;
    }

    public String getNombresEmpleado() {
        return nombresEmpleado;
    }

    public void setNombresEmpleado(String nombresEmpleado) {
        this.nombresEmpleado = nombresEmpleado;
    }

    public String getApellidosEmpleado() {
        return apellidosEmpleado;
    }

    public void setApellidosEmpleado(String apellidosEmpleado) {
        this.apellidosEmpleado = apellidosEmpleado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getCargoEmpleadoID() {
        return cargoEmpleadoID;
    }

    public void setCargoEmpleadoID(int cargoEmpleadoID) {
        this.cargoEmpleadoID = cargoEmpleadoID;
    }
    
    
}
