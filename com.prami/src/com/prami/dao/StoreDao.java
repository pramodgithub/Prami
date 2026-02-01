package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.CollectionCount;
import com.prami.bean.SimilarityCheck;
import com.prami.bean.OnlineStore;
import com.prami.bean.StoreProperties;
import com.prami.bean.UpdateStore;
import com.prami.db.MySQLAccess;

public class StoreDao {
	Statement statement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	MySQLAccess dao = new MySQLAccess();
	CallableStatement stmt = null;
	public List<OnlineStore> getOnlineStoreFromProdId(int prodID){
		ProductDao pd = new ProductDao();
		int storeID = pd.getStoreId(prodID);
		List<OnlineStore> storePropertyList = new ArrayList<OnlineStore>();
		
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT * FROM storemapping sm join storeproperties sp on sp.storePropertyId = sm.storePropertyId  where sm.storeId="+storeID+"");
		    resultSet = statement.getResultSet();
		    
		    while(resultSet.next()){
		    	OnlineStore store = new OnlineStore(resultSet.getString("storeProperty"),resultSet.getInt("propertyPoints"), resultSet.getString("storePropertyDesc"));
		    	storePropertyList.add(store);
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> getStoreFromProdId()"+e.getMessage());
			
		}finally{
			close();
		}
		
		return storePropertyList;
    }
	public List<StoreProperties> getStoreProperties(int storeID){
		List<StoreProperties> storePropertyList = new ArrayList<StoreProperties>();
		StoreProperties storeProperty;
		try{
			connect = dao.getConnection();
			
			String query = "{call sp_get_store_properties(?)}";

			stmt = connect.prepareCall(query);
			stmt.setInt(1, storeID);
			resultSet = stmt.executeQuery();
		    
		    while(resultSet.next()){
		    	storeProperty= new StoreProperties(resultSet.getString("storeProperty"), resultSet.getInt("propertyPoints"), resultSet.getString("storePropertyDesc"), resultSet.getString("storepropertyBadge"), resultSet.getString("storepropertyIcon"));
		    	storePropertyList.add(storeProperty);
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> getStoreFromProdId()"+e.getMessage());
			
		}finally{
			close();
		}
		
		return storePropertyList;
    }
	public List<OnlineStore> getStoreList(){
		
		List<OnlineStore> storeList = new ArrayList<OnlineStore>();
		
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("select * from storetbl where storeStatus=1");
		    resultSet = statement.getResultSet();
		    
		    while (resultSet.next()) {
		    	OnlineStore store = new OnlineStore(resultSet.getInt("storeID"), resultSet.getString("storeName"), resultSet.getString("storeLogo"), resultSet.getString("storeDesc"));
		    	storeList.add(store);
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> getStoreList()"+e.getMessage());
			
		}finally{
			close();
		}
		
		return storeList;
    }
	
	public OnlineStore getStoreInfo(int storeId){
		
		OnlineStore onlineStore = new OnlineStore();
		
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("select * from storetbl where storeStatus=1 and storeId="+storeId);
		    resultSet = statement.getResultSet();
		    
		    while (resultSet.next()) {
		    	onlineStore = new OnlineStore(resultSet.getInt("storeID"), resultSet.getString("storeName"), resultSet.getString("storeLogo"), resultSet.getString("storeDesc"));
		    	
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> getStoreList()"+e.getMessage());
			
		}finally{
			close();
		}
		
		return onlineStore;
    }
	
	public List<SimilarityCheck> getStoreProductCount(){
		List<SimilarityCheck> similarityCheckList = new ArrayList<SimilarityCheck>();
		SimilarityCheck similarityCheck;
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_store_product_count()}";
			stmt = connect.prepareCall(query);
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	similarityCheck = new SimilarityCheck(resultSet.getInt("storeID"), resultSet.getString("storeName"), resultSet.getInt("toCheck"));
		    	similarityCheckList.add(similarityCheck);
		    }
			
		}catch(Exception e){
			System.out.println("failed at getStoreProductCount-->"+e.getMessage());
		}finally{
			close();
		}
		return similarityCheckList;
	}
	public List<CollectionCount> getStoreCollectionCount(int storeId){
		List<CollectionCount> collectionList = new ArrayList<CollectionCount>();
		CollectionCount collectionCount;
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_store_collection_count(?)}";
			stmt = connect.prepareCall(query);
			stmt.setInt(1, storeId);
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	collectionCount =  new CollectionCount(resultSet.getInt("subcatID"), resultSet.getString("subcatName"), resultSet.getInt("catID"), resultSet.getString("catName"), resultSet.getInt("total"), resultSet.getInt("disabled"));
		    	collectionList.add(collectionCount);
		    }
			
		}catch(Exception e){
			System.out.println("failed at getStoreCollectionCount-->"+e.getMessage());
		}finally{
			close();
		}
		return collectionList;
	}
	public boolean updateStoreTokenLog(String token, List<Integer> storeIds){
		boolean success =false;
		long currTime = System.currentTimeMillis();
		try{
			connect = dao.getConnection();
			
			String sqlStatement = "INSERT INTO updatestore (updateToken, storeId, updateStoreStatus, updateTime ) VALUES ";
			for(int storeId:storeIds){
				
				sqlStatement += "('"+token+"',"+storeId+" ,1,"+currTime +"),";
			}
			
			statement = connect.prepareStatement(sqlStatement);
			statement.executeUpdate(sqlStatement.replaceAll(",$", ""));
			success = true;
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at StoreDao class in updateStoreTokenLog()-->"+e.getMessage());
			
		}finally{
			close();
		}
		return success;
	}
	public List<UpdateStore> getUpdateStoreLog(){
		List<UpdateStore> updateStoreList = new ArrayList<UpdateStore>();
		UpdateStore updateStore;
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_update_store_tokenLog()}";
			stmt = connect.prepareCall(query);
			
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	updateStore = new UpdateStore(resultSet.getInt("storeId"), resultSet.getString("updateToken"), resultSet.getString("updateTime"), resultSet.getInt("updateStoreStatus"));
		    	updateStoreList.add(updateStore);
		    }
			
		}catch(Exception e){
			System.out.println("failed at getUpdateStoreTokenLog-->"+e.getMessage());
		}finally{
			close();
		}
		return updateStoreList;
	}
	public List<Integer> getStoreListByToken(String token){
		List<Integer> storeList = new ArrayList<Integer>();
		String query = "SELECT * FROM updatestore where updateToken='"+token+"'";
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
		    resultSet = statement.executeQuery(query);
		    while (resultSet.next()) {
		    	storeList.add(resultSet.getInt("storeId"));
		    }
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Update Log DAO getStoreListByToken()->"+e.getMessage());
		}finally{
			close();
		}
		return storeList;
	}
	private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }
	      if (stmt != null) {
		        stmt.close();
		      }
	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }
}
