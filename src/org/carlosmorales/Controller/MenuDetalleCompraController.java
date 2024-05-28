
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
import org.carlosmorales.Bean.Compras;
import org.carlosmorales.Bean.DetallesCompras;
import org.carlosmorales.Bean.Productos;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;


public class MenuDetalleCompraController implements Initializable {
    
    private Main escenarioPrincipal;
     private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetallesCompras> listaDetalles;
    private ObservableList <Productos> listaProductos;
    private ObservableList <Compras> listaCompras;
    
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoD;
    @FXML private TextField txtCostoUnitario;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox cmbCodigoProd;
    @FXML private ComboBox cmbNumeroD;
    @FXML private TableView tblDetalles;
    @FXML private TableColumn colCodigoD;
    @FXML private TableColumn colCostoUnitario;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colCodigoProd;
    @FXML private TableColumn colNumeroD;
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
        cmbCodigoProd.setItems(getProducto());
        cmbNumeroD.setItems(getCompras());
        
    }   
    
    public void cargarDatos(){
        tblDetalles.setItems(getDetalle());
        colCodigoD.setCellValueFactory(new PropertyValueFactory<DetallesCompras, Integer>("detalleCompraID"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory<DetallesCompras, Double>("costoUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetallesCompras, Integer>("cantidad"));
        colCodigoProd.setCellValueFactory(new PropertyValueFactory<DetallesCompras, String>("productoID"));
        colNumeroD.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("numeroDocumento"));
    }
    
    public void seleccionarElenemtos(){
        txtCodigoD.setText(String.valueOf(((DetallesCompras)tblDetalles.getSelectionModel().getSelectedItem()).getDetalleCompraID()));
        txtCostoUnitario.setText(String.valueOf(((DetallesCompras)tblDetalles.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCantidad.setText(String.valueOf(((DetallesCompras)tblDetalles.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbCodigoProd.getSelectionModel().select(buscarProducto(((DetallesCompras)tblDetalles.getSelectionModel().getSelectedItem()).getProductoID()));
        cmbNumeroD.getSelectionModel().select(buscarCompra(((DetallesCompras)tblDetalles.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
    }
    
    
     public Productos buscarProducto(String ProductoID){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProducto(?)}");
            procedimiento.setString(1, ProductoID);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
               resultado = new Productos (registro.getString("ProductoID"),
                       registro.getString("descripcionProducto"),
                       registro.getDouble("precioUnitario"),
                       registro.getDouble("precioDocena"),
                       registro.getDouble("precioMayor"),
                       registro.getInt("existencia"),
                       registro.getInt("tipoProductoID"),
                       registro.getInt("proveedorID")
               );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
     
     
     public Compras buscarCompra(int numeroDocumento){
        Compras resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCompra(?)}");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
               resultado = new Compras (registro.getInt("numeroDocumento"),
                       registro.getString("fechaDocumento"),
                       registro.getString("descripcion"),
                       registro.getString("totalDocumento")
               );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
      public ObservableList<DetallesCompras> getDetalle(){
        ArrayList<DetallesCompras> lista = new ArrayList <DetallesCompras>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarDetallesCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetallesCompras(resultado.getInt("detalleCompraID"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("productoID"),
                        resultado.getInt("numeroDocumento")         
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDetalles = FXCollections.observableList(lista); 
        
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
      
     public ObservableList<Compras> getCompras(){
        ArrayList<Compras>lista = new ArrayList<>();
        try{
            PreparedStatement procedimeiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarCompra()}");
            ResultSet resultado = procedimeiento.executeQuery();
            while(resultado.next()){
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                resultado.getString("fechaDocumento"),
                resultado.getString("descripcion"),
                resultado.getString("totalDocumento")
                ));
                
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableList(lista);
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
        DetallesCompras registro = new DetallesCompras();
        registro.setDetalleCompraID(Integer.parseInt(txtCodigoD.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setProductoID(((Productos)cmbCodigoProd.getSelectionModel().getSelectedItem()).getProductoID());
        registro.setNumeroDocumento(((Compras)cmbNumeroD.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetalleCompra(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getDetalleCompraID());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getProductoID());
            procedimiento.setInt(5, registro.getNumeroDocumento());
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
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                            procedimiento.setString(1, ((DetallesCompras)tblDetalles.getSelectionModel().getSelectedItem()).getProductoID());
                            procedimiento.execute();
                            listaProductos.remove(tblDetalles.getSelectionModel().getSelectedItem());
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
                    txtCodigoD.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "debe de selscionar un detalle para actualizar");
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
               txtCodigoD.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
      
      public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarDetalleCompra (?,?,?,?,?)}");
            DetallesCompras registro = (DetallesCompras) tblDetalles.getSelectionModel().getSelectedItem();
            
            registro.setDetalleCompraID(Integer.parseInt(txtCodigoD.getText()));
            registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setProductoID(((Productos)cmbCodigoProd.getSelectionModel().getSelectedItem()).getProductoID());
            registro.setNumeroDocumento(((Compras)cmbNumeroD.getSelectionModel().getSelectedItem()).getNumeroDocumento());
            procedimiento.setInt(1, registro.getDetalleCompraID());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getProductoID());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     public void desactivarControles(){
       txtCodigoD.setEditable(false);
       txtCostoUnitario.setEditable(false);
       txtCantidad.setEditable(false);
       cmbCodigoProd.setDisable(true);
       cmbNumeroD.setDisable(true);
   } 
    
    public void activarControles(){
       txtCodigoD.setEditable(true);
       txtCostoUnitario.setEditable(true);
       txtCantidad.setEditable(true);
       cmbCodigoProd.setDisable(false);
       cmbNumeroD.setDisable(false); 
    }
    
    public void limpiarControles(){
       txtCodigoD.clear();
       txtCostoUnitario.clear();
       txtCantidad.clear();
       tblDetalles.getSelectionModel().getSelectedItem();
       cmbCodigoProd.getSelectionModel().getSelectedItem();
       cmbNumeroD.getSelectionModel().getSelectedItem();
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
