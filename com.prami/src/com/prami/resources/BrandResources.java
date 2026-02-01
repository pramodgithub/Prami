package com.prami.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.prami.dao.BrandDao;

@Path("/brands")
public class BrandResources {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;

	@POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/getAllBrands")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<String> getAllBrands() {
		  BrandDao brands = new BrandDao();
		  return brands.getAllAvailableBrands();
	 }
}
