/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danny.controller;

import com.danny.model.AccidentsCriteria;
import com.danny.model.AccidentsCriteriaDAO;
import com.danny.model.AccidentsDetails;
import com.danny.model.AccidentsDetailsDAO;
import javafx.event.ActionEvent;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;


/**
 *
 * @author Danny
 */
public class AccidentsController {
    
    /* The following variables are for the controls when adding an accident to 
     * the db */
    
    @FXML
    private DatePicker dpInsertDate;
    
    @FXML
    private TextField txtCity;
    
    @FXML
    private TextField txtState;
    
    @FXML
    private ComboBox cbVin;
    
    ObservableList<String> autosList = FXCollections.observableArrayList();

    @FXML
    private TextField txtDamages;
    
    @FXML
    private ComboBox cbDriver;
    
    ObservableList<String> driversList = FXCollections.observableArrayList();
    
    @FXML
    private TableColumn<AccidentsDetails, String> colAccNewVin;
    
    @FXML
    private TableColumn<AccidentsDetails, String> colAccNewDamages;
    
    @FXML
    private TableColumn<AccidentsDetails, String> colAccNewDriver;
    
    @FXML
    private TableView tblNewAutoDriver;
    
    ObservableList<AccidentsDetails> newAutosDriversList = FXCollections.observableArrayList();

    /* The following variables are for the controls when searching for an accident from
     * the db using the id of the accident */    
    @FXML
    private TextField txtAID;
    
    @FXML
    private TableColumn<AccidentsDetails, Integer> colAccId;
    
    @FXML
    private TableColumn<AccidentsDetails, String> colAccDate;
    
    @FXML
    private TableColumn<AccidentsDetails, String> colAccCity;
    
    @FXML
    private TableColumn<AccidentsDetails, String> colAccState;
    
    @FXML
    private TableColumn<AccidentsDetails, String> colAccVin;
    
    @FXML
    private TableColumn<AccidentsDetails, String> colAccDamages;
    
    @FXML
    private TableColumn<AccidentsDetails, String> colAccDriver;
    
    @FXML
    private TableView tblAccidentsDetails;
    
    /* The following variables are for the controls when searching for an accident from
     * the db using the user criteria (dates, avg damages, total damages) */
    
    @FXML
    private DatePicker dpFromDate;
    
    @FXML
    private DatePicker dpToDate;
    
    @FXML
    private TextField txtMinAvgDamages;
    
    @FXML
    private TextField txtMaxAvgDamages;
    
    @FXML
    private TextField txtMinTotalDamages;
    
    @FXML
    private TextField txtMaxTotalDamages;
    
    @FXML
    private TableColumn<AccidentsCriteria, Integer> colAccCriteriaId;
    
    @FXML
    private TableColumn<AccidentsCriteria, String> colAccCriteriaDate;
    
    @FXML
    private TableColumn<AccidentsCriteria, String> colAccCriteriaCity;
    
    @FXML
    private TableColumn<AccidentsCriteria, String> colAccCriteriaState;
    
    @FXML
    private TableColumn<AccidentsCriteria, String> colAccCriteriaDamages;
    
    @FXML
    private TableView tblAccidentsCriteria;
    
