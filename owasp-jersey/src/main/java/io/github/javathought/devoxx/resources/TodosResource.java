package io.github.javathought.devoxx.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.javathought.devoxx.dao.TodosDao;
import io.github.javathought.devoxx.model.Todo;
import io.github.javathought.devoxx.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static io.github.javathought.devoxx.resources.TodosResource.PATH;
import static javax.ws.rs.core.MediaType.*;

/**
 *
 */
@Path(PATH)
@Api(value = PATH, description = "Browse Todos")
public class TodosResource {
    public static final String PATH = "todos";

    @Context
    SecurityContext security;

    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @ApiOperation(value = "Browse todos")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Somethong got wrong on server")})
    public List<Todo> getTodos() {
        return TodosDao.getAll();
    }

    @GET
    @Path("/public")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @ApiOperation(value = "Browse todos")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Somethong got wrong on server")})
    public List<Todo> getPublicTodos() {
        return TodosDao.getPublic();
    }

    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id : \\d+}")
    @ApiOperation(value = "Get todo by id", notes = "Returns todo identified by is internal id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Todo not found"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response getById(@PathParam("id") long id) {

        Optional<Todo> todoOptional = TodosDao.getById(id);

        if (todoOptional.isPresent()) {
            return Response.ok().entity(todoOptional.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @ApiOperation(value = "Create a todo", notes = "post a new todo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response create(Todo todo) {
        TodosDao.create(todo);
        return Response.created(URI.create(PATH + "/" + todo.getId())).entity(todo).build();
    }

    // Resource exposed to CSRF with text plain media type
    @POST
    @Consumes({TEXT_PLAIN})
    @Produces({APPLICATION_JSON})
    @ApiOperation(value = "Create a todo", notes = "post a new todo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response createFromText(String todoText) throws IOException {
//        if
        ObjectMapper mapper = new ObjectMapper();

        Todo todo = mapper.readValue(todoText, Todo.class);
        todo.setUserId(((User)security.getUserPrincipal()).getId());
        TodosDao.create(todo);
        return Response.created(URI.create(PATH + "/" + todo.getId())).entity(todo).build();
    }

    // Resource exposed to XML with text plain media type
    @POST
    @Path("/xml")
    @Consumes({TEXT_PLAIN})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @ApiOperation(value = "Create a todo", notes = "post a new todo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response createFromXml(String todoText) throws IOException, JAXBException, XMLStreamException {

        JAXBContext jc = JAXBContext.newInstance(Todo.class);

        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, true);
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
        XMLStreamReader xsr = xif.createXMLStreamReader(IOUtils.toInputStream(todoText, "UTF-8"));


        Unmarshaller unmarshaller = jc.createUnmarshaller();

        Todo todo = (Todo) unmarshaller.unmarshal(xsr);
        todo.setUserId(((User)security.getUserPrincipal()).getId());
        TodosDao.create(todo);
        return Response.created(URI.create(PATH + "/" + todo.getId())).entity(todo).build();
    }

    @PUT
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id : \\d+}")
    @ApiOperation(value = "Modify some properties of a todo", notes = "post a new todo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response update(@PathParam("id") long id, Todo todo) {
        TodosDao.update(todo);
        return Response.ok(todo).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation(value = "Modify some properties of a todo", notes = "post a new todo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated"),
            @ApiResponse(code = 500, message = "Something wrong in Server")})
    public Response delete(@PathParam("id") long id) {
        TodosDao.delete(id);
        return Response.noContent().build();
    }

}
