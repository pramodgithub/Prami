package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.prami.bean.ProductSize;
import com.prami.db.MySQLAccess;

public class SizeDao {
	
	Statement statement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	MySQLAccess dao = new MySQLAccess();
	CallableStatement stmt = null;
	
	public List<ProductSize> getSizesByProdId(int productId){
		List<ProductSize> productSizeList = new ArrayList<ProductSize>();
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			statement.executeQuery("SELECT * FROM sizetbl where productId="+productId);
			resultSet = statement.getResultSet();
			while(resultSet.next()){

				ProductSize productSize = new ProductSize(resultSet.getInt("idProdSize"), resultSet.getInt("productId") , resultSet.getString("productSize"), resultSet.getBoolean("sizeStatus"));
				productSizeList.add(productSize);

			}

		}catch(Exception e){
			e.printStackTrace();

			System.out.println("failed at SizeDao --> getSizesByProdId()-->"+e.getMessage());
		}finally{
			close();
		}
		return productSizeList;
	}
	
	public int addProductSizeData(int prodId, String prodSizes){
		String sizeUpdate;
		JSONArray jsonSizeArray;
		int isUpdated = 1;
		try{
			jsonSizeArray = new JSONArray(prodSizes);
			connect = dao.getConnection();
			for(int i = 0; i < jsonSizeArray.length(); i++){
				JSONObject jObj = jsonSizeArray.getJSONObject(i);
				String prodSize = jObj.get("productSize").toString();
				int sizeStatus = (jObj.get("productSizeStatus").toString().equals("true"))? 1 : 0;
			
				 sizeUpdate = "{call sp_add_update_product_sizes(?, ?, ?)}";
					
					stmt = connect.prepareCall(sizeUpdate);
					stmt.setInt(1, prodId);
					stmt.setString(2, prodSize);
					stmt.setInt(3, sizeStatus);
					
					isUpdated = stmt.executeUpdate();
					if(isUpdated==0){
						break;
					}
			}
		}catch(Exception e){
			isUpdated = 0;
			e.printStackTrace();
		}finally{
			close();
		}
		return isUpdated;
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

	    }
	  }
}
