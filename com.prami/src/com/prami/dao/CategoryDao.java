package com.prami.dao;

import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import com.prami.bean.Category;
import com.prami.bean.CategoryType;
import com.prami.bean.Collection;
import com.prami.bean.CollectionHistory;
import com.prami.bean.OnlineStore;
import com.prami.bean.ProductType;
import com.prami.bean.SimilarityCheck;
import com.prami.db.MySQLAccess;

public class CategoryDao {
	Statement statement = null;
	Statement statement2 = null;
	ResultSet resultSet = null;
	ResultSet resultSet2 = null;
	Connection connect = null;
	CallableStatement stmt = null;
	MySQLAccess dao = new MySQLAccess();
	
	public List<Category> getCategoryList(){
		List<Category> categoryList = new ArrayList<Category>();
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
		    resultSet = statement.executeQuery("select * from categorytbl where catStatus=1");
		    
		    while (resultSet.next()) {
		    	Category category = new Category(resultSet.getInt("catID"), resultSet.getString("catName"), resultSet.getString("catImage"),resultSet.getInt("categoryPagePattern"));
		       	categoryList.add(category);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getCategorylist"+e.getMessage());
		}finally{
			close();
		}
		return categoryList;
	}
	public List<Category> getCategoryListBySelection(int prodID){
		List<Category> selectedCatList = new ArrayList<Category>(); 
		
		int isSelected = 0;
		int catID;
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			resultSet = statement.executeQuery("select * from categorytbl where catStatus=1");
		    resultSet2 = statement2.executeQuery("select catID from producttbl where productID="+prodID);
		    resultSet2.next();
		    catID = resultSet2.getInt(1);
		    while (resultSet.next()) {
		    	isSelected = resultSet.getInt("catID")==catID?1:0;
		    	Category category = new Category(resultSet.getInt("catID"), resultSet.getString("catName"),resultSet.getInt("categoryPagePattern"),isSelected);
		    	selectedCatList.add(category);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getCategoryListBySelection - "+e.getMessage());
		}finally{
			close();
		}
		return selectedCatList;
	}
	public List<CategoryType> getProductTypeBySelection(int prodID){
		List<CategoryType> typeList = new ArrayList<CategoryType>(); 
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select prodTypeID,prodTypeName,prodTypeImage from producttypetbl where prodTypeID in(SELECT DISTINCT(typeId) FROM producttypemapping  where status=1 and productId in (select productId from producttbl  where productStatus=1 and SubCatID in (select SubCatID from producttbl where productID="+prodID+")))");
		
			while (resultSet.next()) {
		    	
				CategoryType type = new CategoryType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("prodTypeImage"));
				typeList.add(type);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getProductTypeBySelection - "+e.getMessage());
		}finally{
			close();
		}
		return typeList;
	}
	public List<CategoryType> getProductTypesByProductType(int productType){
		List<CategoryType> typeList = new ArrayList<CategoryType>(); 
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select prodTypeID,prodTypeName,prodTypeImage from producttypetbl where prodTypeID in(SELECT DISTINCT(typeId) FROM producttypemapping  where typeId="+productType+" and status=1)");
		
			while (resultSet.next()) {
		    	
				CategoryType type = new CategoryType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("prodTypeImage"));
				typeList.add(type);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getProductTypeBySelection - "+e.getMessage());
		}finally{
			close();
		}
		return typeList;
	}
	public List<CategoryType> getProductTypeByCollection(int collectionID){
		List<CategoryType> typeList = new ArrayList<CategoryType>(); 
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select prodTypeID,prodTypeName,prodTypeImage from producttypetbl where prodTypeID in(SELECT DISTINCT(typeId) FROM producttypemapping  where status=1 and productId in (select productId from producttbl  where productStatus=1 and SubCatID = "+collectionID+"))");
		
			while (resultSet.next()) {
		    	
				CategoryType type = new CategoryType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("prodTypeImage"));
				typeList.add(type);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getProductTypeByCollection - "+e.getMessage());
		}finally{
			close();
		}
		return typeList;
	}
	
	public List<CategoryType> getProductTypeByTypeSelection(int typeID, int collId, int prodId){
		List<CategoryType> typeList = new ArrayList<CategoryType>(); 
		ProductDao p = new ProductDao();
		
		if(collId<=0){
			collId = p.getCollectionIdFromProduct(prodId);
		}
		try{
			String productQuery="";
			connect = dao.getConnection();
			if(typeID>0){
				productQuery = "{call sp_get_product_types_by_typeid(?)}";
				stmt = connect.prepareCall(productQuery);
				stmt.setInt(1, typeID);
			}else{
				productQuery = "{call sp_get_product_types_by_collectionid(?)}";
				stmt = connect.prepareCall(productQuery);
				stmt.setInt(1, collId);
			}
			
			resultSet = stmt.executeQuery();
		
			while (resultSet.next()) {
		    	
				CategoryType type = new CategoryType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("prodTypeImage"));
				typeList.add(type);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getProductTypeByCollection - "+e.getMessage());
		}finally{
			close();
		}
		return typeList;
	}
	
	
	public String getCollectionName(int collectionID){
		String collectionName = null;
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select subcatName  FROM subcategorytbl where SubCatID="+collectionID);
		
			while (resultSet.next()) {
		    	
				collectionName = resultSet.getString("subcatName");
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getCollectionName - "+e.getMessage());
		}finally{
			close();
		}
		return collectionName;
	}
	
	public List<CategoryType> getProductTypeBySearch(String searchKey){
		searchKey = searchKey.trim().replaceAll("\\s{2,}", " ");
		List<CategoryType> typeList = new ArrayList<CategoryType>(); 
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			String searchParam = "";
			String key[] = URLDecoder.decode(searchKey, "UTF-8").split("\\s",0);
			for(String s:key){
				searchParam += "+"+s+"* ";
			}
			resultSet = statement.executeQuery("select prodTypeID,prodTypeName,prodTypeImage from producttypetbl where prodTypeID in(SELECT DISTINCT(typeId) FROM producttypemapping  where  status=1 and productId in (select productId from producttbl  where productStatus=1 and MATCH (productName) AGAINST ('"+searchParam+"' IN BOOLEAN MODE)))");
		    
			while (resultSet.next()) {
		    	
				CategoryType type = new CategoryType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("prodTypeImage"));
				typeList.add(type);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getProductTypeBySelection - "+e.getMessage());
		}finally{
			close();
		}
		return typeList;
	}
	public List<CategoryType> getProductTypeBySearchType(int typeId, String searchKey){
		searchKey = searchKey.trim().replaceAll("\\s{2,}", " ");
		List<CategoryType> typeList = new ArrayList<CategoryType>(); 
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			String searchParam = "";
			String sql="";
			String key[] = URLDecoder.decode(searchKey, "UTF-8").split("\\s",0);
			for(String s:key){
				searchParam += "+"+s+"* ";
			}
			if(typeId>0){
				sql= "select prodTypeID,prodTypeName,prodTypeImage from producttypetbl where prodTypeID in(SELECT DISTINCT(typeId) FROM producttypemapping  where status=1 and productId in (SELECT pm.productId FROM producttypemapping pm inner join producttbl p on p.productId=pm.productId where pm.typeId="+typeId+" and p.productStatus=1 and MATCH (p.productName) AGAINST ('"+searchParam+"' IN BOOLEAN MODE)))";
			}else{
				sql = "select prodTypeID,prodTypeName,prodTypeImage from producttypetbl where prodTypeID in(SELECT DISTINCT(typeId) FROM producttypemapping  where status=1 and productId in (select productId from producttbl  where productStatus=1 and MATCH (productName) AGAINST ('"+searchParam+"' IN BOOLEAN MODE)))";
			}
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
		    	
				CategoryType type = new CategoryType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("prodTypeImage"));
				typeList.add(type);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getProductTypeBySearchType - "+e.getMessage());
		}finally{
			close();
		}
		return typeList;
	}
	public List<Category> getCollectionCategories(int catID){
		List<Category> selectedCatList = new ArrayList<Category>(); 
		
		int isSelected = 0;
		
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
			
			resultSet = statement.executeQuery("select * from categorytbl where catStatus=1");
		   
		   
		    while (resultSet.next()) {
		    	isSelected = resultSet.getInt("catID")==catID?1:0;
		    	Category category = new Category(resultSet.getInt("catID"), resultSet.getString("catName"), isSelected);
		    	selectedCatList.add(category);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getCollectionCategories - "+e.getMessage());
		}finally{
			close();
		}
		return selectedCatList;
	}
	
	public List<Category> getThemeCategories(int subCatID){
		List<Category> selectedCatList = new ArrayList<Category>(); 
			
			int isSelected = 0;
			int catID;
			try{
				connect = dao.getConnection();
				 
				statement = connect.createStatement();
				statement2 = connect.createStatement();
				resultSet = statement.executeQuery("select * from categorytbl where catStatus=1");
			    resultSet2 = statement2.executeQuery("select catID  FROM subcategorytbl where SubCatID="+subCatID);
			    resultSet2.next();
			    catID = resultSet2.getInt(1);
			    while (resultSet.next()) {
			    	isSelected = resultSet.getInt("catID")==catID?1:0;
			    	Category category = new Category(resultSet.getInt("catID"), resultSet.getString("catName"),resultSet.getInt("categoryPagePattern"), isSelected);
			    	selectedCatList.add(category);
			    }
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at CategoryDAO --> getCategoryListBySelection - "+e.getMessage());
			}finally{
				close();
			}
			return selectedCatList;
	}
	
	public List<Collection> getCollectionResource(int catID){
		List<Collection> collectionList = new ArrayList<Collection>(); 
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM subcategorytbl where catID="+catID+" and subcatStatus=1");
		    while (resultSet.next()) {
		    	Collection collection = new Collection(resultSet.getInt("subcatID"), resultSet.getString("subcatName"), resultSet.getString("subcatTitle"), resultSet.getString("subcatDescription"), resultSet.getString("subcatImage"));
		    	collectionList.add(collection);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CategoryDAO --> getCollectionCategories - "+e.getMessage());
		}finally{
			close();
		}
		return collectionList;
	}
	public OnlineStore getStore(int storeID){
		OnlineStore store = null;
		
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT * FROM storemapping sm join storetbl s on  sm.storeId = s.storeID where sm.storeId="+storeID+"");
		    resultSet = statement.getResultSet();
		    
		    if(resultSet.next()){
		    	store = new OnlineStore(resultSet.getInt("storeId"), resultSet.getString("storeName"),resultSet.getInt("propertyPoints"), resultSet.getString("storeDesc"));
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> getStore()"+e.getMessage());
			
		}finally{
			close();
		}
		
		return store;
    }
	public List<Collection> getCollectionList(int reqCategoryID){
		List<Collection> collectionList = new ArrayList<Collection>();
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
		    resultSet = statement.executeQuery("select * from subcategorytbl where catID="+reqCategoryID+" and subCatStatus=1");
		    
		    while (resultSet.next()) {
		    	Collection subCategory = new Collection(resultSet.getInt("subCatID"), resultSet.getString("SubCatName"));
		    				
		    	collectionList.add(subCategory);
		    }
			
		}catch(Exception e){
			System.out.println("failed at getSubCategorylist"+e.getMessage());
		}finally{
			close();
		}
		return collectionList;
	}
	
	public List<Collection> getSearchedCollectionList(String searchKey){
		searchKey = searchKey.replaceAll("[^a-zA-Z0-9_ ]", "");
		searchKey = searchKey.trim().replaceAll("\\s{2,}", " ");
		List<Collection> collectionList = new ArrayList<Collection>();
		Collection collection;
		String searchParam = "";
		try{
			String key[] = URLDecoder.decode(searchKey, "UTF-8").split("\\s",0);
			for(String s:key){
				searchParam += "+"+s+"* ";
			}
			connect = dao.getConnection();
			String query = "{call sp_get_searched_collection(?)}";
			stmt = connect.prepareCall(query);
			stmt.setString(1, searchParam);
			
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	collection = new Collection(resultSet.getInt("subcatID"), resultSet.getString("subcatName"), resultSet.getString("subcatImage"));
		    	collectionList.add(collection);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at getSearchedCollectionList==>"+e.getMessage());
		}finally{
			close();
		}
		return collectionList;
	}
	
	public CollectionHistory getCollectionHistoryByToken(String token){
		
		CollectionHistory history = new CollectionHistory();
		
		try{
			
			connect = dao.getConnection();
			String query = "{call sp_get_collection_history_by_token(?)}";
			stmt = connect.prepareCall(query);
			stmt.setString(1, token);
			
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	history = new CollectionHistory(resultSet.getInt("idcollectionHistory"), resultSet.getString("token"), resultSet.getString("collectionUrl"), resultSet.getInt("storeId"), resultSet.getInt("readCount"), resultSet.getInt("catId"), resultSet.getInt("subCatId"), resultSet.getString("byUser"), resultSet.getString("addedTime"), resultSet.getInt("status"));
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at getCollectionHistoryByToken==>"+e.getMessage());
		}finally{
			close();
		}
		return history;
	}
	
	
	public List<ProductType> getSearchedTypesInCollectionList(String searchKey, int collectionId){
		searchKey = searchKey.replaceAll("[^a-zA-Z0-9_ ]", "");
		searchKey = searchKey.trim().replaceAll("\\s{2,}", " ");
		List<ProductType> productTypeList = new ArrayList<ProductType>();
		String searchParam = "";
		try{
			String key[] = URLDecoder.decode(searchKey, "UTF-8").split("\\s",0);
			for(String s:key){
				searchParam += "+"+s+"* ";
			}
			connect = dao.getConnection();
			String query = "{call sp_get_searched_types_in_collection(?,?)}";
			stmt = connect.prepareCall(query);
			stmt.setString(1, searchParam);
			stmt.setInt(2, collectionId);
			
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	ProductType productType = new ProductType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("prodTypeIcon"),resultSet.getString("prodTypeImage"));	
		    	productTypeList.add(productType);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at getSearchedTypesInCollectionList==>"+e.getMessage());
		}finally{
			close();
		}
		return productTypeList;
	}
	
	public List<SimilarityCheck> getSimilarityCheckList(){
		List<SimilarityCheck> similarityCheckList = new ArrayList<SimilarityCheck>();
		SimilarityCheck similarityCheck;
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_similarity_check_list()}";
			stmt = connect.prepareCall(query);
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	similarityCheck = new SimilarityCheck(resultSet.getInt("subcatID"), resultSet.getString("subcatName"), resultSet.getInt("toCheck"));
		    	similarityCheckList.add(similarityCheck);
		    }
			
		}catch(Exception e){
			System.out.println("failed at getAllCollectionList"+e.getMessage());
		}finally{
			close();
		}
		return similarityCheckList;
	}
	
	private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }
	      if (resultSet2 != null) {
		        resultSet2.close();
		      }
	      if (statement != null) {
	        statement.close();
	      }
	      if (stmt != null) {
		        stmt.close();
		  }
	      if (statement2 != null) {
		        statement2.close();
		  }
	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }
}
