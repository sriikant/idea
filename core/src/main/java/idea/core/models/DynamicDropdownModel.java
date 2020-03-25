package idea.core.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import idea.core.bean.CountryDetail;
import idea.core.bean.CountryList;
import idea.core.impl.GetJsonFomUrl;

import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.wrappers.ValueMapDecorator;
 
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.iterators.*;
 
// import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
@Model(adaptables = SlingHttpServletRequest.class)
public class DynamicDropdownModel {


	@Self
	SlingHttpServletRequest request;
	
	@OSGiService
	GetJsonFomUrl getJsonUrl;
	
	@SlingObject
    ResourceResolver resolver;
	private static final Logger log = LoggerFactory.getLogger(DynamicDropdownModel.class);
   
 
     
    @PostConstruct
    public void getCompName() throws IOException {
    	log.info("In getCompName");
    	//Creating the Map instance to insert the countries
    	 final Map<String, String> countries = new LinkedHashMap<String, String>();
    	 String myResponse = getJsonUrl.sendGET();
    	 
    		Gson gson = new Gson();
    		CountryList jsonInString = gson.fromJson(myResponse, CountryList.class); 
    	List<CountryDetail> countryDetail = jsonInString.getData();
    	 log.info("In myResponse"+myResponse);
     	 Iterator<CountryDetail> myIterator = countryDetail.iterator();
    	while(myIterator.hasNext()) {
    		CountryDetail cd = myIterator.next();
    		 countries.put(cd.getId(),cd.getEmployee_name());
        	 
    	}
    	 /* countries.put("us", "United States");
    	 countries.put("aus", "Australia");
    	 countries.put("pak", "Pakistan");
    	 countries.put("sri1", "Srilanka1");
    	 countries.put("sri", "Srilanka");
    	 countries.put("sri2", "Srilanka2");*/
    	 
    	 @SuppressWarnings("unchecked")
    	 
    	//Creating the Datasource Object for populating the drop-down control.
    	 DataSource ds = new SimpleDataSource(new TransformIterator(countries.keySet().iterator(), new Transformer() {
    	 
    	 @Override
    	 
    	//Transforms the input object into output object
    	 public Object transform(Object o) {
    	 String country = (String) o;
    	 
    	//Allocating memory to Map
    	 ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());
    	 
    	//Populate the Map
    	 vm.put("value", country);
    	 vm.put("text", countries.get(country));
    	 
    	 return new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm);
    	 }
    	 }));
    	 
    	 request.setAttribute(DataSource.class.getName(), ds);
    	 
    	 }
    
    

}
	

