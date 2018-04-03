package io.github.javathought.devoxx.filters;


import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.util.logging.Logger;

/**
 *
 */
public class CrossDomainFilter implements ContainerResponseFilter {

    private static final Logger LOG = Logger.getLogger(CrossDomainFilter.class.getName());


    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        String path = request.getUriInfo().getRequestUri().getPath();
        LOG.fine(String.format("Received request : crossdomain for %s", path));

        // TODO : disable for production
        // *** ONLY FOR DEMO ***
//        response.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3002");
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers", "Content-Range, Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        response.getHeaders().add("Access-Control-Expose-Headers", "Content-Range, Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
//        response.getHeaders().add("Access-Control-Allow-Credentials", "http://localhost:3002");
        response.getHeaders().add("Access-Control-Allow-Credentials", "*");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
        response.getHeaders().add("Access-Control-Max-Age", "*");
    }
}
