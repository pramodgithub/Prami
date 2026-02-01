package com.prami.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.Image;
import com.prami.bean.Product;
import com.prami.bean.Structure;
import com.prami.db.MySQLAccess;

public class StructureUtil {
	Statement statement = null;
	Statement statement2 = null;
	ResultSet resultSet = null;
	ResultSet imageResultSet = null;
	
	Connection connect = null;
	PreparedStatement pst = null;
	MySQLAccess dao = new MySQLAccess();
		
	public List<Structure> getPagePattern(int patternId) {
		
		List<Structure> pagePatternList= new ArrayList<>();
		int i=0;
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT patternPageColumn,patternBoxColumn,patternBoxProductCount FROM patterntable where patternType="+patternId+" order by patternSequence asc");
		    resultSet = statement.getResultSet();
		    
		    while (resultSet.next()) {
		    			    	
		    	Structure structure = new Structure(resultSet.getInt("patternPageColumn"),resultSet.getInt("patternBoxColumn"),resultSet.getInt("patternBoxProductCount"));
		    	
		    	pagePatternList.add(i,structure);
		    	i++;
		    }
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at StructureUtil --> getPagePattern()-->"+e.getMessage());
			
		}finally{
			close();
		}
		return pagePatternList;
    }
public List<Product> getRecentProductsNotIn(int structureBoxId,String existingProducts,int limit) {
		List<Product> recentProductList= new ArrayList<>();
		existingProducts = existingProducts.replaceAll(",$", "");
		existingProducts = (existingProducts!="")?existingProducts:"0";
		
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
			statement2 = connect.createStatement();
		    statement.executeQuery("select * from producttbl where  subCatId="+structureBoxId+" and productStatus=1 and productID not in ("+existingProducts+") order by productDate desc limit 0,"+limit+"");
		    
		    resultSet = statement.getResultSet();
		    
		    while (resultSet.next()) {
		    	List<Image> imageList = new ArrayList<Image>();
		    	imageResultSet = statement2.executeQuery("select * from imagetbl where productID="+resultSet.getInt("productID")+" and imgStatus=1");
		    	 while (imageResultSet.next()){
		    		 
		    		 Image image = new Image(imageResultSet.getInt("imgID"), imageResultSet.getInt("productID"), imageResultSet.getString("imgURL"), imageResultSet.getInt("imageType"));
		    		 imageList.add(image);
		    	 }
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"), imageList);
		    	recentProductList.add(product);
		    	
		    }
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at StructureUtil --> getRecentProductsNotIn()-->"+e.getMessage());
			
		}finally{
			close();
		}
		return recentProductList;
    }
	 private void close() {
		    try {
		      if (resultSet != null) {
		        resultSet.close();
		      }
		      if (imageResultSet != null) {
		    	  imageResultSet.close();
			  }
		      if (statement != null) {
		        statement.close();
		      }
		      if (statement2 != null) {
			        statement2.close();
			  }
		      if (pst != null) {
			        pst.close();
			      }
		      if (connect != null) {
		        connect.close();
		      }
		      if (pst != null) {
			        pst.close();
			  }
		    } catch (Exception e) {

		    }
		  }
}
