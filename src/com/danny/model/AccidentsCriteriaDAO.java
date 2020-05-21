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
public class AccidentsCriteriaDAO {

    public static ObservableList<AccidentsCriteria> getAllRecords() throws ClassNotFoundException, SQLException
    {
        String sql = "SELECT a.aid, accident_date, city, state, SUM(damages) \n" +
                     "FROM involvements i NATURAL JOIN accidents a \n" +
                     "GROUP BY a.aid";
        
        try 
        {
            ResultSet rsSet = DBUtil.dbExecuteQuery(sql);
            ObservableList<AccidentsCriteria> accCriteriaList = getAccidentCriteriaObjects(rsSet, true);
            return accCriteriaList;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while fetching the records from DB" + e);
            e.printStackTrace();
            throw e;
        }
        
         
        
    }

    private static ObservableList<AccidentsCriteria> getAccidentCriteriaObjects(ResultSet rsSet, boolean isSum) throws ClassNotFoundException, SQLException
    {
        try 
        {
            ObservableList<AccidentsCriteria> accCriteriaList = FXCollections.observableArrayList();
            
            while(rsSet.next())
            {   
                AccidentsCriteria accc = new AccidentsCriteria();
                accc.setAccId(rsSet.getInt("aid"));
                accc.setAccDate(rsSet.getString("accident_date"));
                accc.setAccCity(rsSet.getString("city"));
                accc.setAccState(rsSet.getString("state"));
                if (isSum)
                    accc.setAccDamages(rsSet.getString("SUM(damages)"));
                else
                    accc.setAccDamages(rsSet.getString("AVG(damages)"));
                
                accCriteriaList.add(accc);
            }
            
            return accCriteriaList;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while fetching the records from DB" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<AccidentsCriteria> searchAccidentByRangeOfDates(String FromDate, String ToDate) throws ClassNotFoundException, SQLException
    {
        String sql = "SELECT a.aid, accident_date, city, state, SUM(damages) FROM involvements i NATURAL JOIN accidents a " +
                     "GROUP BY a.aid HAVING accident_date >= '" + FromDate + "' and accident_date <= '" + ToDate + "'";
        
        try 
        {
            ResultSet rsSet = DBUtil.dbExecuteQuery(sql);
            ObservableList<AccidentsCriteria> accCriteriaList = getAccidentCriteriaObjects(rsSet, true);
            return accCriteriaList;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while searching the record(s) from DB" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<AccidentsCriteria> searchAccidentByRangeOfAvgDamages(String MinAvgDamages, String MaxAvgDamages) throws ClassNotFoundException, SQLException
    {
        String sql = "SELECT a.aid, accident_date, city, state, AVG(damages) FROM involvements i NATURAL JOIN accidents a " +
                     "GROUP BY a.aid HAVING AVG(damages) >= " + MinAvgDamages + " AND AVG(damages) <= " + MaxAvgDamages + "";
        
        try 
        {
            ResultSet rsSet = DBUtil.dbExecuteQuery(sql);
            ObservableList<AccidentsCriteria> accCriteriaList = getAccidentCriteriaObjects(rsSet, false);
            return accCriteriaList;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while searching the record(s) from DB" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<AccidentsCriteria> searchAccidentByRangeOfTotalDamages(String MinTotalDamages, String MaxTotalDamages) throws ClassNotFoundException, SQLException
    {
        String sql = "SELECT a.aid, accident_date, city, state, SUM(damages) FROM involvements i NATURAL JOIN accidents a " +
                     "GROUP BY a.aid HAVING SUM(damages) >= " + MinTotalDamages + " AND SUM(damages) <= " + MaxTotalDamages + "";
        
        try 
        {
            ResultSet rsSet = DBUtil.dbExecuteQuery(sql);
            ObservableList<AccidentsCriteria> accCriteriaList = getAccidentCriteriaObjects(rsSet, true);
            return accCriteriaList;
        }
        catch(SQLException e)
        {
            System.out.println("Exception occured while searching the record(s) from DB" + e);
            e.printStackTrace();
            throw e;
        }
    }


}
