package io.github.javathought.devoxx.bdd;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import cucumber.api.java8.Fr;
import io.github.javathought.devoxx.Main;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.glassfish.grizzly.http.server.HttpServer;

import static io.github.javathought.devoxx.filters.SecurityFilter.ROLES;
import static io.github.javathought.devoxx.filters.SecurityFilter.USER_ID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;


public class ServeurSteps implements En {

    private static final int TEST_PORT = 8085;
    private ValidatableResponse valResponse;
    private HttpServer server;
    private RequestSpecification api;
    private Response response;

    public ServeurSteps() {

        When("^l'utilisateur se connecte avec l'identifiant \"([^\"]*)\"$", (String arg0) -> api.header(USER_ID, arg0));
        And("^l'utilisateur a le role \"([^\"]*)\"$", (String arg0) -> api.header(ROLES, arg0));
        And("^l'utilisateur appelle l'url \"([^\"]*)\"$", (String arg0) -> response = api.when().get("myapp/" + arg0));
        Then("^le code retour est (\\d+)$", (Integer arg0) -> valResponse = response.then().statusCode(arg0));
        And("^la réponse contient \"([^\"]*)\"$", (String arg0) -> valResponse.body(equalTo(arg0)));
    }

    @Before
    public void setUp() {
        // start the server
        server = Main.startServer(TEST_PORT);
        api = given().port(TEST_PORT);
    }

    @After
    public void tearDown(){
       server.shutdown();
    }
}
