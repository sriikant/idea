package idea.core.servlets;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Component(service=Servlet.class, immediate=true,
property={
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,       
        "sling.servlet.paths=" + "/bin/idea/SelectDropDownServlet",
})
public class SelectDropDownServlet extends SlingAllMethodsServlet {

private static final long serialVersionUID = 1L;

@Override
protected void doGet(SlingHttpServletRequest request,
SlingHttpServletResponse response) throws ServletException,
IOException {
String[] allCountries = Locale.getISOCountries();

/*JsonObject js = new JsonObject() ;
js.getAsJsonObject("{data:name}");
js.get("data");
*/
JsonObject countryJson;
JsonArray countyArray = new JsonArray();
for (int i = 0; i < allCountries.length; i++) {

String country = allCountries[i];
Locale locale = new Locale("en", country);
countryJson = new JsonObject();
// Get the country name by calling getDisplayCountry()
String countryName = locale.getDisplayCountry();

countryJson.addProperty("text", countryName);
countryJson.addProperty("value", countryName);
countyArray.add(countryJson);
}

 

JsonObject finalJson = new JsonObject();
finalJson.add("country", countyArray);
//Setting the response as a json
response.setContentType("application/json");
//encoding the response for special characters
response.setCharacterEncoding("utf8");
response.getWriter().print(finalJson);


}

}