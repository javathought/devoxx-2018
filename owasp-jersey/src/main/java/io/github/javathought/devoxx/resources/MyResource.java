package io.github.javathought.devoxx.resources;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import java.security.Principal;

import static io.github.javathought.devoxx.resources.MyResource.PATH;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path(PATH)
@Api(value = PATH, description = "testing resource")
public class MyResource {

    private static final Logger LOG = LoggerFactory.getLogger(MyResource.class);

    static final String PATH = "myresource";

    @Context
    SecurityContext context;


    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Path("informations")
    @Produces(APPLICATION_JSON)
    public Principal getUserInformations(@Context SecurityContext sc) {
        return context.getUserPrincipal();

    }
}
