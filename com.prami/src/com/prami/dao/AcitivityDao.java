package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.Activity;
import com.prami.bean.PurchaseHistory;
import com.prami.db.MySQLAccess;

public class AcitivityDao {
	Statement statement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	CallableStatement callableStatement = null;
	PreparedStatement pst = null;
	MySQLAccess dao = new MySQLAccess();
	 public void insertActivity(Activity activity) {
			try{
				connect = dao.getConnection();
				java.util.Date date= new java.util.Date();
				long currentTime = new Timestamp(date.getTime()).getTime();
				String query = "insert into activitytbl (activityType, userId, userIP, productId ,userCountry, userRegion, userCity, userZip, latitude, longitude, activityTime) values ('"+activity.getActivityType()+"','"+activity.getUserId()+"','"+activity.getUserIP()+"','"+activity.getProductId()+"','"+activity.getCountry()+"','"+activity.getRegion()+"','"+activity.getCity()+"','"+activity.getZip()+"','"+activity.getLatitude()+"','"+activity.getLongitude()+"','"+currentTime+"')";
				pst = connect.prepareStatement(query);
		    	pst.executeUpdate(); 
		    	
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at ActivityDAO --> insertActivity()-->"+e.getMessage());
				
			}finally{
				close();
			}
			
	    }
	 public int getProductViews(int productId) {
			int totoalViews=0;
			try{
				connect = dao.getConnection();
				String query = "SELECT count(productId) FROM activitytbl where productId="+productId;
				statement = connect.createStatement();			
		    	resultSet = statement.executeQuery(query);
		    	resultSet.next();
			    totoalViews = resultSet.getInt(1);
		    	
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at ActivityDAO --> getProductViews()-->"+e.getMessage());
				
			}finally{
				close();
			}
			return totoalViews;
	    }
	 
	 public List<PurchaseHistory> getPurchaseHistory(int userId, String token){
		 
		 	List<PurchaseHistory> purchaseHistoryList = new ArrayList<PurchaseHistory>();
			String query;
			try{
				int headerUserId = Integer.parseInt(token.split(":")[0]);
				if(headerUserId == userId){
					connect = dao.getConnection();
					query = "{call sp_get_buy_activity(?)}";
	
					callableStatement = connect.prepareCall(query);
					callableStatement.setInt(1, userId);
					
					resultSet = callableStatement.executeQuery();
						
					while (resultSet.next()){
						
						PurchaseHistory purchaseHistory = new PurchaseHistory(resultSet.getString("productName"), "Not Confirmed", resultSet.getString("activityDate"));
						purchaseHistoryList.add(purchaseHistory);
					}
				}
			}catch(Exception e){
					e.printStackTrace();
					System.out.println("failed at ActivityDAO --> getPurchaseHistory()-"+e.getMessage());

			}finally{
				close();
			}
			return purchaseHistoryList;
		 
	 }
	 
	 private void close() {
		    try {
		      if (resultSet != null) {
		        resultSet.close();
		      }

		      if (statement != null) {
		        statement.close();
		      }
		      if (pst != null) {
			        pst.close();
			      }
		      if (connect != null) {
		        connect.close();
		      }
		    
		      if (callableStatement != null) {
		    	  callableStatement.close();
			  }
		    } catch (Exception e) {

		    }
		  }
}
