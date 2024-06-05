
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
import org.carlosmorales.Bean.DetallesFacturas;
import org.carlosmorales.Bean.Facturas;
import org.carlosmorales.Bean.Productos;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;


public class MenuDetallesFacturaController implements Initializable {

    
    private Main escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <DetallesFacturas> listaDetalles;
    private ObservableList <Facturas> listaFacturas;
    private ObservableList<Productos> listaProductos;
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoDetalleFact;
    @FXML private TextField txtPrecioU;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox cmbFacturaID;
    @FXML private ComboBox cmbProductoID;
    @FXML private TableView tblDetalles;
    @FXML private TableColumn colCodigoDetalleFact;
    @FXML private TableColumn colPrecioU;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colFacturaID;
    @FXML private TableColumn colProductoID;
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
        cmbFacturaID.setItems(getFactura());
        cmbProductoID.setItems(getProducto());
    }
    
    public void cargarDatos(){
        tblDetalles.setItems(getDetalle());
        colCodigoDetalleFact.setCellValueFactory(new PropertyValueFactory<DetallesFacturas, Integer>("detalleFacturaID"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<DetallesFacturas, Double>("precioUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetallesFacturas, Integer>("cantidad"));
        colFacturaID.setCellValueFactory(new PropertyValueFactory<DetallesFacturas, Integer>("facturaID"));
        colProductoID.setCellValueFactory(new PropertyValueFactory<DetallesFacturas, String>("productoID"));
        
    }
    
    
    public void seleccionarElenemtos(){
        txtCodigoDetalleFact.setText(String.valueOf(((DetallesFacturas)tblDetalles.getSelectionModel().getSelectedItem()).getDetalleFacturaID()));
        txtPrecioU.setText(String.valueOf(((DetallesFacturas)tblDetalles.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtCantidad.setText(String.valueOf(((DetallesFacturas)tblDetalles.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbFacturaID.getSelectionModel().select(buscarFactura(((DetallesFacturas)tblDetalles.getSelectionModel().getSelectedItem()).getFacturaID()));
        cmbProductoID.getSelectionModel().select(buscarProducto(((DetallesFacturas)tblDetalles.getSelectionModel().getSelectedItem()).getProductoID()));
    }
    
    public Facturas buscarFactura(int FacturaID){
        Facturas resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarFactura(?)}");
            procedimiento.setInt(1, FacturaID);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Facturas (registro.getInt("facturaID"),
                registro.getString("estado"),
                registro.getDouble("totalFactura"),
                registro.getString("fechaFactura"),
                registro.getInt("clienteID"),
                registro.getInt("empleadoID")
                );
               
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return resultado; 
    }
    
    public Productos buscarProducto(String productoID){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProducto(?)}");
            procedimiento.setString(1, productoID);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Productos (registro.getString("productoID"),
                registro.getString("descripcionProducto"),
                registro.getDouble("precioUnitario"),
                registro.getDouble("precioDocena"),
                registro.getDouble("precioMayor"),
                registro.getInt("existencia"),
                registro.getInt("tipoProductoID"),
                registro.getInt("proveedorID")
                );  
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return resultado; 
    }
    
    
    
    public ObservableList<DetallesFacturas> getDetalle(){
        ArrayList<DetallesFacturas> lista = new ArrayList <DetallesFacturas>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarDetalleFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetallesFacturas(resultado.getInt("detalleFacturaID"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("facturaID"),
                        resultado.getString("productoID")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDetalles = FXCollections.observableList(lista); 
        
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
    
    public ObservableList<Productos> getProducto(){
        ArrayList<Productos> lista = new ArrayList <Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos(resultado.getString("productoID"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getInt("existencia"),
                        resultado.getInt("tipoProductoID"),
                        resultado.getInt("proveedorID")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableList(lista); 
        
    }
    
    
    public void Agregar(){
        switch (tipoDeOperaciones){
            case NINGUNO:
                txtPrecioU.setDisable(true);
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
        DetallesFacturas registro = new DetallesFacturas();
        registro.setDetalleFacturaID(Integer.parseInt(txtCodigoDetalleFact.getText()));
        registro.setPrecioUnitario(Double.parseDouble("0.00"));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setFacturaID(((Facturas)cmbFacturaID.getSelectionModel().getSelectedItem()).getFacturaID());
        registro.setProductoID(((Productos)cmbProductoID.getSelectionModel().getSelectedItem()).getProductoID());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetalleFactura(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getDetalleFacturaID());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getFacturaID());
            procedimiento.setString(5, registro.getProductoID());
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
                if(tblDetalles.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Detalle", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleFactura(?)}");
                            procedimiento.setInt(1, ((DetallesFacturas)tblDetalles.getSelectionModel().getSelectedItem()).getDetalleFacturaID());
                            procedimiento.execute();
                            listaDetalles.remove(tblDetalles.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un detalle para eliminar");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblDetalles.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCodigoDetalleFact.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "debe de selscionar un Detalle para actualizar");
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
               txtCodigoDetalleFact.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarDetalleFactura (?,?,?,?,?)}");
            DetallesFacturas registro = (DetallesFacturas) tblDetalles.getSelectionModel().getSelectedItem();
            
            registro.setDetalleFacturaID(Integer.parseInt(txtCodigoDetalleFact.getText()));
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setFacturaID(((Facturas)cmbFacturaID.getSelectionModel().getSelectedItem()).getFacturaID());
            registro.setProductoID(((Productos)cmbProductoID.getSelectionModel().getSelectedItem()).getProductoID());
            procedimiento.setInt(1, registro.getDetalleFacturaID());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getFacturaID());
            procedimiento.setString(5, registro.getProductoID());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
       txtCodigoDetalleFact.setEditable(false);
       txtPrecioU.setEditable(false);
       txtCantidad.setEditable(false);;
       cmbFacturaID.setDisable(true);
       cmbProductoID.setDisable(true);
   } 
    
    public void activarControles(){
       txtCodigoDetalleFact.setEditable(true);
       txtPrecioU.setEditable(true);
       txtCantidad.setEditable(true);
       cmbFacturaID.setDisable(false);
       cmbProductoID.setDisable(false); 
    }
    
    public void limpiarControles(){
       txtCodigoDetalleFact.clear();
       txtPrecioU.clear();
       txtCantidad.clear();
       tblDetalles.getSelectionModel().getSelectedItem();
       cmbFacturaID.getSelectionModel().getSelectedItem();
       cmbProductoID.getSelectionModel().getSelectedItem();
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
