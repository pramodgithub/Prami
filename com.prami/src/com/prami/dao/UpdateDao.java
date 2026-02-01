package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.prami.bean.CollectionSEO;
import com.prami.bean.UpdateProduct;
import com.prami.db.MySQLAccess;

public class UpdateDao {
	Statement statement = null;
	Statement statement2 = null;
	ResultSet resultSet = null;
	Connection connect = null;
	CallableStatement stmt = null;
	MySQLAccess dao = new MySQLAccess();
	UpdateLogDAO logDao = new UpdateLogDAO();
	SizeDao sizeDao = new SizeDao();
	
	static final int PRODUCT_DISABLED = 1;
	static final int PRICE_UPDATED = 2;
	static final int PRODUCT_DISABLE_FAILED = 3;
	static final int PRICE_UPDATE_FAILED = 4;
	static final int PRICE_SAME = 5;
	static final int PRODUCT_UNAVAILABLE = 6;
	static final int PRODUCT_UNAVAILABLE_UPDATE_FAILED = 7;
	static final int PRODUCT_ADDED = 8;
	static final int RE_RUN_UPDATE = 9;
	
	public int disableProduct(int prodId, String token, boolean reUpdate) throws SQLException{
		int update = 0, isUpdated=0;
		long currTime = System.currentTimeMillis();
		try{
			connect = dao.getConnection();
			connect.setAutoCommit(false);
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			statement.executeUpdate("update producttbl set productStatus=0,productModificationDate="+currTime+" where productID="+prodId);
			
			isUpdated = statement2.executeUpdate("update producttypemapping set status=0 where productId ="+prodId);
			connect.commit();
			
			update = (isUpdated>0)?logDao.addUpdateLog(prodId, 1, PRODUCT_DISABLED, token, reUpdate):logDao.addUpdateLog(prodId, -1, PRODUCT_DISABLE_FAILED, token, reUpdate);
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("failed at updateDao --> disableProduct-->"+e.getMessage());
			System.err.println("=========>> Unable disable product id==========>"+prodId);
			connect.rollback();
		}finally{
			close();
		}
		return update;
	}
	
	public int updateProductPrice(int prodId, float price, int status, int type, String token, boolean reUpdate, String prodSizes) throws SQLException {
		int update = 0;
		int isUpdated = 0,sizeUpdated = 0;
		long currTime = System.currentTimeMillis();
		
		try{
			connect = dao.getConnection();
			connect.setAutoCommit(false);
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			if(status!=1){	
				statement.executeUpdate("update producttbl set productPrice="+price+",productModificationDate="+currTime+", productStatus=1 where productID="+prodId);
				isUpdated = statement2.executeUpdate("update producttypemapping set status=1 where productId ="+prodId);
				
				if(isUpdated>0){
					sizeUpdated = sizeDao.addProductSizeData(prodId, prodSizes);
				}
				if(sizeUpdated>0){
					isUpdated = 1;
					connect.commit();
				}else{
					isUpdated = 0;
				}
				
			}else{
				isUpdated = statement.executeUpdate("update producttbl set productPrice="+price+",productModificationDate="+currTime+" where productID="+prodId);
				if(isUpdated>0){
					sizeUpdated = sizeDao.addProductSizeData(prodId, prodSizes);
				}
				if(type != PRICE_SAME){
					connect.commit();
				}else{
					isUpdated = 1;
				}
			}
			update = (isUpdated>0)?	logDao.addUpdateLog(prodId, price, type, token, reUpdate) : logDao.addUpdateLog(prodId, price, PRICE_UPDATE_FAILED, token, reUpdate);
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("failed at updateDao --> updateProductPrice-->"+e.getMessage());
			connect.rollback();
		}finally{
			close();
		}
		return update;
	}
	public int productUnavailable(int prodId, String token, boolean reUpdate) throws SQLException{
		int update = 0, isUpdated=0;
		long currTime = System.currentTimeMillis();
		try{
			connect = dao.getConnection();
			connect.setAutoCommit(false);
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			statement.executeUpdate("update producttbl set productStatus=2,productModificationDate="+currTime+" where productID="+prodId);
			isUpdated = statement2.executeUpdate("update producttypemapping set status=2 where productId ="+prodId);
			connect.commit();
			
			update = (isUpdated>0)? logDao.addUpdateLog(prodId, 1, PRODUCT_UNAVAILABLE, token, reUpdate): logDao.addUpdateLog(prodId, -1, PRODUCT_UNAVAILABLE_UPDATE_FAILED, token, reUpdate);
			
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("failed at updateDao --> productUnavailable -->"+e.getMessage());
			System.err.println("=========>> Unable disable product id==========>"+prodId);
			connect.rollback();
		}finally{
			close();
		}
		return update;
	}
	public int reRunUpdate(int prodId, String token, boolean reUpdate) throws SQLException{
		int update = 0;
		long currTime = System.currentTimeMillis();
		try{
			connect = dao.getConnection();
			connect.setAutoCommit(false);
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			statement.executeUpdate("update producttbl set productStatus=0,productModificationDate="+currTime+" where productID="+prodId);
			statement2.executeUpdate("update producttypemapping set status=0 where productId ="+prodId);
			connect.commit();
			
			update = logDao.addUpdateLog(prodId, 1, RE_RUN_UPDATE, token, reUpdate);			
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("failed at updateDao --> rerunUpdate -->"+e.getMessage());
			System.err.println("=========>> Unable to disable product id==========>"+prodId);
			connect.rollback();
		}finally{
			close();
		}
		return update;
	}
	
	
	
	
	
