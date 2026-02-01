package com.prami.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.prami.dao.LandingPageDao;

@Path("/lp")
public class LandingPageResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;


	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getLandingPage(@FormParam("landingType") int landingType, @FormParam("page") int page, @FormParam("limit") int limit) {
		LandingPageDao landingPage = new LandingPageDao();
		return landingPage.getlandingPageDetails(landingType, page, limit);
	}

}
