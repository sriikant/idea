package idea.core.servlets;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.PropertyType;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=HTTP servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/NewSimpleUserGroup" })

public class NewSimpleUserGroup extends SlingAllMethodsServlet {


	private static final long serialVersionUID = -7287331813072523926L;
	private Session adminSession;
	  
	 @Reference
	 ResourceResolverFactory resolverFactory;
	 private static Logger logger = LoggerFactory.getLogger(NewSimpleUserGroup.class);
	   
	 protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
		 ResourceResolver resolver = null;   
		  String groupName= request.getParameter("groupName");
		  String userName=request.getParameter("userName");
		  String password="sampleUser";
		    logger.info(" userName************" +userName);
	    	logger.info(" groupName **********" +groupName);
	    	logger.info(" password************" +password);
  try {
	      logger.info("   inside try block   ");
		  Map<String, Object> authInfoParam = new HashMap<String, Object>();
		    authInfoParam.put(ResourceResolverFactory.SUBSERVICE, "dataservice");
		   	resolver = resolverFactory.getServiceResourceResolver(authInfoParam);
		    logger.info("  **** subservice done********   ");
		   	  logger.info("*** IN SERVLET *** GroupName is "+groupName);
		        //Invoke the adaptTo method to create a Session used to create a QueryManager
		         resolver = resolverFactory.getServiceResourceResolver(authInfoParam);
		         adminSession = resolver.adaptTo(Session.class);

       final UserManager userManager= resolver.adaptTo(UserManager.class);	         
       Group group= null;
       if (userManager.getAuthorizable(groupName) == null) {
    	   logger.info("   ********group creation started********  ");
    	   //adminResolver.refresh();
           group = userManager.createGroup(groupName,new SimplePrincipal(groupName),"/home/groups/n");
            
           ValueFactory valueFactory = adminSession.getValueFactory();
           Value groupNameValue = valueFactory.createValue(groupName, PropertyType.STRING);
           group.setProperty("./profile/givenName", groupNameValue);
           //adminResolver.commit();
           logger.info("----------------------> {} Group successfully created.",group.getID());
       } else {
           logger.info("----------------------> Group already exist..");
       }	         
		 
    // Create a User
       User user = null;
       if (userManager.getAuthorizable(userName) == null) {
    	   logger.info("   ********** user creation started ************   ");
    	   //adminResolver.refresh();
           user=userManager.createUser(userName, password,new SimplePrincipal(userName),"/home/users/M");
            
           ValueFactory valueFactory = adminSession.getValueFactory();
           Value firstNameValue = valueFactory.createValue("Arpit", PropertyType.STRING);
           user.setProperty("./profile/givenName", firstNameValue);
            
           Value lastNameValue = valueFactory.createValue("Bora", PropertyType.STRING);
           user.setProperty("./profile/familyName", lastNameValue);
            
           Value emailValue = valueFactory.createValue("arpit.p.bora@gmail.com", PropertyType.STRING);
           user.setProperty("./profile/email", emailValue);
           //adminResolver.commit();
           logger.info("-------------------------> {} User successfully created.",user.getID());
       } else {
           logger.info("-------------------------> User already exist..");
       }
       
	    // Add Users to Group
	       Group addUserToGroup = (Group)(userManager.getAuthorizable(groupName));
	       addUserToGroup.addMember(userManager.getAuthorizable(userName));
	       adminSession.save();

			}catch (Exception e) {
		         logger.info("------------------> Not able to perform User Management..");
		         logger.info("-----------------------> Exception.." + e.getMessage());
		         response.getWriter().write("AEM User WAS NOT successfully created.."); 
		     } finally {
		         if (adminSession != null && adminSession.isLive()) {
		             adminSession.logout();
		         }
		         if (resolver != null)
		             resolver.close();
		         response.getWriter().write("AEM User "+userName +" successfully created.."); 
		     }
		 }
	 protected final void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse res)throws IOException, ServletException{
	     doPost(req, res);
      }
	 private static class SimplePrincipal implements Principal {
	     protected final String name;
	 
	     public SimplePrincipal(String name) {
	    	 logger.info("   SimplePrincipal class " +name);
	         if (name.compareTo("")==0) {
	             throw new IllegalArgumentException("Principal name cannot be blank.");
	         }
	         this.name = name;
	     }
	 
	     public String getName() {
	         return name;
	     }
	 
	     @Override
	     public int hashCode() {
	         return name.hashCode();
	     }
	 
	     @Override
	     public boolean equals(Object obj) {
	         if (obj instanceof Principal) {
	             return name.equals(((Principal) obj).getName());
	         }
	         return false;
	     }
	 }
	  
	}