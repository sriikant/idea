

import java.util.HashMap;
import java.util.Map;


import org.osgi.service.cm.Configuration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.LoginException;



/**
 * The Class WeatherServiceImpl.
 */

public class AuthenticationUtility {

  /** The Constant logger. */
  final static Logger logger = LoggerFactory.getLogger(AuthenticationUtility.class);


  /** The resource resolver factory. */
  @Reference
  private ResourceResolverFactory resourceResolverFactory;

  private ResourceResolver resolver;


  /**
   * Activate.
   *
   * @param config the config
   */
  



  /**
   * check if we can get /apps resource, print weather it's null or not
   */
  public String checkApps() {
    logger.info("*************** checkApps");
    Map<String, Object> param = new HashMap<String, Object>();
    param.put(ResourceResolverFactory.SUBSERVICE,"datawrite");
    ResourceResolver resourceResolver = null;
    try
    {
      resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
      logger.info("*************** resource resolver user id: "+resourceResolver.getUserID());
      Resource appsResource = resourceResolver.getResource("/apps");

      // return appropriate msg
      return appsResource == null
             ? "apps resource is null"
             : "apps resource is NOT null";

    }
    catch (LoginException e)
    {
      logger.error("LoginException",e);
      return e.toString();
    }
    finally
    {
      if(resourceResolver != null && resourceResolver.isLive()){
        resourceResolver.close();
      }
    }

  }
  
}
