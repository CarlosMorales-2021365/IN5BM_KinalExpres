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
import org.carlosmorales.Bean.Clientes;
import org.carlosmorales.system.Main;

public class MenuTiposProductoController implements Initializable {

    private Main escenarioPrincipal;
    
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<Clientes> listaClientes;

    private @FXML
    Button btnRegresar;
    @FXML private TextField txtCodigoTipoP;
    @FXML private TextField txtDecripcionTipoP;
    @FXML private TableView tblTipoProductos;
    @FXML private TableColumn colCodigoTipoC;
    @FXML private TableColumn colDecripcionTipoP;
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
    
    public void CargarDatos(){
        
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEcenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
