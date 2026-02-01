package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.Collection;
import com.prami.bean.CollectionSEO;
import com.prami.bean.Group;
import com.prami.bean.Structure;
import com.prami.db.MySQLAccess;
import com.prami.util.StructureUtil;

public class CollectionDao {
	Statement statement = null;
	Statement statement2 = null;
	ResultSet resultSet = null;
	ResultSet resultSet2 = null;
	Connection connect = null;
	CallableStatement stmt = null;
	MySQLAccess dao = new MySQLAccess();
	
	public List<CollectionSEO> getCollectionSeo(int collectionID){
		List<CollectionSEO> collectionSEOList = new ArrayList<CollectionSEO>();
		CollectionSEO collection;
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			
			resultSet = statement.executeQuery("select collectionName, subcatName, subcatTitle, subcatDescription, subcatImage, seoKeywords FROM subcategorytbl where SubCatID="+collectionID);
			while (resultSet.next()) {
		    	collection = new CollectionSEO(resultSet.getString("collectionName"), resultSet.getString("subcatName"), resultSet.getString("subcatTitle"), resultSet.getString("subcatDescription"), resultSet.getString("subcatImage"), resultSet.getString("seoKeywords"));
		    	collectionSEOList.add(collection);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CollectionDao --> getCollectionSeo - "+e.getMessage());
		}finally{
			close();
		}
		return collectionSEOList;
	}
	public List<CollectionSEO> getAllCollectionSeo(){
		List<CollectionSEO> collectionSEOList = new ArrayList<CollectionSEO>();
		CollectionSEO collection;
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			
			resultSet = statement.executeQuery("select subcatID, collectionName, subcatName, subcatTitle, subcatDescription, subcatImage, seoKeywords FROM subcategorytbl where subcatStatus = 1");
			while (resultSet.next()) {
		    	collection = new CollectionSEO(resultSet.getInt("subcatID"), resultSet.getString("collectionName"), resultSet.getString("subcatName"), resultSet.getString("subcatTitle"), resultSet.getString("subcatDescription"), resultSet.getString("subcatImage"), resultSet.getString("seoKeywords"));
		    	collectionSEOList.add(collection);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CollectionDao --> getAllCollectionSeo - "+e.getMessage());
		}finally{
			close();
		}
		return collectionSEOList;
	}
	
	public List<Group> getAllGroupSeo(){
		List<Group> groupSEOList = new ArrayList<Group>();
		Group group;
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			
			resultSet = statement.executeQuery("SELECT collectionGroupID, collectionGroupName, collectionGroupImage, collectiongroupDesc, collectiongroupSeoKeywords FROM collectiongrouptbl where collectiongroupStatus = 1;");
			while (resultSet.next()) {
		    	group = new Group(resultSet.getInt("collectionGroupID"), resultSet.getString("collectionGroupName"), resultSet.getString("collectionGroupImage"), resultSet.getString("collectiongroupDesc"), resultSet.getString("collectiongroupSeoKeywords"));
		    	groupSEOList.add(group);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CollectionDao --> getAllGroupSeo - "+e.getMessage());
		}finally{
			close();
		}
		return groupSEOList;
	}
	
	public List<Collection> getAllGroups(int groupId, int groupPattern){
		List<Collection> collectionGroupList = new ArrayList<Collection>();
		List<Structure> page;
		StructureUtil pagePattern = new StructureUtil();
		int patternIndex = 0;
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_featured_page_structure_list(?)}";

			stmt = connect.prepareCall(query);
			stmt.setInt(1, groupId);
			resultSet = stmt.executeQuery();
			page = pagePattern.getPagePattern(groupPattern);
		    while (resultSet.next()) {
		    	
		    	Collection collections = new Collection(resultSet.getInt("subCatId"), resultSet.getString("subcatName"), resultSet.getString("catName"), resultSet.getString("subcatTitle"), resultSet.getString("subcatDescription"), resultSet.getString("subcatImage"), page.get(patternIndex).getPageColumn());
		    	collectionGroupList.add(collections);
		    	if(page.size() == patternIndex+1){
		    		patternIndex = 0;
		    	}else{
		    		patternIndex ++;
		    	}
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at CollectionDao --> getAllGroups-->"+e.getMessage());
		}finally{
			close();
		}
		return collectionGroupList;
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