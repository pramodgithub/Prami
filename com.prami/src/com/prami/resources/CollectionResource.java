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

import com.prami.bean.Collection;
import com.prami.bean.CollectionHistory;
import com.prami.bean.CollectionSEO;
import com.prami.bean.Group;
import com.prami.bean.Product;
import com.prami.bean.ProductSEO;
import com.prami.bean.ProductType;
import com.prami.bean.SimilarityCheck;
import com.prami.dao.CategoryDao;
import com.prami.dao.CollectionDao;
import com.prami.dao.ProductDao;

	@Path("/collection")
	public class CollectionResource {
		 @Context
		  UriInfo uriInfo;
		  @Context
		  Request request;

		  @GET
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Collection> getCollectionJson(@QueryParam("cat") Integer c) {
		     CategoryDao collection = new CategoryDao();
		     return collection.getCollectionResource(c);
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/selected")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Product> getProductsFromSelectionJson(@FormParam("collection") String collectionId,@FormParam("page") String pageNum,  @FormParam("limit") String limit, @FormParam("types[]") List<Integer> types, @FormParam("sort") String sort) {
			  ProductDao product = new ProductDao();
			  return product.getProductsFromCollection(Integer.parseInt(collectionId),Integer.parseInt(pageNum),Integer.parseInt(limit), types, sort);
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/seoCollection")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<ProductSEO> getSEOProducts(@FormParam("collection") String collectionId,@FormParam("page") String pageNum,  @FormParam("limit") String limit) {
			  ProductDao product = new ProductDao();
			  return product.getSeoProductsList(Integer.parseInt(collectionId),Integer.parseInt(pageNum),Integer.parseInt(limit));
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/admin/availableInCollection")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Product> isProductAvailableInCollection(@FormParam("storeId") String storeId, @FormParam("collectionId") String collectionId) {
			  ProductDao product = new ProductDao();
			  return product.getIsAvailableProductInCollection(Integer.parseInt(storeId),Integer.parseInt(collectionId));
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/collectionList")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Collection> getcollectionList(@FormParam("catID") String collectionId) {
			  CategoryDao collection = new CategoryDao();
			  return collection.getCollectionList(Integer.parseInt(collectionId));
		 }
		 
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/searchCollection")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Collection> getSearchedCollection(@FormParam("searchkey") String searchkey) {
			  CategoryDao collection = new CategoryDao();
			  return collection.getSearchedCollectionList(searchkey);
		 }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/searchTypesInCollection")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<ProductType> getSearchedTypesInCollection(@FormParam("searchkey") String searchkey, @FormParam("collectionId") String collectionId) {
			  CategoryDao collection = new CategoryDao();
			  return collection.getSearchedTypesInCollectionList(searchkey, Integer.parseInt(collectionId));
		 }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/admin/similarityCheckList")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<SimilarityCheck> getSimilarityCheckList() {
			  CategoryDao collection = new CategoryDao();
			  return collection.getSimilarityCheckList();
		 }
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/admin/addCollectionHistory")
		  @Produces(MediaType.APPLICATION_JSON)
		  public boolean addCollectionHistory(@FormParam("token") String token, @FormParam("collectionUrl") String collectionUrl,
				  @FormParam("storeId") String storeId, @FormParam("readCount") String readCount,
				  @FormParam("catId") String catId, @FormParam("subCatId") String subCatId, @FormParam("byUser") String byUser) {
			  
			  ProductDao product = new ProductDao();
			  return product.addCollectionHistory(token,collectionUrl, Integer.parseInt(storeId), Double.parseDouble(readCount), Integer.parseInt(catId), Integer.parseInt(subCatId), byUser);
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/collectionSeo")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<CollectionSEO> getCollectionSeo(@FormParam("collection") String collectionId) {
		     CollectionDao collectionSeo = new CollectionDao();
		     return collectionSeo.getCollectionSeo(Integer.parseInt(collectionId));
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/allCollectionSeo")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<CollectionSEO> getAllCollectionSeo() {
		     CollectionDao collectionSeo = new CollectionDao();
		     return collectionSeo.getAllCollectionSeo();
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/groupSeo")
		  @Produces(MediaType.APPLICATION_JSON)
		  public List<Group> getAllGroupSeo() {
		     CollectionDao groupSeo = new CollectionDao();
		     return groupSeo.getAllGroupSeo();
		  }
		  
		  @POST
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		  @Path("/admin/collectionHistoryByToken")
		  @Produces(MediaType.APPLICATION_JSON)
		  public CollectionHistory getCollectionHistoryByToken(@FormParam("token") String token) {
		     CategoryDao history = new CategoryDao();
		     return history.getCollectionHistoryByToken(token);
		    		 
		  }
	}

