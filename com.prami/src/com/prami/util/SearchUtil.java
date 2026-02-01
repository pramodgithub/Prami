package com.prami.util;

import java.sql.CallableStatement;
import java.sql.Connection;

import com.prami.db.MySQLAccess;

public class SearchUtil {
	private Connection connect = null;
	private CallableStatement stmt = null;
	private MySQLAccess dao = new MySQLAccess();
	
	public int insertSearchQuery(String userId, String searchQuery, int searchType , int localStoreArea){
		String searchTerm;
		int isUpdated = 0;
		long currTime = System.currentTimeMillis();
		
		try{
			connect = dao.getConnection();
								
			searchTerm = "{call sp_insert_search_query(?, ?, ?, ?, ?)}";
			stmt = connect.prepareCall(searchTerm);
			stmt.setString(1, userId);
			stmt.setString(2, searchQuery);
			stmt.setInt(3, searchType);
			stmt.setInt(4, localStoreArea);
			stmt.setLong(5, currTime);			
			isUpdated = stmt.executeUpdate();
						
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at SearchUtil --> insertSearchQuery-->"+e.getMessage());
			
		}finally{
			close();
		}
		return isUpdated;
	}
	private void close() {
	    try {
	      if (connect != null) {
	        connect.close();
	      }
	      if (stmt != null) {
				stmt.close();
			}
	    } catch (Exception e) {
	    	System.out.println("Failed to close==>"+e.getMessage());
	    }
	  }
}
