package org.carlosmorales.Controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.carlosmorales.Bean.Compras;
import org.carlosmorales.Bean.Proveedores;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;

public class MenuComprasController implements Initializable {

    private Main escenarioPrincipal;
    
     private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<Compras> listaCompras;

    
    @FXML private Button btnRegresar;
    @FXML private TextField txtNumeroD;
    @FXML private TextField txtFechaD;
    @FXML private TextField txtDescripcion;
    @FXML private TextField TotalD;
    @FXML private TableView tblCompras;
    @FXML private TableColumn colNumeroD;
    @FXML private TableColumn colFechaD;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colTotalD;
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
        tblCompras.setItems(getCompras());
        
    }
    
    public ObservableList<Compras> getCompras(){
        ArrayList<Compras>lista = new ArrayList<>();
        try{
            PreparedStatement procedimeiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarCompra()}");
            ResultSet resultado = procedimeiento.executeQuery();
            while(resultado.next()){
                lista.add(new Compras(resultado.getInt("")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableList(lista);
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
