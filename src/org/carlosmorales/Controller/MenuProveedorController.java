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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.carlosmorales.Bean.Clientes;
import org.carlosmorales.Bean.Proveedores;

import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;

public class MenuProveedorController implements Initializable {

    private Main ecenarioPrincipal;
    
     private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<Proveedores> listaProveedores;

    @FXML
    private Button btnRegresar;
    @FXML private TextField txtCodigoP;
    @FXML private TextField txtNombreP;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtNitP;
    @FXML private TextField txtDireccionP;
    @FXML private TextField txtRazonSocial;
    @FXML private TextField txtContactoPrincipal;
    @FXML private TextField txtPaginaWeb;
    @FXML private TableView tblProveedor;
    @FXML private TableColumn colCodigoP;
    @FXML private TableColumn colNombreP;
    @FXML private TableColumn colApellidoP;
    @FXML private TableColumn colNitP;
    @FXML private TableColumn colDireccionP;
    @FXML private TableColumn colRazonSocial;
    @FXML private TableColumn colContactoPrincipal;
    @FXML private TableColumn colPaginaWeb;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblProveedor.setItems(getProveedores());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("proveedorID"));
        colNombreP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombresProveedor"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        colNitP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nitProveedor"));
        colDireccionP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoPrincipal.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }
    
    public void seleccionarElementos(){
       txtCodigoP.setText(String.valueOf(((Proveedores)tblProveedor.getSelectionModel().getSelectedItem()).getProveedorID()));
       txtNombreP.setText(((Proveedores)tblProveedor.getSelectionModel().getSelectedItem()).getNombresProveedor());
       txtApellidoP.setText(((Proveedores)tblProveedor.getSelectionModel().getSelectedItem()).getApellidosProveedor());
       txtNitP.setText(((Proveedores)tblProveedor.getSelectionModel().getSelectedItem()).getNitProveedor());
       txtDireccionP.setText(((Proveedores)tblProveedor.getSelectionModel().getSelectedItem()).getDireccionProveedor());
       txtRazonSocial.setText(((Proveedores)tblProveedor.getSelectionModel().getSelectedItem()).getRazonSocial());
       txtContactoPrincipal.setText(((Proveedores)tblProveedor.getSelectionModel().getSelectedItem()).getContactoPrincipal());
       txtPaginaWeb.setText(((Proveedores)tblProveedor.getSelectionModel().getSelectedItem()).getPaginaWeb());
    }
        
    public ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Proveedores(resultado.getInt("proveedorID"),
                resultado.getString("nombresProveedor"),
                resultado.getString("apellidosProveedor"),
                resultado.getString("nitProveedor"),
                resultado.getString("direccionProveedor"),
                resultado.getString("razonSocial"),
                resultado.getString("contactoPrincipal"),
                resultado.getString("paginaWeb")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(lista);
    }
    



    public void setEcenarioPrincipal(Main ecenarioPrincipal) {
        this.ecenarioPrincipal = ecenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            ecenarioPrincipal.menuPrincipalView();
        }
    }
}
