
package org.carlosmorales.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.carlosmorales.Bean.CargoEmpleado;
import org.carlosmorales.Bean.Proveedores;
import org.carlosmorales.system.Main;


public class MenuCargoEmpleadoController implements Initializable {

    private Main escenarioPrincipal;
    
     private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<CargoEmpleado> listaCargos;

    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCargoE;
    @FXML private TextField txtNombreCargo;
    @FXML private TextField txtDescripcionCargo;
    @FXML private TableView tblCargoEmpleado;
    @FXML private TableColumn colCargoE;
    @FXML private TableColumn  colNombreCargo;
    @FXML private TableColumn colDescripcionCargo;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    } 

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    @FXML
     public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