    @FXML
    private void initialize() throws Exception
    {
        
        setOnlyText(txtCity);
        setOnlyText(txtState);
        setOnlyNumbers(txtDamages);
        setOnlyNumbers(txtMinAvgDamages);
        setOnlyNumbers(txtMaxAvgDamages);
        setOnlyNumbers(txtMinTotalDamages);
        setOnlyNumbers(txtMaxTotalDamages);
        
        setMaxLength(txtCity, 12);
        setMaxLength(txtState, 2);
        setMaxLength(txtDamages, 7);
        setMaxLength(txtMinAvgDamages, 15);
        setMaxLength(txtMaxAvgDamages, 15);
        setMaxLength(txtMinTotalDamages, 15);
        setMaxLength(txtMaxTotalDamages, 15);
        
        setUppercase(txtCity);
        setUppercase(txtState);
        

        autosList = AccidentsDetailsDAO.getAllAutos();
        cbVin.setItems(autosList);
        
        driversList = AccidentsDetailsDAO.getAllDrivers();
        cbDriver.setItems(driversList);
        
        // for accidents details table
        colAccId.setCellValueFactory(cellData -> cellData.getValue().getAccidentId().asObject());
        colAccDate.setCellValueFactory(cellData -> cellData.getValue().getAccidentDate());
        colAccCity.setCellValueFactory(cellData -> cellData.getValue().getAccidentCity());
        colAccState.setCellValueFactory(cellData -> cellData.getValue().getAccidentState());
        colAccVin.setCellValueFactory(cellData -> cellData.getValue().getAccidentVin());
        colAccDamages.setCellValueFactory(cellData -> cellData.getValue().getAccidentDamages());
        colAccDriver.setCellValueFactory(cellData -> cellData.getValue().getAccidentDriver());
        ObservableList<AccidentsDetails> accDetailsList = AccidentsDetailsDAO.getAllRecords();
        populateAccidentsDetailsTable(accDetailsList);
        
        // for accident criteria table
        colAccCriteriaId.setCellValueFactory(cellData -> cellData.getValue().getAccidentId().asObject());
        colAccCriteriaDate.setCellValueFactory(cellData -> cellData.getValue().getAccidentDate());
        colAccCriteriaCity.setCellValueFactory(cellData -> cellData.getValue().getAccidentCity());
        colAccCriteriaState.setCellValueFactory(cellData -> cellData.getValue().getAccidentState());
        colAccCriteriaDamages.setCellValueFactory(cellData -> cellData.getValue().getAccidentDamages());
        ObservableList<AccidentsCriteria> accCriteriaList = AccidentsCriteriaDAO.getAllRecords();
        populateAccidentsCriteriaTable(accCriteriaList);
        
        // for new auto/driver table
        colAccNewVin.setCellValueFactory(cellData -> cellData.getValue().getAccidentVin());
        colAccNewDamages.setCellValueFactory(cellData -> cellData.getValue().getAccidentDamages());
        colAccNewDriver.setCellValueFactory(cellData -> cellData.getValue().getAccidentDriver());
        populateNewAutoDriverTable(newAutosDriversList);
    }
    
    private void populateAccidentsDetailsTable(ObservableList<AccidentsDetails> accDetailsList) 
    {
        tblAccidentsDetails.setItems(accDetailsList);
    }
    
    private void populateAccidentsCriteriaTable(ObservableList<AccidentsCriteria> accDetailsList) 
    {
        tblAccidentsCriteria.setItems(accDetailsList);
    }
    
    private void populateNewAutoDriverTable(ObservableList<AccidentsDetails> newAutosDriversList) 
    {
        tblNewAutoDriver.setItems(newAutosDriversList);
    }
    
    // the following method is called when the user clicks add accident button
    @FXML
    private void insertAccident(ActionEvent event) throws ClassNotFoundException, SQLException
    {   
        if(dpInsertDate.getValue() == null || txtCity.getText().trim().isEmpty() || txtState.getText().trim().isEmpty() || newAutosDriversList.size() <= 0)
        {
            // populating message to show feedback
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter data for all the above fields!");
            alert.showAndWait();
        }
        else
        {
            // convert date to string
            String date = dpInsertDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            try 
            {
                // storing new accident to db, get aid from new accident
                int aid = AccidentsDetailsDAO.insertAccident(date, txtCity.getText(), txtState.getText(), newAutosDriversList);

                // reset table with new accident
                ObservableList<AccidentsDetails> accDetailsList = AccidentsDetailsDAO.getAllRecords();
                populateAccidentsDetailsTable(accDetailsList);
                
                // reset table with new accident
                ObservableList<AccidentsCriteria> accCriteriaList = AccidentsCriteriaDAO.getAllRecords();
                populateAccidentsCriteriaTable(accCriteriaList);
                
                // reset autos list after insert
                autosList = AccidentsDetailsDAO.getAllAutos();
                cbVin.setItems(autosList);
        
                // reset drivers list after insert
                driversList = AccidentsDetailsDAO.getAllDrivers();
                cbDriver.setItems(driversList);

                // clearing fields
                dpInsertDate.setValue(null);
                txtCity.clear();
                txtState.clear();
                newAutosDriversList.clear();

                // scroll to the new accident
                tblAccidentsDetails.scrollTo(accDetailsList.size());
                
                // scroll to the new accident
                tblAccidentsCriteria.scrollTo(accDetailsList.size());

                // populating message to show feedback
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Accident Created");
                alert.setHeaderText(null);
                alert.setContentText("Accident id: " + aid + " was created.");
                alert.showAndWait();
   
        } 
            catch(SQLException e)
            {
                System.out.println("Exception occur in insertion " +e);
                e.printStackTrace();
                throw e;
            }
        }
        
        
    }
    
