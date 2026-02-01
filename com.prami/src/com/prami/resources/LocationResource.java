package com.prami.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.prami.bean.Cities;
import com.prami.dao.LocationDao;

@Path("/location")
public class LocationResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;

	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/cities")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Cities> getCities(@FormParam("countryId") int countryId) {
		  LocationDao city = new LocationDao();
		  return city.getCities(countryId);
	 }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/area")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Cities> getAreasByCity(@FormParam("cityId") int cityId) {
		  LocationDao city = new LocationDao();
		  return city.getAreasByCity(cityId);
	 } 
	 
	  
}
