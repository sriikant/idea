package idea.core.servlets;

import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;

import javax.jcr.PropertyType;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=HTTP servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/SimpleUserGroup" })

public class SimpleUserGroup extends SlingAllMethodsServlet{

	
	private static final long serialVersionUID = 10314493469702688L;
    private static final Logger logger=LoggerFactory.getLogger(SimpleUserGroup.class);
    
    protected final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse responce)throws ServletException, IOException{
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
    	final UserManager userManager = resourceResolver.adaptTo(UserManager.class);
    	
    	if(null==userManager.getAuthorizable("administrators")){
    		logger.info("  group creation if condition started   ");
            Group group=userManager.createGroup(groupName,new SimplePrincipal(groupName),"/home/groups/1"); 

            Value value=session.getValueFactory().createValue("Sample Group", PropertyType.STRING);
            group.setProperty("./profile/givenName", value);
             
            value=session.getValueFactory().createValue("Test Group", PropertyType.STRING);
            group.setProperty("./profile/aboutMe", value);
             
            value=session.getValueFactory().createValue("abc@gmail.com", PropertyType.STRING);
            group.setProperty("./profile/email", value);        
            logger.info("  group creation if condition ended   ");         
            }else{
            	Object obj = null; 
            	 Group gp = (Group) userManager.getAuthorizable("administrators");
            	 Iterator<Authorizable> users = gp.getMembers();
            	   while(users.hasNext()){
                       obj = users.next();
                               if(!(obj instanceof User)){
                                    continue;
                                }

                   User   user = (User)obj;
                       JsonArray userArray = new JsonArray();
                      userArray.add(user.getID());         		  
          		  
          		    
          		  String myresp = userArray.toString();
            	logger.info("  Group already created   ");	
            responce.getWriter().write(myresp);
            	   }
            }
    	if(userManager.getAuthorizable(userName)==null){
    		logger.info("  user creation if condition started   ");
            User user=userManager.createUser(userName, password, new SimplePrincipal(userName),"/home/users/i");
            Value value=session.getValueFactory().createValue(userName, PropertyType.STRING);
            user.setProperty("./profile/familyName", value);
            
            value=session.getValueFactory().createValue("Testetest tes", PropertyType.STRING);
            user.setProperty("./profile/givenName", value);
            
            value=session.getValueFactory().createValue("Testetest v", PropertyType.STRING);
            user.setProperty("./profile/aboutMe", value);
            
            value=session.getValueFactory().createValue("Testetest@gmail.com", PropertyType.STRING);
            user.setProperty("./profile/email", value);      
            logger.info("   user creation if condition ended   ");
            }else{
            	
            	logger.info("  user already created   ");	
             responce.getWriter().write("   *****User already exist..******"+userName); 
            }
    
        	Group group = (Group)(userManager.getAuthorizable(groupName));
              group.addMember(userManager.getAuthorizable(userName));
             session.save();
    	}catch(Exception e) {
    		logger.error(e.getMessage(),e);
    	    e.printStackTrace();
    	    responce.getWriter().write("not able to perfoem user management");
    	}finally {
    		responce.getWriter().write("******AEM user/group successfully created------*****");
    	}
     }
protected final void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse res)throws IOException, ServletException{
    	     doPost(req, res);
    }
		  
private static class SimplePrincipal implements Principal {
		        protected final String name;
		 
		        public SimplePrincipal(String name) {
		        	logger.info("   SimplePrincipal class " +name);
		            if (StringUtils.isBlank(name)) {
		            	logger.info("   SimplePrincipal if condition " +name);
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