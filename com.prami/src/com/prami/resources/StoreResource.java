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

import com.prami.bean.CollectionCount;
import com.prami.bean.SimilarityCheck;
import com.prami.bean.OnlineStore;
import com.prami.dao.StoreDao;
import com.prami.util.OnlineStoreUtil;
import com.prami.util.OnlineStoreUtil.StoreInfo;

@Path("/store")
public class StoreResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;


	  // Return the list of todos to the user in the browser
	  // Return the list of todos for applications
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/storeList")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<OnlineStore> getStoreList() {
	     StoreDao store = new StoreDao();
	     return store.getStoreList();
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/storeInfo")
	  @Produces(MediaType.APPLICATION_JSON)
	  public StoreInfo getstoreProperties(@FormParam("storeId") int storeId) {
	     OnlineStoreUtil storeUtil = new OnlineStoreUtil();
	     return storeUtil.getOnlineStoreDetails(storeId);
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/storeProductCount")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<SimilarityCheck> storeProductCount() {
	     StoreDao store = new StoreDao();
	     return store.getStoreProductCount();
	  }
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/storeCollectionCount")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<CollectionCount> storeCollectionCount(@FormParam("storeId") String storeId) {
	     StoreDao store = new StoreDao();
	     return store.getStoreCollectionCount(Integer.parseInt(storeId));
	  }
	  
	  
	  
	   
}
