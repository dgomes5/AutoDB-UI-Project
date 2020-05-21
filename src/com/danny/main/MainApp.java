/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danny.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 *
 * @author Danny
 */
public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/com/danny/controller/AccidentsView.fxml"));
        ScrollPane sp = new ScrollPane();
        sp.setContent(root);
        Scene scene = new Scene(sp);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

                
    }
    
    public static void main(String[] args)
    {
        launch(args);  
        
    }
    
}
