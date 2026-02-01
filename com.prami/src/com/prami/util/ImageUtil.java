package com.prami.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.Image;
import com.prami.db.MySQLAccess;

public class ImageUtil {
	
	ResultSet resultSet = null;
	Connection connect = null;
	CallableStatement statement = null;
	MySQLAccess dao = new MySQLAccess();
	 
	 public List<Image> getProductImages(int productId) {
		 List<Image> imageList = new ArrayList<Image>();
			try{
				connect = dao.getConnection();
				String query = "{call sp_get_product_images(?)}";

				CallableStatement stmt = connect.prepareCall(query);
				stmt.setInt(1, productId);
				resultSet = stmt.executeQuery();
		    	 while (resultSet.next()) {
		    		 Image image = new Image(resultSet.getInt("imgID"), resultSet.getInt("productID"), resultSet.getString("imgURL"), resultSet.getInt("imageType"));
		    		 imageList.add(image);
		    	 }
		    	
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at ActivityDAO --> getProductViews()-->"+e.getMessage());
				
			}finally{
				close();
			}
			return imageList;
	    }
	 public String getSeoProductImages(int productId) {
		 String image = "";
			try{
				connect = dao.getConnection();
				String query = "{call sp_get_product_images(?)}";

				CallableStatement stmt = connect.prepareCall(query);
				stmt.setInt(1, productId);
				resultSet = stmt.executeQuery();
		    	 while (resultSet.next()) {
		    		 if(resultSet.getInt("imageType") == 1){
		    			 image = resultSet.getString("imgURL");
		    		 }
		    	 }
		    	
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at ActivityDAO --> getSeoProductImages()-->"+e.getMessage());
				
			}finally{
				close();
			}
			return image;
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
