/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carlosmorales.Controller;

import java.net.URL;
import java.security.Principal;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.carlosmorales.system.Main;

/**
 *
 * @author informatica
 */
public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    MenuItem btnMenuClientes;
    @FXML
    MenuItem btnVistaProgramador;
    @FXML
    MenuItem btnMenuProveedor;
    @FXML
    MenuItem btnMenuTipoProducto;
    @FXML MenuItem btnCompras;
    @FXML MenuItem btnCargoEmpleado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public MenuItem getBtnMenuClientes() {
        return btnMenuClientes;
    }

    public void setBtnMenuClientes(MenuItem btnMenuClientes) {
        this.btnMenuClientes = btnMenuClientes;
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public MenuItem getBtnVistaProgramador() {
        return btnVistaProgramador;
    }

    public void setBtnVistaProgramador(MenuItem btnVistaProgramador) {
        this.btnVistaProgramador = btnVistaProgramador;
    }

    public MenuItem getBtnMenuProveedor() {
        return btnMenuProveedor;
    }

    public void setBtnMenuProveedor(MenuItem btnMenuProveedor) {
        this.btnMenuProveedor = btnMenuProveedor;
    }

    public MenuItem getBtnMenuTipoProducto() {
        return btnMenuTipoProducto;
    }

    public void setBtnMenuTipoProducto(MenuItem btnMenuTipoProducto) {
        this.btnMenuTipoProducto = btnMenuTipoProducto;
    }

    public MenuItem getBtnCompras() {
        return btnCompras;
    }

    public void setBtnCompras(MenuItem btnCompras) {
        this.btnCompras = btnCompras;
    }

    public MenuItem getBtnCargoEmpleado() {
        return btnCargoEmpleado;
    }

    public void setBtnCargoEmpleado(MenuItem btnCargoEmpleado) {
        this.btnCargoEmpleado = btnCargoEmpleado;
    }
    
    


 


    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();
        }
        if (event.getSource() == btnVistaProgramador) {
            escenarioPrincipal.ProgramadorView();
        }
        if (event.getSource() == btnMenuProveedor) {
            escenarioPrincipal.MenuProveedorView();
        }
        if (event.getSource() == btnMenuTipoProducto) {
            escenarioPrincipal.MenuTipoProductoView();
        }if (event.getSource() == btnCompras){
            escenarioPrincipal.MenuComprasView();
        }if (event.getSource()== btnCargoEmpleado){
            escenarioPrincipal.MenuCargoEmpleadoView();
        }
        

    }
}
