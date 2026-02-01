package com.prami.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.LocalSocial;
import com.prami.bean.LocalStoreServices;
import com.prami.db.MySQLAccess;

public class LocalStoreUtil {
	private CallableStatement statement = null;
	private ResultSet resultSet = null;
	private Connection connect = null;
	private MySQLAccess dao = new MySQLAccess();

	
	
	public List<LocalStoreServices> getStoreServices(int storeId){
		LocalStoreServices localStoreServices = new LocalStoreServices();
		List<LocalStoreServices> LocalStoreServicesList = new ArrayList<LocalStoreServices>();
		try{
			connect = dao.getConnection();
			String storeServices = "{call sp_get_loc_store_services(?)}";
			statement = connect.prepareCall(storeServices);
			statement.setInt(1, storeId);
			resultSet = statement.executeQuery();
			
		    while (resultSet.next()) {
		    	
		    		localStoreServices = new LocalStoreServices(resultSet.getInt("serviceId"), resultSet.getString("servicesName"), resultSet.getString("servicesDescription"), resultSet.getString("servicesImage"), resultSet.getInt("isServicesCustomizable"), resultSet.getString("servicesPriceRange"));
		    		LocalStoreServicesList.add(localStoreServices);

		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at LocalStoreUtil in getStoreServices() -->"+e.getMessage());
		}finally{
			close();
		}
		return LocalStoreServicesList;
	}
	public List<LocalSocial> getStoreSocial(int storeId){
		LocalSocial social = new LocalSocial();
		List<LocalSocial> LocalStoreSocialList = new ArrayList<LocalSocial>();
		try{
			connect = dao.getConnection();
			String storeSocial = "{call sp_get_loc_store_social(?)}";
			statement = connect.prepareCall(storeSocial);
			statement.setInt(1, storeId);
			resultSet = statement.executeQuery();
			
		    while (resultSet.next()) {
		    	
		    	social = new LocalSocial(resultSet.getInt("socialId"), resultSet.getString("socialUrl"), resultSet.getString("socialName"), resultSet.getString("socialClass"));
		    	LocalStoreSocialList.add(social);

		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at LocalStoreUtil in getStoreSocial() -->"+e.getMessage());
		}finally{
			close();
		}
		return LocalStoreSocialList;
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
