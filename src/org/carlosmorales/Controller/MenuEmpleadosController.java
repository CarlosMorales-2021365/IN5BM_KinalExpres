
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
import org.carlosmorales.Bean.CargoEmpleado;
import org.carlosmorales.Bean.Empleados;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.system.Main;


public class MenuEmpleadosController implements Initializable {
    
    private Main escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList <Empleados> listaEmpleados;
    private ObservableList <CargoEmpleado> listaCargo;
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoEmp;
    @FXML private TextField txtNombreEmp;
    @FXML private TextField txtApellidosEmp;
    @FXML private TextField txtSueldo;
    @FXML private TextField txtDireccionEmp;
    @FXML private TextField txtTurno;
    @FXML private ComboBox cmbCargoE;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoEmp;
    @FXML private TableColumn colNombreEmp;
    @FXML private TableColumn colApellidosEmp;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colDireccionEmp;
    @FXML private TableColumn colTurno;
    @FXML private TableColumn colCargoE;
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
        cmbCargoE.setItems(getCargo());
    }
    
     public void cargarDatos(){
        tblEmpleados.setItems(getEmpleado());
        colCodigoEmp.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("empleadoID"));
        colNombreEmp.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colApellidosEmp.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccionEmp.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCargoE.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("cargoEmpleadoID"));
        
    }
     
     public void seleccionarElenemtos(){
        txtCodigoEmp.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoID()));
        txtNombreEmp.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApellidosEmp.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtSueldo.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccionEmp.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion()));
        txtTurno.setText((((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTurno()));
        cmbCargoE.getSelectionModel().select(buscarCargo(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCargoEmpleadoID()));
    }
    
    public CargoEmpleado buscarCargo(int cargoEmpleadoID){
        CargoEmpleado resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCargo(?)}");
            procedimiento.setInt(1,cargoEmpleadoID);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
               resultado = new CargoEmpleado (registro.getInt("cargoEmpleadoID"),
                       registro.getString("nombreCargo"),
                       registro.getString("descripocionCargo")
               );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
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
        return listaCargo = FXCollections.observableList(lista);
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
        Empleados registro = new Empleados();
        registro.setEmpleadoID(Integer.parseInt(txtCodigoEmp.getText()));
        registro.setNombresEmpleado(txtNombreEmp.getText());
        registro.setApellidosEmpleado(txtApellidosEmp.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setDireccion(txtDireccionEmp.getText());
        registro.setTurno(txtTurno.getText()); 
        registro.setCargoEmpleadoID(((CargoEmpleado)cmbCargoE.getSelectionModel().getSelectedItem()).getCargoEmpleadoID());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getEmpleadoID());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCargoEmpleadoID());
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
                if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoID());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Empleado para eliminar");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Guardar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCodigoEmp.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "debe de selscionar un Empleado para actualizar");
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
               txtCodigoEmp.setEditable(false);
               tipoDeOperaciones = operaciones.ACTUALIZAR;
               cargarDatos();
               break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleado (?,?,?,?,?,?,?)}");
            Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
            
            registro.setEmpleadoID(Integer.parseInt(txtCodigoEmp.getText()));
            registro.setNombresEmpleado(txtNombreEmp.getText());
            registro.setApellidosEmpleado(txtApellidosEmp.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccionEmp.getText());
            registro.setTurno(txtTurno.getText());
            registro.setCargoEmpleadoID(((CargoEmpleado)cmbCargoE.getSelectionModel().getSelectedItem()).getCargoEmpleadoID());
            procedimiento.setInt(1, registro.getEmpleadoID());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCargoEmpleadoID());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void desactivarControles(){
       txtCodigoEmp.setEditable(false);
       txtNombreEmp.setEditable(false);
       txtApellidosEmp.setEditable(false);
       txtSueldo.setEditable(false);
       txtDireccionEmp.setEditable(false);
       txtTurno.setEditable(false);
       cmbCargoE.setDisable(true);
   } 
    
    public void activarControles(){
       txtCodigoEmp.setEditable(true);
       txtNombreEmp.setEditable(true);
       txtApellidosEmp.setEditable(true);
       txtSueldo.setEditable(true);
       txtDireccionEmp.setEditable(true);
       txtTurno.setEditable(true);
       cmbCargoE.setDisable(false);
    }
    
    public void limpiarControles(){
       txtCodigoEmp.clear();
       txtNombreEmp.clear();
       txtApellidosEmp.clear();
       txtSueldo.clear();
       txtDireccionEmp.clear();
       txtTurno.clear();
       tblEmpleados.getSelectionModel().getSelectedItem();
       cmbCargoE.getSelectionModel().getSelectedItem();
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
