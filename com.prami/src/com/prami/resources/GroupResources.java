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

import com.prami.bean.Group;
import com.prami.dao.GroupDAO;

@Path("/groups")
public class GroupResources {
		 @Context
		  UriInfo uriInfo;
		  @Context
		  Request request;

		  // Return the list of todos to the user in the browser
		  // Return the list of todos for applications
		
		  @POST
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Group> getGroups(@FormParam("catId") String catId) {
		     GroupDAO g = new GroupDAO();
		     return g.getGroups(Integer.parseInt(catId));
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/all")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Group> getAllGroups() {
			  GroupDAO g = new GroupDAO();
			     return g.getAllGroups();
		 }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/byStore")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Group> getAllGroupsByStore(@FormParam("storeId") int storeId) {
			  GroupDAO g = new GroupDAO();
			     return g.getAllGroupsByStore(storeId);
		 }
}
