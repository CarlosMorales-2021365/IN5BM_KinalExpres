/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.carlosmorales.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.carlosmorales.system.Main;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class VistaProgramadorController implements Initializable {
    private Main escenarioPrincipal;
    @FXML private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    public void setEcenarioPrincipal(Main escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void handleButtonAction (ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
    
    
}
