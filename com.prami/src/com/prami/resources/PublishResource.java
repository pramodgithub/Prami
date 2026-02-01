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
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.prami.bean.Image;
import com.prami.bean.Product;
import com.prami.bean.ProductType;
import com.prami.db.DBUtil;
import com.google.gson.JsonObject;
import com.prami.util.PublishProductUtil;
import com.prami.util.TypeUtil;

@Path("/admin/publish")
public class PublishResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;

	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/product")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response publishProduct(@FormParam("title") String title,
			  @FormParam("rowID") String rowID,
			  @FormParam("title") String strProductName,
			  @FormParam("brand") String strProductBrand,
			  @FormParam("affUrl") String strProductURL,
			  @FormParam("URL") String strOriginalURL,
			  @FormParam("price") Float fltProductPrice,
			  @FormParam("description") String strProductDescription,
			  @FormParam("color") String strProductColor,
			  @FormParam("catID") int intCatID,
			  @FormParam("subcatID") int intSubCatID,
			  @FormParam("storeID") int inrStoreID,
			  @FormParam("thumbImage") String thumbImage,
			  @FormParam("mainImage") String mainImage,
			  @FormParam("productType[]") List<String> productType,
			  @FormParam("isAvailable") int isAvailable,
			  @FormParam("prodSizes") String productSizes) {
				  	int intProdWebType =1;
				  	long longProductAddedDate = System.currentTimeMillis();
				  	long updatedTime = 0;
				  	String productbyUser = "admin";
				  	JsonObject json = new JsonObject(); 	     
				  	List<Image> imageList = new ArrayList<Image>();
					PublishProductUtil publish = new PublishProductUtil();
					Image image = new Image();
					
					
					String status = "error";
					Product publishProduct = new Product(strProductName, strProductBrand, strProductURL, strOriginalURL, fltProductPrice, strProductDescription, strProductColor, intCatID, 
							intSubCatID, inrStoreID, intProdWebType, longProductAddedDate, updatedTime, productbyUser, isAvailable, productType, productSizes);
					
					image.setThumbImage(thumbImage);
					imageList.add(image);
					
					image.setMainImage(mainImage);
					imageList.add(image);
					
					status = publish.publishProduct(publishProduct, image);
					
				    json.addProperty("rowId", rowID); 
				   	json.addProperty("status", status); 
		    	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/type")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response publishProductType(@FormParam("base64") String image,
			  @FormParam("name") String typeName,
			  @FormParam("catid") int catId,
			  @FormParam("subcatid") int subCatId
			  ) {
		  			JsonObject json = new JsonObject(); 	 
				  	TypeUtil type = new TypeUtil();
			    	int insertedTypeID = type.addNewProductType(image, typeName, catId, subCatId);
					json.addProperty("insertedId", insertedTypeID); 
		    	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/updateNewtype")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response updateNewProductType(@FormParam("image") String image,
			  @FormParam("name") String typeName,
			  @FormParam("catid") int catId,
			  @FormParam("subcatid") int subCatId
			  ) {
		  			JsonObject json = new JsonObject(); 	 
				  	TypeUtil type = new TypeUtil();
			    	int insertedTypeID = type.updateNewProductType(image, typeName, catId, subCatId);
					json.addProperty("insertedId", insertedTypeID); 
		    	  return Response.status(Status.OK).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/getProductType")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<ProductType> productTypeList( @FormParam("catID") int catId, @FormParam("subCatID") int subCatId ) {
				  	DBUtil type = new DBUtil();
				  	List<ProductType> typeList = type.getProductTypeList(catId, subCatId);
				  	return typeList;
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/getAllProductType")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<ProductType> allTypeList() {
				  	DBUtil type = new DBUtil();
				  	List<ProductType> typeList = type.getAllProductTypeList();
				  	return typeList;
	  }
}
