package si.fri.prpo.postajalisca.entityBeans;

import si.fri.prpo.postajalisca.dtos.CancelDTO;
import si.fri.prpo.postajalisca.dtos.CreateDTO;
import si.fri.prpo.postajalisca.dtos.FreeTimeDTO;
import si.fri.prpo.postajalisca.entitete.Session;
import si.fri.prpo.postajalisca.entitete.Station;
import si.fri.prpo.postajalisca.entitete.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ManageSessionsBean {

    @PersistenceContext(unitName = "postajalisca-jpa")
    private EntityManager em;

    @Inject
    SessionBean sessionBean;
    @Inject
    UserBean userBean;
    @Inject
    StationBean stationBean;

    private Logger log = Logger.getLogger(ManageSessionsBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna: " + ManageSessionsBean.class.getSimpleName() + " ID: " + UUID.randomUUID());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna: " + ManageSessionsBean.class.getSimpleName());
    }

    public List<String> getFreeTimeOnStation(FreeTimeDTO dto) {
        List<String> ret = new LinkedList<String>();
        Station station = stationBean.getStation(dto.getStation_id());
        List<Session> sessions = sessionBean.getSessionsByStartRegexAndStation(dto.getStation_id(), dto.getDate());
        //sort by start time
        sessions.sort(Comparator.comparing(Session::getStartTime));
        String firsttime = station.getWorkHours().substring(0,5);
        int startfrom = 0;
        if ( sessions.get(0).getStartTime().substring(11).equals(firsttime) ) {
            firsttime = sessions.get(0).getEndTime().substring(11);
            startfrom = 1;
        }
        String temp = firsttime;
        for (int i = startfrom; i < sessions.size(); i++) {
            if (temp.equals(sessions.get(i).getStartTime().substring(11)))
                continue;
            temp += "-";
            temp += sessions.get(i).getStartTime().substring(11);
            ret.add(temp);
            temp = "";
            temp += sessions.get(i).getEndTime().substring(11);
        }
        if (!sessions.get(sessions.size() - 1).getEndTime().substring(11).equals(station.getWorkHours().substring(6))) {
            temp += "-";
            temp += station.getWorkHours().substring(6);
            ret.add(temp);
        }
        return ret;
    }

    @Transactional
    public boolean cancelSession(CancelDTO dto) {
        Session sesh = sessionBean.getSession(dto.getSession_id());
        if (sesh == null) {
            log.info("Can't cancel session: session doesn't exist");
            return false;
        }
        if (dto.getUser_id() != sesh.getUser().getId()) {
            log.info("Can't cancel session: wrong user");
            return false;
        }
        sessionBean.deleteSession(dto.getSession_id());
        return true;
    }

    @Transactional
    public boolean createSession(CreateDTO dto) {
        User user = userBean.getUser(dto.getUser_id());
        Station station = stationBean.getStation(dto.getStation_id());
        Session toadd = new Session();
        toadd.setStartTime(dto.getStart_time());
        toadd.setEndTime(dto.getEnd_time());

        if (user == null) {
            log.info("Can't create session: user does not exist");
            return false;
        }
        toadd.setUser(user);

        if (station == null) {
            log.info("Can't create session: station does not exist");
            return false;
        }
        toadd.setStation(station);

        Session lastStart = sessionBean.getLastSessionBeforeSOnStation(dto.getStation_id(), dto.getStart_time());

        Session firstStart = sessionBean.getFirstSessionAfterSOnStation(dto.getStation_id(), dto.getEnd_time());

        int addhourstart = Integer.parseInt(dto.getStart_time().substring(11,13));
        int addminutestart = Integer.parseInt(dto.getStart_time().substring(14));

        int addhourend = Integer.parseInt(dto.getEnd_time().substring(11,13));
        int addminuteend = Integer.parseInt(dto.getEnd_time().substring(14));

        if(lastStart != null) {
            int beforehour = Integer.parseInt(lastStart.getEndTime().substring(11,13));
            int beforeminute = Integer.parseInt(lastStart.getEndTime().substring(14));
            if (beforehour > addhourstart || (beforehour == addhourstart && beforeminute > addminutestart) ) {
                log.info("Can't create session: different session already exists (1)");
                return false;
            }
        }

        if (firstStart != null) {
            int afterhour = Integer.parseInt(firstStart.getStartTime().substring(11,13));
            int afterminute = Integer.parseInt(firstStart.getStartTime().substring(14));
            if (afterhour < addhourend || (afterhour == addhourend && afterminute < addminuteend) ) {
                log.info("Can't create session: different session already exists (2)");
                return false;
            }
        }

        int stationopenhour = Integer.parseInt(station.getWorkHours().substring(0,2));
        int stationopenminute = Integer.parseInt(station.getWorkHours().substring(3,5));
        int stationclosehour = Integer.parseInt(station.getWorkHours().substring(6,8));
        int stationcloseminute = Integer.parseInt(station.getWorkHours().substring(9));

        if ( (addhourstart < stationopenhour || (addhourstart == stationopenhour && addminutestart < stationopenminute)) ||
                (addhourstart > stationclosehour ||  (addhourstart == stationclosehour && addminutestart > stationcloseminute) ) ||
                (addhourend < stationopenhour || (addhourend == stationopenhour && addminuteend < stationopenminute)) ||
                (addhourend > stationclosehour ||  (addhourend == stationclosehour && addminuteend > stationcloseminute) )
        ) {
            log.info("Can't create session: station is closed during that time");
            return false;
        }

        em.persist(toadd);
        return true;
    }
}
