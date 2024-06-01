
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.carlosmorales.Bean.Proveedores;
import org.carlosmorales.Bean.TelefonosProveedores;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;

public class MenuTelefonoProveedoresController implements Initializable {
    
    private Main escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <TelefonosProveedores> listaTelefonos;
    private ObservableList <Proveedores>ListaProveedores;
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoTel;
    @FXML private TextField txtNumPrincipal;
    @FXML private TextField txtNumSecundario;
    @FXML private TextField txtObservaciones;
    @FXML private ComboBox cmbProveedorID;
    @FXML private TableView tblTelefonos;
    @FXML private TableColumn colCodigoTel;
    @FXML private TableColumn colNumPrincipal;
    @FXML private TableColumn colNumSecundario;
    @FXML private TableColumn colObservaciones;
    @FXML private TableColumn colCodigoProveedor;
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
        cargarDatos();
        cmbProveedorID.setItems(getProveedores());
    } 
    
    public void cargarDatos(){
        tblTelefonos.setItems(getTelefono());
        colCodigoTel.setCellValueFactory(new PropertyValueFactory<TelefonosProveedores, Integer>("telefonoID"));
        colNumPrincipal.setCellValueFactory(new PropertyValueFactory<TelefonosProveedores, String>("numeroPrincipal"));
        colNumSecundario.setCellValueFactory(new PropertyValueFactory<TelefonosProveedores, String>("numeroSecundario"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<TelefonosProveedores, String>("observaciones"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<TelefonosProveedores, Integer>("proveedorID"));    
    }
    
    public void seleccionarElenemtos(){
        txtCodigoTel.setText(String.valueOf(((TelefonosProveedores)tblTelefonos.getSelectionModel().getSelectedItem()).getTelefonoID()));
        txtNumPrincipal.setText(((TelefonosProveedores)tblTelefonos.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
        txtNumSecundario.setText(((TelefonosProveedores)tblTelefonos.getSelectionModel().getSelectedItem()).getNumeroSecundario());
        txtObservaciones.setText(((TelefonosProveedores)tblTelefonos.getSelectionModel().getSelectedItem()).getObservaciones());
        cmbProveedorID.getSelectionModel().select(buscarProveedor(((TelefonosProveedores)tblTelefonos.getSelectionModel().getSelectedItem()).getProveedorID()));
    }
    
    
    
    
    public Proveedores buscarProveedor(int proveedorID){
        Proveedores resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProveedor(?)}");
            procedimiento.setInt(1, proveedorID);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Proveedores (registro.getInt("proveedorID"),
                registro.getString("nombresProveedor"),
                registro.getString("apellidosProveedor"),
                registro.getString("nitProveedor"),
                registro.getString("direccionProveedor"),
                registro.getString("razonSocial"),
                registro.getString("contactoPrincipal"),
                registro.getString("paginaWeb")
                );
               
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return resultado; 
    }
    
    public ObservableList<TelefonosProveedores> getTelefono(){
        ArrayList<TelefonosProveedores> lista = new ArrayList <TelefonosProveedores>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarTelefonos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TelefonosProveedores(resultado.getInt("telefonoID"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("proveedorID")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTelefonos = FXCollections.observableList(lista); 
        
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
        return ListaProveedores = FXCollections.observableList(lista);
    }
    
    public void Agregar(){
        switch (tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
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
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosmorales/Images/agregar.png"));
                imgEliminar.setImage(new Image("/org/carlosmorales/Images/eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    
     public void guardar(){
        TelefonosProveedores registro = new TelefonosProveedores();
        registro.setTelefonoID(Integer.parseInt(txtCodigoTel.getText()));
        registro.setNumeroPrincipal(txtNumPrincipal.getText());
        registro.setNumeroSecundario(txtNumSecundario.getText());
        registro.setObservaciones(txtObservaciones.getText()); 
        registro.setProveedorID(((Proveedores)cmbProveedorID.getSelectionModel().getSelectedItem()).getProveedorID());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTelefono(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getTelefonoID());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getProveedorID());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     
     public void reporte(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("org/carlosmorales/Images/editar.png"));
                imgReportes.setImage(new Image("/org/carlosmorales/Images/Reportes.png"));
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
             btnReportes.setDisable(false);
             imgAgregar.setImage(new Image("/org/carlosmorales/Images/agregar.png"));
             imgEliminar.setImage(new Image("/org/carlosmorales/Images/eliminar.png"));
             tipoDeOperaciones = operaciones.NINGUNO;
             break;
            default:
                if(tblTelefonos.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Telefono", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTelefono(?)}");
                            procedimiento.setInt(1, ((TelefonosProveedores)tblTelefonos.getSelectionModel().getSelectedItem()).getTelefonoID());
                            procedimiento.execute();
                            listaTelefonos.remove(tblTelefonos.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Telefono para eliminar");
                }
        }
    } 
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblTelefonos.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCodigoTel.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "debe de selscionar un Telefono para actualizar");
                }
                break;
            case ACTUALIZAR:
               actualizar();
               btnEditar.setText("Guardar");
               btnReportes.setText("Cancelar");
               btnAgregar.setDisable(true);
               btnEliminar.setDisable(true);
               imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
               imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
               desactivarControles();
               limpiarControles();
               txtCodigoTel.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTelefono (?,?,?,?,?)}");
            TelefonosProveedores registro = (TelefonosProveedores) tblTelefonos.getSelectionModel().getSelectedItem();
            
            registro.setTelefonoID(Integer.parseInt(txtCodigoTel.getText()));
            registro.setNumeroPrincipal(txtNumPrincipal.getText());
            registro.setNumeroSecundario(txtNumSecundario.getText());
            registro.setObservaciones(txtObservaciones.getText());
            registro.setProveedorID(((Proveedores)cmbProveedorID.getSelectionModel().getSelectedItem()).getProveedorID());
            procedimiento.setInt(1, registro.getTelefonoID());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getProveedorID());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
       txtCodigoTel.setEditable(false);
       txtNumPrincipal.setEditable(false);
       txtNumSecundario.setEditable(false);
       txtObservaciones.setEditable(false);
       cmbProveedorID.setDisable(true);
   }
    
    public void activarControles(){
       txtCodigoTel.setEditable(true);
       txtNumPrincipal.setEditable(true);
       txtNumSecundario.setEditable(true);
       txtObservaciones.setEditable(true);
       cmbProveedorID.setDisable(false); 
    }
    
    public void limpiarControles(){
       txtCodigoTel.clear();
       txtNumPrincipal.clear();
       txtNumSecundario.clear();
       txtObservaciones.clear();
       tblTelefonos.getSelectionModel().getSelectedItem();
       cmbProveedorID.getSelectionModel().getSelectedItem();
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
