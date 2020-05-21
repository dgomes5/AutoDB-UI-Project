/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danny.model;

import com.danny.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Danny
 */
public class AccidentsDetailsDAO {
    
    public static int insertAccident(String pickedDate, String City, String State, ObservableList<AccidentsDetails> list) throws SQLException, ClassNotFoundException
    {
        String sql = "INSERT INTO accidents(aid, accident_date, city, state) " +
                        "SELECT MAX(aid) + 1, '" + pickedDate + "', '" + City + "', '" + State + "' FROM accidents;";
        
        String sqlID = "SELECT MAX(aid) FROM accidents";

        try 
        {
            DBUtil.dbExecuteUpdate(sql);
            ResultSet rsSet = DBUtil.dbExecuteQuery(sqlID);
            
            while(rsSet.next())
            {
               int aid = rsSet.getInt("MAX(aid)");  
               
               for (AccidentsDetails ad : list)
               {
                   String sql2 = "INSERT INTO involvements(aid, vin, damages, driver_ssn) " +
                             "VALUES('" + aid + "', '" + ad.getAccVin() + "', '" + ad.getAccDamages() + "', '" + ad.getAccDriver() + "');";
                   DBUtil.dbExecuteUpdate(sql2);
               }
               
               return aid;
            }   
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while inserting the data" + e);
            e.printStackTrace();
            throw e;
        } 
        
        return 0;
    }
    
    public static ObservableList<AccidentsDetails> getAllRecords() throws ClassNotFoundException, SQLException
    {
        String sql = "SELECT a.aid, accident_date, city, state, vin, damages, driver_ssn " +
        "FROM involvements i natural join accidents a;";
        
        try 
        {
            ResultSet rsSet = DBUtil.dbExecuteQuery(sql);
            ObservableList<AccidentsDetails> accDetailsList = getAccidentDetailsObjects(rsSet);
            return accDetailsList;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while fetching the records from DB" + e);
            e.printStackTrace();
            throw e;
        }
        
         
        
    }

    private static ObservableList<AccidentsDetails> getAccidentDetailsObjects(ResultSet rsSet) throws ClassNotFoundException, SQLException
    {
        try 
        {
            ObservableList<AccidentsDetails> accDetailsList = FXCollections.observableArrayList();
            
            while(rsSet.next())
            {   
                AccidentsDetails accd = new AccidentsDetails();
                accd.setAccId(rsSet.getInt("aid"));
                accd.setAccDate(rsSet.getString("accident_date"));
                accd.setAccCity(rsSet.getString("city"));
                accd.setAccState(rsSet.getString("state"));
                accd.setAccVin(rsSet.getString("vin"));
                accd.setAccDamages(rsSet.getString("damages"));
                accd.setAccDriver(rsSet.getString("driver_ssn"));
                accDetailsList.add(accd);
            }
            
            return accDetailsList;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while fetching the records from DB" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<AccidentsDetails> searchAccident(String accId) throws ClassNotFoundException, SQLException
    {
        String sql = "SELECT a.aid, accident_date, city, state, vin, damages, driver_ssn " +
        "FROM involvements i natural join accidents a WHERE aid = " + accId;
        
        try 
        {
            ResultSet rsSet = DBUtil.dbExecuteQuery(sql);
            ObservableList<AccidentsDetails> accDetailsList = getAccidentDetailsObjects(rsSet);
            return accDetailsList;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while seraching the record(s) from DB" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<String> getAllAutos() throws ClassNotFoundException, SQLException
    {
        String sql = "SELECT vin FROM autos";
        
        ObservableList<String> autos = FXCollections.observableArrayList();

        try 
        {
            ResultSet rsSet = DBUtil.dbExecuteQuery(sql);
            
            while(rsSet.next())
            {   
                String auto = rsSet.getString("vin");
                autos.add(auto);
            }
            return autos;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while fetching the records from DB" + e);
            e.printStackTrace();
            throw e;
        }
    }
       
    public static ObservableList<String> getAllDrivers() throws ClassNotFoundException, SQLException
    {
        String sql = "SELECT ssn FROM people";
        
        ObservableList<String> drivers = FXCollections.observableArrayList();

        try 
        {
            ResultSet rsSet = DBUtil.dbExecuteQuery(sql);
            
            while(rsSet.next())
            {   
                String driver = rsSet.getString("ssn");
                drivers.add(driver);
            }
            return drivers;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while fetching the records from DB" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    
}
