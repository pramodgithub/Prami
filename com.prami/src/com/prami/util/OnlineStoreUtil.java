package com.prami.util;

import java.util.List;

import com.prami.bean.OnlineStore;
import com.prami.bean.StoreProperties;
import com.prami.dao.StoreDao;

public class OnlineStoreUtil {
	
	public class StoreInfo {
		OnlineStore onlineStore;
		List<StoreProperties> storeProperties;
		public OnlineStore getOnlineStore() {
			return onlineStore;
		}
		public void setOnlineStore(OnlineStore onlineStore) {
			this.onlineStore = onlineStore;
		}
		public List<StoreProperties> getStoreProperties() {
			return storeProperties;
		}
		public void setStoreProperties(List<StoreProperties> storeProperties) {
			this.storeProperties = storeProperties;
		}
		
		
	}
	
	public StoreInfo getOnlineStoreDetails(int storeId) {
		StoreInfo storeInfo = new StoreInfo();
		StoreDao store = new StoreDao();
		
		storeInfo.setOnlineStore(store.getStoreInfo(storeId));
		storeInfo.setStoreProperties(store.getStoreProperties(storeId));
		
		return storeInfo;
	}
}
