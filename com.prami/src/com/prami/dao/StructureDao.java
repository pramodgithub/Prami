package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.Collection;
import com.prami.bean.LandingStructure;
import com.prami.bean.Product;
import com.prami.bean.SubCategory;
import com.prami.db.MySQLAccess;

public class StructureDao {
	
	ResultSet resultSet = null;
	Connection connect = null;
	CallableStatement statement = null;
	MySQLAccess dao = new MySQLAccess();
	public List<SubCategory> getProductStructure(int groupId,  int box, int limit) {
		
		List<SubCategory> collectionList= new ArrayList<>();
		SubCategory collection = new SubCategory();
		
		String query;
		int tempId = 0,isCollection = 0;
		try{
			connect = dao.getConnection();
				query = "{call sp_get_collection_products_by_group(?, ?, ?)}";
				statement = connect.prepareCall(query);
				statement.setInt(1, groupId);
				statement.setInt(2, box);
				statement.setInt(3, limit);
						
			
			resultSet = statement.executeQuery();
		    //List<Structure> page = pattern.getPagePattern(patternId);
			List<Product> productList = new ArrayList<Product>();
			while (resultSet.next()) {
				
				Product product = new Product();
				

				if(tempId != resultSet.getInt("subCatID") && tempId !=0) {
					 
					
					collection.setProducts(productList);
					collectionList.add(collection);
					productList = new ArrayList<Product>();
					collection = new SubCategory();
					isCollection = 0;
				}
				
				
				collection.setSubCatID(resultSet.getInt("subCatID"));
				collection.setName(resultSet.getString("subcatName"));
				collection.setTitle(resultSet.getString("subcatTitle"));
				collection.setImage(resultSet.getString("subcatImage"));
				
				product.setIntProductID(resultSet.getInt("productID"));
				product.setStrProductName(resultSet.getString("productName"));
				product.setStrProductURL(resultSet.getString("imgURL"));
				product.setStrProductBrand(resultSet.getString("productBrand"));
				
				productList.add(product);
				
				isCollection = 1;
				tempId = resultSet.getInt("subCatID");
				
		    }
			if(isCollection == 1) {
		    	collection.setProducts(productList);
				collectionList.add(collection);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at StructureDAO --> getProductStructure()"+e.getMessage());
			
		}finally{
			close();
		}
		return collectionList;
    }
	
public List<SubCategory> getProductStructureByStore(int groupId, int storeId, int box, int limit) {
		
		List<SubCategory> collectionList= new ArrayList<>();
		SubCategory collection = new SubCategory();
		
		String query;
		int tempId = 0, isCollection = 0;
		try{
			connect = dao.getConnection();
			if(groupId == 0) {
				query = "{call sp_get_collection_products_by_store(?, ?, ?, ?)}";
			}else {
				query = "{call sp_get_collection_products_by_group_store(?, ?, ?, ?)}";
			}
				statement = connect.prepareCall(query);
				statement.setInt(1, groupId);
				statement.setInt(2, storeId);
				statement.setInt(3, box);
				statement.setInt(4, limit);
						
			
			resultSet = statement.executeQuery();
		    //List<Structure> page = pattern.getPagePattern(patternId);
			List<Product> productList = new ArrayList<Product>();
			while (resultSet.next()) {
				
				Product product = new Product();
				
							
				if(tempId != resultSet.getInt("subCatID") && tempId !=0) {
					 					
					collection.setProducts(productList);
					collectionList.add(collection);
					productList = new ArrayList<Product>();
					collection = new SubCategory();
					isCollection = 0;
				}
				
				
				collection.setSubCatID(resultSet.getInt("subCatID"));
				collection.setName(resultSet.getString("subcatName"));
				collection.setTitle(resultSet.getString("subcatTitle"));
				collection.setImage(resultSet.getString("subcatImage"));
				
				product.setIntProductID(resultSet.getInt("productID"));
				product.setStrProductName(resultSet.getString("productName"));
				product.setStrProductURL(resultSet.getString("imgURL"));
				product.setStrProductBrand(resultSet.getString("productBrand"));
				
				productList.add(product);
				
				isCollection = 1;
				tempId = resultSet.getInt("subCatID");
				
		    }
		    if(isCollection == 1) {
		    	collection.setProducts(productList);
				collectionList.add(collection);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at StructureDAO --> getProductStructure()"+e.getMessage());
			
		}finally{
			close();
		}
		return collectionList;
    }
	
	public List<LandingStructure> getlandingPageData(int nextPage)  {
		List<LandingStructure> pageStructureList= new ArrayList<>();
		
		LandingStructure structure = new LandingStructure();
		CollectionDao collections = new CollectionDao();
		
		String query;
		int groupId, groupPattern;
		try{
			connect = dao.getConnection();
			query = "{call sp_get_all_groups(?)}";

			statement = connect.prepareCall(query);
			statement.setInt(1, nextPage);
			
			resultSet = statement.executeQuery();
				
			while (resultSet.next()){
				groupId = resultSet.getInt("collectionGroupID");
				groupPattern = resultSet.getInt("collectiongroupPattern");
				List<Collection> collectionGroupList = collections.getAllGroups(groupId, groupPattern);
				structure = new LandingStructure(resultSet.getInt("collectionGroupID"), resultSet.getString("collectionGroupName"), resultSet.getString("collectionGroupImage"), collectionGroupList);
				pageStructureList.add(structure);
			}
		}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at StructureDAO --> landingPageResultSet()"+e.getMessage());

		}finally{
			close();
		}
		return pageStructureList;
	}
	
	
/*public List<LandingStructure> getlandingPageData(int patternId) {
		
		List<LandingStructure> pageStructureList= new ArrayList<>();
		StructureUtil pattern = new StructureUtil();
		String query;
		int groupId = -1,collectionGroupId;
		try{
			connect = dao.getConnection();
			query = "{call sp_get_featured_page_structure_list()}";
			
			statement = connect.prepareCall(query);
			resultSet = statement.executeQuery();

			List<Collection> collectionList = new ArrayList<>();
			LandingStructure structure = new LandingStructure();
			while (resultSet.next()) {
				
				Collection collections = new Collection(resultSet.getInt("subCatId"), resultSet.getString("subcatName"), resultSet.getString("subcatTitle"), resultSet.getString("subcatDescription"), resultSet.getString("subcatImage"), 1);
				collectionList.add(collections);
				groupId = resultSet.getInt("collectionGroupID");
				resultSet.next();
				collectionGroupId = resultSet.getInt("collectionGroupID");
				
				if(groupId != collectionGroupId){
						groupId = collectionGroupId;
						resultSet.previous();
						structure = new LandingStructure(resultSet.getInt("collectionGroupID"), resultSet.getString("collectionGroupName"), resultSet.getString("collectionGroupImage"), resultSet.getString("catName"), collectionList);
						pageStructureList.add(structure);
						collectionList = new ArrayList<>();
				}
				
				 page = pattern.getPagePattern(resultSet.getInt("collectiongroupPattern"));
				
				Collection collections = new Collection(resultSet.getInt("subCatId"), resultSet.getInt("collectionGroupID"), resultSet.getString("subcatName"), resultSet.getString("subcatTitle"), resultSet.getString("subcatDescription"), resultSet.getString("subcatImage"), 1);
				collectionList.add(collections);
				
		    	//SubCategory subCat = new SubCategory(resultSet.getInt("catID"), resultSet.getString("subcatName"), resultSet.getString("subcatTitle"), resultSet.getString("subcatDescription"), resultSet.getString("subcatImage"), resultSet.getInt("collectionGroupID"), resultSet.getString("catName"), resultSet.getString("collectionGroupName"), resultSet.getString("collectionGroupImage"));		    	
		    	//Structure structure = new Structure(patternId, resultSet.getInt("sequence"), resultSet.getInt("subCatId"), page.get(patternIndex).getPageColumn(), page.get(patternIndex).getProdCount(), page.get(patternIndex).getProdColumns(), subCat,  resultSet.getInt("total"));
				
				
		    }
			resultSet.beforeFirst();
			while (resultSet.next()) {
				Collection collections = new Collection(resultSet.getInt("subCatId"), resultSet.getInt("collectionGroupID"), resultSet.getString("subcatName"), resultSet.getString("subcatTitle"), resultSet.getString("subcatDescription"), resultSet.getString("subcatImage"), 1);
				collectionList.add(collections);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at StructureDAO --> getlandingPageData()"+e.getMessage());
			
		}finally{
			close();
		}
		return pageStructureList;
    }*/
	
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
