/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maxbilbow.aicubo.hud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author José
 */
public class InfoWindowController implements Initializable {
    
    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
    }
    
    public void okAction(ActionEvent event) {
        
        System.out.println("Click ok button :" + username.getText());
    }

    public void cancelAction(ActionEvent event) {
        
        System.out.println("Click cancel button :" + password.getText());
    }
}
