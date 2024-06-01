
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
import org.carlosmorales.Bean.Clientes;
import org.carlosmorales.Bean.Empleados;
import org.carlosmorales.Bean.Facturas;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;


public class MenuFacturasController implements Initializable {

    public Main escenarioPrincipal;
     private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Facturas> listaFacturas;
    private ObservableList <Clientes> listaClientes;
    private ObservableList<Empleados> listaEmpleados;
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoFact;
    @FXML private TextField txtEstado;
    @FXML private TextField txtTotalFact;
    @FXML private TextField txtFechaFact;
    @FXML private ComboBox cmbClienteID;
    @FXML private ComboBox cmbEmpleadoID;
    @FXML private TableView tblFacturas;
    @FXML private TableColumn colCodigoFact;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colTotalFact;
    @FXML private TableColumn colFechaFact;
    @FXML private TableColumn colClienteID;
    @FXML private TableColumn colEmpleadoID;
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
       cmbClienteID.setItems(getClientes());
       cmbEmpleadoID.setItems(getEmpleado());
    } 
    
    
    public void cargarDatos(){
        tblFacturas.setItems(getFactura());
        colCodigoFact.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("facturaID"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Facturas, String>("estado"));
        colTotalFact.setCellValueFactory(new PropertyValueFactory<Facturas, Double>("totalFactura"));
        colFechaFact.setCellValueFactory(new PropertyValueFactory<Facturas, String>("fechaFactura"));
        colClienteID.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("clienteID"));
        colEmpleadoID.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("empleadoID"));
        
    }
    
    public void seleccionarElenemtos(){
        txtCodigoFact.setText(String.valueOf(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getFacturaID()));
        txtEstado.setText(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getEstado());
        txtTotalFact.setText(String.valueOf(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getTotalFactura()));
        txtFechaFact.setText(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getFechaFactura());
        cmbClienteID.getSelectionModel().select(buscarCliente(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getClienteID()));
        cmbEmpleadoID.getSelectionModel().select(buscarEmpleado(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getEmpleadoID()));
    }
    
    public Clientes buscarCliente(int clienteID){
        Clientes resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarClientes(?)}");
            procedimiento.setInt(1,clienteID);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
               resultado = new Clientes (registro.getInt("clienteID"),
                       registro.getString("nombreCliente"),
                       registro.getString("apellidoCliente"),
                       registro.getString("clienteNit"),
                       registro.getString("telefonoCliente"),
                       registro.getString("direccionCliente"),
                       registro.getString("correoCliente")
                       
               );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    public Empleados buscarEmpleado(int empleadoID){
        Empleados resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarEmpleado(?)}");
            procedimiento.setInt(1, empleadoID);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empleados (registro.getInt("empleadoID"),
                registro.getString("nombresEmpleado"),
                registro.getString("apellidosEmpleado"),
                registro.getDouble("sueldo"),
                registro.getString("direccion"),
                registro.getString("turno"),
                registro.getInt("cargoEmpleadoID")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return resultado; 
    }
    
    
    public ObservableList<Facturas> getFactura(){
        ArrayList<Facturas> lista = new ArrayList <Facturas>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Facturas(resultado.getInt("facturaID"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("clienteID"),
                        resultado.getInt("empleadoID")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaFacturas = FXCollections.observableList(lista); 
        
    }
    
    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("clienteID"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("clienteNit"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("correoCliente")
                ));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableList(lista);
    }
    
    public ObservableList<Empleados> getEmpleado(){
        ArrayList<Empleados> lista = new ArrayList <Empleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleados(resultado.getInt("empleadoID"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("cargoEmpleadoID")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableList(lista); 
        
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
        Facturas registro = new Facturas();
        registro.setFacturaID(Integer.parseInt(txtCodigoFact.getText()));
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalFact.getText()));
        registro.setFechaFactura(txtFechaFact.getText());
        registro.setClienteID(((Clientes)cmbClienteID.getSelectionModel().getSelectedItem()).getClienteID());
        registro.setEmpleadoID(((Empleados)cmbEmpleadoID.getSelectionModel().getSelectedItem()).getEmpleadoID());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarFactura(?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getFacturaID());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getClienteID());
            procedimiento.setInt(6, registro.getEmpleadoID());
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
                if(tblFacturas.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarFactura(?)}");
                            procedimiento.setInt(1, ((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getFacturaID());
                            procedimiento.execute();
                            listaFacturas.remove(tblFacturas.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar una factura para eliminar");
                }
        }
    }
    
     
     public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblFacturas.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCodigoFact.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "debe de selscionar una Factura para actualizar");
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
               txtCodigoFact.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
     
      public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarFactura (?,?,?,?,?,?)}");
            Facturas registro = (Facturas) tblFacturas.getSelectionModel().getSelectedItem();
            
            registro.setFacturaID(Integer.parseInt(txtCodigoFact.getText()));
            registro.setEstado(txtEstado.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotalFact.getText()));
            registro.setFechaFactura(txtFechaFact.getText());
            registro.setClienteID(((Clientes)cmbClienteID.getSelectionModel().getSelectedItem()).getClienteID());
            registro.setEmpleadoID(((Empleados)cmbEmpleadoID.getSelectionModel().getSelectedItem()).getEmpleadoID());
            procedimiento.setInt(1, registro.getFacturaID());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getClienteID());
            procedimiento.setInt(6, registro.getEmpleadoID());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
       txtCodigoFact.setEditable(false);
       txtEstado.setEditable(false);
       txtTotalFact.setEditable(false);
       txtFechaFact.setEditable(false);
       cmbClienteID.setDisable(true);
       cmbEmpleadoID.setDisable(true);
   } 
    
    public void activarControles(){
       txtCodigoFact.setEditable(true);
       txtEstado.setEditable(true);
       txtTotalFact.setEditable(true);
       txtFechaFact.setEditable(true);
       cmbClienteID.setDisable(false);
       cmbEmpleadoID.setDisable(false); 
    }
    
    public void limpiarControles(){
       txtCodigoFact.clear();
       txtEstado.clear();
       txtTotalFact.clear();
       txtFechaFact.clear();
       tblFacturas.getSelectionModel().getSelectedItem();
       cmbClienteID.getSelectionModel().getSelectedItem();
       cmbEmpleadoID.getSelectionModel().getSelectedItem();
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
