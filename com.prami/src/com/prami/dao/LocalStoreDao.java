package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

import com.prami.bean.LocalSocial;
import com.prami.bean.LocalStore;
import com.prami.bean.LocalStoreForm;
import com.prami.bean.LocalStoreServices;
import com.prami.bean.LocalStoreSocial;
import com.prami.bean.LocalStoreFeatures;
import com.prami.bean.Structure;
import com.prami.db.MySQLAccess;
import com.prami.util.CreateStoreUtil;
import com.prami.util.LocalStoreUtil;
import com.prami.util.StructureUtil;

public class LocalStoreDao {
	private CallableStatement statement = null;
	private ResultSet resultSet = null;
	private Connection connect = null;
	private MySQLAccess dao = new MySQLAccess();
	 
	 
	 
	 public List<LocalStore> getStoreList(String userId, int cityId, int page, int limit, int groupPattern, List<Integer> areas, String sort){
			List<LocalStore> LocalStoreList = new ArrayList<LocalStore>();
			
			LocalStoreUtil localUtil = new LocalStoreUtil();
			LocalStore localStore;
			List<Structure> structureList;
			StructureUtil pagePattern = new StructureUtil();
			int patternIndex = 0;
			String designers;
			String areasString = "";
			try{
				
				for(Integer area : areas) {
					areasString += area +",";
				}
				areasString = areasString.replaceAll(",$", "");
				connect = dao.getConnection();
				
				if(areas.size() <= 0){
					designers = "{call sp_get_loc_stores_by_cityid(?,?,?)}";
					statement = connect.prepareCall(designers);
					statement.setInt(1, cityId);
					statement.setInt(2, page);
					statement.setInt(3, limit);
				}else{
					
					designers = "{call sp_get_loc_stores_by_filter(?,?,?,?)}";
					
					statement = connect.prepareCall(designers);
					
					statement.setInt(1, page);
					statement.setInt(2, limit);
					statement.setString(3, areasString);
					statement.setString(4, sort);
					
					
				}
				
				
				resultSet = statement.executeQuery();
			    while (resultSet.next()) {
			    	List<LocalStoreServices> LocalStoreServicesList = localUtil.getStoreServices(resultSet.getInt("storeid"));
			    
			    	structureList = pagePattern.getPagePattern(groupPattern);
			    	localStore = new LocalStore(resultSet.getInt("storeid"), resultSet.getString("storeName"), resultSet.getString("storeTagLine"), resultSet.getString("storeDescription"), resultSet.getString("storeLogo"), resultSet.getString("storeImage"), resultSet.getInt("isStoreVerified"), resultSet.getString("storeEstablishedDate"), resultSet.getInt("storeAreaId"), resultSet.getString("storeAreaName"), resultSet.getInt("storeCityId"), resultSet.getString("storeCityName"), resultSet.getString("storeLandmark"), LocalStoreServicesList, structureList.get(patternIndex).getPageColumn());
			    	LocalStoreList.add(localStore);
			    	if(structureList.size() == patternIndex+1){
			    		patternIndex = 0;
			    	}else{
			    		patternIndex ++;
			    	}
			    }
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at getStoreList-->"+e.getMessage());
			}finally{
				close();
			}
			return LocalStoreList;
		}
	 
	 public LocalStore getStoreDetails(int storeId){
			LocalStore localStore = null;
			LocalStoreUtil localUtil = new LocalStoreUtil();
			try{
				connect = dao.getConnection();
				String stores = "{call sp_get_loc_store(?)}";
				statement = connect.prepareCall(stores);
				statement.setInt(1, storeId);
				resultSet = statement.executeQuery();
			    while (resultSet.next()) {
			    	List<LocalStoreServices> localStoreServicesList = localUtil.getStoreServices(storeId);
			    	List<LocalSocial> localStoreSocialList = localUtil.getStoreSocial(storeId);
			    	localStore = new LocalStore(resultSet.getInt("storeid"), resultSet.getString("storeName"), resultSet.getString("storeTagLine"), resultSet.getString("storeDescription"), resultSet.getString("storeServicesDescription"), resultSet.getString("storeLogo"), resultSet.getString("storeImage"), resultSet.getInt("isStoreVerified"), resultSet.getString("storeEstablishedDate"), resultSet.getString("storeAddress"), resultSet.getInt("storeAreaId"), resultSet.getString("storeAreaName"), resultSet.getInt("storeCityId"), resultSet.getString("storeCityName"), resultSet.getString("storeLandmark"), resultSet.getString("storeGeoLocation"), resultSet.getString("storePin"), resultSet.getString("storeEmail"), resultSet.getString("storeContact1"), resultSet.getString("storeContact2"), resultSet.getString("storeWebsite"), localStoreServicesList, localStoreSocialList);
			    }
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at getStoreDetails-->"+e.getMessage());
			}finally{
				close();
			}
			return localStore;
		}
	 
