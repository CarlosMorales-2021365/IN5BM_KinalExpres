/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carlosmorales.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import org.carlosmorales.Bean.Productos;
import org.carlosmorales.Bean.Proveedores;
import org.carlosmorales.system.Main;

public class MenuProductosController implements Initializable {

    private Main escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Productos> listaProductos;
    private ObservableList <Proveedores> listaProveedor;
    
    @FXML private ComboBox cmbCodigoTioP;
    @FXML private ComboBox cmbProveedorID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    
    
   public void desactivarControles(){
       
   } 
    
    
    
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
