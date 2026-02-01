package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prami.db.MySQLAccess;

public class BrandDao {
	CallableStatement stmt = null;
	ResultSet resultSet = null;
	Connection connect = null;
	PreparedStatement pst = null;
	MySQLAccess dao = new MySQLAccess();
	
	public List<String> getAllAvailableBrands(){
		
		List<String> brandList = new ArrayList<String>();
		try{
			connect = dao.getConnection();
			String productQuery = "{call sp_get_all_available_brands()}";
			stmt = connect.prepareCall(productQuery);
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	brandList.add(resultSet.getString("productBrand"));
		    }
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Brand DAO-> in getAllAvailableBrands()==>"+e.getMessage());
		}finally{
			close();
		}
		return brandList;
	}
	private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }
	      if (stmt != null) {
		        stmt.close();
		      }
	      if (pst != null) {
		        pst.close();
		      }
	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }
}
