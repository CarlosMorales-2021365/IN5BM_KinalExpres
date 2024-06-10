package org.carlosmorales.Controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.carlosmorales.Bean.Clientes;
import org.carlosmorales.DB.Conexion;
import org.carlosmorales.report.GenerarReportes;
import org.carlosmorales.system.Main;

public class MenuClientesController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<Clientes> listaClientes;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtNombresC;
    @FXML
    private TextField txtApellidosC;
    @FXML
    private TextField txtNit;
    @FXML
    private TextField txtTelefonoC;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtCorreoC;
    @FXML
    private TableView tblClientes;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colApellidoC;
    @FXML
    private TableColumn colNit;
    @FXML
    private TableColumn colTelefonoC;
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colCorreoC;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReportes;
    @FXML
    private ImageView imgRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("clienteID"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colNit.setCellValueFactory(new PropertyValueFactory<Clientes, String>("clienteNit"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }

    public void selecionarElementos() {
        txtCodigoC.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getClienteID()));
        txtNombresC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
        txtApellidosC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
        txtNit.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getClienteNit());
        txtTelefonoC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtDireccionC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtCorreoC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
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

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                imgEliminar.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarConroles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosmorales/Images/AgregarClientes.png"));
                imgEliminar.setImage(new Image("/org/carlosmorales/Images/EliminarClientes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Clientes registro = new Clientes();
        registro.setClienteID(Integer.parseInt(txtCodigoC.getText()));
        registro.setNombreCliente(txtNombresC.getText());
        registro.setApellidoCliente(txtApellidosC.getText());
        registro.setClienteNit(txtNit.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getClienteID());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(4, registro.getClienteNit());
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getDireccionCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReporte();
                break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarConroles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/carlosmorales/Images/EditarClientes.png"));
                imgReportes.setImage(new Image("/org/carlosmorales/Images/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarConroles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosmorales/Images/AgregarClientes.png"));
                imgEliminar.setImage(new Image("/org/carlosmorales/Images/EliminarClientes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion de registro", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCliente(?)}");
                            procedimiento.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getClienteID());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                            limpiarConroles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para eliminar");
                }
        }
    }

    // editar llevar el mismo concepto de agregar y editar
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Guardar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                    imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                    activarControles();
                    txtCodigoC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para actualizar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Guardar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/carlosmorales/Images/guardar.png"));
                imgReportes.setImage(new Image("/org/carlosmorales/Images/cancelar.png"));
                desactivarControles();
                limpiarConroles();
                txtCodigoC.setEditable(false);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                cargarDatos();
                break;
        }
    }
    
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("ClienteID", null);
        GenerarReportes.mostrarReportes("reportesClientes.jasper", "Reportes de Clientes", parametros);  
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCliente(?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes) tblClientes.getSelectionModel().getSelectedItem();

            registro.setClienteID(Integer.parseInt(txtCodigoC.getText()));
            registro.setNombreCliente(txtNombresC.getText());
            registro.setApellidoCliente(txtApellidosC.getText());
            registro.setClienteNit(txtNit.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getClienteID());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(4, registro.getClienteNit());
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getDireccionCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void desactivarControles() {
        txtCodigoC.setEditable(false);
        txtNombresC.setEditable(false);
        txtApellidosC.setEditable(false);
        txtNit.setEditable(false);
        txtTelefonoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtCorreoC.setEditable(false);
    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtNombresC.setEditable(true);
        txtApellidosC.setEditable(true);
        txtNit.setEditable(true);
        txtTelefonoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtCorreoC.setEditable(true);
    }

    public void limpiarConroles() {
        txtCodigoC.clear();
        txtNombresC.clear();
        txtApellidosC.clear();
        txtNit.clear();
        txtTelefonoC.clear();
        txtDireccionC.clear();
        txtCorreoC.clear();
    }

    public void setEscenarioPrincipal(Main ecenarioPrincipal) {
        this.escenarioPrincipal = ecenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
