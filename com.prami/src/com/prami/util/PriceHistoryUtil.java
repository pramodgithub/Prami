package com.prami.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prami.bean.PriceGraph;
import com.prami.bean.PriceHistory;
import com.prami.dao.PriceHistoryDao;

public class PriceHistoryUtil {
	
	public PriceGraph getPriceHistoryGraph(int prodcutId){
		int minPrice, maxPrice;
		
		List<Integer> price = new ArrayList<Integer>();
		List<PriceHistory> priceHistoryList = new ArrayList<PriceHistory>();
		PriceHistoryDao p = new PriceHistoryDao();
		priceHistoryList = p.getProductPriceHistory(prodcutId);
		PriceGraph priceGraph = null;
		int predictedPrice,predictedDiscount, currentPrice = 0;
		String description = "";
		for(PriceHistory ph: priceHistoryList){
			if(Integer.valueOf(ph.getUpdateType()) == 2 || Integer.valueOf(ph.getUpdateType()) == 5  ){
				price.add(ph.getUpdateInfo());
				currentPrice = ph.getUpdateInfo();
			}
		}
		try{
			Map<Integer,Integer> MinMaxMap = getMinMaxPrice(price);
			Map.Entry<Integer, Integer> minMaxEntry = MinMaxMap.entrySet().iterator().next();
			minPrice = minMaxEntry.getKey();
			maxPrice = minMaxEntry.getValue();
			
			
			predictedPrice = getAverageDeviation(price);
			//predictedDiscount = (( predictedPrice * 100) / currentPrice ) - 100;
			
			 Map<String,Integer> descriptionMap = getDescription(minPrice, maxPrice, predictedPrice, currentPrice);
			 Map.Entry<String, Integer> descEntry = descriptionMap.entrySet().iterator().next();
			 description = descEntry.getKey();
			 predictedDiscount = descEntry.getValue();
			priceGraph = new PriceGraph(getDateValues(priceHistoryList), getPriceValues(priceHistoryList, maxPrice), minPrice, maxPrice, predictedDiscount, description);
		}catch(Exception e){
			//e.printStackTrace();
			priceGraph = new PriceGraph("No Data to show");
		}
		return priceGraph;
	}
	
	public List<Integer> getPriceValues(List<PriceHistory> priceHistory, int maxPrice){
		List<Integer> priceList = new ArrayList<Integer>();
		int priceVal ;
		for(PriceHistory p : priceHistory){
			priceVal = ( Integer.valueOf(p.getUpdateInfo() * 100) / maxPrice);
			priceList.add(priceVal - (priceVal/10));
			
		}
		for(int i=priceHistory.size();i<20;i++){
			priceList.add(0);
		}
		return priceList;
	}
	
	public List<String> getDateValues(List<PriceHistory> priceHistory){
		List<String> dateList = new ArrayList<String>();
		int count = 0, dateRange = 4;
		for(PriceHistory p : priceHistory){
			if((count%dateRange)==0){
				dateList.add(p.getReadableDate());
			}
			count++;
		}
		return dateList;
	}
	
	public Integer getAverageDeviation(List<Integer> price){
		int sum = 0,predictedPrice=0;
		float mean,absDev,absDevSum = 0;
		for(int pr : price){
			sum = sum + pr;
		}
		
        mean = (float)sum / price.size();

        for(int pr : price){
        	absDevSum = absDevSum + Math.abs(pr - mean);
		}
        absDev = absDevSum/price.size();
               
        Collections.sort(price);
       
        for (Integer value : price) {
		    if (value >= (mean-absDev) && value <= (mean+absDev)) {
		    	predictedPrice = value;
		        break;
		    }
		}
        return predictedPrice;
	}
	
	
	public Integer getStandardDeviation(List<Integer> price){
		int sum = 0,predictedPrice=0;
		float mean,stdDev,stdDevSum = 0;
		
		
		for(int pr : price){
			sum = sum + pr;
		}
		
        mean = (float)sum / price.size();

        for(int pr : price){
        	stdDevSum = (float) (stdDevSum + Math.pow((pr - mean),2));
		}
        stdDev = (float) Math.sqrt(stdDevSum/(price.size()-1));
        
        for (Integer value : price) {
		    if (value >= (mean-stdDev) && value <= (mean+stdDev)) {
		    	predictedPrice = value;
		        break;
		    }
		}
        return predictedPrice;
	}
	public Map<String,Integer> getDescription(int min, int  max, int predictedPrice, int currentPrice){
		Map<String,Integer> descMap = new HashMap<String,Integer>();
		String description = "", suggestion = "", originalDiscount = "";
		int desc = 0;
		int percent = (( predictedPrice * 100) / currentPrice ) - 100;
		
		if(percent == 0){
			desc = 0;
			originalDiscount = "Hmm, looks like the odds aren't in your favour";
			suggestion= " You may buy product at this price.";
		}else if(percent < 0){
			desc = percent;
			originalDiscount = "Price is higher than expected";
			suggestion= "But hey! You can save some of your money here, find related products bellow.";
		}else if(percent > 0){
			desc = percent;
			originalDiscount = "Now the odds are in your favour";
			suggestion= "Reward yourself with the discount of "+desc+"%.";
		}
		//". Max price was Rs."+max+" and lowest was Rs."+min+";
		description = originalDiscount+". "+suggestion;
		descMap.put(description, desc);
		return descMap;
	}
	public Map<Integer,Integer> getMinMaxPrice(List<Integer> priceList){
		Map<Integer,Integer> minMaxMap = new HashMap<Integer,Integer>();
		minMaxMap.put(Collections.min(priceList), Collections.max(priceList));
		return minMaxMap;
		
	}
}
