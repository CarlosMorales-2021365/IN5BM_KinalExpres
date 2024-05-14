
package org.carlosmorales.Bean;


public class CargoEmpleado {
    
    private int cargoEmpleadoID;
    private String nombreCargo;
    private String descripocionCargo;
    
    
    public CargoEmpleado(){
        
    }

    public CargoEmpleado(int cargoEmpleadoID, String nombreCargo, String descripocionCargo) {
        this.cargoEmpleadoID = cargoEmpleadoID;
        this.nombreCargo = nombreCargo;
        this.descripocionCargo = descripocionCargo;
    }

    public int getCargoEmpleadoID() {
        return cargoEmpleadoID;
    }

    public void setCargoEmpleadoID(int cargoEmpleadoID) {
        this.cargoEmpleadoID = cargoEmpleadoID;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripocionCargo() {
        return descripocionCargo;
    }

    public void setDescripocionCargo(String descripocionCargo) {
        this.descripocionCargo = descripocionCargo;
    }
    
    
    
}
