package com.prami.dao;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Random;

import com.prami.bean.User;
import com.prami.db.MySQLAccess;
import com.prami.util.FileUtil;

public class AuthDAO {
	Statement statement = null;
	ResultSet resultSet = null;
	Connection connect = null;
	PreparedStatement pst = null;
	MySQLAccess dao = new MySQLAccess();
	public Boolean authenticate(String username, String password) throws Exception {
		Boolean flag = false;
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT userID FROM usertbl where userName='"+username+"' and password='"+password+"'");
		    resultSet = statement.getResultSet();
		    
		    flag= resultSet.first();
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> authenticate"+e.getMessage());
			return flag;
		}finally{
			close();
		}
		return flag;
    }

    public String issueToken(String username) {
    	 String token = "";
    	 int days = 30;
		 try{
			 java.util.Date date= new java.util.Date();
			 long currentTime = new Timestamp(date.getTime()).getTime();
			 long tokenValidity = (currentTime + (days*86400000));
			 Random random = new SecureRandom();
			 connect = dao.getConnection();   	
	    	 token = new BigInteger(130, random).toString(32);
	    	 String query = "UPDATE usertbl SET token='"+token+"',tokenValidity='"+tokenValidity+"' WHERE userName='"+username+"'";
	    	 pst = connect.prepareStatement(query);
	    	 pst.executeUpdate();
	    	 
	     }catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at AuthDAO --> issueToken"+e.getMessage());
		}finally{
			close();
		}
		 return token;
        
    }
    public String isUnameAvailable(String username, String userEmail) throws Exception {
		String message = "error";
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT userID FROM usertbl where userName='"+username+"' and status=1");
		    resultSet = statement.getResultSet();
		    
		    if(resultSet.first()){
		    	message = "Sorry! This Username is not available.";
		    }else{
		    	 statement.executeQuery("SELECT userID FROM usertbl where email='"+userEmail+"' and status=1");
				 resultSet = statement.getResultSet();
				 if(resultSet.first()){
				    	message = "Email is already registerd with us.";
				 }
		    }
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> isUnameAvailable()"+e.getMessage());
			return message;
		}finally{
			close();
		}
		return message;
    }
    
    public Boolean isOldPassCorrect(String userId, String password) throws Exception {
		Boolean flag = false;
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT userID FROM usertbl where userID = '"+userId+"' and password='"+password+"' and status=1");
		    resultSet = statement.getResultSet();
		    
		    flag= resultSet.first();
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> isOldPassCorrect()-->"+e.getMessage());
			return false;
		}finally{
			close();
		}
		return flag;
    }
    public boolean updateNewPassword(int userId, String password) {
   	 
		 try{
			 connect = dao.getConnection();
	    	 String query = "UPDATE usertbl SET password='"+password+"' WHERE userID='"+userId+"'";
	    	 pst = connect.prepareStatement(query);
	    	 pst.executeUpdate();
	    	 return true;
	     }catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at AuthDAO --> updateNewPassword()-->"+e.getMessage());
				return false;
		}finally{
			close();
		}
		
       
   }
    public HashMap<String, String> getUserID(String username) throws Exception {
		HashMap<String, String> userProfile = new HashMap<String, String>();
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT u.userID, ur.userRole FROM usertbl u left join user_roles ur on ur.userId = u.userID where u.userName='"+username+"' and status=1");
		    resultSet = statement.getResultSet();
		    
		    while(resultSet.next()){
		    	userProfile.put("userid", String.valueOf(resultSet.getInt("userID")));
		    	userProfile.put("role", resultSet.getString("userRole"));
		    }
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> getUserID()"+e.getMessage());
			
		}finally{
			close();
		}
		return userProfile;
    }
    
    public HashMap<String, String> getAuthUserRole(int userid){
		HashMap<String, String> userProfile = new HashMap<String, String>();
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT ur.userRole, u.token FROM usertbl u left join user_roles ur on ur.userId = u.userID where u.userID="+userid+" and status=1");
		    resultSet = statement.getResultSet();
		    
		    while(resultSet.next()){
		    	userProfile.put("role", resultSet.getString("userRole"));
		    	userProfile.put("token", resultSet.getString("token"));
		    }
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> getAuthUserRole()"+e.getMessage());
			
		}finally{
			close();
		}
		return userProfile;
    }
    
    
    public Boolean isEmailpresent(String userEmail) throws Exception {
		Boolean flag = false;
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT userID FROM usertbl where email='"+userEmail+"' and status=1");
		    resultSet = statement.getResultSet();
		    
		    flag= resultSet.first();
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> isEmailpresent()"+e.getMessage());
			return false;
		}finally{
			close();
		}
		return flag;
    }
    public Boolean registerUser(String username,String password,String email, String gender) throws Exception {
		Boolean flag = false;
		try{
			connect = dao.getConnection();
			java.util.Date date= new java.util.Date();
			 long currentTime = new Timestamp(date.getTime()).getTime();
			String query = "insert into usertbl (userName, password,email,gender,addedDate,status) values ('"+username+"','"+password+"','"+email+"','"+gender+"','"+currentTime+"',1)";
	    	pst = connect.prepareStatement(query);
	    	int rowsUpdated = pst.executeUpdate(); 
	    	if (rowsUpdated > 0) {
	    	   flag =true;
	    	}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> registerUser()-->"+e.getMessage());
			return false;
		}finally{
			close();
		}
		return flag;
    }
    public HashMap<String, String> generatePassword(String email){
    	 
    	 HashMap<String, String> profile = new HashMap<String, String>();
		 try{
			 Random random = new SecureRandom();
			 connect = dao.getConnection();   	
			 String password = new BigInteger(50, random).toString(32);
	    	 String query = "UPDATE usertbl SET password='"+password+"' WHERE email='"+email+"'";
	    	 pst = connect.prepareStatement(query);
	    	 pst.executeUpdate();
	    	 statement = connect.createStatement();
			 statement.executeQuery("SELECT userName FROM usertbl where email='"+email+"' and status=1");
			 resultSet = statement.getResultSet();
			 resultSet.next();
			 profile.put("username", resultSet.getString("userName"));
			 profile.put("password", password);
			 			 
	     }catch(Exception e){
				e.printStackTrace();
				
				System.out.println("failed at AuthDAO --> issueToken"+e.getMessage());
		}finally{
			close();
		}
		 return profile;
	}
    public Boolean getUserAuthentication(String token, int uID){
    	
    	Boolean flag = false;
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT * FROM usertbl where token='"+token+"' and userID='"+uID+"' and status=1");
		    resultSet = statement.getResultSet();
		    java.util.Date date= new java.util.Date();
			long currentTime = new Timestamp(date.getTime()).getTime();
		    while(resultSet.next()){
		    	if(Long.parseLong(resultSet.getString("tokenValidity")) > currentTime){
		    		flag = false;
		    	}else{
		    		flag= true;
		    	}
		    }
		   
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> getUserAuthentication()"+e.getMessage());
			return false;
		}finally{
			close();
		}
		return flag;
    	
    	
    }
		public Boolean getFBUserAuthentication(String fbID){
		    	
		    	Boolean flag = false;
				try{
					connect = dao.getConnection();
					
					statement = connect.createStatement();
				    statement.executeQuery("SELECT * FROM usertbl where facebookId='"+fbID+"' and status=1");
				    resultSet = statement.getResultSet();
				    flag= resultSet.first();
				   
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("failed at AuthDAO --> getFBUserAuthentication()-->"+e.getMessage());
					return false;
				}finally{
					close();
				}
				return flag;
		    	
		    	
		    }
		public Boolean getGoogleUserAuthentication(String googleId){
	    	
	    	Boolean flag = false;
			try{
				connect = dao.getConnection();
				
				statement = connect.createStatement();
			    statement.executeQuery("SELECT * FROM usertbl where googleId='"+googleId+"' and status=1");
			    resultSet = statement.getResultSet();
			    flag= resultSet.first();
			   
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at AuthDAO --> getGoogleUserAuthentication()-->"+e.getMessage());
				return false;
			}finally{
				close();
			}
			return flag;
	    	
	    	
	    }
		public String issueFbToken(String fbId) {
			 String token = "";
			 int days = 30;
			 try{
				 java.util.Date date= new java.util.Date();
				 long currentTime = new Timestamp(date.getTime()).getTime();
				 long tokenValidity = (currentTime + (days*86400000));
				 Random random = new SecureRandom();
				 connect = dao.getConnection();   	
		   	 token = new BigInteger(130, random).toString(32);
		   	 String query = "UPDATE usertbl SET token='"+token+"',tokenValidity='"+tokenValidity+"' WHERE facebookId='"+fbId+"'";
		   	 pst = connect.prepareStatement(query);
		   	 pst.executeUpdate();
		   	 
		    }catch(Exception e){
					e.printStackTrace();
					System.out.println("failed at AuthDAO --> issueFbToken()-->"+e.getMessage());
			}finally{
				close();
			}
			 return token;
		   
		}
		public String issueGoogleToken(String googleId) {
			 String token = "";
			 int days = 30;
			 try{
				 java.util.Date date= new java.util.Date();
				 long currentTime = new Timestamp(date.getTime()).getTime();
				 long tokenValidity = (currentTime + (days*86400000));
				 Random random = new SecureRandom();
				 connect = dao.getConnection();   	
		   	 token = new BigInteger(130, random).toString(32);
		   	 String query = "UPDATE usertbl SET token='"+token+"',tokenValidity='"+tokenValidity+"' WHERE googleId='"+googleId+"'";
		   	 pst = connect.prepareStatement(query);
		   	 pst.executeUpdate();
		   	 
		    }catch(Exception e){
					e.printStackTrace();
					System.out.println("failed at AuthDAO --> issueGoogleToken()-->"+e.getMessage());
			}finally{
				close();
			}
			 return token;
		   
		}
		public HashMap<String, String> getFBUserDetails(String facebookId) throws Exception {
			HashMap<String, String> profile = new HashMap<String, String>();
			try{
				connect = dao.getConnection();
				
				statement = connect.createStatement();
			    statement.executeQuery("SELECT userID,firstName,profileImage,token FROM usertbl where facebookId='"+facebookId+"' and status=1");
			    resultSet = statement.getResultSet();
			    
			    resultSet.next();
			    profile.put("userId", ""+resultSet.getInt("userID")+"");
			    profile.put("first_name", resultSet.getString("firstName"));
				profile.put("profile_image", resultSet.getString("profileImage"));
				
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at AuthDAO --> getUserDetails()"+e.getMessage());
				
			}finally{
				close();
			}
			return profile;
		}
		public HashMap<String, String> getGoogleUserDetails(String googleId) throws Exception {
			HashMap<String, String> profile = new HashMap<String, String>();
			try{
				connect = dao.getConnection();
				
				statement = connect.createStatement();
			    statement.executeQuery("SELECT userID,firstName,profileImage,token FROM usertbl where googleId='"+googleId+"' and status=1");
			    resultSet = statement.getResultSet();
			    
			    resultSet.next();
			    profile.put("userId", ""+resultSet.getInt("userID")+"");
			    profile.put("first_name", resultSet.getString("firstName"));
				profile.put("profile_image", resultSet.getString("profileImage"));
				
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at AuthDAO --> getUserDetails()"+e.getMessage());
				
			}finally{
				close();
			}
			return profile;
		}
		public Boolean registerFBUser(String fbId,String firstName,String lastName,String email, String gender,String picture) throws Exception {
			Boolean flag = false;
			try{
				connect = dao.getConnection();
				java.util.Date date= new java.util.Date();
				 long currentTime = new Timestamp(date.getTime()).getTime();
				 
				 FileUtil file = new FileUtil();
				 String destination = "/var/www/images/profile";
				 String profileImage = file.saveImage(picture,destination);
				 
				String query = "insert into usertbl (firstName, lastName,email,gender,profileImage,facebookId,addedDate,status) values ('"+firstName+"','"+lastName+"','"+email+"','"+gender+"','"+profileImage+"','"+fbId+"','"+currentTime+"',1)";
		    	pst = connect.prepareStatement(query);
		    	int rowsUpdated = pst.executeUpdate(); 
		    	if (rowsUpdated > 0) {
		    	   flag =true;
		    	}
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at AuthDAO --> registerUser()-->"+e.getMessage());
				return false;
			}finally{
				close();
			}
			return flag;
		}
		public Boolean registerGoogleUser(String googleId,String firstName,String lastName,String email, String gender,String picture) throws Exception {
			Boolean flag = false;
			try{
				connect = dao.getConnection();
				java.util.Date date= new java.util.Date();
				 long currentTime = new Timestamp(date.getTime()).getTime();
				 
				 FileUtil file = new FileUtil();
				 String destination = "/var/www/images/profile";
				 String profileImage = file.saveImage(picture,destination);
				 
				String query = "insert into usertbl (firstName, lastName,email,gender,profileImage,googleId,addedDate,status) values ('"+firstName+"','"+lastName+"','"+email+"','"+gender+"','"+profileImage+"','"+googleId+"','"+currentTime+"',1)";
		    	pst = connect.prepareStatement(query);
		    	int rowsUpdated = pst.executeUpdate(); 
		    	if (rowsUpdated > 0) {
		    	   flag =true;
		    	}
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at AuthDAO --> registerUser()-->"+e.getMessage());
				return false;
			}finally{
				close();
			}
			return flag;
		}
		public User getUserDetails(int userID, String authToken){
			User user = null ;
			
			try{
				connect = dao.getConnection();
				statement = connect.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM usertbl where userID="+userID+" and token='"+authToken+"' and status=1");
			    resultSet.next();
			    
			    user = new User(resultSet.getString("userName"),resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("gender"), resultSet.getString("email"),resultSet.getString("profileImage"));
			    
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("failed at AuthDAO --> getUserDetails() - "+e.getMessage());
			}finally{
				close();
			}
			return user;
		}
		
		
	public boolean updateProfile(String userId,String fname,String lname,String gender, String authToken) {
		 
		 try{
			 connect = dao.getConnection();   	
			 String query = "UPDATE usertbl SET firstName='"+fname+"',lastName='"+lname+"',gender='"+gender+"' WHERE userID='"+userId+"' and token='"+authToken+"'";
		   	 pst = connect.prepareStatement(query);
		   	 pst.executeUpdate();
		   	 return true;
		    }catch(Exception e){
					e.printStackTrace();
					System.out.println("failed at AuthDAO --> updateProfile()-->"+e.getMessage());
					return false;
			}finally{
				close();
			}
			
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
	      if (pst != null) {
		        pst.close();
		  }
	    } catch (Exception e) {

	    }
	  }
}