    @FXML
    private void addNewAutoDriver(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        if (dpInsertDate.getValue() == null || txtCity.getText().trim().isEmpty() || txtState.getText().trim().isEmpty() || cbVin.getValue() == null  || txtDamages.getText().trim().isEmpty() || cbDriver.getValue() == null )
        {
            // populating message to show feedback
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all accident/automobile/driver information.");
            alert.showAndWait();
        }
        else     
        {
           // add details to list (the grid view)
           AccidentsDetails accDetails = new AccidentsDetails();
           accDetails.setAccVin(cbVin.getValue().toString());
           accDetails.setAccDamages(txtDamages.getText());
           accDetails.setAccDriver(cbDriver.getValue().toString());
           newAutosDriversList.add(accDetails);
           
           // remove selected item from list to avoid repeating
           autosList.remove(cbVin.getValue().toString());
           driversList.remove(cbDriver.getValue().toString());


           // clearing fields
           cbVin.valueProperty().set(null);
           txtDamages.clear();
           cbDriver.valueProperty().set(null);
        }
    }


    @FXML
    private void searchAccident(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        if(txtAID.getText().trim().isEmpty())
        {
            // populating message to show feedback
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a Accident ID.");
            alert.showAndWait();
        }
        else
        {
            try 
            {
                ObservableList<AccidentsDetails> accDetailsList = AccidentsDetailsDAO.searchAccident(txtAID.getText());
                if (accDetailsList.size() > 0)
                {
                    populateAccidentsDetailsTable(accDetailsList);
                }
                else
                {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("No Accident Found");
                    alert.setHeaderText(null);
                    alert.setContentText("No Accident found containing id: " + txtAID.getText());
                    alert.showAndWait();
                }
            } 
            catch(SQLException e)
            {
                System.out.println("Exception occur in search " +e);
                e.printStackTrace();
                throw e;
            }
        }  
    }
    
    @FXML
    private void searchAllAccidents(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        try 
        {
            ObservableList<AccidentsDetails> accDetailsList = AccidentsDetailsDAO.getAllRecords();
            populateAccidentsDetailsTable(accDetailsList);
            
            // scroll back to reset
            tblAccidentsDetails.scrollTo(0);  
            
            // reset field
            txtAID.clear();
        } 
        catch(SQLException e)
        {
            System.out.println("Exception occur in search " +e);
            e.printStackTrace();
            throw e;
        }
        
    }
    
    @FXML
    private void searchAllAccidentsCriteria(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        try 
        {
            colAccCriteriaDamages.setText("Total Damages");
            ObservableList<AccidentsCriteria> accCriteriaList = AccidentsCriteriaDAO.getAllRecords();
            populateAccidentsCriteriaTable(accCriteriaList);
            
            // scroll back to reset
             tblAccidentsCriteria.scrollTo(0);
            
             // reset field
             dpFromDate.setValue(null);
             dpToDate.setValue(null);
             txtMinAvgDamages.clear();
             txtMaxAvgDamages.clear();
             txtMinTotalDamages.clear();
             txtMaxTotalDamages.clear();
        } 
        catch(SQLException e)
        {
            System.out.println("Exception occur in search " +e);
            e.printStackTrace();
            throw e;
        }
        
    }
    
