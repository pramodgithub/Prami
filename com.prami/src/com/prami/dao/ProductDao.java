package com.prami.dao;

import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.prami.bean.Affiliate;
import com.prami.bean.Image;
import com.prami.bean.Product;
import com.prami.bean.ProductSEO;
import com.prami.bean.ProductSize;
import com.prami.bean.UpdateProduct;
import com.prami.db.MySQLAccess;
import com.prami.util.ImageUtil;
import com.prami.util.SearchUtil;
import com.prami.util.StructureUtil;

public class ProductDao {
	Statement statement = null;
	Statement statement2 = null;
	CallableStatement stmt = null;
	ResultSet resultSet = null;
	ResultSet imageResultSet = null;
	Connection connect = null;
	PreparedStatement pst = null;
	MySQLAccess dao = new MySQLAccess();
	
	public List<Product> getProductList(int pageNum,int pageLimit){
		List<Product> productList = new ArrayList<Product>();
		
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
			statement2 = connect.createStatement();
		    resultSet = statement.executeQuery("select * from producttbl where productStatus=1 limit "+pageNum+","+pageLimit+"");
		    
		    while (resultSet.next()) {
		    	List<Image> imageList = new ArrayList<Image>();
		    	imageResultSet = statement2.executeQuery("select * from imagetbl where productID="+resultSet.getInt("productID")+" and imgStatus=1");
		    	 while (imageResultSet.next()){
		    		 
		    		 Image image = new Image(imageResultSet.getInt("imgID"), imageResultSet.getInt("productID"), imageResultSet.getString("imgURL"), imageResultSet.getInt("imageType"));
		    		 imageList.add(image);
		    	 }
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"), imageList);
		    	productList.add(product);
		    	
		    }
		    
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
	public List<Product> getFavoriteProductList(int userId,int pageNum,int pageLimit){
		List<Product> productList = new ArrayList<Product>();
		
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
			statement2 = connect.createStatement();
		    resultSet = statement.executeQuery("select * from producttbl where productStatus=1 and productID in (select favProdID from favouritetbl where favUserID="+userId+") limit "+pageNum+","+pageLimit+"");
		    
		    while (resultSet.next()) {
		    	List<Image> imageList = new ArrayList<Image>();
		    	imageResultSet = statement2.executeQuery("select * from imagetbl where productID="+resultSet.getInt("productID")+" and imgStatus=1");
		    	 while (imageResultSet.next()){
		    		 
		    		 Image image = new Image(imageResultSet.getInt("imgID"), imageResultSet.getInt("productID"), imageResultSet.getString("imgURL"), imageResultSet.getInt("imageType"));
		    		 imageList.add(image);
		    	 }
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"), imageList);
		    	productList.add(product);
		    	
		    }
		    
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
	public List<Product> getBoxProductList(int structureBoxId,int count){
		List<Product> productList = new ArrayList<Product>();
		String existingProducts = "";
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			
		    resultSet = statement.executeQuery("select p.productID, p.productName,p.productBrand,p.productURL,p.productPrice,p.productDesc,p.productColor, COUNT(*) as totalviews from producttbl p join  activitytbl a on p.productID=a.productID where p.subCatId="+structureBoxId+" and p.productStatus=1 group by p.productID, p.productName,p.productBrand,p.productURL,p.productPrice,p.productDesc,p.productColor order by totalviews desc limit 0,"+count+"");
		    while (resultSet.next()) {
		    	List<Image> imageList = new ArrayList<Image>();
		    	imageResultSet = statement2.executeQuery("select * from imagetbl where productID="+resultSet.getInt("productID")+" and imgStatus=1");
		    	 while (imageResultSet.next()){
		    		 
		    		 Image image = new Image(imageResultSet.getInt("imgID"), imageResultSet.getInt("productID"), imageResultSet.getString("imgURL"), imageResultSet.getInt("imageType"));
		    		 imageList.add(image);
		    	 }
		    	 existingProducts += resultSet.getInt("productID")+",";
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"), imageList);
		    	productList.add(product);
		    	
		    }
		    
		    if(productList.size() < count){
		    	StructureUtil s = new StructureUtil();
		    	productList.addAll(s.getRecentProductsNotIn(structureBoxId,existingProducts,(count - productList.size())));
		    }
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at box Product DAO-->"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
	
	
	
	public Product getProduct(int productID){
		Product product = null;	
		AcitivityDao activity = new AcitivityDao();
		String query = "select * from producttbl p LEFT OUTER JOIN storetbl t on t.storeID = p.storeID  where p.productID="+productID;
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			
			
		    resultSet = statement.executeQuery(query);
		   
		    while (resultSet.next()) {
		    	
		    	List<Image> imageList = new ArrayList<Image>();
		    	imageResultSet = statement2.executeQuery("select * from imagetbl where productID="+productID+" and imgStatus=1");
		    	 while (imageResultSet.next()){
		    		 
		    		 Image image = new Image(imageResultSet.getInt("imgID"), imageResultSet.getInt("productID"), imageResultSet.getString("imgURL"), imageResultSet.getInt("imageType"));
		    		 imageList.add(image);
		    	 }
		    	
		    	int views = activity.getProductViews(productID);
		    	 product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"),resultSet.getString("storeName"),resultSet.getString("storeDesc"),resultSet.getInt("catID"), imageList,views);
		    	return product;
		    	
		    }
		    
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO getProduct"+e.getMessage());
		}finally{
			close();
		}
		return product;
	}
	
	public Product getRedirectProduct(int productID){
		Product redirectProduct = null;
		Affiliate affiliate = null;
		String query = "select productID,productURL,orginalURL as originalURL,storeName,storeLogo from producttbl p LEFT OUTER JOIN storetbl t on t.storeID = p.storeID  where p.productID="+productID;
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
		    resultSet = statement.executeQuery(query);
		    while (resultSet.next()) {
		    	if(resultSet.getString("originalURL").equals(resultSet.getString("productURL"))){
	    			affiliate = new Affiliate(false, 0);
	    			//System.out.println("No affiliate......");
	    		}else{
	    			affiliate = new Affiliate(true, 0);
	    			//System.out.println("With affiliate......");
	    		}
		    	redirectProduct = new Product(resultSet.getInt("productID"), resultSet.getString("productURL"), resultSet.getString("storeName"), resultSet.getString("storeLogo"), affiliate);
		    }
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO getProduct"+e.getMessage());
		}finally{
			close();
		}
		return redirectProduct;
	}
	public int getStoreId(int productID){
		int storeId = 0;
		String query = "select storeID from producttbl where productID="+productID;
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
		   
		    while (resultSet.next()) {
		    	storeId = resultSet.getInt("storeID");
		    }
		    
		   
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO getStoreId"+e.getMessage());
		}finally{
			close();
		}
		return storeId;
	}
	
