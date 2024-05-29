
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
import org.carlosmorales.Bean.Productos;
import org.carlosmorales.Bean.Proveedores;
import org.carlosmorales.Bean.TiposProducto;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;

public class MenuProductosController implements Initializable {

    private Main escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Productos> listaProductos;
    private ObservableList <Proveedores> listaProveedores;
    private ObservableList<TiposProducto> listaTipoProducto;
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoProd;
    @FXML private TextField txtDescripcionProd;
    @FXML private TextField txtPrecioUnitario;
    @FXML private TextField txtPrecioDocena;
    @FXML private TextField txtPrecioMayor;
    @FXML private TextField txtExistencia;
    @FXML private ComboBox cmbCodigoTipoP;
    @FXML private ComboBox cmbProveedorID;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodigoProd;
    @FXML private TableColumn colDescripcionProd;
    @FXML private TableColumn colPrecioUnitario;
    @FXML private TableColumn colPrecioDocena;
    @FXML private TableColumn colPrecioMayor;
    @FXML private TableColumn colExistencia;
    @FXML private TableColumn colCodigoP;
    @FXML private TableColumn colCodigoTipoP;
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
        cmbCodigoTipoP.setItems(getTipoP());
        cmbProveedorID.setItems(getProveedores());
    }
    
    
    public void cargarDatos(){
        tblProductos.setItems(getProducto());
        colCodigoProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("productoID"));
        colDescripcionProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precionUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("tipoProductoID"));
        colCodigoTipoP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("proveedorID"));
        
    }
    
    public void seleccionarElenemtos(){
        txtCodigoProd.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getProductoID());
        txtDescripcionProd.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioUnitario.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecionUnitario()));
        txtPrecioDocena.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioMayor.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtExistencia.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCodigoTipoP.getSelectionModel().select(buscarTipoProducto(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getTipoProductoID()));
        cmbProveedorID.getSelectionModel().select(buscarProveedor(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getProveedorID()));
    }
    
    public TiposProducto buscarTipoProducto(int tipoProductoID){
        TiposProducto resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
            procedimiento.setInt(1,tipoProductoID);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
               resultado = new TiposProducto (registro.getInt("tipoProductoID"),
                       registro.getString("descripcion")
               );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
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

    public ObservableList<TiposProducto> getTipoP(){
        ArrayList<TiposProducto> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TiposProducto(resultado.getInt("tipoProductoID"),
                        resultado.getString("descripcion")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoProducto = FXCollections.observableList(lista);
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
        Productos registro = new Productos();
        registro.setProductoID(txtCodigoProd.getText());
        registro.setDescripcionProducto(txtDescripcionProd.getText());
        registro.setPrecionUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
        registro.setExistencia(Integer.parseInt(txtExistencia.getText())); 
        registro.setProveedorID(((Proveedores)cmbProveedorID.getSelectionModel().getSelectedItem()).getProveedorID());
        registro.setTipoProductoID(((TiposProducto)cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getTipoProductoID());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProducto(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getProductoID());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecionUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setInt(6, registro.getExistencia());
            procedimiento.setInt(7, registro.getTipoProductoID());
            procedimiento.setInt(8, registro.getProveedorID());
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
                if(tblProductos.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProducto(?)}");
                            procedimiento.setString(1, ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getProductoID());
                            procedimiento.execute();
                            listaProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un producto para eliminar");
                }
        }
    }
    
    
     public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblProductos.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCodigoProd.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "debe de selscionar un producto para actualizar");
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
               txtCodigoProd.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProducto (?,?,?,?,?,?,?,?)}");
            Productos registro = (Productos) tblProductos.getSelectionModel().getSelectedItem();
            
            registro.setProductoID(txtCodigoProd.getText());
            registro.setDescripcionProducto(txtDescripcionProd.getText());
            registro.setPrecionUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
            registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
            registro.setProveedorID(((Proveedores)cmbProveedorID.getSelectionModel().getSelectedItem()).getProveedorID());
            registro.setTipoProductoID(((TiposProducto)cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getTipoProductoID());
            procedimiento.setString(1, registro.getProductoID());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecionUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setInt(6, registro.getExistencia());
            procedimiento.setInt(7, registro.getProveedorID());
            procedimiento.setInt(8, registro.getTipoProductoID());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
   public void desactivarControles(){
       txtCodigoProd.setEditable(false);
       txtDescripcionProd.setEditable(false);
       txtPrecioUnitario.setEditable(false);
       txtPrecioDocena.setEditable(false);
       txtPrecioMayor.setEditable(false);
       txtExistencia.setEditable(false);
       cmbCodigoTipoP.setDisable(true);
       cmbProveedorID.setDisable(true);
   } 
    
    public void activarControles(){
       txtCodigoProd.setEditable(true);
       txtDescripcionProd.setEditable(true);
       txtPrecioUnitario.setEditable(true);
       txtPrecioDocena.setEditable(true);
       txtPrecioMayor.setEditable(true);
       txtExistencia.setEditable(true);
       cmbCodigoTipoP.setDisable(false);
       cmbProveedorID.setDisable(false); 
    }
    
    public void limpiarControles(){
       txtCodigoProd.clear();
       txtDescripcionProd.clear();
       txtPrecioUnitario.clear();
       txtPrecioDocena.clear();
       txtPrecioMayor.clear();
       txtExistencia.clear();
       tblProductos.getSelectionModel().getSelectedItem();
       cmbCodigoTipoP.getSelectionModel().getSelectedItem();
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
