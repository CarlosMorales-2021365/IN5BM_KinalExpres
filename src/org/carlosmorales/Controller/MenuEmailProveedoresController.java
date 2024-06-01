
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
import org.carlosmorales.Bean.EmailProveedores;
import org.carlosmorales.Bean.Proveedores;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;


public class MenuEmailProveedoresController implements Initializable {

    public Main escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <EmailProveedores> listaEmails;
    private ObservableList <Proveedores> listaProveedores;
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoEmail;
    @FXML private TextField txtEmailProveedor;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox cmbProveedorID;
    @FXML private TableView tblEmils;
    @FXML private TableColumn colCodigoEmail;
    @FXML private TableColumn colEmailProveedor;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colProveedorID;
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
        tblEmils.setItems(getEmail());
        colCodigoEmail.setCellValueFactory(new PropertyValueFactory<EmailProveedores, Integer>("emailID"));
        colEmailProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedores, String>("emailProveedor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<EmailProveedores, String>("descripcion"));
        colProveedorID.setCellValueFactory(new PropertyValueFactory<EmailProveedores, Integer>("proveedorID"));
        
    }
    
    public void seleccionarElenemtos(){
        txtCodigoEmail.setText(String.valueOf(((EmailProveedores)tblEmils.getSelectionModel().getSelectedItem()).getEmailID()));
        txtEmailProveedor.setText(((EmailProveedores)tblEmils.getSelectionModel().getSelectedItem()).getEmailProveedor());
        txtDescripcion.setText(((EmailProveedores)tblEmils.getSelectionModel().getSelectedItem()).getDescripcion());
        cmbProveedorID.getSelectionModel().select(buscarProveedor(((EmailProveedores)tblEmils.getSelectionModel().getSelectedItem()).getProveedorID()));
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
    
    
    public ObservableList<EmailProveedores> getEmail(){
        ArrayList<EmailProveedores> lista = new ArrayList <EmailProveedores>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarEmails()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new EmailProveedores(resultado.getInt("emailID"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("proveedorID")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmails = FXCollections.observableList(lista); 
        
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
        EmailProveedores registro = new EmailProveedores();
        registro.setEmailID(Integer.parseInt(txtCodigoEmail.getText()));
        registro.setEmailProveedor(txtEmailProveedor.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setProveedorID(((Proveedores)cmbProveedorID.getSelectionModel().getSelectedItem()).getProveedorID());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmail(?,?,?,?)}");
            procedimiento.setInt(1, registro.getEmailID());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getProveedorID());
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
                if(tblEmils.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Email", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmail(?)}");
                            procedimiento.setInt(1, ((EmailProveedores)tblEmils.getSelectionModel().getSelectedItem()).getEmailID());
                            procedimiento.execute();
                            listaEmails.remove(tblEmils.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Email para eliminar");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblEmils.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCodigoEmail.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "debe de selscionar un Email para actualizar");
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
               txtCodigoEmail.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmail (?,?,?,?)}");
            EmailProveedores registro = (EmailProveedores) tblEmils.getSelectionModel().getSelectedItem();
            
            registro.setEmailID(Integer.parseInt(txtCodigoEmail.getText()));
            registro.setEmailProveedor(txtEmailProveedor.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setProveedorID(((Proveedores)cmbProveedorID.getSelectionModel().getSelectedItem()).getProveedorID());
            procedimiento.setInt(1, registro.getEmailID());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getProveedorID());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    public void desactivarControles(){
       txtCodigoEmail.setEditable(false);
       txtEmailProveedor.setEditable(false);
       txtDescripcion.setEditable(false);
       cmbProveedorID.setDisable(true);
   } 
    
    public void activarControles(){
       txtCodigoEmail.setEditable(true);
       txtEmailProveedor.setEditable(true);
       txtDescripcion.setEditable(true);
       cmbProveedorID.setDisable(false); 
    }
    
    public void limpiarControles(){
       txtCodigoEmail.clear();
       txtEmailProveedor.clear();
       txtDescripcion.clear();
       tblEmils.getSelectionModel().getSelectedItem();
       cmbProveedorID.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource()== btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
    
    
    
}
