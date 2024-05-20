/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package org.carlosmorales.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.carlosmorales.Controller.MenuCargoEmpleadoController;
import org.carlosmorales.Controller.MenuClientesController;
import org.carlosmorales.Controller.MenuComprasController;
import org.carlosmorales.Controller.MenuDetalleCompraController;
import org.carlosmorales.Controller.MenuPrincipalController;
import org.carlosmorales.Controller.MenuProductosController;
import org.carlosmorales.Controller.MenuProveedorController;
import org.carlosmorales.Controller.MenuTiposProductoController;
import org.carlosmorales.Controller.VistaProgramadorController;
import org.carlosmorales.Controller.MenuEmpleadosController;

/*
nombre Carlos Morales
carne 2021365
codigo tecnico IN5BM
fecha 10/04/2024
fechas de modificacioes
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Expres");
        menuPrincipalView();
        escenarioPrincipal.getIcons().add(new Image("/org/carlosmorales/Images/Logo.png"));
        //Parent root = FXMLLoader.load(getClass().getResource("/org/carlosmorales/View/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public Initializable cambiarEcena(String fxmlname, int width, int heigth) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream("/org/carlosmorales/View/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/org/carlosmorales/View/" + fxmlname));

        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEcena("MenuPrincipalView.fxml", 625, 418);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuClientesView() {
        try {
            MenuClientesController menuClienteView = (MenuClientesController) cambiarEcena("ClienteView.fxml", 925, 528);
            menuClienteView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ProgramadorView() {
        try {
            VistaProgramadorController vistaProgramadorController = (VistaProgramadorController) cambiarEcena("ProgramadorView.fxml", 607, 373);
            vistaProgramadorController.setEcenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void MenuProveedorView() {
        try {
            MenuProveedorController menuProveedorController = (MenuProveedorController) cambiarEcena("ProveedorView.fxml", 1046, 591);
            menuProveedorController.setEcenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void MenuTipoProductoView() {
        try {
            MenuTiposProductoController menuTiposProductoController = (MenuTiposProductoController) cambiarEcena("tipoProductoView.fxml", 716, 451);
            menuTiposProductoController.setEcenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void MenuComprasView(){
        try{
            MenuComprasController menuComprasController = (MenuComprasController) cambiarEcena ("ComprasView.fxml",826,447);
            menuComprasController.setEscenarioPrincipal(this);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void MenuCargoEmpleadoView(){
        try{
         MenuCargoEmpleadoController menuCargoEmpleadoController = (MenuCargoEmpleadoController) cambiarEcena ("CargoEmpleadoView.fxml",750,418);
         menuCargoEmpleadoController.setEscenarioPrincipal(this);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void MenuProductosView(){
        try{
            MenuProductosController menuProductosController = (MenuProductosController) cambiarEcena ("ProductoView.fxml",1134,629);
            menuProductosController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public void MenuDetallesCompraView(){
        try{
            MenuDetalleCompraController menuDetalleCompraController  = (MenuDetalleCompraController) cambiarEcena ("DetalleComprasView.fxml", 792, 506);
            menuDetalleCompraController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public void MenuEmpleadosView(){
        try{
            MenuEmpleadosController menuEmpleadosController = (MenuEmpleadosController) cambiarEcena ("EmpleadosView.fxml",974,594);
            menuEmpleadosController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
