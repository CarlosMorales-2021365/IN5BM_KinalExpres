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
    @FXML private TextField txtTotalD;
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
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblCompras.setItems(getCompras());
        colNumeroD.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaD.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("fechaDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("descripcion"));
        colTotalD.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("totalDocumento"));
    }
    
    public void seleccionarElementos(){
       txtNumeroD.setText(String.valueOf(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
       txtFechaD.setText(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento());
       txtDescripcion.setText(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
       txtTotalD.setText(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento());
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
                break;
        }
    }
    
    public void guardar(){
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroD.getText()));
        registro.setFechaDocumento(txtFechaD.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setTotalDocumento(txtTotalD.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompra (?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);
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
                if(tblCompras.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompra(?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
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
                if(tblCompras.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtNumeroD.setEditable(false);
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
               txtNumeroD.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCompra (?,?,?,?)}");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            
            registro.setNumeroDocumento(Integer.parseInt(txtNumeroD.getText()));
            registro.setFechaDocumento(txtFechaD.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setTotalDocumento(txtTotalD.getText());
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtNumeroD.setEditable(false);
        txtFechaD.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTotalD.setEditable(false);
    }
    
    public void activarControles(){
        txtNumeroD.setEditable(true);
        txtFechaD.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTotalD.setEditable(true);
    }
    
    public void limpiarControles(){
        txtNumeroD.clear();
        txtFechaD.clear();
        txtDescripcion.clear();
        txtTotalD.clear();
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
