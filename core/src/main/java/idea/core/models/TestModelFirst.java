package idea.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

@Model(adaptables = SlingHttpServletRequest.class)
public class TestModelFirst {


	@Inject
    private Resource resource;
   
	private static final Logger log = LoggerFactory.getLogger(TestModelFirst.class);
    @PostConstruct
    private void init() {
    	log.info("In sling model");
    }
 // Injects currentPage using ScriptVariable annotation
    @ScriptVariable(name="currentPage")
    Page page;
    
    @SlingObject
    ResourceResolver resourceResolver;
    
    public String getPagePath() {
        return  page.getPath();
    }
    public String getTitle() {
     return page.getTitle();
    }
    public String getName() {
     return resource.getName();
    }
     
    public String getCompName() {
        Resource res = resourceResolver.getResource(resource.getResourceType());
        		ValueMap vp = res.adaptTo(ValueMap.class);
        String compName = vp.get("jcr:title",String.class);
        return compName;
    }
	
}
