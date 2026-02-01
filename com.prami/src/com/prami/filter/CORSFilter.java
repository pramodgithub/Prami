package com.prami.filter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.prami.dao.AuthDAO;

@Provider
public class CORSFilter implements ContainerResponseFilter, ContainerRequestFilter {


    @Override
    public void filter(ContainerRequestContext request,
            ContainerResponseContext response) throws IOException {
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers","origin, content-type, accept, authorization, authtoken");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD");
        if (request.getMethod().equals("OPTIONS"))
        	response.setStatus(200);
    }

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		  
		AuthDAO auth = new AuthDAO();
		if(requestContext.getUriInfo().getPath().contains("secure")){
			
				  try {
					  final MultivaluedMap<String, String> headers = requestContext.getHeaders();
					  final List<String> authorization = headers.get("authToken");
					    
					  //If no authorization information present; block access
					  if(authorization == null || authorization.isEmpty())
					  {
						  requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
								.entity("You cannot access this resource").build());
					      return;
					  }
					    
					  //Get encoded username and password
					  final String[] encodedAuthToken = authorization.get(0).split(":");
					  final String token = encodedAuthToken[1];
					  final int userid = Integer.parseInt(encodedAuthToken[0]);
					
					if(!auth.getUserAuthentication(token, userid)){
						requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
								.entity("You cannot access this resource").build());
					      return;
					}
				} catch(Exception ae){
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
							.entity("Failed to authenticate user! You cannot access this resource right now").build());
		              return;
				}
			  
		}
		if(requestContext.getUriInfo().getPath().contains("admin")){
			try{
		      final MultivaluedMap<String, String> headers = requestContext.getHeaders();
		      final List<String> authorization = headers.get("authToken");
		        
		      //If no authorization information present; block access
		      if(authorization == null || authorization.isEmpty())
		      {
		    	  requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
		  				.entity("You cannot access this resource").build());
	              return;
		      }
		        
		      //Get encoded username and password
		      final String[] encodedAuthToken = authorization.get(0).split(":");
		      final String token = encodedAuthToken[1];
		      final int userid = Integer.parseInt(encodedAuthToken[0]);
			
		      HashMap<String, String> adminRoles = auth.getAuthUserRole(userid);
		      
		     		      
		      if(!adminRoles.get("token").equals(token) || !adminRoles.get("role").equals("ADMINISTRATOR")){
		    	  requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
							.entity("You cannot access this resource").build());
		              return;
		      }
		     
			  
			}catch(Exception ae){
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
						.entity("Failed to authenticate admin!! You cannot access this resource right now").build());
	              return;
			}
		
		}
	}
}