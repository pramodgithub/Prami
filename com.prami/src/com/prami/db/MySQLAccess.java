package com.prami.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLAccess {
	 private Connection connect = null;
	 
	 public Connection getConnection() throws Exception {
		    try {
		      String URL = "jdbc:mysql://localhost:3306/trendydb";
		      
		      String uname = "dbuser";
		      String password = "Password#123";
		    
		      Class.forName("com.mysql.jdbc.Driver");
		      connect = DriverManager.getConnection(URL,  uname, password);
		    	return connect;	      
		    } catch (Exception e) {
		      throw e;
		    } 
		  }
	 	 
}