	public int updateSimilarProducts(String similarList){
		int update = 0;
		long currTime = System.currentTimeMillis();
		int currentproductId;
		List<Integer> tempList= new ArrayList<Integer>();
		String[] similarArr = null;
		boolean flag = true;
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			JSONArray jsonarr = new JSONArray(similarList);
			for(int i = 0; i < jsonarr.length(); i++){
				JSONObject jObj = jsonarr.getJSONObject(i);
				currentproductId = Integer.parseInt(jObj.get("productId").toString());
				//System.out.println(currentproductId+"==>");
				similarArr = jObj.get("currProductId").toString().replace("[", "").replace("]", "").split(",");	
				for(int j = 0; j < similarArr.length; j++){
					if(!similarArr[j].equals("") && !tempList.contains(currentproductId)){
						if(flag){
							statement.executeUpdate("update producttbl set similarityCheck=1, productModificationDate="+currTime+" where productID="+currentproductId);
							flag=false;
						}
						statement.executeUpdate("update producttbl set similarID="+currentproductId+",similarityCheck=1, productModificationDate="+currTime+" where productID="+similarArr[j]);
						tempList.add(Integer.parseInt(similarArr[j]));
					}else{
						if(!tempList.contains(currentproductId)){
							statement.executeUpdate("update producttbl set similarityCheck=1, productModificationDate="+currTime+" where productID="+currentproductId);
						}
					}
				}
			}



		}catch(Exception e){
			e.printStackTrace();

			System.out.println("failed at updateDao --> updateSimilarProducts-->"+e.getMessage());
		}finally{
			close();
		}
		return update;
	}
	public boolean updateAffiliationURL(List<Integer> storeIds, List<String> newAffiliateUrl) {
    	List<UpdateProduct> productList = new ArrayList<UpdateProduct>();
    	ProductDao productDetails = new ProductDao();
    	String URL;
    	String affiliateProductURL = null,affUpdateQuery;
    	boolean status = true;
    	int limit = 500;
		int offset = 0;
    	try{
    		connect = dao.getConnection();
    		for(Integer storeId:storeIds){
    			offset = 0;
    			while(!productList.isEmpty() || offset == 0){
    				productList = productDetails.getProductURLList(storeId, offset, limit);
    				offset = offset + limit;
		    			for(UpdateProduct productUrls:productList){
		    				try{
			    				URL = productUrls.getProductURL();
			    				if(newAffiliateUrl.get(2).equals("true")){
					    			affiliateProductURL = URL;	
					    		}else if(!newAffiliateUrl.get(0).isEmpty()){
					    			if(!URL.contains("?")){URL += "?";	}
							 		String encodedURL = java.net.URLEncoder.encode(URL,"UTF-8");
							 		affiliateProductURL = newAffiliateUrl.get(0)+encodedURL+newAffiliateUrl.get(1);
					    		}else if(newAffiliateUrl.get(0).isEmpty() && !newAffiliateUrl.get(1).isEmpty()){
					    			affiliateProductURL = URL+newAffiliateUrl.get(1);
					    		}
					    		if(affiliateProductURL!=null){
								 	affUpdateQuery = "{call sp_update_affiliated_urls(?, ?)}";
									stmt = connect.prepareCall(affUpdateQuery);
									stmt.setInt(1, productUrls.getProductId());
									stmt.setString(2, affiliateProductURL);
									resultSet = stmt.executeQuery();
					    		}
		    				}catch(Exception ie){
		    					System.out.println("Failed to update Affiliate URL Product Id==>"+productUrls.getProductId());
		    				}
		    			}
    				}
    			}
    	}catch(Exception e){
    		e.printStackTrace();
    		status = false;
    	}
		return status;
	}
	public boolean addProductUpdateLog(int productId, float update, int type){
		boolean flag = true;
		long currTime = System.currentTimeMillis();
		try{
			connect = dao.getConnection();
			String sqlStatement = "INSERT INTO updatetbl (productID, updateInfo, updateType,updateTime) VALUES ('"+productId+"', '"+update+"', '"+type+"', '"+currTime+"')";
			statement = connect.prepareStatement(sqlStatement);
			statement.executeUpdate(sqlStatement);
			
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("failed at UpdateDao class in addProductUpdateLog()-->"+e.getMessage());
			flag=false;
		}finally{
			close();
		}
		return flag;
	}
	
	
	
	public boolean updateCollectionSequence() {
		boolean update = false;
		int count = 1;
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			
			String productQuery = "{call sp_get_collection_sequence()}";
			stmt = connect.prepareCall(productQuery);
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	statement2.executeUpdate("update subcategorytbl set sequence = "+count+" where subcatID = "+resultSet.getInt("subcatID"));
		    	count++;
		    }
			update = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return update;
	}
	
	public boolean updateProductTrendFactor() {
		boolean update = false;
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			
			CollectionDao collection = new CollectionDao();
			List<CollectionSEO> collecitonList = collection.getAllCollectionSeo();
			String updateQuery = "{call sp_update_trend_factor(?)}";
			stmt = connect.prepareCall(updateQuery);
			for(CollectionSEO c : collecitonList){
				stmt.setInt(1, c.getCollectionId());
				resultSet = stmt.executeQuery();
				System.out.println(c.getCollectionId());
				
			}
			update = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return update;
	}
	
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}
			if (statement2 != null) {
				statement2.close();
			}
			if (connect != null) {
				connect.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			
		}
	}
	
}
