package com.prami.resources;

import java.util.ArrayList;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.prami.bean.MainProduct;
import com.prami.bean.PriceGraph;
import com.prami.bean.Product;
import com.prami.bean.SimilarityCheck;
import com.prami.bean.OnlineStore;
import com.prami.dao.CategoryDao;
import com.prami.dao.ProductDao;
import com.prami.dao.SimilarProductDao;
import com.prami.dao.StoreDao;
import com.prami.util.PriceHistoryUtil;

@Path("/products")
public class ProductsResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;

	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public MainProduct getProductsFromUrl(@QueryParam("p") String p) {
		  MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters(); 
		  SimilarProductDao product = new SimilarProductDao();
	      return product.getSimilarProductList(Integer.parseInt(queryParams.getFirst("p")));
	 }
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  @Path("/redirect")
	  public Product getRedirectProduct(@QueryParam("p") String p) {
		  MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters(); 
		  ProductDao product = new ProductDao();
	      return product.getRedirectProduct(Integer.parseInt(queryParams.getFirst("p")));
	 }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/mainProduct")
	  @Produces(MediaType.APPLICATION_JSON)
	  public MainProduct getMainProducts(@FormParam("productId") String prodId) {
		  SimilarProductDao product = new SimilarProductDao();
	      return product.getSimilarProductList(Integer.parseInt(prodId));
	 }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/selected")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Product> getProductsFromSelectionJson(@FormParam("selected") String prodId,@FormParam("page") String pageNum,  @FormParam("limit") String limit,@FormParam("types[]") List<Integer> types,  @FormParam("sort") String sort) {
		  ProductDao product1 = new ProductDao();
		  return product1.getProductFromSelection(Integer.parseInt(prodId),Integer.parseInt(pageNum),Integer.parseInt(limit), types, sort);
	 }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/theme")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Product> getProductsFromThemeJson(@FormParam("theme") String subCatId,@FormParam("page") String pageNum,  @FormParam("limit") String limit) {
		  
		  ProductDao product = new ProductDao();
	      return product.getProductListByTheme(Integer.parseInt(subCatId),Integer.parseInt(pageNum),Integer.parseInt(limit));
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/secure/favorite")
	  @Produces(MediaType.APPLICATION_JSON)
	  public int updateFavoriteSelection(@FormParam("userID") String userID,@FormParam("productID") String productID,  @FormParam("selected") String selected) {
			  ProductDao product = new ProductDao();
		      return product.favoriteSelection(Integer.parseInt(userID),Integer.parseInt(productID),Integer.parseInt(selected));
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/secure/getfavorite")
	  @Produces(MediaType.APPLICATION_JSON)
	  public int getFavoriteSelection(@FormParam("userID") String userID,@FormParam("productID") String productID) {
			  ProductDao product = new ProductDao();
		      return product.getFavoriteSelecetion(Integer.parseInt(userID),Integer.parseInt(productID));
	 }
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/secure/getfavoriteproducts")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Product> getFavoriteProducts(@FormParam("userID") String userID,@FormParam("token") String token,@FormParam("page") String pageNum,  @FormParam("limit") String limit) {
		  ProductDao product = new ProductDao();
		  List<Product> favProducts = new ArrayList<>();
		  favProducts = product.getFavoriteProductList(Integer.parseInt(userID),Integer.parseInt(pageNum),Integer.parseInt(limit));
		  return favProducts;
	 } 
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/search")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Product> getSearchedProducts(@FormParam("searched") String searchKey,@FormParam("page") String pageNum,  @FormParam("limit") String limit,@FormParam("collection") String collection,@FormParam("type") String type) {
		  ProductDao product = new ProductDao();
		  return product.getSearchedProducts(searchKey,Integer.parseInt(pageNum),Integer.parseInt(limit),Integer.parseInt(collection),Integer.parseInt(type));
	 } 
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/searchQuery")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Product> getSearchedProductsByQueryList(@FormParam("searched") String searchKey,@FormParam("page") String pageNum,  @FormParam("limit") String limit,@FormParam("userId") String userId) {
		  ProductDao product = new ProductDao();
		  return product.getSearchedProductsByQuery(searchKey,Integer.parseInt(pageNum), Integer.parseInt(limit), userId);
	 } 
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/storeproperties")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<OnlineStore> getSearchedProducts(@FormParam("prodID") String prodID) {
		  StoreDao sd = new StoreDao();
		  return sd.getOnlineStoreFromProdId(Integer.parseInt(prodID));
	 }  
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/admin/getAllBrands")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<SimilarityCheck> getAllBrands() {
		  CategoryDao collection = new CategoryDao();
		  return collection.getSimilarityCheckList();
	 }
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Path("/productPriceHistory")
	  @Produces(MediaType.APPLICATION_JSON)
	  public PriceGraph getProductPriceHistory(@FormParam("productid") String productId){
		  PriceHistoryUtil priceHistoryUtil = new PriceHistoryUtil();
		  PriceGraph priceGraph = priceHistoryUtil.getPriceHistoryGraph(Integer.parseInt(productId));
		  return priceGraph;
	  }
}
