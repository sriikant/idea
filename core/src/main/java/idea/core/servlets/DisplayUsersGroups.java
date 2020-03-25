/**
 * 
 */
package idea.core.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



/**
 * @author srikanth samala
 *
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=HTTP servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/displayAllUsersGroups" })

public class DisplayUsersGroups extends SlingAllMethodsServlet  {
	private List<String> users,groups;
	private static final long serialVersionUID = 1L;	
	 private static final Logger logger=LoggerFactory.getLogger(DisplayUsersGroups.class);
	 protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)throws ServletException, IOException{
		   
	 String groupName = request.getParameter("groupName");
 	String userName = request.getParameter("userName");
 	String password = "sampleUser";
 	logger.info(" userName************" +userName);
 	logger.info(" groupName **********" +groupName);
 	logger.info(" password************" +password);
	
 	try {
	  	ResourceResolver resourceResolver = request.getResourceResolver();
    	Session session = resourceResolver.adaptTo(Session.class);
         logger.info("   inside try block   ");
         UserManager userManager = ((JackrabbitSession) session).getUserManager();
         Iterator<Authorizable> userIterator = userManager.findAuthorizables("jcr:primaryType","rep:User");
		  //  Iterator<Authorizable> groupIterator = userManager.findAuthorizables("jcr:primaryType","rep:Group");
		    users = new LinkedList<>();
		    groups= new LinkedList<>();
		    JsonArray userArray = new JsonArray();
		    while(userIterator.hasNext()) {
		    	logger.info("getting users");
		    	Authorizable user = userIterator.next();
		    	  if(!user.isGroup()) {
		    		  logger.info("user founf : {}",user.getID());
		    		  JsonObject temp = new JsonObject();
		    		  userArray.add(user.getID());
		                 	  }
		             }
		    JsonObject myobj = new JsonObject();
		    myobj.add("userId", userArray);
		    
		  String myresp = myobj.toString();
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(myresp);
		    /*		 while(groupIterator.hasNext()) {
		    			 logger.info("getting groups");
		    		Authorizable group = groupIterator.next();
		    		if(group.isGroup()) {
		    			logger.info("group found {}",group.getID());
		    		    groups.add(group.getID());
		    		}
		    	  }*/
 	
 	}catch(Exception e) {
 		e.printStackTrace();
 		logger.info(e.getMessage(),e);
 		response.getWriter().write("not able to perfoem user/group management");
 	}
	 }
	protected final void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse res)throws IOException, ServletException{
	     doPost(req, res);
     }
}
