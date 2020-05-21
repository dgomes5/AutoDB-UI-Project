/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danny.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Danny
 */
public class AccidentsDetails {
    
    private IntegerProperty idProperty;
    
    private StringProperty dateProperty;
    
    private StringProperty cityProperty;
 
    private StringProperty stateProperty;
    
    private StringProperty vinProperty;

    private StringProperty damagesProperty;
    
    private StringProperty driverProperty;
    
    public AccidentsDetails()
    {
        this.idProperty = new SimpleIntegerProperty();
        this.dateProperty = new SimpleStringProperty();
        this.cityProperty = new SimpleStringProperty();
        this.stateProperty = new SimpleStringProperty();
        this.vinProperty = new SimpleStringProperty();
        this.damagesProperty = new SimpleStringProperty();
        this.driverProperty = new SimpleStringProperty();

    }

    public int getAccId()
    {
        return idProperty.get();
    }
    
    public void setAccId(int id)
    {
        this.idProperty.set(id);
    }
    
    public IntegerProperty getAccidentId()
    {
        return idProperty;
    }
    
    public String getAccDate()
    {
        return dateProperty.get();
    }
    
    public void setAccDate(String date)
    {
        this.dateProperty.set(date);
    }
    
    public StringProperty getAccidentDate()
    {
        return dateProperty;
    }
    
    public String getAccCity()
    {
        return cityProperty.get();
    }
    
    public void setAccCity(String city)
    {
        this.cityProperty.set(city);
    }
    
    public StringProperty getAccidentCity()
    {
        return cityProperty;
    }
    
    public String getAccState()
    {
        return stateProperty.get();
    }
    
    public void setAccState(String state)
    {
        this.stateProperty.set(state);
    }
    
    public StringProperty getAccidentState()
    {
        return stateProperty;
    }
    
    public String getAccVin()
    {
        return vinProperty.get();
    }
    
    public void setAccVin(String vin)
    {
        this.vinProperty.set(vin);
    }
    
    public StringProperty getAccidentVin()
    {
        return vinProperty;
    }
    
    public String getAccDamages()
    {
        return damagesProperty.get();
    }
    
    public void setAccDamages(String damages)
    {
        this.damagesProperty.set(damages);
    }
    
    public StringProperty getAccidentDamages()
    {
        return damagesProperty;
    }
    
    public String getAccDriver()
    {
        return driverProperty.get();
    }
    
    public void setAccDriver(String driver)
    {
        this.driverProperty.set(driver);
    }
    
    public StringProperty getAccidentDriver()
    {
        return driverProperty;
    } 
}
