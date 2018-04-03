package io.github.javathought.devoxx.resources;

import io.github.javathought.devoxx.dao.TodosDao;
import io.github.javathought.devoxx.dao.UsersDao;
import io.github.javathought.devoxx.model.Role;
import io.github.javathought.devoxx.model.Todo;
import io.github.javathought.devoxx.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static io.github.javathought.devoxx.resources.UsersResource.PATH;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

/**
 *
 */
@Path(PATH)
@Api(value = PATH, description = "Browse users")
public class UsersResource {

    private static final Logger LOG = LoggerFactory.getLogger(UsersResource.class);

    static final String PATH = "users";

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @ApiOperation(value = "Browse for users", notes = "Returns all users in a flat list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public List<User> getAll() {
        return UsersDao.getAll();
    }

    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id : \\d+}")
    @ApiOperation(value = "Get user by id", notes = "Returns user identified by is internal id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response getById(@PathParam("id") long id) {

        Optional<User> userOptional = UsersDao.getById(id);

        if (userOptional.isPresent()) {
            return Response.ok().entity(userOptional.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @ApiOperation(value = "Create a user", notes = "post a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response create(User user) {
        UsersDao.create(user);
        return Response.created(URI.create(PATH + "/" + user.getId())).entity(user).build();
    }

    @PUT
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id : \\d+}")
    @ApiOperation(value = "Modify some properties of a user", notes = "post a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response update(@PathParam("id") long id, User user) {
        UsersDao.update(user);
        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation(value = "Modify some properties of a user", notes = "post a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response delete(@PathParam("id") long id) {
        UsersDao.delete(id);
        return Response.noContent().build();
    }

    @Path("current")
    @Produces(APPLICATION_JSON)
    @GET
    public Response getUserInformations(@Context SecurityContext sc) {
        return Response.ok().entity(sc.getUserPrincipal()).build();
    }

    @Path("/{id : \\d+}/todos")
    @Produces(APPLICATION_JSON)
    @GET
    public List<Todo> getUserTodos(@PathParam("id") long id) {
        return TodosDao.getByOwner(id);

    }

/*
    @Path("current/roles")
    @Produces(APPLICATION_JSON)
    @GET
    public Response getUserRoles(@Context SecurityContext sc) {
        return Response.ok().entity(((User) sc.getUserPrincipal()).getRoles()).build();
    }
*/

}
