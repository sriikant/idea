/*package idea.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import airtel.core.bean.JsonFinalResponse;
import airtel.core.bean.TestData;

@Component(service =  {Servlet.class}, property= {"sling.servlet.methods="+ HttpConstants.METHOD_GET,"sling.servlet.methods="+ HttpConstants.METHOD_POST,"sling.servlet.paths="+"/bin/server/MyMovieSer"})
public class MyTestServlet extends SlingAllMethodsServlet {

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 8296768504278043385L;
	private static final Logger log=LoggerFactory.getLogger(MyTestServlet.class);
	@Override
	public void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse response) throws ServletException,IOException{
		log.info("in servlet=");
		try {
			//JSONObject eachOption;
			//JSONArray optionsArray = new JSONArray();
			//TestData testData = new TestData();
			String[] movies = { "Terminator", "Kicking & Screaming","Harold and Maude", 
                    "Ratatouille", "10 Things I Hate About You", "American Beauty", 
                    "The Dark Knight", "The Wolf of Wall Street","Mean Girls", 
                    "Inception", "Life Is Beautiful", "No Country for Old Men" };
	
			
			
		List<TestData> testData = new ArrayList<TestData>();
		for(int i=0;i<movies.length;i++) {
			testData.add(new TestData(movies[i],movies[i]));
			}
		Gson gson = new Gson();
		JsonFinalResponse jsonRes = new JsonFinalResponse();
		jsonRes.setRoot(testData);
		String jsonInString = gson.toJson(testData);
		log.info("jsonInString="+jsonInString);
		//JSONObject finalJsonResponce = new JSONObject();
		//finalJsonResponce.put("root", optionsArray);
		response.getWriter().println(jsonInString);
		}catch(IOException e) {
			log.error("IOException");
		}
	}
	
	public void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse response ) throws ServletException,IOException {
		log.error("inside do post");
	}
	
}


*/