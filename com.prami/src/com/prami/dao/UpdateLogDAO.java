package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.ProductSize;
import com.prami.bean.UpdateLog;
import com.prami.bean.UpdateLogs;
import com.prami.bean.UpdateProduct;
import com.prami.db.MySQLAccess;

public class UpdateLogDAO {
	Statement statement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	CallableStatement stmt = null;
	MySQLAccess dao = new MySQLAccess();

	public List<UpdateLog> getUpdatelogByToken(String token, int offset, int limit){
		List<UpdateLog> updateLogList = new ArrayList<UpdateLog>();
		String query = "SELECT productID, updateInfo, updateType, updateTime FROM updatetbl where updateToken='"+token+"' limit "+offset+","+limit;
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
		    resultSet = statement.executeQuery(query);
		    while (resultSet.next()) {
		    	UpdateLog updatelog = new UpdateLog(resultSet.getInt("productID"), resultSet.getLong("updateInfo"), resultSet.getInt("updateType"), resultSet.getString("updateTime"));
		    	updateLogList.add(updatelog);
		    }
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Update Log DAO getUpdatelogByToken()->"+e.getMessage());
		}finally{
			close();
		}
		return updateLogList;
	}
	
	public int addUpdateLog(int prodId, float price, int type, String token, boolean reUpdate){
		String logUpdate;
		int isUpdated = 0;
		long currTime = System.currentTimeMillis();
		
		try{
			connect = dao.getConnection();
			if(reUpdate){					
				logUpdate = "{call sp_update_update_log(?, ?, ?, ?, ?)}";
			}else{
				logUpdate = "{call sp_insert_update_log(?, ?, ?, ?, ?)}";
			}
			stmt = connect.prepareCall(logUpdate);
			stmt.setInt(1, prodId);
			stmt.setFloat(2, price);
			stmt.setLong(3, currTime);
			stmt.setLong(4, type);
			stmt.setString(5, token);
			isUpdated = stmt.executeUpdate();
						
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at updateDao --> addUpdateLog-->"+e.getMessage());
			
		}finally{
			close();
		}
		return isUpdated;
	}
	
	public List<Integer> getUpdateFailedProductList(String token, int storeId, int offset, int limit){
		String prodcutIdList;
		
		List<Integer> updateFailedProductList = new ArrayList<Integer>();
		try{
			connect = dao.getConnection();
			prodcutIdList = "{call sp_get_update_failed_products(?, ?, ?, ?)}";
			
			stmt = connect.prepareCall(prodcutIdList);
			stmt.setString(1, token);
			stmt.setInt(2, storeId);
			stmt.setFloat(3, offset);
			stmt.setLong(4, limit);
			
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	updateFailedProductList.add(resultSet.getInt("productID"));
		    }
						
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at updateDao --> getUpdateFailedProductList-->"+e.getMessage());
			
		}finally{
			
			close();
		}
		return updateFailedProductList;
	}
	
	public List<UpdateLogs> getUpdatelogsList(int page, int limit){
		List<UpdateLogs> UpdateLogsList= new ArrayList<UpdateLogs>();
		StoreDao storeDao = new StoreDao();
		UpdateLogs updateLogs;
		
		try{
			connect = dao.getConnection();
			
			String query = "{call sp_get_update_log_list(?, ?)}";

			stmt = connect.prepareCall(query);
			stmt.setInt(1, page);
			stmt.setInt(2, limit);
			resultSet = stmt.executeQuery();
		    
		    while(resultSet.next()){
		    	if(resultSet.getString("updateToken")!=null){
			    	List<Integer> storeList = storeDao.getStoreListByToken(resultSet.getString("updateToken"));
			    	updateLogs= new UpdateLogs(resultSet.getString("updateToken"),resultSet.getString("updateTime"), resultSet.getInt("totalLogs"), resultSet.getInt("ProductDisabled"),resultSet.getInt("PriceUpdated"),resultSet.getInt("FailedToDisable"),resultSet.getInt("PriceUpdateFailed"),	resultSet.getInt("PriceSame"),resultSet.getInt("ProductUnavailable"),resultSet.getInt("ProductUnavailableUpdateFailed"),resultSet.getInt("ProductAdded"), storeList,resultSet.getInt("ReUpdate"));
			    	UpdateLogsList.add(updateLogs);
		    	}
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at UpdateLogDAO --> getUpdatelogsList()"+e.getMessage());
			
		}finally{
			close();
		}
		
		return UpdateLogsList;
	}
	
	
	
	public List<UpdateProduct> getProductUpdateLogsDetails(String token, int storeId, String types, int offset, int limit){
		List<UpdateProduct> updateProductList= new ArrayList<UpdateProduct>();
		SizeDao sizes = new SizeDao();
		UpdateProduct productUpdate;
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_product_update_details_by_update_type(?, ?, ?, ?, ?)}";

			stmt = connect.prepareCall(query);
			stmt.setString(1, token);
			stmt.setInt(2, storeId);
			stmt.setString(3, types);
			stmt.setInt(4, offset);
			stmt.setInt(5, limit);
			
			resultSet = stmt.executeQuery();
		    
		    while(resultSet.next()){
		    	List<ProductSize> productSizesList = sizes.getSizesByProdId(resultSet.getInt("productID"));
		    	productUpdate = new UpdateProduct(resultSet.getInt("productID"), resultSet.getString("orginalURL"), resultSet.getInt("productStatus"), resultSet.getFloat("productPrice"), resultSet.getInt("storeID"), "", productSizesList);
		    	updateProductList.add(productUpdate);
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at UpdateLogDAO --> getProductUpdateLogsDetails()"+e.getMessage());
			
		}finally{
			close();
		}
		
		return updateProductList;
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
	      if (stmt != null) {
				stmt.close();
			}
	    } catch (Exception e) {
	    	System.out.println("Failed to close==>"+e.getMessage());
	    }
	  }
}
