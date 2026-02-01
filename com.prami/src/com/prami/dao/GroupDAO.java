package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.prami.bean.Group;
import com.prami.db.MySQLAccess;

public class GroupDAO {
	
	ResultSet resultSet = null;
	Connection connect = null;
	CallableStatement statement = null;
	MySQLAccess dao = new MySQLAccess();
	
	public List<Group> getGroups(int catId){
		List<Group> groupList = new ArrayList<Group>();
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_collection_groups(?)}";

			statement = connect.prepareCall(query);
			statement.setInt(1, catId);
			resultSet = statement.executeQuery();
		    
		    while (resultSet.next()) {
		    	Group group = new Group(resultSet.getInt("collectionGroupID"), resultSet.getString("collectionGroupName"), resultSet.getString("collectionGroupImage"), resultSet.getString("collectiongroupDesc"), resultSet.getInt("collectiongroupStatus"));
		    	groupList.add(group);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at GroupDAO --> getGroups-->"+e.getMessage());
		}finally{
			close();
		}
		return groupList;
	}
	
	public List<Group> getAllGroups(int catId){
		List<Group> groupList = new ArrayList<Group>();
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_all_collection_groups()}";

			statement = connect.prepareCall(query);
			
			resultSet = statement.executeQuery();
		    
		    while (resultSet.next()) {
		    	Group group = new Group(resultSet.getInt("collectionGroupID"), resultSet.getString("collectionGroupName"), resultSet.getString("collectionGroupImage"), resultSet.getInt("collectionGroupID"), resultSet.getString("collectionGroupName") );
		    	groupList.add(group);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at GroupDAO --> getGroups-->"+e.getMessage());
		}finally{
			close();
		}
		return groupList;
	}
	
	public List<Group> getAllGroups(){
		List<Group> groupList = new ArrayList<Group>();
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_all_collection_groups()}";

			statement = connect.prepareCall(query);
			resultSet = statement.executeQuery();
		    
		    while (resultSet.next()) {
		    	Group group = new Group(resultSet.getInt("collectionGroupID"), resultSet.getString("collectionGroupName"), resultSet.getString("collectionGroupImage"), resultSet.getInt("catID"), resultSet.getString("catName"));
		    	groupList.add(group);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at GroupDAO --> getAllGroups()-->"+e.getMessage());
		}finally{
			close();
		}
		return groupList;
	}
	
	public List<Group> getAllGroupsByStore(int storeId){
		List<Group> groupList = new ArrayList<Group>();
		try{
			connect = dao.getConnection();
			String query = "{call sp_get_all_collection_groups_by_store(?)}";

			statement = connect.prepareCall(query);
			statement.setInt(1, storeId);
			resultSet = statement.executeQuery();
		    
		    while (resultSet.next()) {
		    	Group group = new Group(resultSet.getInt("collectionGroupID"), resultSet.getString("collectionGroupName"), resultSet.getString("collectionGroupImage"), resultSet.getInt("catID"), resultSet.getString("catName"));
		    	groupList.add(group);
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at GroupDAO --> getAllGroupsByStore()-->"+e.getMessage());
		}finally{
			close();
		}
		return groupList;
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