	 public List<LocalStoreFeatures> getStoreFeatures(int storeId, int storeType){
			List<LocalStoreFeatures> storeFeaturesList = new ArrayList<LocalStoreFeatures>();
			LocalStoreFeatures storeFeatures;
			try{
				connect = dao.getConnection();
				String features = "{call sp_get_loc_store_features(?,?)}";
				statement = connect.prepareCall(features);
				statement.setInt(1, storeId);
				statement.setInt(2, storeType);
				resultSet = statement.executeQuery();
			    while (resultSet.next()) {
			    	storeFeatures = new LocalStoreFeatures(resultSet.getInt("featuresId"), resultSet.getString("featuresTitle"), resultSet.getString("featuresDescription"), resultSet.getString("featuresCover"), resultSet.getString("featuresVideoLink"), resultSet.getInt("featuresType"));
			    	storeFeaturesList.add(storeFeatures);
			    }
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at getStoreFeatures-->"+e.getMessage());
			}finally{
				close();
			}
			return storeFeaturesList;
		}
	 
	 public List<String> createNewStore(MultivaluedMap<String, String> queryParams){
		 
		 ArrayList<LocalStoreForm> storeFormList = new ArrayList<LocalStoreForm>();
		 LocalStore store = new LocalStore();
		 
		 ArrayList<LocalStoreForm> contentFormList = new ArrayList<LocalStoreForm>();
		 List<LocalStoreFeatures> featuresList = new ArrayList<LocalStoreFeatures>();
		 ArrayList<LocalStoreForm> servicesFormList = new ArrayList<LocalStoreForm>();
		 List<LocalStoreServices> serviceList = new ArrayList<LocalStoreServices>();
		 ArrayList<LocalStoreForm> socialFormList = new ArrayList<LocalStoreForm>();
		 List<LocalStoreSocial> socialList = new ArrayList<LocalStoreSocial>();
		 
		  for(String paramName : queryParams.keySet()){
			  	
			  	LocalStoreForm contentForm = null; 
			  	
			  	if(paramName.contains("-")){
			  		int formId = Integer.parseInt(paramName.split("-")[2]);
			    	String inputName = paramName.split("-")[0]+"-"+paramName.split("-")[1];
			    	String inputValue = queryParams.getFirst(paramName).replace("'", "\\'");
			  		if(paramName.split("-")[0].equals("content")){
				    	 contentForm = new LocalStoreForm(formId, inputName, inputValue);
				    	 contentFormList.add(contentForm);
			  		}
			  		if(paramName.split("-")[0].equals("services")){
				    	 contentForm = new LocalStoreForm(formId, inputName, inputValue);
				    	 servicesFormList.add(contentForm);
			  		}
			  		if(paramName.split("-")[0].equals("social")){
				    	 contentForm = new LocalStoreForm(formId, inputName, inputValue);
				    	 socialFormList.add(contentForm);
			  		}
			  	}else{
			    	String inputName = paramName;
			    	String inputValue = queryParams.getFirst(paramName);
			  		contentForm = new LocalStoreForm(1, inputName, inputValue);
			  		storeFormList.add(contentForm);
			  	}
		  }
		  
		  
		  for(int f = 1; f < 10; f++){
			  LocalStoreServices services  = new LocalStoreServices();
			  for(LocalStoreForm servicesDetails: servicesFormList){
				  
				  if(servicesDetails.getFormId() == f && servicesDetails.getFormId()!=0){
					  if(servicesDetails.getInputName().equals("services-name")){
						  services.setServicesName(servicesDetails.getInputValue());
					  }
					  if(servicesDetails.getInputName().equals("services-description")){
						  services.setServicesDescription(servicesDetails.getInputValue());
					  }
					  if(servicesDetails.getInputName().equals("services-image")){
						  services.setServicesImage(servicesDetails.getInputValue());
					  }
					  if(servicesDetails.getInputName().equals("services-iscustomizable")){
						  
						  services.setIsServicesCustomizable(servicesDetails.getInputValue().equals("true")?1:0);
					  }
					  if(servicesDetails.getInputName().equals("services-price-range")){
						  services.setServicesPriceRange(servicesDetails.getInputValue());
					  }
				  }
			  }
			  if(services.getServicesName() != null){
				  serviceList.add(services);
			  }
		  }
		  
		  for(int f = 1; f < 10; f++){
			  LocalStoreFeatures features = new LocalStoreFeatures();
			  for(LocalStoreForm contentDetails: contentFormList){
				  
				  if(contentDetails.getFormId() == f && contentDetails.getFormId()!=0){
					  if(contentDetails.getInputName().equals("content-title")){
						  features.setFeaturesTitle(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("content-description")){
						  features.setFeaturesDescription(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("content-youtubeid")){
						  features.setFeaturesVideoLink(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("content-image")){
						  features.setFeaturesCover(contentDetails.getInputValue());
					  }
				  }
			  }
			  if(features.getFeaturesTitle() != null){
				  featuresList.add(features);
			  }
		  }
		  for(int f = 1; f < 10; f++){
			  LocalStoreSocial social = new LocalStoreSocial();
			  for(LocalStoreForm contentDetails: socialFormList){
				  
				  if(contentDetails.getFormId() == f && contentDetails.getFormId()!=0){
					  if(contentDetails.getInputName().equals("social-name")){
						  social.setSocialName(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("social-url")){
						  social.setSocialUrl(contentDetails.getInputValue());
					  }
				  }
			  }
			  if(social.getSocialName() != null){
				  socialList.add(social);
			  }
		  }
			
		  for(int f = 1; f < 10; f++){
			  
			  for(LocalStoreForm contentDetails: storeFormList){
				  
				  if(contentDetails.getFormId() == f && contentDetails.getFormId()!=0){
					  if(contentDetails.getInputName().equals("storename")){
						  store.setStoreName(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("storetagline")){
						  store.setStoreTagLine(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("storedescription")){
						  store.setStoreDescription(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("storelogo")){
						  store.setStoreLogo(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("storeimage")){
						  store.setStoreImage(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("isverified")){
						  store.setIsStoreVerified(contentDetails.getInputValue().equals("true")?1:0);
					  }
					  if(contentDetails.getInputName().equals("estdate")){
						  store.setStoreEstablishedDate(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("address1")){
						  store.setStoreAddress(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("landmark")){
						  store.setStoreLandmark(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("city")){
						  store.setStoreCityId(Integer.parseInt(contentDetails.getInputValue()));
					  }
					  if(contentDetails.getInputName().equals("city")){
						  store.setStoreCityName(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("area")){
						  store.setStoreAreaId(Integer.parseInt(contentDetails.getInputValue()));
					  }
					  if(contentDetails.getInputName().equals("area")){
						  store.setStoreAreaName(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("pin")){
						  store.setStorePin(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("phonenumber")){
						  store.setStoreContact1(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("email")){
						  store.setStoreEmail(contentDetails.getInputValue());
					  }
					  if(contentDetails.getInputName().equals("website")){
						  store.setStoreWebsite(contentDetails.getInputValue());
					  }
				  }
			  }
			 
		  }
		  
		  CreateStoreUtil c= new CreateStoreUtil();
		  List<String> status = c.createNewStore(store, serviceList, featuresList, socialList);
		  return status;
	 }
	 
	 
	/*
	 * public List<String> createNewStoreTest(MultivaluedMap<String, String>
	 * queryParams){
	 * 
	 * ArrayList<LocalStoreForm> storeFormList = new ArrayList<LocalStoreForm>();
	 * LocalStore store = new LocalStore();
	 * 
	 * for(String paramName : queryParams.keySet()){
	 * 
	 * LocalStoreForm contentForm = null;
	 * 
	 * 
	 * String inputName = paramName; String inputValue =
	 * queryParams.getFirst(paramName); contentForm = new LocalStoreForm(1,
	 * inputName, inputValue); storeFormList.add(contentForm);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * for(int f = 1; f < 10; f++){
	 * 
	 * for(LocalStoreForm contentDetails: storeFormList){
	 * 
	 * if(contentDetails.getFormId() == f && contentDetails.getFormId()!=0){
	 * 
	 * if(contentDetails.getInputName().equals("storeimage")){
	 * store.setStoreImage(contentDetails.getInputValue()); }
	 * 
	 * } }
	 * 
	 * } List<String> errorList = new ArrayList<String>(); TypeUtil type = new
	 * TypeUtil(); type.uploadStoreImage(store.getStoreImage(), "test1",
	 * "/var/www/images/"); return errorList; }
	 */
	 
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
