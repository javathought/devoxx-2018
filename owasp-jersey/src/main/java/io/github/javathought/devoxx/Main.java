package io.github.javathought.devoxx;

import io.github.javathought.devoxx.dao.Connexion;
import io.github.javathought.devoxx.filters.CrossDomainFilter;
import io.github.javathought.devoxx.filters.SecurityFilter;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import io.swagger.models.auth.OAuth2Definition;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.filtering.SelectableEntityFilteringFeature;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.server.ResourceConfig;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://localhost:%d/myapp/";
    private static final int BACKEND_PORT = 8084;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer(int port) {
        Map<String, Object> properties = new HashMap();

        bridgeLog();

        // create a resource config that scans for JAX-RS resources and providers
        // in io.github.javathought.devoxx package
        final ResourceConfig rc = new ResourceConfig().packages("io.github.javathought.devoxx", "org.glassfish.jersey.examples.jackson");
        rc.register(SecurityFilter.class);
        rc.register(CrossDomainFilter.class);
        rc.register(SelectableEntityFilteringFeature.class);
        rc.property(SelectableEntityFilteringFeature.QUERY_PARAM_NAME, "select");
        rc.register(JacksonFeature.class);

        rc.register(ApiListingResource.class);
        rc.register(SwaggerSerializers.class);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8084");
        beanConfig.setBasePath("/myapp");
        beanConfig.setResourcePackage("io.github.javathought.devoxx.resources");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(getBaseUri(port)), rc);
    }

    /**
     * Main method.
     * @param args command line arguments
     * @throws IOException Stops if I/O problem
     */
    public static void main(String[] args) throws IOException {
        if (Connexion.getInstance()
                .setConnection("jdbc:mysql://localhost:3306/devoxx_tia",
        "devoxx", "owasp-2017;").getDbConnection() != null) {

            final HttpServer server = startServer();
            System.out.println(String.format("Jersey app started with WADL available at "
                    + "%sapplication.wadl\nHit enter to stop it...", getBaseUri()));
//            System.in.read();
//            server.shutdownNow();
        } else {
            System.err.println("Unable to connect to DB");
        }
    }

    static HttpServer startServer() {
        return startServer(BACKEND_PORT);
    }


    static String getBaseUri() {
        return getBaseUri(BACKEND_PORT);
    }

    private static String getBaseUri(int port) {
        return String.format(BASE_URI, port);
    }

    private static void bridgeLog() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Logger.getLogger("global").setLevel(Level.FINEST);
    }
}

