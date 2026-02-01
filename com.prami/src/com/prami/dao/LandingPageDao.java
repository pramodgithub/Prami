package com.prami.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import org.json.JSONObject;

import com.prami.bean.LandingPageItems;
import com.prami.bean.LandingPageSection;
import com.prami.db.MySQLAccess;

public class LandingPageDao {
	
	
	public class IntPair {
	     public int page;
	     public int pageCount;
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getPageCount() {
			return pageCount;
		}
		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}
	     
	     
	}
	
	private CallableStatement statement = null, mainStatement = null;
	private Statement norm_statement = null;
	private ResultSet resultSet = null, mainResultSet = null;
	private Connection connect = null;
	private MySQLAccess dao = new MySQLAccess();
	private PreparedStatement pst = null;
	public List<LandingPageSection> prepareLandingPageDetails(int page, int index, int limit) {

		String subSps;
		String ITEM_JSON_DATA;
		JSONObject obj, item;
		JSONArray fieldsData;

		List<LandingPageSection> sectionList = new ArrayList<LandingPageSection>();

		try {
			connect = dao.getConnection();

			String mainLp = "{call sp_get_landing_page(?, ?, ?)}";

			mainStatement = connect.prepareCall(mainLp);
			mainStatement.setInt(1, page);
			mainStatement.setInt(2, index);
			mainStatement.setInt(3, limit);
			mainResultSet = mainStatement.executeQuery();
			while (mainResultSet.next()) {
				LandingPageSection section = new LandingPageSection();
				List<LandingPageItems> sectionItemsList = new ArrayList<LandingPageItems>();
				subSps = "{call " + mainResultSet.getString("section_content") + "}";
				statement = connect.prepareCall(subSps);
				resultSet = statement.executeQuery();
				ITEM_JSON_DATA = mainResultSet.getString("section_content_type");

				section.setSectionName(mainResultSet.getString("section_name"));
				section.setSectionSubTitle(mainResultSet.getString("section_description"));
				section.setSectionFormat(mainResultSet.getString("section_format"));
				section.setSectionPage(mainResultSet.getString("section_page"));
				obj = new JSONObject(ITEM_JSON_DATA);
				fieldsData = obj.getJSONArray("fields");

				while (resultSet.next()) {
					LandingPageItems sectionItems = new LandingPageItems();
					item = fieldsData.getJSONObject(0);
					if (item.has("itemID")) {
						sectionItems.setItemID(resultSet.getString(item.getString("itemID")));
					}
					if (item.has("itemName")) {
						sectionItems.setItemName(resultSet.getString(item.getString("itemName")));
					}
					if (item.has("itemImage")) {
						sectionItems.setItemImage(resultSet.getString(item.getString("itemImage")));
					}
					if (item.has("itemDescription")) {
						sectionItems.setItemDescription(resultSet.getString(item.getString("itemDescription")));
					}
					if (item.has("itemType")) {
						sectionItems.setItemType(resultSet.getString(item.getString("itemType")));
					}
					if (item.has("itemLogo")) {
						sectionItems.setItemLogo(resultSet.getString(item.getString("itemLogo")));
					}
					if (item.has("area")) {
						sectionItems.setItemArea(resultSet.getString(item.getString("area")));
					}
					if (item.has("city")) {
						sectionItems.setItemCity(resultSet.getString(item.getString("city")));
					}
					if (item.has("itemPath")) {
						sectionItems.setItemPath(prepareItemPath(resultSet, item));
					}
					if (item.has("itemImagePath")) {
						sectionItems.setItemImagePath(item.getString("itemImagePath"));
					}

					sectionItemsList.add(sectionItems);
					section.setLandingPageItems(sectionItemsList);

				}
				sectionList.add(section);

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed at getStoreDetails-->" + e.getMessage());
		} finally {
			close();
		}
		return sectionList;
	}

		
	public String prepareItemPath(ResultSet results, JSONObject jsonItem) {
		String path = "";
		
		try {
			String basePath = jsonItem.getString("itemPath");
			if (basePath.equals("/store")) {

				path = basePath + "/" + results.getString("storeCityName") + "/" + results.getString("storeCityId")
						+ "/" + results.getString("storeAreaName") + "/" + results.getString("storeAreaId") + "/"
						+ results.getString("storeName").replace(" ", "-").toLowerCase() + "/" + results.getString("storeid");
			} else if (basePath.equals("/collections")) {
				path = basePath + "/" +  results.getString("subcatName").replace(" ", "-").toLowerCase() + "/" + results.getString("subcatID");
			} else if (basePath.equals("/product")) {
				path = basePath + "/" +  results.getString("productName").replace(" ", "-").toLowerCase() + "/" + results.getString("productID");
			} else if (basePath.equals("/shop/groups")) {
				path = basePath + "/" +  results.getString("collectionGroupName").replace(" ", "-").toLowerCase() + "/" + results.getString("collectionGroupID");
			}
		} catch (Exception e) {
			System.out.println("failed at prepareItemPath-->" + e.getMessage());
		}
		return path;
	}
	public String getlandingPageDetails(int landingType, int page, int limit) {
		String jsonString = null;

		try {
			connect = dao.getConnection();

			String mainLp = "{call sp_get_preloaded_landing_page(?, ?, ?)}";

			mainStatement = connect.prepareCall(mainLp);
			mainStatement.setInt(1, landingType);
			mainStatement.setInt(2, page);
			mainStatement.setInt(3, limit);

			mainResultSet = mainStatement.executeQuery();
			while (mainResultSet.next()) {
				jsonString = mainResultSet.getString("payload");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed at getlandingPageDetails-->" + e.getMessage());
			;
		} finally {
			close();
		}

		return jsonString;
	}

	
	public List<IntPair> getlandingPageCount() {
		
		List<IntPair> pagesList = new ArrayList<LandingPageDao.IntPair>(); 
		IntPair pages = new IntPair();
		try {
			connect = dao.getConnection();
			String query = "SELECT section_page, count(*) as pagesCount FROM landing_page_prami where section_status = 1 group by section_page";
			norm_statement = connect.createStatement();
			resultSet = norm_statement.executeQuery(query);
			while(resultSet.next()) {
				 pages = new IntPair();
				 pages.setPage(resultSet.getInt("section_page"));
				 pages.setPageCount(resultSet.getInt("pagesCount"));
				 pagesList.add(pages);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed at getlandingPageCount-->" + e.getMessage());
			;
		} finally {
			close();
		}

		return pagesList;
	}

	public boolean reloadLandingPage(String payload, int preloadType) {
		boolean status = false;
		try{
			connect = dao.getConnection();
			java.util.Date date= new java.util.Date();
			long currentTime = new Timestamp(date.getTime()).getTime();
			String query = "INSERT INTO landing_page_preloaded (payload, preloadtype, status, updatedTime) VALUES ('"+payload.replaceAll("'", "\\\\'")+"', '"+preloadType+"', '1', '"+currentTime+"')";
			pst = connect.prepareStatement(query);
	    	pst.executeUpdate(); 
	    	status = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at LandingPageDao --> reloadLandingPage()-->"+e.getMessage());
			status = false;
		}finally{
			close();
		}
		return status;
	}
	public boolean deletePreloadedLandingPage() {
		boolean status = false;
		try{
			connect = dao.getConnection();
			String query = "DELETE  FROM   landing_page_preloaded";
			pst = connect.prepareStatement(query);
	    	pst.executeUpdate(); 
	    	status = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed at LandingPageDao --> deletePreloadedLandingPage()-->"+e.getMessage());
			status = false;
		}finally{
			close();
		}
		return status;
	}
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (mainResultSet != null) {
				mainResultSet.close();
			}
			if (mainStatement != null) {
				mainStatement.close();
			}
			if (norm_statement != null) {
				norm_statement.close();
			}
			if (connect != null) {
				connect.close();
			}
			if (pst != null) {
		        pst.close();
		      }
		} catch (Exception e) {

		}
	}
}
