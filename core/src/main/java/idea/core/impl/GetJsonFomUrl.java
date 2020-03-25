
package idea.core.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;

@Component(service =  GetJsonFomUrl.class, immediate=true )

public class GetJsonFomUrl implements JsonUrlInterface {

	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "http://dummy.restapiexample.com/api/v1/employees";

	private static final String POST_URL = "https://localhost:9090/SpringMVCExample/home";

	private static final String POST_PARAMS = "userName=Pankaj";

	private static final Logger logger=LoggerFactory.getLogger(GetJsonFomUrl.class);

	public String sendGET() throws IOException {
		logger.info("sendGet method called");
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		StringBuffer response = new StringBuffer();
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
		return response.toString();

	}

	public void sendPOST() throws IOException {
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}
	}
	//Get the Resource resolver through resolver factory - Creating the node through Service
			@Reference
			private ResourceResolverFactory resolverFactory;//Get the resolverFactory reference in the service

	public void createNode(ResourceResolver resresolve, String jsonData) throws RepositoryException {
		final String BASE_PATH = "/content/hideandshowdialogfields/jcr:content"; // the folder under which the nodes should be created
		System.out.println("BASE_PATH=="+BASE_PATH);
		Session session = resresolve.adaptTo(Session.class);
		String nodeName = "testdatsa";
		//Create the Node
		//get the value
		
		Node node = JcrUtil.createPath(BASE_PATH+nodeName, JcrConstants.NT_UNSTRUCTURED, session);
//update
		
		//Set the required properties
		node.setProperty("name", "sample");
		node.setProperty("description", "sample");

		//Save the session
		session.save();
	}
}
