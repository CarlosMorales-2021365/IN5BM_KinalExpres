
package org.carlosmorales.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.carlosmorales.Bean.DetallesFacturas;
import org.carlosmorales.Bean.Facturas;
import org.carlosmorales.Bean.Productos;
import org.carlosmorales.system.Main;


public class MenuDetallesFacturaController implements Initializable {

    
    private Main escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <DetallesFacturas> listaDetalles;
    private ObservableList <Facturas> listaFacturas;
    private ObservableList<Productos> listaProductos;
    
    @FXML private Button btnRegresar;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void setEscenarioPrincipal(Main EscenarioPrincipal) {
        this.escenarioPrincipal = EscenarioPrincipal;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource()== btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }

    
    
}
