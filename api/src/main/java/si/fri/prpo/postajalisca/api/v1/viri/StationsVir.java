package si.fri.prpo.postajalisca.api.v1.viri;

import si.fri.prpo.postajalisca.dtos.StationDTO;
import si.fri.prpo.postajalisca.entitete.Station;
import si.fri.prpo.postajalisca.entityBeans.StationBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("stations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class StationsVir {
    @Inject
    StationBean stationBean;

    @GET
    public Response getStations(){
        ArrayList<Station> users = new ArrayList<Station>(stationBean.getStations());
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("{sid}")
    public Response getUser(@PathParam("sid") int sid) {
        Station station = stationBean.getStation(sid);
        return Response.status(Response.Status.OK).entity(station).build();
    }

    @DELETE
    @Path("{sid}")
    public void deleteUser(@PathParam("sid") int sid) {
        stationBean.deleteStation(sid);
    }

    @POST
    public void addStation(Station toadd) {
        stationBean.addStation(toadd);
    }

    @PUT
    @Path("{sid}")
    public void updateStation(@PathParam("sid") int sid, StationDTO stationDTO) {
        stationBean.updateStation(sid, stationDTO);
    }

}