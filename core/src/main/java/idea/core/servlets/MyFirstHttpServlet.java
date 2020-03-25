package idea.core.servlets;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import org.osgi.framework.Constants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=HTTP servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/demo/httpcall" })

public class MyFirstHttpServlet extends SlingSafeMethodsServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2926109243133184167L;
	private static final Logger log = LoggerFactory.getLogger(MyFirstHttpServlet.class);
	
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)throws ServletException,IOException {
		
		try {
			
			 printHello();
			 String msg = myMessage();
			 printHello("Test");
			 String msg2 = myMessage("My Test");
			/**
			 * Printing the json response on the browser
			 */
			response.getWriter().println("srikanth first servlet"+msg+msg2);
			log.info("first servlet---------------srk");
			
			} catch (Exception e) {
				
				log.error(e.getMessage(), e);
			}
	}
	
	private void printHello() {
		log.info("printing hello world");
	}
	private String myMessage() {
		return "Hello new world";
	}
	private void printHello(String param) {
		log.info("printing hello world"+param);
	}
	private String myMessage(String param) {
		return "Hello new world" +param;
	}
}