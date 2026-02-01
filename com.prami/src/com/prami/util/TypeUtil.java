package com.prami.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.prami.db.DBUtil;

//import Decoder.BASE64Decoder;

public class TypeUtil {

	public boolean uploadTypeImage(String image, String typeName, String destination){
		FileUtil fileutil = new FileUtil();
		boolean success = true;
		try{
	    	    	
	    	String base64Image = image.split(",")[1];
	    	
	    	//BASE64Decoder decoder = new BASE64Decoder();
	    	byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
	    	
	    	 BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decodedBytes));
	    	 
	    	 if (bufferedImage == null) {
	    		 System.out.println("Buffered Image is null");
	          }
	    	 
			if(fileutil.createDirectory(destination)){
				
				File outputfile = new File(destination+"/"+typeName.replaceAll("\\s","")+".png");
				ImageIO.write(bufferedImage, "png", outputfile);
			}
			System.out.println("Image saved at "+destination+"...!");
			
    	}catch(Exception e){
    		System.out.println("Image save failed...!");
    		e.printStackTrace();
    		success = false;
    	}
		return success;
	}
	
	
	/*
	 * public boolean uploadStoreImage(String image, String imageName, String
	 * destination){ FileUtil fileutil = new FileUtil(); boolean success = false;
	 * try{
	 * 
	 * String base64Image = image.split(",")[1];
	 * System.out.println(image.split(",")[0].split("/")[1]); byte[] imageBytes =
	 * javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
	 * BufferedImage bufferedImage = ImageIO.read(new
	 * ByteArrayInputStream(imageBytes)); if(fileutil.createDirectory(destination)){
	 * File outputfile = new
	 * File(destination+"/"+imageName.replaceAll("\\s","")+".jpg");
	 * ImageIO.write(bufferedImage, "jpg", outputfile); }
	 * System.out.println("Image saved at "+destination+"...!"); success = true;
	 * }catch(Exception e){ System.out.println("Image save failed...!");
	 * e.printStackTrace(); success = false; } return success; }
	 */
	
	public int addNewProductType(String image, String typeName, int catId, int subCatId){
		int insertedTypeID=0;
		DBUtil dbutil = new DBUtil();
		NameUtil nameUtil = new NameUtil();
		String catName = nameUtil.getCategory(catId).getCategoryName();
		String subCatName = nameUtil.getSubCategory(subCatId).getSubCatName();

		String savePath = "/var/www/images/type/";
		String directory = catName.replaceAll("\\s","")+"/"+subCatName.replaceAll("\\s","");
		String destination = savePath+directory;
		try{
			if(uploadTypeImage(image,typeName,destination)){
				String imageName = directory+"/"+typeName.replaceAll("\\s","")+".png";
				insertedTypeID= dbutil.insertProductType(imageName, typeName, catId, subCatId);
			}
			
		}catch(Exception e){
			System.out.println("Image save failed...!");
    		e.printStackTrace();
    		
		}
		return insertedTypeID;
	}
	
	public int updateNewProductType(String image, String typeName, int catId, int subCatId){
		int insertedTypeID=0;
		DBUtil dbutil = new DBUtil();
		try{
				insertedTypeID= dbutil.insertProductType(image, typeName, catId, subCatId);
		}catch(Exception e){
			System.out.println("Failed at updateNewProductType......");
    		e.printStackTrace();
		}
		return insertedTypeID;
	}
}
