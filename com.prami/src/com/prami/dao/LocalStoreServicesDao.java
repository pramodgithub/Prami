package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.LocalStoreServices;
import com.prami.db.MySQLAccess;

public class LocalStoreServicesDao {
	private CallableStatement statement = null;
	private ResultSet resultSet = null;
	private Connection connect = null;
	private MySQLAccess dao = new MySQLAccess();
	 
	 
	 
	public List<LocalStoreServices> getStoreServicesList(int storeId){
			List<LocalStoreServices> LocalStoreList = new ArrayList<LocalStoreServices>();
			//LocalStoreServices localStore;
			try{
				connect = dao.getConnection();
				String query = "{call sp_get_loc_store_services(?)}";
				statement = connect.prepareCall(query);
				statement.setInt(1, storeId);
				
				resultSet = statement.executeQuery();
			    while (resultSet.next()) {
			    	System.out.println(resultSet.getInt("serviceId"));
				/*
				 * localStore = new LocalStore(resultSet.getInt("storeid"),
				 * resultSet.getString("storeName"), resultSet.getString("storeDescription"),
				 * resultSet.getString("storeLogo"), resultSet.getString("storeImage"),
				 * resultSet.getInt("isStoreVerified"),
				 * resultSet.getString("storeEstablishedDate"), resultSet.getInt("storeAreaId"),
				 * resultSet.getString("storeAreaName"), resultSet.getInt("storeCityId"),
				 * resultSet.getString("storeCityName"), resultSet.getString("storeLandmark"));
				 * LocalStoreList.add(localStore);
				 */
			    }
				
			}catch(Exception e){
				System.out.println("failed at getStoreList-->"+e.getMessage());
			}finally{
				close();
			}
			return LocalStoreList;
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
