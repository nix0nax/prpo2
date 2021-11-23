package si.fri.prpo.postajalisca.api.v1.viri;

import si.fri.prpo.postajalisca.dtos.CancelDTO;
import si.fri.prpo.postajalisca.dtos.CreateDTO;
import si.fri.prpo.postajalisca.entitete.Session;
import si.fri.prpo.postajalisca.entityBeans.ManageSessionsBean;
import si.fri.prpo.postajalisca.entityBeans.SessionBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SessionsVir {
    @Inject
    SessionBean sessionBean;

    @Inject
    ManageSessionsBean manageSessionsBean;

    @GET
    public Response getSessions(){
        ArrayList<Session> sessions = new ArrayList<Session>(sessionBean.getSessions());
        return Response.status(Response.Status.OK).entity(sessions).build();
    }

    @GET
    @Path("{sid}")
    public Response getSession(@PathParam("sid") int sid) {
        Session session = sessionBean.getSession(sid);
        return Response.status(Response.Status.OK).entity(session).build();
    }

    @DELETE
    public void cancelSession(CancelDTO cancelDTO) { manageSessionsBean.cancelSession(cancelDTO); }

    @POST
    public void createSession(CreateDTO createDTO) {
        manageSessionsBean.createSession(createDTO);
    }

    @PUT
    @Path("{sid}")
    public void updateEndTime(@PathParam("sid") int sid, String endtime) {
        sessionBean.updateEndTime(sid, endtime);
    }

}