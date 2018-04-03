package io.github.javathought.devoxx.resources;

import io.github.javathought.devoxx.dao.RolesDao;
import io.github.javathought.devoxx.dao.UsersDao;
import io.github.javathought.devoxx.model.Credentials;
import io.github.javathought.devoxx.model.Role;
import io.github.javathought.devoxx.resources.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.List;

import static io.github.javathought.devoxx.resources.AuthenticateResource.PATH;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path(PATH)
@Api(value = PATH, description = "Authenticate user")
public class AuthenticateResource {

    public static final String PATH = "authenticate";

    @Context
    SecurityContext securityContext;

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Authenticate a user", notes = "Verify a user credentials")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user authenticated"),
            @ApiResponse(code = 401, message = "Wrong user or password")})
    public Response authenticate(Credentials credentials) {
        if (UsersDao.authenticate(credentials)) {
            return Response.ok().entity(new ResponseMessage(true, "user authenticated")).build();
        } else {
            return Response.status(UNAUTHORIZED).entity(new ResponseMessage(false, "Username or password is incorrect")).build();
        }

    }

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Get roles of a user", notes = "Get UI roles of a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "roles retrieved"),
            @ApiResponse(code = 500, message = "Something got wrong on server")})
    public List<Role> getRoles() {
        return RolesDao.getUserRoles(securityContext.getUserPrincipal().getName());
    }

}
