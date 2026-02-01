package com.prami.resources;

import java.sql.SQLException;
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

import com.prami.dao.ProductDao;
import com.prami.dao.UpdateDao;
import com.prami.dao.UpdateLogDAO;

@Path("/update")
public class UpdateResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;


	  // Return the list of todos to the user in the browser
	  // Return the list of todos for applications
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/productList")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getProductUrlList(@FormParam("storeId") int storeId,
			  @FormParam("offset") int offset,
			  @FormParam("limit") int limit){
	     ProductDao productUrls = new ProductDao();
	     return Response.status(201).entity(productUrls.getProductURLList(storeId, offset, limit)).build();
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/disableProduct")
	  @Produces(MediaType.TEXT_PLAIN)
	  public int disableProduct(@FormParam("prodId") int prodId,
			  @FormParam("token") String token,
			  @FormParam("isReUpdate") boolean reUpdate) throws SQLException{
	     
		  		UpdateDao productUpdate = new UpdateDao();
		  		return productUpdate.disableProduct(prodId, token, reUpdate);
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/productUnavailable")
	  @Produces(MediaType.TEXT_PLAIN)
	  public int productUnavailable(
			  @FormParam("prodId") int prodId,
			  @FormParam("token") String token,
			  @FormParam("isReUpdate") boolean reUpdate) throws SQLException{
	     
		  		UpdateDao productUpdate = new UpdateDao();
		  		return productUpdate.productUnavailable(prodId, token, reUpdate);
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/reRunUpdate")
	  @Produces(MediaType.TEXT_PLAIN)
	  public int reRunUpdayte(
			  @FormParam("prodId") int prodId,
			  @FormParam("token") String token,
			  @FormParam("isReUpdate") boolean reUpdate) throws SQLException{
	     
		  		UpdateDao productUpdate = new UpdateDao();
		  		return productUpdate.reRunUpdate(prodId, token, reUpdate);
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/productPrice")
	  @Produces(MediaType.TEXT_PLAIN)
	  public int updatePrice( @FormParam("prodId") int prodId,
			  @FormParam("newPrice") Float price,
			  @FormParam("prodStatus") int status,
			  @FormParam("updateType") int type,
			  @FormParam("token") String token,
			  @FormParam("isReUpdate") boolean reUpdate,
			  @FormParam("availableSizes") String prodSizes) throws SQLException{
		  			  
		  		UpdateDao productUpdate = new UpdateDao();
		  		return productUpdate.updateProductPrice(prodId, price, status, type, token, reUpdate, prodSizes);
	  }
	 
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/updateAffiliateUrl")
	  @Produces(MediaType.APPLICATION_JSON)
	  public boolean updateAffiliateUrl(@FormParam("storeIds[]") List<Integer> storeIds,@FormParam("newAffiliateUrl[]") List<String> newAffiliateUrl){
		  UpdateDao affUpdate = new UpdateDao();
		  return affUpdate.updateAffiliationURL(storeIds, newAffiliateUrl);
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/updateCollectionSequence")
	  @Produces(MediaType.APPLICATION_JSON)
	  public boolean updateCollectionSequence(){
		  UpdateDao affUpdate = new UpdateDao();
		  
		  return affUpdate.updateCollectionSequence();
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/updateTrendFactor")
	  @Produces(MediaType.APPLICATION_JSON)
	  public boolean updateTrendFactor(){
		  UpdateDao affUpdate = new UpdateDao();
		  return affUpdate.updateProductTrendFactor();
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/updateFailedProductList")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getupdateFailedProductList(@FormParam("token") String token,
			  @FormParam("storeId") int storeId,
			  @FormParam("offset") int offset,
			  @FormParam("limit") int limit){
	     UpdateLogDAO updateFailedIdList = new UpdateLogDAO();
	     return Response.status(201).entity(updateFailedIdList.getUpdateFailedProductList(token, storeId, offset, limit)).build();
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/updateLog")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response updateFailedLog(@FormParam("token") String token,
			  @FormParam("productId") int productId,
			  @FormParam("isReUpdate") boolean reUpdate,
			  @FormParam("updateInfo") float updateInfo,
			  @FormParam("updateType") int updateType){
		  UpdateLogDAO updateLog = new UpdateLogDAO();
	     return Response.status(201).entity(updateLog.addUpdateLog(productId, updateInfo, updateType, token, reUpdate)).build();
	  }
}
