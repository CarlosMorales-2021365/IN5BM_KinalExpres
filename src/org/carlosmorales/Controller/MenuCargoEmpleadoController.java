
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
import org.carlosmorales.Bean.CargoEmpleado;
import org.carlosmorales.Bean.Proveedores;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;


public class MenuCargoEmpleadoController implements Initializable {

    private Main escenarioPrincipal;
    
     private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<CargoEmpleado> listaCargos;

    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCargoE;
    @FXML private TextField txtNombreCargo;
    @FXML private TextField txtDescripcionCargo;
    @FXML private TableView tblCargoEmpleado;
    @FXML private TableColumn colCargoE;
    @FXML private TableColumn  colNombreCargo;
    @FXML private TableColumn colDescripcionCargo;
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
        tblCargoEmpleado.setItems(getCargo());
        colCargoE.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("cargoEmpleadoID"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("descripocionCargo"));
    }
    
    public void seleccionarElementos(){
       txtCargoE.setText(String.valueOf(((CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCargoEmpleadoID()));
       txtNombreCargo.setText(((CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo());
       txtDescripcionCargo.setText(((CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripocionCargo());
    }

    public ObservableList<CargoEmpleado> getCargo(){
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarCargo ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new CargoEmpleado(resultado.getInt("cargoEmpleadoID"),
                resultado.getString("nombreCargo"),
                resultado.getString("descripocionCargo")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCargos = FXCollections.observableList(lista);
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
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCargoEmpleadoID(Integer.parseInt(txtCargoE.getText()));
        registro.setNombreCargo(txtNombreCargo.getText());
        registro.setDescripocionCargo(txtDescripcionCargo.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargo (?,?,?)}");
            procedimiento.setInt(1, registro.getCargoEmpleadoID());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripocionCargo());
            procedimiento.execute();
            listaCargos.add(registro);
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
                if(tblCargoEmpleado.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Cargo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargo(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCargoEmpleadoID());
                            procedimiento.execute();
                            listaCargos.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
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
                if(tblCargoEmpleado.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCargoE.setEditable(false);
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
               txtCargoE.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCargo (?,?,?)}");
            CargoEmpleado registro = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();
            
            registro.setCargoEmpleadoID(Integer.parseInt(txtCargoE.getText()));
            registro.setNombreCargo(txtNombreCargo.getText());
            registro.setDescripocionCargo(txtDescripcionCargo.getText());
            procedimiento.setInt(1, registro.getCargoEmpleadoID());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripocionCargo());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void desactivarControles(){
        txtCargoE.setEditable(false);
        txtNombreCargo.setEditable(false);
        txtDescripcionCargo.setEditable(false);
    }
    
    public void activarControles(){
        txtCargoE.setEditable(true);
        txtNombreCargo.setEditable(true);
        txtDescripcionCargo.setEditable(true);
    }
    
     public void limpiarControles(){
        txtCargoE.clear();
        txtNombreCargo.clear();
        txtDescripcionCargo.clear();
    }
    
    @FXML
     public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
