package si.fri.prpo.postajalisca.api.v1.viri;

import si.fri.prpo.postajalisca.dtos.UserDTO;
import si.fri.prpo.postajalisca.entitete.User;
import si.fri.prpo.postajalisca.entityBeans.UserBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UsersVir {
    @Inject
    UserBean userBean;

    @GET
    public Response getUsers(){
        ArrayList<User> users = new ArrayList<User>(userBean.getUsers());
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("{uid}")
    public Response getUser(@PathParam("uid") int uid) {
        User user = userBean.getUser(uid);
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @DELETE
    @Path("{uid}")
    public boolean deleteUser(@PathParam("uid") int uid) {
        return userBean.deleteUser(uid);
    }

    @POST
    public void addUser(User user) {
        userBean.addUser(user);
    }

    @PUT
    @Path("{uid}")
    public void updateUser(@PathParam("uid") int uid, UserDTO userDTO) {
        userBean.updateUser(uid, userDTO);
    }

}