    @FXML
    public void searchAccidentByRangeOfDates(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        if(dpFromDate.getValue() == null || dpToDate.getValue() == null)
        {
            // populating message to show feedback
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a date range.");
            alert.showAndWait();
        }
        else
        {
           // convert date to string
            String FromDate = dpFromDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String ToDate = dpToDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            try 
            {
                ObservableList<AccidentsCriteria> accCriteriaList = AccidentsCriteriaDAO.searchAccidentByRangeOfDates(FromDate, ToDate);
                populateAccidentsCriteriaTable(accCriteriaList);
                
                if (accCriteriaList.size() >= 1)
                {
                    // populating message to show feedback
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Accidents Found");
                    alert.setHeaderText(null);
                    alert.setContentText("Accident(s) found between " + dpFromDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) 
                            + " and " + dpToDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".");
                    alert.showAndWait();
                }
                else
                {
                    // populating message to show feedback
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("No Accidents Found");
                    alert.setHeaderText(null);
                    alert.setContentText("No accident(s) found between " + dpFromDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) 
                            + " and " + dpToDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".");
                    alert.showAndWait();
                } 
                
                // reset field
                dpFromDate.setValue(null);
                dpToDate.setValue(null);
                
            } 
            catch(SQLException e)
            {
                System.out.println("Exception occur in search " +e);
                e.printStackTrace();
                throw e;
            } 
        }
        
    }
    
    @FXML
    public void searchAccidentByRangeOfAvgDamages(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        if(txtMinAvgDamages.getText().trim().isEmpty() || txtMaxAvgDamages.getText().trim().isEmpty())
        {
            // populating message to show feedback
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a range of average damages.");
            alert.showAndWait();
        }
        else
        {
            try 
            {
                colAccCriteriaDamages.setText("Avg Damages");
                ObservableList<AccidentsCriteria> accCriteriaList = AccidentsCriteriaDAO.searchAccidentByRangeOfAvgDamages(txtMinAvgDamages.getText(), txtMaxAvgDamages.getText());
                populateAccidentsCriteriaTable(accCriteriaList);
                
                if (accCriteriaList.size() >= 1)
                {
                    // populating message to show feedback
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Accidents Found");
                    alert.setHeaderText(null);
                    alert.setContentText("Accident(s) found containing average damages between " +  txtMinAvgDamages.getText()
                            + " and " + txtMaxAvgDamages.getText() + ".");
                    alert.showAndWait();
                }
                else
                {
                    // populating message to show feedback
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("No Accidents Found");
                    alert.setHeaderText(null);
                    alert.setContentText("No accident(s) found containing average damages between " +  txtMinAvgDamages.getText()
                            + " and " + txtMaxAvgDamages.getText() + ".");
                    alert.showAndWait();
                }
                
                // reset fields
                txtMinAvgDamages.clear();
                txtMaxAvgDamages.clear();
            } 
            catch(SQLException e)
            {
                System.out.println("Exception occur in search " +e);
                e.printStackTrace();
                throw e;
            }
        } 
    }
    
    @FXML
    public void searchAccidentByRangeOfTotalDamages(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        if(txtMinTotalDamages.getText().trim().isEmpty() || txtMaxTotalDamages.getText().trim().isEmpty())
        {
            // populating message to show feedback
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a range of total damages.");
            alert.showAndWait();
        }
        else
        {
           try 
           {
               colAccCriteriaDamages.setText("Total Damages");
               ObservableList<AccidentsCriteria> accCriteriaList = AccidentsCriteriaDAO.searchAccidentByRangeOfTotalDamages(txtMinTotalDamages.getText(), txtMaxTotalDamages.getText());
               populateAccidentsCriteriaTable(accCriteriaList);
   
               if (accCriteriaList.size() >= 1)
                {
                    // populating message to show feedback
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Accidents Found");
                    alert.setHeaderText(null);
                    alert.setContentText("Accident(s) found containing total damages between " +  txtMinTotalDamages.getText()
                            + " and " + txtMaxTotalDamages.getText() + ".");
                    alert.showAndWait();
                }
                else
                {
                    // populating message to show feedback
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("No Accidents Found");
                    alert.setHeaderText(null);
                    alert.setContentText("No accident(s) found containing total damages between " +  txtMinTotalDamages.getText()
                            + " and " + txtMaxTotalDamages.getText() + ".");
                    alert.showAndWait();
                }
               
               // reset fields
               txtMinTotalDamages.clear();
               txtMaxTotalDamages.clear();
               
           } 
           catch(SQLException e)
           {
               System.out.println("Exception occur in search " +e);
               e.printStackTrace();
               throw e;
           }   
        }
        
    }
    
    private void setOnlyText(TextField tf) 
    {
        tf.textProperty().addListener((observable, oldValue, newValue) -> 
        {
            if (!newValue.matches("\\sa-zA-Z*")) {
                tf.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });    
    }

    private void setOnlyNumbers(TextField tf) 
    {
        tf.textProperty().addListener((observable, oldValue, newValue) -> 
        {
            if (!newValue.matches("\\d*")) {
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });    
    }
    
    private void setMaxLength(TextField tf, int MaxLength)
    {
        tf.setOnKeyTyped(event ->
        {
            if(tf.getText().length() >= MaxLength) event.consume();
        });
    }
    
    private void setUppercase(TextField tf)
    {
        tf.setTextFormatter(new TextFormatter<>((change) -> 
        {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
    }
}
