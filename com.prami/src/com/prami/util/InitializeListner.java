package com.prami.util;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prami.bean.LandingPageSection;
import com.prami.dao.LandingPageDao;
import com.prami.dao.LandingPageDao.IntPair;



@SuppressWarnings("serial")
public class InitializeListner extends HttpServlet {
	public void init() {
		
		//startlandingPageUtility();
	}
	
	class BeepTask implements Runnable {

	      public void run() {
	         System.out.println("Started landing page utility");   
	         LandingPageDao l = new LandingPageDao();
	 		
	 		List<IntPair> intPairList = l.getlandingPageCount();
	 		l.deletePreloadedLandingPage();
	 		for(IntPair pair : intPairList) {	
	 			int index = 0;
	 			int limit = 3;
	 			int mod = (pair.getPageCount() % limit > 0) ? 1 : 0;
	 			int loop =  pair.getPageCount() / limit + mod ;
	 			
	 			
	 			for (int i = 0; i < loop; i++) {
	 				List<LandingPageSection> section = l.prepareLandingPageDetails(pair.getPage(), index, limit);
	 	
	 				Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	 				String jsonSectionList = gson.toJson(section);
	 				l.reloadLandingPage(jsonSectionList, pair.getPage());
	 				index += limit;
	 			}
	 		} 
	      }
	   }
	public void startlandingPageUtility() {
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		  final ScheduledFuture<?> beepHandler = 
		     scheduler.scheduleAtFixedRate(new BeepTask(), 2, 10, TimeUnit.SECONDS);

		  scheduler.schedule(new Runnable() {
			 @Override
		     public void run() {
		    	 
		        beepHandler.cancel(true);
		        scheduler.shutdown();			
		     }
		  }, 10, TimeUnit.SECONDS); 

		   
		
	}
}