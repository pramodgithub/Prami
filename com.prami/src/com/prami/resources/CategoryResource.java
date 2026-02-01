package com.prami.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.prami.bean.Category;
import com.prami.bean.CategoryType;
import com.prami.dao.CategoryDao;

	@Path("/categories")
	public class CategoryResource {
		 @Context
		  UriInfo uriInfo;
		  @Context
		  Request request;


		  // Return the list of todos to the user in the browser
		  // Return the list of todos for applications
		
		  @POST
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Category> getCategories() {
		     CategoryDao c = new CategoryDao();
		     return c.getCategoryList();
		  }
		  
		 
		  @GET
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Category> getCategoriesBySelection(@QueryParam("selected") Integer s) {
		     CategoryDao c = new CategoryDao();
		     return c.getCategoryListBySelection(s);
		  }
		  
		  @GET
		  @Path("/type")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<CategoryType> getProductTypeBySelection(@QueryParam("selected") Integer t) {
		     CategoryDao c = new CategoryDao();
		     return c.getProductTypeBySelection(t);
		  }
		  
		  @GET
		  @Path("/productType")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<CategoryType> getProductTypesByProducttype(@QueryParam("type") Integer t) {
		     CategoryDao c = new CategoryDao();
		     return c.getProductTypesByProductType(t);
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/collectionType")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<CategoryType> getProductTypeByCollection(@FormParam("collection") String collectionId) {
		     CategoryDao c = new CategoryDao();
		     return c.getProductTypeByCollection(Integer.parseInt(collectionId));
		  }
		 
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/selectedType")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<CategoryType> getProductTypeByTypeSelection(@FormParam("selectedType") String typeId,@FormParam("collection") String collectionId,@FormParam("productid") String productId) {
		     CategoryDao c = new CategoryDao();
	    	 return c.getProductTypeByTypeSelection(Integer.parseInt(typeId),Integer.parseInt(collectionId),Integer.parseInt(productId));
		  }

		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/collectionName")
		  @Produces(MediaType.TEXT_PLAIN)
		  public String getCollectionName(@FormParam("collection") String collectionId) {
		     CategoryDao c = new CategoryDao();
		     return c.getCollectionName(Integer.parseInt(collectionId));
		  }
		  
		  @GET
		  @Path("/searchCategories")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<CategoryType> getProductTypeBySearch(@QueryParam("key") String key) {
		     CategoryDao c = new CategoryDao();
		     return c.getProductTypeBySearch(key);
		  }
		  @POST
		  @Path("/selectedTypeBySearch")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<CategoryType> getProductTypeBySearch(@FormParam("selectedType") String typeId,@FormParam("searchkey") String key) {
		     CategoryDao c = new CategoryDao();
		     return c.getProductTypeBySearchType(Integer.parseInt(typeId),key);
		  }
		  @GET
		  @Path("/collection")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Category> getCollection(@QueryParam("cat") Integer c) {
		     CategoryDao collection = new CategoryDao();
		     return collection.getCollectionCategories(c);
		  }
		  @GET
		  @Path("/theme")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Category> getCollectionByTheme(@QueryParam("theme") Integer theme) {
		     CategoryDao collection = new CategoryDao();
		     return collection.getThemeCategories(theme);
		  }
		  
		  	
	}

