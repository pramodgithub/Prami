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

import com.prami.bean.LandingStructure;
import com.prami.bean.Product;
import com.prami.bean.SubCategory;
import com.prami.dao.ProductDao;
import com.prami.dao.StructureDao;

@Path("/structure")
public class StructureResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;


	  // Return the list of todos to the user in the browser
	  // Return the list of todos for applications
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<SubCategory> getPageStructureData(@FormParam("collectionGroup") int groupId, @FormParam("box") int box,@FormParam("limit") int limit) {
	     StructureDao structure = new StructureDao();
	     
	     return structure.getProductStructure(groupId, box, limit);
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/group/store")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<SubCategory> getPageStructureByStoreData(@FormParam("collectionGroup") int groupId, @FormParam("storeId") int storeId, @FormParam("box") int box,@FormParam("limit") int limit) {
		     StructureDao structure = new StructureDao();
		     
		     return structure.getProductStructureByStore(groupId, storeId, box, limit);
		  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/box")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Product> getBoxData(@FormParam("structureBox") String structureBoxId,@FormParam("prodCount") String count ) {
	     ProductDao boxProduct = new ProductDao();
	         
	     return boxProduct.getBoxProductList(Integer.parseInt(structureBoxId), Integer.parseInt(count));
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/landingPageStructure")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<LandingStructure> getlandingPageData(@FormParam("page") String page) {
		  StructureDao structure = new StructureDao();	         
	     return structure.getlandingPageData(Integer.parseInt(page));
	  }
}
