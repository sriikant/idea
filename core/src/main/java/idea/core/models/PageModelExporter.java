/*package idea.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.Self;

//import com.day.cq.wcm.api.Page;

@Model(adaptables = Resource.class, resourceType = "/apps/practise-srk/components/practise")
@Exporter(name = "jackson", extensions = "json")
public class PageModelExporter {

    @Self
    private Resource resource;
    
    @Inject @Named("jcr:title") @Required
    private String title;
   
    @PostConstruct
    private void init() {
    }
   
    public String getTitle() {
  return title;
     
    }
    
   
}*/