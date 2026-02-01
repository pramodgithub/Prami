package com.prami.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.prami.bean.LocalStore;
import com.prami.bean.LocalStoreFeatures;
import com.prami.dao.LocalStoreDao;

@Path("/localstore")
public class LocalStoreResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;

	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/designers")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<LocalStore> getDesigners(@FormParam("userId") String userId, @FormParam("cityId") int cityId, @FormParam("page") int page, @FormParam("limit") int limit, @FormParam("pattern") int pattern, @FormParam("searchArea[]") List<Integer> areas, @FormParam("sort") String sort) {
		  LocalStoreDao localStore = new LocalStoreDao();
		  return localStore.getStoreList(userId, cityId, page, limit, pattern, areas, sort);
	 }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/store")
	  @Produces(MediaType.APPLICATION_JSON)
	  public LocalStore getStore(@FormParam("storeId") int storeId) {
		  LocalStoreDao localStore = new LocalStoreDao();
		  return localStore.getStoreDetails(storeId);
	 }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/features")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<LocalStoreFeatures> getStoreFeatures(@FormParam("storeId") int storeId, @FormParam("store") int storeType) {
		  LocalStoreDao storeFeatures = new LocalStoreDao();
		  return storeFeatures.getStoreFeatures(storeId, storeType);
	 }
	 
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/addNewStore")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<String> addNewStore(MultivaluedMap<String, String> queryParams){
		  LocalStoreDao newStore = new LocalStoreDao();
		  return newStore.createNewStore(queryParams);
	 }
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/addNewStoreTest")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<String> addNewStoreTest(MultivaluedMap<String, String> queryParams){
		  List<String> errorList = new ArrayList<String>();
		  
		  errorList.add("Failed Services..!");
		  errorList.add("Failed Features..!");
		  errorList.add("Failed Social..!");
		  return errorList;
	 }
}
