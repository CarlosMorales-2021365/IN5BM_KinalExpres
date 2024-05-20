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
import org.carlosmorales.Bean.TiposProducto;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;

public class MenuTiposProductoController implements Initializable {

    private Main escenarioPrincipal;
    
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<TiposProducto> listaTiposProducto;

    private @FXML
    Button btnRegresar;
    @FXML private TextField txtCodigoTipoP;
    @FXML private TextField txtDecripcionTipoP;
    @FXML private TableView tblTipoProductos;
    @FXML private TableColumn colCodigoTipoP;
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
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblTipoProductos.setItems(getTiposProductos());
        colCodigoTipoP.setCellValueFactory(new PropertyValueFactory<TiposProducto, Integer>("tipoProductoID"));
        colDecripcionTipoP.setCellValueFactory(new PropertyValueFactory<TiposProducto, String>("descripcion"));
    }
    
     public void seleccionarElementos(){
       txtCodigoTipoP.setText(String.valueOf(((TiposProducto)tblTipoProductos.getSelectionModel().getSelectedItem()).getTipoProductoID()));
       txtDecripcionTipoP.setText(((TiposProducto)tblTipoProductos.getSelectionModel().getSelectedItem()).getDescripcion());
    }
    
    public ObservableList<TiposProducto> getTiposProductos(){
        ArrayList<TiposProducto> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new TiposProducto(resultado.getInt("tipoProductoID"),
                        resultado.getString("descripcion")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTiposProducto = FXCollections.observableList(lista);
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
                break;
        }
    }
    
    public void guardar(){
        TiposProducto registro = new TiposProducto();
        registro.setTipoProductoID(Integer.parseInt(txtCodigoTipoP.getText()));
        registro.setDescripcion(txtDecripcionTipoP.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregartipoProducto (?,?)}");
            procedimiento.setInt(1, registro.getTipoProductoID());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listaTiposProducto.add(registro);
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
                if(tblTipoProductos.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TiposProducto) tblTipoProductos.getSelectionModel().getSelectedItem()).getTipoProductoID());
                            procedimiento.execute();
                            listaTiposProducto.remove(tblTipoProductos.getSelectionModel().getSelectedItem());
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
                if(tblTipoProductos.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCodigoTipoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "debe de selscionar un Proveedor para actualizar");
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
               txtCodigoTipoP.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoProducto (?,?)}");
            TiposProducto registro = (TiposProducto) tblTipoProductos.getSelectionModel().getSelectedItem();
            
            registro.setTipoProductoID(Integer.parseInt(txtCodigoTipoP.getText()));
            registro.setDescripcion(txtDecripcionTipoP.getText());
            procedimiento.setInt(1, registro.getTipoProductoID());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     
     public void desactivarControles(){
        txtCodigoTipoP.setEditable(false);
        txtDecripcionTipoP.setEditable(false);
    }
     
     public void activarControles(){
        txtCodigoTipoP.setEditable(true);
        txtDecripcionTipoP.setEditable(true);
    }

     public void limpiarControles(){
        txtCodigoTipoP.clear();
        txtDecripcionTipoP.clear();
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
