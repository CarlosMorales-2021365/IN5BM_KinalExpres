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
import javax.swing.JOptionPane;
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
    @FXML private ImageView imgReporte;

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
    
    public void Agregar(){
        switch (tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                imgEliminar.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosmorales/Images/AgregarClientes.png"));
                imgEliminar.setImage(new Image("/org/carlosmorales/Images/EliminarClientes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
    public void guardar(){
        Proveedores registro = new Proveedores();
        registro.setProveedorID(Integer.parseInt(txtCodigoP.getText()));
        registro.setNombresProveedor(txtNombreP.getText());
        registro.setApellidosProveedor(txtApellidoP.getText());
        registro.setNitProveedor(txtNitP.getText());
        registro.setDireccionProveedor(txtDireccionP.getText());
        registro.setRazonSocial(txtRazonSocial.getText());
        registro.setContactoPrincipal(txtContactoPrincipal.getText());
        registro.setPaginaWeb(txtPaginaWeb.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores (?,?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getProveedorID());
            procedimiento.setString(2, registro.getNombresProveedor());
            procedimiento.setString(3, registro.getApellidosProveedor());
            procedimiento.setString(4, registro.getNitProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            listaProveedores.add(registro);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void reporte(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("org/carlosmorales/Images/EditarClientes.png"));
                imgReporte.setImage(new Image("/org/carlosmorales/Images/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
             desactivarControles();
             limpiarControles(); 
             btnAgregar.setText("Agregar");
             btnEliminar.setText("Eliminar");
             btnEditar.setDisable(false);
             btnReporte.setDisable(false);
             imgAgregar.setImage(new Image("/org/carlosmorales/Images/AgregarClientes.png"));
             imgEliminar.setImage(new Image("/org/carlosmorales/Images/EliminarClientes.png"));
             tipoDeOperaciones = operaciones.NINGUNO;
             break;
            default:
                if(tblProveedor.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblProveedor.getSelectionModel().getSelectedItem()).getProveedorID());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedor.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un proveedor para eliminar");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblProveedor.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReporte.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCodigoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "debe de selscionar un Proveedor para actualizar");
                }
                break;
            case ACTUALIZAR:
               actualizar();
               btnEditar.setText("Guardar");
               btnReporte.setText("Cancelar");
               btnAgregar.setDisable(true);
               btnEliminar.setDisable(true);
               imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
               imgReporte.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
               desactivarControles();
               limpiarControles();
               txtCodigoP.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProveedor (?,?,?,?,?,?,?,?)}");
            Proveedores registro = (Proveedores) tblProveedor.getSelectionModel().getSelectedItem();
            
            registro.setProveedorID(Integer.parseInt(txtCodigoP.getText()));
            registro.setNombresProveedor(txtNombreP.getText());
            registro.setApellidosProveedor(txtApellidoP.getText());
            registro.setNitProveedor(txtNitP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonSocial.getText());
            registro.setContactoPrincipal(txtContactoPrincipal.getText());
            registro.setPaginaWeb(txtPaginaWeb.getText());
            procedimiento.setInt(1, registro.getProveedorID());
            procedimiento.setString(2, registro.getNombresProveedor());
            procedimiento.setString(3, registro.getApellidosProveedor());
            procedimiento.setString(4, registro.getNitProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void desactivarControles(){
        txtCodigoP.setEditable(false);
        txtNombreP.setEditable(false);
        txtApellidoP.setEditable(false);
        txtNitP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtContactoPrincipal.setEditable(false);
        txtPaginaWeb.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoP.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true);
        txtNitP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtContactoPrincipal.setEditable(true);
        txtPaginaWeb.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoP.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtNitP.clear();
        txtDireccionP.clear();
        txtRazonSocial.clear();
        txtContactoPrincipal.clear();
        txtPaginaWeb.clear();
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
