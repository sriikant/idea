package idea.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import idea.core.bean.CountryList;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=HTTP servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/gettingDataFromJson" })

public class DisplayHtmlFromJsonUrl extends SlingSafeMethodsServlet{

	private static final String GET_URL = "http://dummy.restapiexample.com/api/v1/employees";
	private static final long serialVersionUID = -6951284712061100361L;
    private static final Logger logger = LoggerFactory.getLogger(DisplayHtmlFromJsonUrl.class);


@Override
protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
	logger.info("getting html from json URL");
	URL obj = new URL(GET_URL);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	con.setRequestMethod("GET");
	//StringBuffer requestURL = new StringBuffer();
	StringBuffer response = new StringBuffer();
    //resp.setContentType("application/json");
    int responseCode = con.getResponseCode();
	System.out.println("GET Response Code :: " + responseCode);
	if (responseCode == HttpURLConnection.HTTP_OK) { // success
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;		
      logger.info("response code "+responseCode );
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		 logger.info(response.toString());
	} else {
		 logger.info("GET request not worked");
	}
	
    //resp.getWriter().write("getting html from json URL");
    Gson gson = new Gson();
    JsonElement jsonob = gson.fromJson("{}", JsonElement.class);
    jsonob = gson.fromJson(response.toString(), JsonElement.class);
    JsonObject newJsonOb = new JsonObject();
    newJsonOb.add("srikanth", jsonob);
    newJsonOb.addProperty("mujju", "sri");
    resp.getWriter().write(response.toString());
    
    
}
}
