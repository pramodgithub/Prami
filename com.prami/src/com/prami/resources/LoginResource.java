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
import com.prami.bean.User;
import com.prami.dao.AuthDAO;
import com.prami.dao.SendEmailDao;



// Will map the resource to the URL todos
@Path("/login")
public class LoginResource {

  // Allows to insert contextual objects into the class,
  // e.g. ServletContext, Request, Response, UriInfo
  @Context
  UriInfo uriInfo;
  @Context
  Request request;


  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response authenticateUser(ContainerRequestContext requestContext){
	  final String AUTHORIZATION_PROPERTY = "authorization";
	  final String AUTHENTICATION_SCHEME = "Basic";
	  final MultivaluedMap<String, String> headers = requestContext.getHeaders();
	  JsonObject json = new JsonObject(); 
      //Fetch authorization header
      final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
        
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
    	 
          if(auth.authenticate(username, password)){
        	  String token = auth.issueToken(username);
        	  HashMap<String, String> profile = auth.getUserID(username);
        	  json.addProperty("auth", "success"); 
        	  json.addProperty("token", token); 
        	  json.addProperty("uname", username); 
        	  json.addProperty("uid",profile.get("userid"));
        	  json.addProperty("role",profile.get("role"));
        	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
          }
          json.addProperty("auth", "Error"); 
          json.addProperty("description", "Username or Password not valid."); 
          return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();

      } catch (Exception e) {
    	  json.addProperty("auth", "Error"); 
    	  json.addProperty("description", "An Error occurred, Please try again later"); 
    	  return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      }
	 
  }
  @POST
  @Path("/forgot")
  @Produces(MediaType.APPLICATION_JSON)
  public Response forgotPassword(ContainerRequestContext requestContext) {
	  SendEmailDao sm = new SendEmailDao();
	  AuthDAO auth = new AuthDAO();
	  JsonObject json = new JsonObject(); 
	  final MultivaluedMap<String, String> headers = requestContext.getHeaders();
	  final List<String> emailList = headers.get("email");
	  String email = emailList.get(0);
	  try{
	  
		  if(auth.isEmailpresent(email)){
			  if(sm.sendMail(email,"forgot_password")){
				  json.addProperty("auth", "success"); 
		    	  json.addProperty("description", "Email sent successfully at : "+email); 
		    	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
			  }else{
				  json.addProperty("auth", "Error"); 
		    	  json.addProperty("description", "An Error occurred, Please try again later"); 
		    	  return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
			  }
		  }else{
			  json.addProperty("auth", "Error"); 
		      json.addProperty("description", "Email is not registered with us"); 
		      return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
		  }
	  }catch(Exception e) {
		  json.addProperty("auth", "Error"); 
    	  json.addProperty("description", "An Error occurred, Please try again later"); 
    	  return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
	  }
 }
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Path("/auth")
  @Produces(MediaType.APPLICATION_JSON)
  public Boolean getAuthentication(@FormParam("token") String token,@FormParam("uid") String name) {
	  AuthDAO auth = new AuthDAO();
	  return auth.getUserAuthentication(token, Integer.parseInt(name));
	  
 }
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Path("/fb")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getFbAuthentication(@FormParam("id") String fbId,@FormParam("fname") String fName,@FormParam("lname") String lName,@FormParam("gender") String gender,@FormParam("email") String email,@FormParam("picture") String picture) {
	  JsonObject json = new JsonObject(); 
	  SendEmailDao sm = new SendEmailDao();
	  
	  ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		
	  try {

          AuthDAO auth = new AuthDAO();
    	 
          if(auth.getFBUserAuthentication(fbId)){
        	  String token = auth.issueFbToken(fbId);
        	  json.addProperty("auth", "success"); 
        	  json.addProperty("token", token); 
        	  HashMap<String, String> profile = auth.getFBUserDetails(fbId);
        	  json.addProperty("userId", profile.get("userId")); 
        	  json.addProperty("firstName", profile.get("first_name")); 
        	  json.addProperty("prodileImage", profile.get("profile_image"));
        	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
          }else if(auth.registerFBUser(fbId,fName,lName,email,gender,picture)){
        	  String emailType = "";
        	  if(gender.equals("female")){
        		  emailType =  "welcome_female";
        	  }else if(gender.equals("male")){
        		  emailType =  "welcome_male";
        	  }
        	  final String innerEmailType = new String(emailType);
        	  emailExecutor.execute(new Runnable() {
                  @Override
                  public void run() {
                      try {
                    	  sm.sendMail(email, innerEmailType);
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }
              });
              emailExecutor.shutdown();
              String token = auth.issueFbToken(fbId);
        	  json.addProperty("auth", "success"); 
        	  json.addProperty("token", token); 
        	  HashMap<String, String> profile = auth.getFBUserDetails(fbId);
        	  json.addProperty("userId", profile.get("userId")); 
        	  json.addProperty("firstName", profile.get("first_name")); 
        	  json.addProperty("prodileImage", profile.get("profile_image"));
        	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
          }
          json.addProperty("auth", "Error"); 
    	  json.addProperty("description", "Facebook authentication failed!"); 
    	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      } catch (Exception e) {
    	  json.addProperty("auth", "Error"); 
    	  json.addProperty("description", "Facebook authentication failed!"); 
    	  return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      }
	  
  }
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Path("/google")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getGoogleAuthentication(@FormParam("id") String googleId,@FormParam("fname") String fName,@FormParam("lname") String lName,@FormParam("gender") String gender,@FormParam("email") String email,@FormParam("picture") String picture) {
	  JsonObject json = new JsonObject(); 
	  SendEmailDao sm = new SendEmailDao();
	  
	  ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		
	  try {

          AuthDAO auth = new AuthDAO();
    	 
          if(auth.getGoogleUserAuthentication(googleId)){
        	  String token = auth.issueGoogleToken(googleId);
        	  json.addProperty("auth", "success"); 
        	  json.addProperty("token", token); 
        	  HashMap<String, String> profile = auth.getGoogleUserDetails(googleId);
        	  json.addProperty("userId", profile.get("userId")); 
        	  json.addProperty("firstName", profile.get("first_name")); 
        	  json.addProperty("prodileImage", profile.get("profile_image"));
        	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
          }else if(auth.registerGoogleUser(googleId,fName,lName,email,gender,picture)){
        	  String emailType = "";
        	  if(gender.equals("female")){
        		  emailType =  "welcome_female";
        	  }else if(gender.equals("male")){
        		  emailType =  "welcome_male";
        	  }
        	  final String innerEmailType = new String(emailType);
        	  emailExecutor.execute(new Runnable() {
                  @Override
                  public void run() {
                      try {
                    	  sm.sendMail(email, innerEmailType);
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }
              });
              emailExecutor.shutdown();
              String token = auth.issueGoogleToken(googleId);
        	  json.addProperty("auth", "success"); 
        	  json.addProperty("token", token); 
        	  HashMap<String, String> profile = auth.getGoogleUserDetails(googleId);
        	  json.addProperty("userId", profile.get("userId")); 
        	  json.addProperty("firstName", profile.get("first_name")); 
        	  json.addProperty("prodileImage", profile.get("profile_image"));
        	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
          }
          json.addProperty("auth", "Error"); 
    	  json.addProperty("description", "Facebook authentication failed!"); 
    	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      } catch (Exception e) {
    	  json.addProperty("auth", "Error"); 
    	  json.addProperty("description", "Facebook authentication failed!"); 
    	  return Response.status(Status.UNAUTHORIZED).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
      }
	  
  }
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Path("/secure/user")
  @Produces(MediaType.APPLICATION_JSON)
  public User getuserDetails(@FormParam("userID") String userID,@FormParam("token") String token) {
	  AuthDAO auth = new AuthDAO();
	  return  auth.getUserDetails( Integer.parseInt(userID), token);
  }
  

} 
