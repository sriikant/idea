package idea.core.models;

import com.adobe.cq.sightly.WCMUsePojo;

     
import idea.core.bean.HeroTextBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
    
    
    
public class HeroTextComponent
extends WCMUsePojo
{
    
     /** The hero text bean. */
    private HeroTextBean heroTextBean = null;
      
    /** Default log. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
         
    @Override
    public void activate() throws Exception {
          
      heroTextBean = new HeroTextBean();
           
        //Get the values that the author entered into the AEM dialog
       
       String description = getProperties().get("description","");
       String dropdown = getProperties().get("dropdown", "");
             
       
       heroTextBean.setDescription(description);
       heroTextBean.setDropdown(dropdown); 
                               
    }
    
         
    public HeroTextBean getHeroTextBean() {
        return this.heroTextBean;
    }
}