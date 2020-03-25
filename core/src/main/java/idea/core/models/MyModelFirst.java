package idea.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

@Model(adaptables = Resource.class)
public class MyModelFirst {


	
	private static final Logger log = LoggerFactory.getLogger(MyModelFirst.class);
    @PostConstruct
    private void init() {
    	log.info("In sling model");
    }
    
    @Inject
    private Resource resource;
   
 // Injects currentPage using ScriptVariable annotation
    @ScriptVariable(name="currentPage")
    Page page;
    
    public String getName() {
        return resource.getName();
       }
    public String getGetName() {
        return  page.getName();
    }
     
   
	
}
