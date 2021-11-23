package si.fri.prpo.postajalisca.entityBeans;

import si.fri.prpo.postajalisca.annotations.BeleziKlice;
import si.fri.prpo.postajalisca.entitete.Session;
import si.fri.prpo.postajalisca.entitete.Station;
import si.fri.prpo.postajalisca.entitete.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class SessionBean {

    private Logger log = Logger.getLogger(SessionBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna: " + SessionBean.class.getSimpleName() + " ID: " + UUID.randomUUID());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna: " + SessionBean.class.getSimpleName());
    }

    @PersistenceContext(unitName = "postajalisca-jpa")
    private EntityManager em;


    @BeleziKlice
    public List<Session> getSessions() {
        Query namedQuery = em.createNamedQuery(Session.GET_ALL);
        return namedQuery.getResultList();
    }

    @BeleziKlice
    public Session getSession(int id) {
        return em.find(Session.class, id);
    }

    public List<Session> getSessionsByStation(int id) {
        Query namedQuery = em.createNamedQuery(Session.GET_BY_STATION_ID);
        namedQuery.setParameter("station_id", id);
        return namedQuery.getResultList();
    }

    public List<Session> getSessionsByUser(int id) {
        Query namedQuery = em.createNamedQuery(Session.GET_BY_USER_ID);
        namedQuery.setParameter("user_id", id);
        return namedQuery.getResultList();
    }


    public List<Session> getSessionsByStartRegexAndStation(int id, String s) {
        Query namedQuery = em.createNamedQuery(Session.GET_BY_STATION_ID_AND_START);
        namedQuery.setParameter("station_id", id);
        namedQuery.setParameter("start_time", "%" + s + "%");
        return namedQuery.getResultList();
    }

    public Session getLastSessionBeforeSOnStation(int id, String s) {
        List<Session> sessions = getSessionsByStartRegexAndStation(id, s.substring(0,10));
        int retid = -1;
        int shour = Integer.parseInt(s.substring(11,13));
        int sminute = Integer.parseInt(s.substring(14));
        int maxhour = 0;
        int maxminute = 0;

        for (Session sesh:
             sessions) {
            int seshhour = Integer.parseInt(sesh.getStartTime().substring(11,13));
            int seshminute = Integer.parseInt(sesh.getStartTime().substring(14));

            if (seshhour > maxhour && seshhour < shour) {
                maxhour = seshhour;
                maxminute = seshminute;
                retid = sesh.getId();
                continue;
            }

            if (seshhour == shour) {
                if (seshminute > maxminute && seshminute < sminute) {
                    maxhour = seshhour;
                    maxminute = seshminute;
                    retid = sesh.getId();
                    continue;
                }
            }
        }

        return getSession(retid);
    }

    public Session getFirstSessionAfterSOnStation(int id, String s) {
        List<Session> sessions = getSessionsByStartRegexAndStation(id, s.substring(0,10));
        int retid = -1;
        int shour = Integer.parseInt(s.substring(11,13));
        int sminute = Integer.parseInt(s.substring(14));
        int minhour = 25;
        int minminute = 61;

        for (Session sesh:
                sessions) {
            int seshhour = Integer.parseInt(sesh.getEndTime().substring(11,13));
            int seshminute = Integer.parseInt(sesh.getEndTime().substring(14));

            if (seshhour < minhour && seshhour > shour) {
                minhour = seshhour;
                minminute = seshminute;
                retid = sesh.getId();
                continue;
            }

            if (seshhour == shour) {
                if (seshminute < minminute && seshminute > sminute) {
                    minhour = seshhour;
                    minminute = seshminute;
                    retid = sesh.getId();
                    continue;
                }
            }
        }

        return getSession(retid);
    }


    @BeleziKlice
    @Transactional
    public void updateEndTime(int id, String endtime) {
        Session update = getSession(id);
        update.setEndTime(endtime);
        return;
    }


    @Transactional
    public void addSession(Session toadd) {
        /*Session toadd = new Session();
        toadd.setStation(station);
        toadd.setUser(user);
        toadd.setStartTime(starttime);
        toadd.setEndTime(endtime);*/
        em.persist(toadd);
    }

    @BeleziKlice
    @Transactional
    public void deleteSession(int id) {
        Session todelete = getSession(id);
        if (todelete != null) {
            em.remove(todelete);
        }
        return;
    }

}
