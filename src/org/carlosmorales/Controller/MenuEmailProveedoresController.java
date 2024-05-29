
package org.carlosmorales.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.carlosmorales.Bean.EmailProveedores;
import org.carlosmorales.Bean.Proveedores;
import org.carlosmorales.system.Main;


public class MenuEmailProveedoresController implements Initializable {

    public Main escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <EmailProveedores> listaEmails;
    private ObservableList <Proveedores> listaProveedores;
    
    @FXML private Button btnRegresar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }  

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource()== btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
    
    
    
}
