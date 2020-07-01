package config;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.core.interception.jaxrs.ContainerResponseFilterRegistryImpl;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class CORSFilter extends ContainerResponseFilterRegistryImpl {


    public CORSFilter(ResteasyProviderFactory providerFactory) {
		super(providerFactory);
	}

	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.putSingle("Access-Control-Allow-Origin", "*");
    }
}
