package com.prami.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.attribute.FileAttribute;
//import java.nio.file.attribute.PosixFilePermission;
//import java.nio.file.attribute.PosixFilePermissions;
//import java.util.Set;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Random;
import com.prami.db.MySQLAccess;
public class FileUtil {
	Statement statement = null;
	Connection connect = null;
	Random random = new SecureRandom();
	public boolean createDirectory(String destination){
		boolean directoryCreated = true;
		try{
			Path mypath = Paths.get(destination);

	        if (!Files.exists(mypath)) {
	        
	           // Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr--r--");
	           // FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);

	            Files.createDirectories(mypath);
	            System.out.println("Directory created");
	            
	        } else {
	            System.out.println("Directory already exists");
	        }
		}catch (Exception e) {
			// TODO: handle exception
			directoryCreated = false;
			e.printStackTrace();
		}
		
		return directoryCreated;
	}
	public String saveImage(String imageUrl,String destination) {
		String profileImage = null;
		String temp = null;
		if(createDirectory(destination)){
			 Random random = new SecureRandom();
			temp = new BigInteger(130, random).toString(32);
						
			try{
				
				URL url = new URL(imageUrl);
				InputStream is = url.openStream();
				OutputStream os = new FileOutputStream(destination+"/"+temp+".jpg");
		
				byte[] b = new byte[2048];
				int length;
		
				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}
				
				profileImage = temp+".jpg";
				is.close();
				os.close();
			}catch(Exception e){
				System.out.println(e.getMessage());
				
			}finally{
				close();
			}
		}
		return profileImage;
	}
	public boolean saveProductImage(int productID, String imageUrl, String destination, String directory, int imageType) {
		boolean flag = true;
		String randomFileName = new BigInteger(90, random).toString(32);
		if(createDirectory(destination)){
			
			
			MySQLAccess dao = new MySQLAccess();
			
			String destinationFile = destination+"/prm"+randomFileName+".jpg";
			String destinationURL = directory+"/prm"+randomFileName+".jpg";
					
			String imageQuery;
						
			try{
				connect = dao.getConnection();
				URL url = new URL(imageUrl);
				
				String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
				
				URLConnection con = url.openConnection();
				con.setRequestProperty("User-Agent", USER_AGENT);
				InputStream is = con.getInputStream();
				//InputStream is = url.openStream();
				OutputStream os = new FileOutputStream(destinationFile);
		
				byte[] b = new byte[2048];
				int length;
				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}
				imageQuery= "INSERT INTO imagetbl (productID, imgURL, imageType, imgStatus ) VALUES ("+productID+", '"+destinationURL+"',"+imageType+", 1)";
				statement = connect.createStatement();
				statement.executeUpdate(imageQuery);
				
				is.close();
				os.close();
			}catch(Exception e){
				System.out.println("Failed at saveProductImage-->"+e.getMessage());
				flag = false;
				return flag;
			}finally{
				close();
			}
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
