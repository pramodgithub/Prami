package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.Affiliate;
import com.prami.bean.Image;
import com.prami.bean.MainProduct;
import com.prami.bean.ProductSize;
import com.prami.bean.SimilarProduct;
import com.prami.db.MySQLAccess;
import com.prami.util.ImageUtil;

public class SimilarProductDao {
	CallableStatement statement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	MySQLAccess dao = new MySQLAccess();
	 
	 
	 public MainProduct getSimilarProductList(int productID){
		 	SimilarProduct similarProduct = null;
			
			ImageUtil image = new ImageUtil();
			Affiliate affiliate = null;
			List<SimilarProduct> similarProductsList = new ArrayList<SimilarProduct>(); 
			
			MainProduct mainProduct = null;
			SizeDao sizes = new SizeDao();
			
			try{
				connect = dao.getConnection();
				String query = "{call sp_get_similar_products(?)}";

				statement = connect.prepareCall(query);
				statement.setInt(1, productID);
				resultSet = statement.executeQuery();
			   
			    while (resultSet.next()) {
			    		
			    		if(resultSet.getString("originalURL").equals(resultSet.getString("productURL"))){
			    			affiliate = new Affiliate(false, 10);
			    		}else{
			    			affiliate = new Affiliate(true, 10);
			    		}
			    		similarProduct = new SimilarProduct(resultSet.getInt("productID"),resultSet.getInt("storeID"), resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"),resultSet.getString("productColor"), resultSet.getString("storeName"),resultSet.getString("storeDesc"), affiliate);
			    		similarProductsList.add(similarProduct);
			    }
			    List<Image> imageList = image.getProductImages(productID);
			    int views = 0 ;//activity.getProductViews(productID);
			    List<ProductSize> productSizesList = sizes.getSizesByProdId(productID);
			    mainProduct =  new MainProduct(similarProductsList, imageList, productSizesList, views);
		    	
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at Similar Product DAO getSimilarProductList"+e.getMessage());
			}finally{
				close();
			}
			return mainProduct;
		}
	 
	 public List<SimilarProduct> getImagePathSimilarityCheck(int collectionId){
			List<SimilarProduct> similarityProductList = new ArrayList<SimilarProduct>();
			SimilarProduct similarProduct;
			try{
				connect = dao.getConnection();
				String query = "{call sp_get_image_path_similarity_check(?)}";
				statement = connect.prepareCall(query);
				statement.setInt(1, collectionId);
				resultSet = statement.executeQuery();
			    while (resultSet.next()) {
			    	similarProduct =  new SimilarProduct(resultSet.getInt("productID"), resultSet.getInt("storeID"), resultSet.getString("productName"), resultSet.getString("imgURL"), resultSet.getFloat("productPrice"),resultSet.getInt("similarityCheck"));
			    	similarityProductList.add(similarProduct);
			    }
				
			}catch(Exception e){
				System.out.println("failed at getAllCollectionList"+e.getMessage());
			}finally{
				close();
			}
			return similarityProductList;
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
