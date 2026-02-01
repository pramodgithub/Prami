package com.prami.resources;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.Base64;

import com.google.gson.JsonObject;

import com.prami.dao.AuthDAO;
import com.prami.dao.SendEmailDao;

// Will map the resource to the URL todos
@Path("/register")
public class RegisterResource {

  // Allows to insert contextual objects into the class,
  // e.g. ServletContext, Request, Response, UriInfo
  @Context
  UriInfo uriInfo;
  @Context
  Request request;


  // Return the list of todos to the user in the browser
  // Return the list of todos for applications
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response authenticateUser(ContainerRequestContext requestContext){
	  final String AUTHORIZATION_PROPERTY = "authorization";
	  final String AUTHENTICATION_SCHEME = "Basic";
	  final MultivaluedMap<String, String> headers = requestContext.getHeaders();
	  JsonObject json = new JsonObject(); 
      //Fetch authorization header
      final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
      final List<String> email = headers.get("email");
      final List<String> gender = headers.get("gender");
      String userEmail = email.get(0);
      String userGender = gender.get(0);
      ExecutorService emailExecutor = Executors.newSingleThreadExecutor(); 
      SendEmailDao sm = new SendEmailDao();
      //If no authorization information present; block access
      if(authorization == null || authorization.isEmpty())
      {
    	  return Response.status(Response.Status.UNAUTHORIZED).build();
      }
        
      //Get encoded username and password
      final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        
      //Decode username and password
      String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;

      //Split username and password tokens
      final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
      final String username = tokenizer.nextToken();
      final String password = tokenizer.nextToken();
      
      //Verifying Username and password
      	  
      try {

          AuthDAO auth = new AuthDAO();
    	 String isUnameEmailAvl = auth.isUnameAvailable(username,userEmail);
          if(isUnameEmailAvl.equals("error")){
        	  
        	 if(auth.registerUser(username,password,userEmail,userGender)){
        		 
        	 String emailType = "";
           	  if(userGender.equals("female")){
           		  emailType =  "welcome_female";
           	  }else if(userGender.equals("male")){
           		  emailType =  "welcome_male";
           	  }
           	  final String innerEmailType = new String(emailType);
           	  emailExecutor.execute(new Runnable() {
                     @Override
                     public void run() {
                         try {
                       	  sm.sendMail(userEmail, innerEmailType);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }
                 });
                 emailExecutor.shutdown();
        		 
	        	  String token = auth.issueToken(username);
	        	  HashMap<String, String> profile = auth.getUserID(username);
	        	  json.addProperty("auth", "success"); 
	        	  json.addProperty("token", token); 
	        	  json.addProperty("uname", username); 
	        	  json.addProperty("uid",profile.get("userid"));
	        	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
	        	  
        	 }else{
        		 json.addProperty("auth", "Error"); 
	           	  json.addProperty("description", "An Error occurred, Please try again later"); 
	           	  return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
        	 }
          }
          json.addProperty("auth", "Error"); 
          json.addProperty("description", isUnameEmailAvl); 
          return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();

      } catch (Exception e) {
    	  json.addProperty("auth", "Error"); 
    	  json.addProperty("description", "An Error occurred, Please try again later"); 
    	  return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      }
	 
  }
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Path("/secure/updateProfile")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUserProfile(@FormParam("token") String authToken,@FormParam("uid") String userId,  @FormParam("fname") String fname,@FormParam("lname") String lname,@FormParam("gender") String gender) {
	  AuthDAO auth = new AuthDAO();
	  JsonObject json = new JsonObject();  
	  try{
	  
		if( auth.updateProfile(userId, fname, lname, gender, authToken)){
			json.addProperty("auth", "success"); 
	   	    json.addProperty("description", "Profile updated successfully"); 
	   	    return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
		}else{
			json.addProperty("auth", "Error"); 
	   	    json.addProperty("description", "Failed to update profile"); 
	   	    return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
		}
	 
	  }catch (Exception e){
    	  json.addProperty("auth", "Error"); 
  	   	  json.addProperty("description", "An Error occurred, Please try again later"); 
  	   	  return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      }
 }

  @POST
  @Path("/resetPassword")
  @Produces(MediaType.APPLICATION_JSON)
  public Response resetPassword(ContainerRequestContext requestContext){
	  final String AUTHORIZATION_PROPERTY = "authorization";
	  final String AUTHENTICATION_SCHEME = "Basic";
	  final MultivaluedMap<String, String> headers = requestContext.getHeaders();
	  JsonObject json = new JsonObject(); 
      //Fetch authorization header
      final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
      final List<String> token = headers.get("token");
      final List<String> uid = headers.get("uid");
      String userToken = token.get(0);
      String userId = uid.get(0);
     
      //If no authorization information present; block access
      if(authorization == null || authorization.isEmpty())
      {
    	  return Response.status(Response.Status.UNAUTHORIZED).build();
      }
        
      //Get encoded username and password
      final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        
      //Decode username and password
      String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;

      //Split username and password tokens
      final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
      final String oldPassword = tokenizer.nextToken();
      final String newPassword = tokenizer.nextToken();
      
      //Verifying Username and password
      	  
      try {

          AuthDAO auth = new AuthDAO();
    	 
          if(auth.getUserAuthentication(userToken, Integer.parseInt(userId))){
      		if( auth.isOldPassCorrect(userId, oldPassword)){
      			if(auth.updateNewPassword(Integer.parseInt(userId), newPassword)){
      		
	      			json.addProperty("auth", "success"); 
	      	   	    json.addProperty("description", "Password updated successfully"); 
	      	   	    return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      			}else{
      				json.addProperty("auth", "Error"); 
          	   	    json.addProperty("description", "Password not updated, Please try again."); 
          	   	    return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      			}
      		}else{
      			json.addProperty("auth", "Error"); 
      	   	    json.addProperty("description", "Old password is not valid"); 
      	   	    return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      		}
      	  }else{	  
      		  json.addProperty("auth", "Error"); 
      	   	  json.addProperty("description", "An Error occurred, Please try again later"); 
      	   	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      	  }
	 
      }catch (Exception e){
    	  json.addProperty("auth", "Error"); 
  	   	  json.addProperty("description", "An Error occurred, Please try again later"); 
  	   	  return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      }
  }
} 
