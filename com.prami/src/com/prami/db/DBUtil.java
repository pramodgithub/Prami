package com.prami.db;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.prami.bean.LocalStoreServices;
import com.prami.bean.LocalStoreSocial;
import com.prami.bean.ProductType;
import com.prami.bean.LocalStoreFeatures;
import com.prami.util.TypeUtil;

public class DBUtil {
	Statement statement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	MySQLAccess dao = new MySQLAccess();

	public boolean checkProductTypeAvailable(String typename) {
		boolean isTypePresent = false;
		try {
			connect = dao.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from producttypetbl where prodTypeName='" + typename + "'");

			if (resultSet.isBeforeFirst()) {
				isTypePresent = true;
			}
		} catch (Exception e) {
			System.out.println("failed at checkProductTypeAvailable-->" + e.getMessage());
		} finally {
			close();
		}
		return isTypePresent;
	}
	public boolean checkProductPresentByURL(String prodURL) {
		boolean isProductPresent = false;
		try {
			connect = dao.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from producttbl where orginalURL='" + prodURL + "' and productStatus=1");

			if (resultSet.isBeforeFirst()) {
				isProductPresent = true;
			}
		} catch (Exception e) {
			System.out.println("failed at checkProductPresentByURL()-->" + e.getMessage());
		} finally {
			close();
		}
		return isProductPresent;
	}
	public int insertProductType(String image, String typeName, int catId, int subCatId) {
		int insertedTypeID=0;
		try {
			connect = dao.getConnection();
			String sqlStatement = "insert into producttypetbl (catID,subCatID,prodTypeName,prodTypeImage,prodTypeStatus) values ("+catId+","+subCatId+",'"+typeName+"','"+image+"',1)";
			statement = connect.prepareStatement(sqlStatement,Statement.RETURN_GENERATED_KEYS);
		    statement.executeUpdate(sqlStatement);
			ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()){
					insertedTypeID = rs.getInt(1);
				}
				
		} catch (Exception e) {
			System.out.println("failed at insertProductType-->" + e.getMessage());
		} finally {
			close();
		}
		return insertedTypeID;
	}
	public boolean insertProductTypeMapping(int productId, List<String> productTypes, int status) {
		boolean success = false;
		try{
			connect = dao.getConnection();
			
			String sqlStatement = "INSERT INTO producttypemapping (productId,typeId,status) VALUES ";
			
			for(String type:productTypes){
				sqlStatement += "("+productId+","+type+","+status+"),";
			}
			//System.out.println(sqlStatement);
			statement = connect.prepareStatement(sqlStatement);
			statement.executeUpdate(sqlStatement.replaceAll(",$", ""));
			success = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at DBUtil class in insertProductTypeMapping-->"+e.getMessage());
			success = false;
		}finally{
			close();
		}
		return success;
	}
	public boolean updateProductSimilarId(int productId) {
		boolean success = false;
		try{
			connect = dao.getConnection();
			
			String sqlStatement = "UPDATE producttbl SET similarID = "+productId+" WHERE productID = "+productId;
			
			statement = connect.prepareStatement(sqlStatement);
			statement.executeUpdate(sqlStatement);
			success = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at DBUtil class in updateProductSimilarId-->"+e.getMessage());
			success = false;
		}finally{
			close();
		}
		return success;
	}
	
	public List<String> insertStoreServices(int storeId, List<LocalStoreServices> serviceList) {
		List<String> errorList = new ArrayList<String>();
		try{
			connect = dao.getConnection();
			TypeUtil type = new TypeUtil();
			Random random = new SecureRandom();
			String savePath = "/var/www/images/store";
			
			String servicesImage = "";
				for(LocalStoreServices ss:serviceList){
				servicesImage = new BigInteger(90, random).toString(32);	
				if(type.uploadTypeImage(ss.getServicesImage(), servicesImage, savePath+"/services")){
					String sqlStatement = "INSERT INTO local_store_services (storeid, servicesName, servicesDescription, servicesImage, isServicesCustomizable, servicesPriceRange, servicesStatus) "
							+ "VALUES ('"+storeId+"', '"+ss.getServicesName()+"', '"+ss.getServicesDescription()+"', '"+servicesImage+".jpg"+"', '"+ss.getIsServicesCustomizable()+"', '"+ss.getServicesPriceRange()+"', '1')";
					
					statement = connect.prepareStatement(sqlStatement);
					statement.executeUpdate(sqlStatement);
					errorList.add("1|Services Created!");
					errorList.add("1|Successfully Uploaded Services Logo & Images!");
				}else{
					errorList.add("2|Service Images failed to uplaod!");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at DBUtil class in insertStoreServices-->"+e.getMessage());
			errorList.add("2|Unable to Create Services");
		}finally{
			close();
		}
		return errorList;
	}
	public List<String> insertLocalStoreFeatures(int storeId, List<LocalStoreFeatures> featuresList) {
		List<String> errorList = new ArrayList<String>();
		long currTime = System.currentTimeMillis();
		try{
			connect = dao.getConnection();
			TypeUtil type = new TypeUtil();
			Random random = new SecureRandom();
			String savePath = "/var/www/images/store";
			
			String featureImage = "";
			for(LocalStoreFeatures feature:featuresList){
				featureImage = new BigInteger(90, random).toString(32);	
				if(type.uploadTypeImage(feature.getFeaturesCover(), featureImage, savePath+"/features")){
					String sqlStatement = "INSERT INTO local_store_features (storeid, featuresTitle, featuresDescription, featuresCover, featuresVideoLink, featuresType, createdDate,featuresStatus) "
							+ "VALUES ('"+storeId+"', '"+feature.getFeaturesTitle()+"', '"+feature.getFeaturesDescription()+"', '"+featureImage+".jpg"+"', '"+feature.getFeaturesVideoLink()+"', '1', '"+currTime+"', '1')";
					
					statement = connect.prepareStatement(sqlStatement);
					statement.executeUpdate(sqlStatement);
					errorList.add("1|Features Created!");
					errorList.add("1|Successfully Uploaded Features Images!");
				}else{
					errorList.add("2|Features Images failed to uplaod!");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at DBUtil class in insertStoreServices-->"+e.getMessage());
			errorList.add("2|Unable to Create Services");
		}finally{
			close();
		}
		return errorList;
	}
	
	public List<String> insertStoreSocial(int storeId, List<LocalStoreSocial> socialList) {
		long currTime = System.currentTimeMillis();
		List<String> errorList = new ArrayList<String>();
		try{
			connect = dao.getConnection();
			for(LocalStoreSocial scl:socialList){
				String sqlStatement = "INSERT INTO local_store_social (storeId, socialId, socialUrl,  addedDate, socialStatus) "
						+ "VALUES ('"+storeId+"', '"+scl.getSocialName()+"', '"+scl.getSocialUrl()+"', '"+currTime+"', '1')";
				
				statement = connect.prepareStatement(sqlStatement);
				statement.executeUpdate(sqlStatement);
			}
			errorList.add("1|Social Account Added!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at DBUtil class in insertStoreSocial-->"+e.getMessage());
			errorList.add("1|Failed to Add Social Accounts!");
		}finally{
			close();
		}
		return errorList;
	}
	
	public List<ProductType> getProductTypeList(int reqCategoryID,int reqSubCategoryID){
		List<ProductType> productTypeList = new ArrayList<ProductType>();
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from producttypetbl where catID="+reqCategoryID+" and subCatID="+reqSubCategoryID+" and prodTypeStatus=1");
		    
		    while (resultSet.next()) {
		    	ProductType productType = new ProductType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("prodTypeIcon"),resultSet.getString("prodTypeImage"));	
		    	productTypeList.add(productType);
		    }
			
		}catch(Exception e){
			System.out.println("failed at getProductTypeList"+e.getMessage());
		}finally {
			close();
		}
		return productTypeList;
	}
	public List<ProductType> getAllProductTypeList(){
		List<ProductType> productTypeList = new ArrayList<ProductType>();
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select pt.prodTypeID, pt.prodTypeName, s.subcatName, pt.prodTypeImage, pt.prodTypeStatus from trendydb.producttypetbl pt join trendydb.subcategorytbl s where pt.subCatID = s.subcatID");
		    
		    while (resultSet.next()) {
		    	ProductType productType = new ProductType(resultSet.getInt("prodTypeID"), resultSet.getString("prodTypeName"), resultSet.getString("subcatName"),resultSet.getString("prodTypeImage"), resultSet.getInt("prodTypeStatus"));	
		    	productTypeList.add(productType);
		    }
			
		}catch(Exception e){
			System.out.println("failed at getProductTypeList"+e.getMessage());
		}finally {
			close();
		}
		return productTypeList;
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
