package com.prami.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.prami.bean.LocalStore;
import com.prami.bean.LocalStoreServices;
import com.prami.bean.LocalStoreSocial;
import com.prami.bean.LocalStoreFeatures;
import com.prami.db.DBUtil;
import com.prami.db.MySQLAccess;
public class CreateStoreUtil {
		
		ResultSet resultSet = null;
		Connection connect = null;
		Statement statement = null;
		MySQLAccess dao = new MySQLAccess();
		 
		 public List<String> createNewStore(LocalStore store, List<LocalStoreServices> serviceList, List<LocalStoreFeatures> featuresList, List<LocalStoreSocial> socialList) {
			 List<String> errorList = new ArrayList<String>();
			 DBUtil dbutil = new DBUtil();
			 int insertedProductID = 0;
				try{
					connect = dao.getConnection();
					TypeUtil type = new TypeUtil();
					Random random = new SecureRandom();
					String savePath = "/var/www/images/store";
					
					String logoImage = new BigInteger(90, random).toString(32);
					String storeImage = new BigInteger(90, random).toString(32);
					if(type.uploadTypeImage(store.getStoreLogo(), logoImage, savePath+"/logo") && type.uploadTypeImage(store.getStoreImage(), storeImage, savePath)){
					
					
						String query = "INSERT INTO local_stores (storeName, storeTagLine, storeDescription, storeLogo, storeImage, isStoreVerified, storeEstablishedDate,"; 
						query += "storeAddress, storeAreaId, storeLandmark, storeGeoLocation, storePin, storeEmail, storeContact1,  storeWebsite, storeStatus)";
						query += "VALUES ('"+store.getStoreName()+"', '"+store.getStoreTagLine()+"', '"+store.getStoreDescription()+"', '"+logoImage+".jpg"+"', '"+storeImage+".jpg"+"'"
								+ ", '"+store.getIsStoreVerified()+"', '"+store.getStoreEstablishedDate()+"', '"+store.getStoreAddress()+"', '"+store.getStoreAreaId()+"'"
								+", '"+store.getStoreLandmark()+"', '"+store.getStoreGeoLocation()+"', '"+store.getStorePin()+"', '"+store.getStoreEmail()+"', '"+store.getStoreContact1()+"'"
								+", '"+store.getStoreWebsite()+"',1)";
	
						statement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
						statement.executeUpdate(query);
						resultSet = statement.getGeneratedKeys();
						if (resultSet.next()){
							insertedProductID = resultSet.getInt(1);
						}
						
						errorList.addAll(dbutil.insertStoreServices(insertedProductID, serviceList));
						
						errorList.addAll(dbutil.insertLocalStoreFeatures(insertedProductID, featuresList));
						
						errorList.addAll(dbutil.insertStoreSocial(insertedProductID, socialList));
						errorList.add("1|Store Created!");
						errorList.add("1|Successfully Uploaded Store Logo & Images!");
						
					}else{
						errorList.add("2|Store Images failed to uplaod!");
					}
					
				} catch (SQLException e) {
		            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		            errorList.add("2|Database Connection Failed!");
		        }catch(Exception e){
					e.printStackTrace();
					System.out.println("failed at CreateStoreUtil --> createNewStore()-->"+e.getMessage());
					errorList.add("2|Unable to Create Store");
				}finally{
					close();
				}
				return errorList;
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
