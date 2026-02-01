package com.prami.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.prami.bean.Category;
import com.prami.bean.ProductType;
import com.prami.bean.OnlineStore;
import com.prami.bean.SubCategory;
import com.prami.db.MySQLAccess;

public class NameUtil {
	Statement statement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	MySQLAccess dao = new MySQLAccess();
	
	public Category getCategory(int catID){
		Category category = new Category();
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
		    resultSet = statement.executeQuery("select * from categorytbl where catID="+catID);
		    
		    while (resultSet.next()) {
		    	category = new Category(resultSet.getInt("catID"), resultSet.getString("catName"), resultSet.getString("catImage"));	
		    	
		    }
			
		}catch(Exception e){
			System.out.println("failed at nameUtils"+e.getMessage());
		}finally{
			close();
		}
		return category;
	}
	
	public SubCategory getSubCategory(int subCatID){
		SubCategory subCategory = new SubCategory();
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
		    resultSet = statement.executeQuery("select * from subcategorytbl where subCatID="+subCatID);
		    
		    while (resultSet.next()) {
		    	subCategory = new SubCategory(resultSet.getInt("subCatID"), resultSet.getString("SubCatName"), resultSet.getString("subCatImage"));		
		    	
		    }
			
		}catch(Exception e){
			System.out.println("failed at nameUtils"+e.getMessage());
		}finally{
			close();
		}
		return subCategory;
	}
	
	public ProductType getProductType(int productTypeID){
		ProductType productType = new ProductType();
		try{
			
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
		    resultSet = statement.executeQuery("select * from producttypetbl where prodTypeID="+productTypeID);
		   
		    while (resultSet.next()) {
		    	productType = new ProductType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("prodTypeIcon"),resultSet.getString("prodTypeImage"));		
		    	
		    }
			
		}catch(Exception e){
			System.out.println("failed at nameUtils"+e.getMessage());
		}finally{
			close();
		}
		return productType;
	}
	public OnlineStore getStore(int storeID){
		OnlineStore store = new OnlineStore();
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
		    resultSet = statement.executeQuery("select * from storetbl where storeID="+storeID);
		    
		    while (resultSet.next()) {
		    	store = new OnlineStore(resultSet.getInt("storeID"), resultSet.getString("storeName"), resultSet.getString("storeLogo"));		
		    	
		    }
			
		}catch(Exception e){
			System.out.println("failed at nameUtils"+e.getMessage());
		}finally{
			close();
		}
		return store;
	}
	public boolean getFirstResultsetCount(String token,int page,int limit){
		
		boolean done = false;
		int rowCount = 0;
		int interrupt = 0 ;
		try{
			connect = dao.getConnection();
			statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,  ResultSet.CONCUR_READ_ONLY);
			while(!done){
				resultSet = statement.executeQuery("SELECT count(*) FROM temp_collection where token='"+token+"'");
				
				resultSet.next();
				rowCount = resultSet.getInt(1);
				
			    if(rowCount>=limit){
			    	
			       done = true;
			    }else{
			    	
			       Thread.sleep(5000);  // sleep 5 second.
			       interrupt = interrupt+5000;
			       if(interrupt>300000){
			    	   done=true;
			       }
			    }
			}
		}catch(Exception e){
			System.out.println("failed at getFirstResultsetCount()-->"+e.getMessage());
		}finally{
			close();
		}
		return done;
		
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
