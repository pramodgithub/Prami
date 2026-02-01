package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.Cities;
import com.prami.db.MySQLAccess;

public class LocationDao {
	CallableStatement statement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	MySQLAccess dao = new MySQLAccess();
	 
	 
	 public List<Cities> getCities(int countryId){
			List<Cities> citiesList = new ArrayList<Cities>();
			Cities city;
			try{
				connect = dao.getConnection();
				String query = "{call sp_get_cities(?)}";
				statement = connect.prepareCall(query);
				statement.setInt(1, countryId);
				resultSet = statement.executeQuery();
			    while (resultSet.next()) {
			    	city =  new Cities(resultSet.getInt("locationid"), resultSet.getString("location_name"), resultSet.getString("location_logo"));
			    	citiesList.add(city);
			    }
				
			}catch(Exception e){
				System.out.println("failed at getCities-->"+e.getMessage());
			}finally{
				close();
			}
			return citiesList;
		}
	 public List<Cities> getAreasByCity(int cityId){
			List<Cities> citiesList = new ArrayList<Cities>();
			Cities city;
			try{
				connect = dao.getConnection();
				String query = "{call sp_get_area_by_city(?)}";
				statement = connect.prepareCall(query);
				statement.setInt(1, cityId);
				resultSet = statement.executeQuery();
			    while (resultSet.next()) {
			    	city =  new Cities(resultSet.getInt("locationid"), resultSet.getString("location_name"), resultSet.getString("location_logo"));
			    	citiesList.add(city);
			    }
				
			}catch(Exception e){
				System.out.println("failed at getAreasByCity-->"+e.getMessage());
			}finally{
				close();
			}
			return citiesList;
		}
	 private void close() {
		    try {
		      if (resultSet != null) {
		        resultSet.close();
		      }
		      if (statement != null) {
		        statement.close();
		      }
		      if (connect != null) {
		        connect.close();
		      }
		    } catch (Exception e) {

		    }
		  }
}
