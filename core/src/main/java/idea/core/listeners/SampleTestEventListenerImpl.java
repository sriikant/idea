package idea.core.listeners;

import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = EventListener.class, immediate = true)
public class SampleTestEventListenerImpl implements EventListener {

	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Reference
	private SlingRepository slingRepository;
	private Session session;
	private ResourceResolverFactory resourceResolverFactory;
	private ResourceResolver resoourceResolver;
	private ObservationManager observationManager;
	private BundleContext bundleContext;
	
	@Activate
	protected void activate(ComponentContext ctx) {
		//this.bundleContext = ctx.getBundleContext();  what is the usage
	logger.info("listener acivate method activated");
	
	  try {
		  session = slingRepository.loginService("datawrite",null);
		  logger.info("session called event listener" +session);
		  observationManager = session.getWorkspace().getObservationManager();
		  logger.info("observationManager++++++ "+observationManager);
		int trigger=  Event.NODE_ADDED | Event.NODE_REMOVED | Event.PROPERTY_CHANGED | Event.PROPERTY_ADDED | Event.PROPERTY_REMOVED ;
		  observationManager.addEventListener(this, trigger , "/apps", true, null, null, false);
	  logger.info("addEventListener method called");
	  }catch(Exception e ) {
		  logger.info(e.getMessage(), e);
	  }
	}
	@Deactivate
	protected void deactivate() {
		if(session != null) {
			session.logout();
			logger.info("deactivate method called");
		}
	}

	@Override
	public void onEvent(EventIterator events) {
   		try {
			while(events.hasNext()){
		logger.info("node has been added or proeprty added "+events.nextEvent());
			}
	      }catch(Exception e){
		logger.info("exception occurred ", e);
	       }
    }
	
}
