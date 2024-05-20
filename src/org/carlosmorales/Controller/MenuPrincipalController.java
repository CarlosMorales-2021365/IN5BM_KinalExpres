
package org.carlosmorales.Controller;

import java.net.URL;
import java.security.Principal;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.carlosmorales.system.Main;


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
    @FXML MenuItem btnProductos;
    @FXML MenuItem btnDetalleCompras;
    @FXML MenuItem btnEmpleados;

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

    public MenuItem getBtnProductos() {
        return btnProductos;
    }

    public void setBtnProductos(MenuItem btnProductos) {
        this.btnProductos = btnProductos;
    }

    public MenuItem getBtnDetalleCompras() {
        return btnDetalleCompras;
    }

    public void setBtnDetalleCompras(MenuItem btnDetalleCompras) {
        this.btnDetalleCompras = btnDetalleCompras;
    }

    public MenuItem getBtnEmpleados() {
        return btnEmpleados;
    }

    public void setBtnEmpleados(MenuItem btnEmpleados) {
        this.btnEmpleados = btnEmpleados;
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
        }if (event.getSource()== btnProductos){
            escenarioPrincipal.MenuProductosView();
        }if (event.getSource() == btnDetalleCompras){
            escenarioPrincipal.MenuDetallesCompraView();
        }if (event.getSource() == btnEmpleados){
            escenarioPrincipal.MenuEmpleadosView();
        }
        
        

    }
}
