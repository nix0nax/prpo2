package si.fri.prpo.postjalisca.servlet;

import si.fri.prpo.postajalisca.dtos.CancelDTO;
import si.fri.prpo.postajalisca.dtos.CreateDTO;
import si.fri.prpo.postajalisca.dtos.FreeTimeDTO;
import si.fri.prpo.postajalisca.entitete.*;
import si.fri.prpo.postajalisca.entityBeans.*;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class Servlet extends HttpServlet {

    @Inject
    private StationBean stationBean;

    @Inject
    private UserBean userBean;

    @Inject
    private SessionBean sessionBean;

    @Inject
    private ManageSessionsBean manageSessionsBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        List<Station> stations = stationBean.getStations();
        for (Station stat : stations) {
            out.println(stat);
        }

        /*List<Station> stationsc = stationBean.getStationsC();
        for (Station stat : stationsc) {
            out.println(stat);
        }*/

        CreateDTO createDTO = new CreateDTO();
        createDTO.setUser_id(1);
        createDTO.setStation_id(1);
        createDTO.setStart_time("07.11.2021-10:00");
        createDTO.setEnd_time("07.11.2021-12:00");
        manageSessionsBean.createSession(createDTO);
        createDTO.setUser_id(1);
        createDTO.setStation_id(1);
        createDTO.setStart_time("07.11.2021-06:00");
        createDTO.setEnd_time("07.11.2021-10:00");
        manageSessionsBean.createSession(createDTO);
        createDTO.setUser_id(2);
        createDTO.setStation_id(1);
        createDTO.setStart_time("07.11.2021-11:00");
        createDTO.setEnd_time("07.11.2021-13:00");
        manageSessionsBean.createSession(createDTO);
        createDTO.setUser_id(2);
        createDTO.setStation_id(1);
        createDTO.setStart_time("07.11.2021-09:00");
        createDTO.setEnd_time("07.11.2021-11:00");
        manageSessionsBean.createSession(createDTO);
        createDTO.setUser_id(1);
        createDTO.setStation_id(2);
        createDTO.setStart_time("07.11.2021-06:00");
        createDTO.setEnd_time("07.11.2021-11:00");
        manageSessionsBean.createSession(createDTO);
        createDTO.setUser_id(1);
        createDTO.setStation_id(2);
        createDTO.setStart_time("07.11.2021-19:00");
        createDTO.setEnd_time("07.11.2021-21:00");
        manageSessionsBean.createSession(createDTO);
        createDTO.setUser_id(2);
        createDTO.setStation_id(1);
        createDTO.setStart_time("07.11.2021-13:30");
        createDTO.setEnd_time("07.11.2021-16:00");
        manageSessionsBean.createSession(createDTO);
        createDTO.setUser_id(2);
        createDTO.setStation_id(1);
        createDTO.setStart_time("07.11.2021-16:00");
        createDTO.setEnd_time("07.11.2021-18:00");
        manageSessionsBean.createSession(createDTO);

        FreeTimeDTO freeTimeDTO = new FreeTimeDTO();
        freeTimeDTO.setStation_id(1);
        freeTimeDTO.setDate("07.11.2021");
        List<String> strings = manageSessionsBean.getFreeTimeOnStation(freeTimeDTO);
        for (String s:
             strings) {
            out.println(s);
        }

        CancelDTO cancelDTO = new CancelDTO();
        cancelDTO.setUser_id(2);
        cancelDTO.setSession_id(4);
        manageSessionsBean.cancelSession(cancelDTO);
        cancelDTO.setUser_id(1);
        cancelDTO.setSession_id(5);
        manageSessionsBean.cancelSession(cancelDTO);
        cancelDTO.setUser_id(1);
        cancelDTO.setSession_id(10);
        manageSessionsBean.cancelSession(cancelDTO);
        cancelDTO.setUser_id(1);
        cancelDTO.setSession_id(4);
        manageSessionsBean.cancelSession(cancelDTO);

        /*List<Session> sessionsbyday = sessionBean.getSessionsByStartRegexAndStation(1, "07.11.2021");
        for (Session sesh : sessionsbyday) {
            out.println(sesh);
        }*/

        List<User> users = userBean.getUsers();
        for (User user : users) {
            out.println(user);
        }

        List<Session> sessions = sessionBean.getSessions();
        for (Session sesh : sessions) {
            out.println(sesh);
        }

    }
}