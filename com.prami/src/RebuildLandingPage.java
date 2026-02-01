import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prami.bean.LandingPageSection;
import com.prami.dao.LandingPageDao;
import com.prami.dao.LandingPageDao.IntPair;

public class RebuildLandingPage {

	public static void main(final String[] arguments) {
		
		LandingPageDao l = new LandingPageDao();
		
		List<IntPair> intPairList = l.getlandingPageCount();
		l.deletePreloadedLandingPage();
		for(IntPair pair : intPairList) {	
			int index = 0;
			int limit = 10;
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