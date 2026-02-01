package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.PriceHistory;
import com.prami.db.MySQLAccess;

public class PriceHistoryDao {
	ResultSet resultSet = null;
	Connection connect = null;
	CallableStatement statement = null;
	MySQLAccess dao = new MySQLAccess();
	public List<PriceHistory> getProductPriceHistory(int productId) {
		
		String query;
		List<Integer> price = new ArrayList<Integer>();
		List<PriceHistory> priceHistoryList = new ArrayList<PriceHistory>();
		try{
			connect = dao.getConnection();
			
				query = "{call sp_get_product_update_data_by_ID(?)}";
				statement = connect.prepareCall(query);
				statement.setInt(1, productId);
				
				
			resultSet = statement.executeQuery();
		    
			
			
			while (resultSet.next()) {
				PriceHistory history = new PriceHistory(resultSet.getInt("updateInfo"), resultSet.getString("logTypeName"), resultSet.getString("readableDate"), resultSet.getString("updateType"));
				priceHistoryList.add(history);
				price.add(resultSet.getInt("updateInfo"));
		    }
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at PriceHistoryDao --> getProductPriceHistory()"+e.getMessage());
			
		}finally{
			close();
		}
		return priceHistoryList;
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
