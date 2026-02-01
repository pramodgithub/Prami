package com.prami.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.prami.bean.Image;
import com.prami.bean.Product;
import com.prami.dao.SizeDao;
import com.prami.dao.UpdateDao;
import com.prami.db.DBUtil;
import com.prami.db.MySQLAccess;
import com.prami.util.FileUtil;
import com.prami.util.NameUtil;

public class PublishProductUtil {
	Statement statement = null;
	Connection connect = null;
	MySQLAccess dao = new MySQLAccess();
	String flag = "Success";
	int insertedProductID;
	static final int PRODUCT_ADDED = 8;
	public String publishProduct(Product product, Image image){
		FileUtil fileutil = new FileUtil();
		NameUtil nameUtil = new NameUtil();
		DBUtil dbutil = new DBUtil();
		UpdateDao updateProd = new UpdateDao();
		SizeDao sizeDao = new SizeDao();
		try{
			if(!dbutil.checkProductPresentByURL(product.getStrOriginalURL())){
				connect = dao.getConnection();
				String storeName = nameUtil.getStore(product.getIntStoreID()).getStoreName().replaceAll(" ", "").toLowerCase();
				String catName = nameUtil.getCategory(product.getIntCatID()).getCategoryName().replaceAll(" ", "").toLowerCase();
				String subCatName = nameUtil.getSubCategory(product.getIntSubCatID()).getSubCatName().replaceAll(" ", "").toLowerCase();
						
				String sqlStatement = "INSERT INTO producttbl (productName, productBrand, productPrice, productOldPrice, productDesc, productTrendyFactor,"+ 
			    		"productColor, productURL,orginalURL, catID, SubCatID, storeID, productWebType, productDate,"+
			    		"productUpdatedBy, productStatus)"+ 
			    "VALUES ('"+product.getStrProductName()+"', '"+product.getStrProductBrand()+"', '"+product.getFltProductPrice()+"', '0','"+product.getStrProductDescription()+"',"+ 
			    		"'0', '"+product.getStrProductColor()+"', '"+product.getStrProductURL()+"', '"+product.getStrOriginalURL()+"', '"+product.getIntCatID()+"', '"+product.getIntSubCatID()+"',"+
			    		"'"+product.getIntStoreID()+"', '1', '"+product.getLongProductAddedDate()+"', '"+product.getStrProductUpdatedBy()+"','"+product.getIntProductStatus()+"' )";
			     statement = connect.prepareStatement(sqlStatement,Statement.RETURN_GENERATED_KEYS);
			     statement.executeUpdate(sqlStatement);
					ResultSet rs = statement.getGeneratedKeys();
					if (rs.next()){
					    insertedProductID = rs.getInt(1);
					}
					
					if(!dbutil.updateProductSimilarId(insertedProductID)){
						System.out.println("..........Failed to add similar id........!");
					}
					
					if(!dbutil.insertProductTypeMapping(insertedProductID,product.getStrProductType(),product.getIntProductStatus())){
						System.out.println("..........Failed to add type mapping........!");
					}
					
					if(sizeDao.addProductSizeData(insertedProductID, product.getProductSizes()) < 0){
						System.out.println("..........Failed to add product size data........!");
					}
					
					
					updateProd.addProductUpdateLog(insertedProductID, 1, PRODUCT_ADDED);
					String savePath = "/var/www/images/product";
					String directory = "/"+storeName+"/"+catName+"/"+subCatName;
					String destination = savePath+directory;
					fileutil.saveProductImage(insertedProductID,image.getMainImage(), destination, directory, 2);
					directory = directory+"/thumbnail";
					destination = savePath+directory;
					fileutil.saveProductImage(insertedProductID, image.getThumbImage(),destination, directory, 1);
			}else{
				flag="Present";
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at publishProduct....! -->"+e.getMessage());
			flag="Error";
		}finally{
			close();
		}
		return flag;
	}
	private void close() {
	    try {
	      if (statement != null) {
	        statement.close();
	      }
	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	}
}
