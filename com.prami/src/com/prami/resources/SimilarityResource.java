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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.prami.bean.SimilarProduct;
import com.prami.dao.SimilarProductDao;
import com.prami.dao.UpdateDao;

@Path("/similarity")
public class SimilarityResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;

	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/similairtyProductList")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<SimilarProduct> getImagePathSimilarityCheckList(@FormParam("collectionId") String collectionId) {
		  SimilarProductDao similarity = new SimilarProductDao();
		  return similarity.getImagePathSimilarityCheck(Integer.parseInt(collectionId));
	 }
	 
	  @POST
	  @Consumes("application/json")
	  @Path("/admin/updateSimilarProduct")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response updateSimilarProducts(String similarProdList){
		  UpdateDao similarUpdate = new UpdateDao();
		  return Response.status(201).entity(similarUpdate.updateSimilarProducts(similarProdList)).build();
	     
	  }
}
