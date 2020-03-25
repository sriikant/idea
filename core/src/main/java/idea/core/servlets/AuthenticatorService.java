package idea.core.servlets;

import org.apache.sling.api.resource.Resource;

public interface AuthenticatorService {
	Resource getResourceResolverForUpdate();
}
