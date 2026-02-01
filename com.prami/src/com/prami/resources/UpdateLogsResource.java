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

import com.prami.bean.UpdateStore;
import com.prami.dao.StoreDao;
import com.prami.dao.UpdateLogDAO;

@Path("/productUpdateLog")
public class UpdateLogsResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;


	  // Return the list of todos to the user in the browser
	  // Return the list of todos for applications
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/getLogByToken")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getLogByToken(@FormParam("update-token") String token, @FormParam("offset") String offset, @FormParam("limit") String limit){
	     UpdateLogDAO updateLog = new UpdateLogDAO();
	    return Response.status(201).entity(updateLog.getUpdatelogByToken(token, Integer.parseInt(offset), Integer.parseInt(limit))).build();
	     
	  }

	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/getUpdateLogList")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getUpdateLogList(@FormParam("page") String page, @FormParam("limit") String limit){
		 UpdateLogDAO updateLogDao = new UpdateLogDAO();
	     
	    return Response.status(201).entity(updateLogDao.getUpdatelogsList(Integer.parseInt(page), Integer.parseInt(limit))).build();
	     
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/getProductUpdateLogDetails")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getProductUpdateLogDetails(@FormParam("token") String token, @FormParam("storeId") int storeId, @FormParam("updateType") String updateType, @FormParam("offset") int offset, @FormParam("limit") int limit){
		 UpdateLogDAO updateLogDao = new UpdateLogDAO();
	     //System.out.println(token+"=="+storeId+"=="+updateType+"=="+offset+"=="+limit);
	    return Response.status(201).entity(updateLogDao.getProductUpdateLogsDetails(token, storeId, updateType, offset, limit )).build();
	     
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/updateStoreTokenLog")
	  @Produces(MediaType.APPLICATION_JSON)
	  public boolean updateStoreTokenLog(@FormParam("storeId[]") List<Integer> storeIds, @FormParam("update-token") String token) {
	     StoreDao store = new StoreDao();
	     return store.updateStoreTokenLog(token, storeIds);
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/getStoreUpdateLog")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<UpdateStore> getUpdateStoreLog() {
		  StoreDao updateStore = new StoreDao();
	     return updateStore.getUpdateStoreLog();
	  }
}