	public int getCollectionIdFromProduct(int productID){
		int collectionId = 0;
		String query = "select SubCatID from producttbl where productID="+productID;
		
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
		   
		    while (resultSet.next()) {
		    	collectionId = resultSet.getInt("SubCatID");
		    }
		    
		   
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO getStoreId"+e.getMessage());
		}finally{
			close();
		}
		return collectionId;
	}
	public List<Product> getProductListByTheme(int subCatID,int pageNum,int pageLimit){
		List<Product> productList = new ArrayList<Product>();
		
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
			statement2 = connect.createStatement();
		    resultSet = statement.executeQuery("select * from producttbl where SubCatID = "+subCatID+" and productStatus=1 limit "+pageNum+","+pageLimit+"");
		    
		    while (resultSet.next()) {
		    	List<Image> imageList = new ArrayList<Image>();
		    	imageResultSet = statement2.executeQuery("select * from imagetbl where productID="+resultSet.getInt("productID")+" and imgStatus=1");
		    	 while (imageResultSet.next()){
		    		 
		    		 Image image = new Image(imageResultSet.getInt("imgID"), imageResultSet.getInt("productID"), imageResultSet.getString("imgURL"), imageResultSet.getInt("imageType"));
		    		 imageList.add(image);
		    	 }
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"), imageList);
		    	productList.add(product);
		    	
		    }
		    
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
	
	public List<Product> getIsAvailableProductInCollection(int storeId, int collectionId){
		List<Product> productList = new ArrayList<Product>();
		
		try{
			connect = dao.getConnection();
			 
			statement = connect.createStatement();
		    resultSet = statement.executeQuery("select * from trendydb.producttbl where storeID = "+storeId+" and SubCatID = "+collectionId);
		    
		    while (resultSet.next()) {
		    	
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("orginalURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"), resultSet.getInt("productStatus"));
		    	productList.add(product);
		    	
		    }
		    
		    
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO in getIsAvailableProductInCollection() function"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
	public List<Product> getProductFromSelection(int prodID,int pageNum,int pageLimit, List<Integer> types, String sort){
		List<Product> productList = new ArrayList<Product>();
		ImageUtil image = new ImageUtil();
		Affiliate affiliate = null;
		List<Image> imageList;
		String typesString = "";
		for(Integer type : types) {
			typesString += type +",";
		}
		typesString = typesString.replaceAll(",$", "");
		try{
			String productQuery="";
			connect = dao.getConnection();
			if(types.size()<= 0){
				productQuery = "{call sp_get_products_grid(?, ?, ?, ?)}";
				stmt = connect.prepareCall(productQuery);
				stmt.setInt(1, prodID);
				stmt.setString(2, sort);
				stmt.setInt(3, pageNum);
				stmt.setInt(4, pageLimit);
			}else{
				productQuery = "{call sp_get_products_grid_by_type(?, ?, ?, ?)}";
				stmt = connect.prepareCall(productQuery);
				stmt.setString(1, typesString);
				stmt.setString(2, sort);
				stmt.setInt(3, pageNum);
				stmt.setInt(4, pageLimit);
			}
			
			resultSet = stmt.executeQuery();
		    
		    while (resultSet.next()) {
		    	if(resultSet.getString("originalURL").equals(resultSet.getString("productURL"))){
	    			affiliate = new Affiliate(false, 0);
	    			//System.out.println("No affiliate......");
	    		}else{
	    			affiliate = new Affiliate(true, 0);
	    			//System.out.println("With affiliate......");
	    		}
		    	imageList = image.getProductImages(resultSet.getInt("productID"));
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"), imageList, affiliate);
		    	productList.add(product);
		    }
		  			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
	public List<Product> getProductsFromCollection(int collectionID, int pageNum, int pageLimit, List<Integer> types, String sort){
		List<Product> productList = new ArrayList<Product>();
		ImageUtil image = new ImageUtil();
		List<Image> imageList;
		Affiliate affiliate = null;
		String typesString = "";
		for(Integer type : types) {
			typesString += type +",";
		}
		typesString = typesString.replaceAll(",$", "");
		
		try{
			String productQuery="";
			connect = dao.getConnection();
			if(types.size() <= 0){
				productQuery = "{call sp_get_collection_products_grid(?, ?, ?, ?)}";
				stmt = connect.prepareCall(productQuery);
				stmt.setInt(1, collectionID);
				stmt.setString(2, sort);
				stmt.setInt(3, pageNum);
				stmt.setInt(4, pageLimit);
			}else{
				productQuery = "{call sp_get_collection_products_grid_by_type(?, ?, ?, ?)}";
				stmt = connect.prepareCall(productQuery);
				stmt.setString(1, typesString);
				stmt.setString(2, sort);
				stmt.setInt(3, pageNum);
				stmt.setInt(4, pageLimit);
			}
		    resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	if(resultSet.getString("originalURL").equals(resultSet.getString("productURL"))){
	    			affiliate = new Affiliate(false, 0);
	    			//System.out.println("No affiliate......");
	    		}else{
	    			affiliate = new Affiliate(true, 0);
	    			//System.out.println("With affiliate......");
	    		}
		    	imageList = image.getProductImages(resultSet.getInt("productID"));
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"),imageList, affiliate);
		    	productList.add(product);
		    }
		  			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
	
	public List<ProductSEO> getSeoProductsList(int collectionID,int pageNum,int pageLimit){
		List<ProductSEO> productList = new ArrayList<ProductSEO>();
		ImageUtil image = new ImageUtil();
		String mainImage, productSEOTitle = "", seoDescription = "", seoProductTypes = "", seoKeywords = "";
		try{
			String productQuery="";
			connect = dao.getConnection();

			productQuery = "{call sp_get_collection_products_grid(?,?,?)}";
			stmt = connect.prepareCall(productQuery);
			stmt.setInt(1, collectionID);
			stmt.setInt(2, pageNum);
			stmt.setInt(3, pageLimit);
			
		    resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	mainImage = image.getSeoProductImages(resultSet.getInt("productID"));
		    	ProductSEO seoProduct = new ProductSEO(resultSet.getInt("productID"), resultSet.getString("productName"), productSEOTitle,  resultSet.getString("productBrand"), resultSet.getFloat("productPrice"), seoDescription, resultSet.getString("productColor"), seoProductTypes, mainImage, seoKeywords);
		    	productList.add(seoProduct);
		    }
		  			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
	public List<Product> getSearchedProducts(String searchKey,int pageNum,int pageLimit,int collection, int type){
		searchKey = searchKey.replaceAll("[^a-zA-Z0-9_ ]", "");
		searchKey = searchKey.trim().replaceAll("\\s{2,}", " ");
		String searchedKey = searchKey;
		List<Product> productList = new ArrayList<Product>();
		ImageUtil image = new ImageUtil();
		List<Image> imageList;
		SearchUtil searchUtil = new SearchUtil();
		try{
			String productQuery="";
			String searchParam = "";
			connect = dao.getConnection();
			ExecutorService searchInsertExecutor = Executors.newSingleThreadExecutor();
			searchInsertExecutor.execute(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                	searchUtil.insertSearchQuery("test", searchedKey, 1, 0);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
			searchInsertExecutor.shutdown();
			String key[] = URLDecoder.decode(searchKey, "UTF-8").split("\\s",0);
			for(String s:key){
				searchParam += "+"+s+"* ";
			}
			if(type==0 && collection==0){
				productQuery = "{call sp_get_searched_products(?,?,?)}";
				stmt = connect.prepareCall(productQuery);
				stmt.setString(1, searchParam);
				stmt.setInt(2, pageNum);
				stmt.setInt(3, pageLimit);
			}else if(type==0 && collection!=0){
				productQuery = "{call sp_get_searched_products_by_collection(?,?,?,?)}";
				stmt = connect.prepareCall(productQuery);
				stmt.setString(1, searchParam);
				stmt.setInt(2, collection);
				stmt.setInt(3, pageNum);
				stmt.setInt(4, pageLimit);
			}else if(type!=0 && collection!=0){
				productQuery = "{call sp_get_searched_products_by_type(?,?,?,?,?)}";
				stmt = connect.prepareCall(productQuery);
				stmt.setString(1, searchParam);
				stmt.setInt(2, collection);
				stmt.setInt(3, type);
				stmt.setInt(4, pageNum);
				stmt.setInt(5, pageLimit);
			}
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	imageList = image.getProductImages(resultSet.getInt("productID"));
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"),imageList);
		    	productList.add(product);
		    }
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
public List<Product> getSearchedProductsByQuery(String searchKey,int pageNum,int pageLimit, String userId){
		searchKey = searchKey.replaceAll("[^a-zA-Z0-9_ ]", "");
		searchKey = searchKey.trim().replaceAll("\\s{2,}", " ");
		String searchedKey = searchKey;
		//System.out.println(searchKey);
		List<Product> productList = new ArrayList<Product>();
		ImageUtil image = new ImageUtil();
		List<Image> imageList;
		SearchUtil searchUtil = new SearchUtil();
		try{
			String productQuery="";
			String searchParam = "";
			connect = dao.getConnection();
			ExecutorService searchInsertExecutor = Executors.newSingleThreadExecutor();
			searchInsertExecutor.execute(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                	System.out.println("in search insert");
	                	searchUtil.insertSearchQuery(userId, searchedKey, 1, 0);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
			searchInsertExecutor.shutdown();
			String key[] = URLDecoder.decode(searchKey, "UTF-8").split("\\s",0);
			for(String s:key){
				searchParam += "+"+s+"* ";
			}
			productQuery = "{call sp_get_searched_products_by_query(?,?,?)}";
			stmt = connect.prepareCall(productQuery);
			stmt.setString(1, searchParam);
			stmt.setInt(2, pageNum);
			stmt.setInt(3, pageLimit);
			resultSet = stmt.executeQuery();
		    while (resultSet.next()) {
		    	imageList = image.getProductImages(resultSet.getInt("productID"));
		    	Product product = new Product(resultSet.getInt("productID"),resultSet.getString("productName"), resultSet.getString("productBrand"), resultSet.getString("productURL"), resultSet.getFloat("productPrice"), resultSet.getString("productDesc"), resultSet.getString("productColor"),imageList);
		    	productList.add(product);
		    }
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO-> in getSearchedProductsByQuery()==>"+e.getMessage());
		}finally{
			close();
		}
		return productList;
	}
	
	public int getFavoriteSelecetion(int userID,int prodID){
		
		int isSelected = 0;
		try{
			connect = dao.getConnection();
			
			statement = connect.createStatement();
		    statement.executeQuery("SELECT isFavorite FROM favouritetbl where favProdID='"+prodID+"' and favUserID='"+userID+"'");
		    resultSet = statement.getResultSet();
		    
		    if(resultSet.next()){
		    	isSelected = resultSet.getInt("isFavorite");
		    }else{
		    	isSelected = 2;
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> getFavoriteSelecetion()"+e.getMessage());
			
		}finally{
			close();
		}
		return isSelected;
    }
	public Boolean insertFavoriteSelecetion(int userID,int productID,int selected) throws Exception {
		Boolean flag = false;
		try{
			connect = dao.getConnection();
			
			String query = "insert into favouritetbl (favProdID, favUserID, isFavorite) values ('"+productID+"','"+userID+"','"+selected+"')";
	    	pst = connect.prepareStatement(query);
	    	int rowsUpdated = pst.executeUpdate(); 
	    	if (rowsUpdated > 0) {
	    	   flag =true;
	    	}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO at insertFavoriteSelecetion()-->"+e.getMessage());
			return false;
		}finally{
			close();
		}
		return flag;
    }
	
	
	public Boolean addCollectionHistory(String token, String collectionUrl, int storeId, double readCount, int catId, int subCatId, String byUser)  {
		Boolean flag = false;
		long currTime = System.currentTimeMillis();
		try{
			connect = dao.getConnection();
			String query = "INSERT INTO collection_history ( token, collectionUrl, storeId, readCount, catId, subCatId, byUser, addedTime, status) VALUES ('"+token+"', '"+collectionUrl+"', '"+storeId+"', '"+readCount+"','"+catId+"', '"+subCatId+"', '"+byUser+"','"+currTime+"', 0)";
	    	pst = connect.prepareStatement(query);
	    	int rowsUpdated = pst.executeUpdate(); 
	    	if (rowsUpdated > 0) {
	    	   flag =true;
	    	}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO at addCollectionHistory()-->"+e.getMessage());
			return false;
		}finally{
			close();
		}
		return flag;
    }
	
	public boolean updateFavoriteSelecetion(int userID,int productID,int selected){
		boolean isSelected = false;
		
		try{
			connect = dao.getConnection();			 
			statement = connect.createStatement();	
			String query = "UPDATE favouritetbl SET isFavorite='"+selected+"' WHERE favUserID='"+userID+"' and favProdID='"+productID+"'";
			pst = connect.prepareStatement(query);
			int rowsUpdated = pst.executeUpdate();
	    	
	    	if (rowsUpdated > 0) {
	    		isSelected =true;
	    	}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at Product DAO at updateFavoriteSelecetion()-->"+e.getMessage());
			return false;
		}finally{
			close();
		}
		return isSelected;
	}
	public int favoriteSelection(int userID,int prodID,int selected) {
		int isSelected = 0;
		boolean flag = false;
		try{
			if(getFavoriteSelecetion(userID, prodID)==2){
				flag = insertFavoriteSelecetion(userID, prodID, selected);
			}else if(getFavoriteSelecetion(userID, prodID)==0 || getFavoriteSelecetion(userID, prodID)==1 ){
				flag = updateFavoriteSelecetion(userID, prodID, selected);
			}
			if(flag){
				isSelected = getFavoriteSelecetion(userID, prodID);
			}else{
				isSelected = 2;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at AuthDAO --> favoriteSelecetion()"+e.getMessage());
			return 2;
		}finally{
			close();
		}
		return isSelected;
    }
	public List<UpdateProduct> getProductURLList(int storeId, int offset, int limit){
		List<UpdateProduct> updateProductList = new ArrayList<UpdateProduct>();
		SizeDao sizes = new SizeDao();
		try{
			connect = dao.getConnection();
			statement = connect.createStatement();
			statement.executeQuery("SELECT productID, orginalURL,productPrice, productStatus FROM producttbl where storeID="+storeId+" and productStatus!=2 limit "+offset+", "+limit);
			resultSet = statement.getResultSet();
			while(resultSet.next()){
				
				List<ProductSize> productSizesList = sizes.getSizesByProdId(resultSet.getInt("productID"));
				
				UpdateProduct updProduct = new UpdateProduct(resultSet.getInt("productID"), resultSet.getString("orginalURL"), resultSet.getFloat("productPrice"), resultSet.getInt("productStatus"), productSizesList);
				updateProductList.add(updProduct);

			}


		}catch(Exception e){
			e.printStackTrace();

			System.out.println("failed at updateDao --> getProductURLList()-->"+e.getMessage());
		}finally{
			close();
		}
		return updateProductList;
	}
	private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }
	      if (imageResultSet != null) {
	    	  imageResultSet.close();
		      }
	      if (statement != null) {
	        statement.close();
	      }
	      if (statement2 != null) {
		        statement2.close();
		      }
	      if (stmt != null) {
		        stmt.close();
		      }
	      if (pst != null) {
		        pst.close();
		      }
	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }
}
