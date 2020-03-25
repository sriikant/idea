package idea.core.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.Session;

import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

/**
 * @author Srikanth Samala
 * 
 * This component lists all the users and groups present in the JCR
 */
public class GettingUsersAndGroups extends WCMUsePojo {

	private static final Logger logger = LoggerFactory.getLogger(GettingUsersAndGroups.class);
	private List<String> users,groups;
	private Session session;
	@Override
	public void activate() throws Exception {
		// TODO Auto-generated method stub
		try {
			logger.info("users/groups class ");
			ResourceResolver resourceResolver= getRequest().getResourceResolver();
			session = resourceResolver.adaptTo(Session.class);
			UserManager userManager = ((JackrabbitSession) session).getUserManager();
			Iterator<Authorizable> userIterator = userManager.findAuthorizables("jcr:primaryType","rep:User");
		    Iterator<Authorizable> groupIterator = userManager.findAuthorizables("jcr:primaryType","rep:Group");
		    users = new LinkedList<>();
		    groups= new LinkedList<>();
		    while(userIterator.hasNext()) {
		    	logger.info("getting users");
		    	Authorizable user = userIterator.next();
		    	  if(!user.isGroup()) {
		    		  logger.info("user founf : {}",user.getID());
		    		     users.add(user.getID());
		                 	  }
		             }
		    		 while(groupIterator.hasNext()) {
		    			 logger.info("getting groups");
		    		Authorizable group = groupIterator.next();
		    		if(group.isGroup()) {
		    			logger.info("group found {}",group.getID());
		    		    groups.add(group.getID());
		    		}
		    	  }
		    	
		    
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	
	/**
	 * @return users
	 */
		public List<String> getUsers(){
			return users;
		}
    /**
     * @return groups
     */
		public List<String> getGroups(){
			return groups;
		}
}