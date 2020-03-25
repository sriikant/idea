package idea.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;


	@Model(adaptables = Resource.class)
	public class SelectHideAndShowSRK {

		// Inject the products node under the current node
	@Inject   
	@Optional
	private Resource select;
	//No need of a post construct as we don't have anything to modify after the
	// model is constructed

	public Resource getSelect() {
		return select;
	}

	}