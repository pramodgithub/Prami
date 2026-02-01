package com.prami.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.prami.bean.Activity;
import com.prami.bean.PurchaseHistory;
import com.prami.dao.AcitivityDao;

@Path("/activity")
public class ActivityResource {
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;

	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/all")
	  @Produces(MediaType.APPLICATION_JSON)
	  public void insertActivity(@FormParam("activityType") String activityType, @FormParam("userId") String userId,@FormParam("userIP") String userIP,@FormParam("productId") String productId,@FormParam("country") String country,@FormParam("region") String region,@FormParam("city") String city, @FormParam("zip") String zip,@FormParam("latitude") String latitude,@FormParam("longitude") String longitude) {
		  AcitivityDao activity = new AcitivityDao();
		  Activity objActivity = new Activity(Integer.parseInt(activityType), userId, Integer.parseInt(productId), userIP, country, region, city, zip, latitude, longitude);
		  activity.insertActivity(objActivity);
	 }
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/views")
	  @Produces(MediaType.APPLICATION_JSON)
	  public int productViews(@FormParam("productId") String productId) {
		  
		  AcitivityDao activity = new AcitivityDao();
		  return activity.getProductViews(Integer.parseInt(productId));
		 
	 }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/secure/purchaseHistory")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<PurchaseHistory> purchaseHistory(@FormParam("userID") String userId, @HeaderParam("authToken") String token) {
		  
		  AcitivityDao activity = new AcitivityDao();
		  return activity.getPurchaseHistory(Integer.parseInt(userId),  token);
		 
	 }
	  
}
