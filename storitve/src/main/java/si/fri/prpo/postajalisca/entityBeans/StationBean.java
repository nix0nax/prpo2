package si.fri.prpo.postajalisca.entityBeans;

import si.fri.prpo.postajalisca.annotations.BeleziKlice;
import si.fri.prpo.postajalisca.dtos.StationDTO;
import si.fri.prpo.postajalisca.entitete.Station;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class StationBean {

    private Logger log = Logger.getLogger(StationBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna: " + StationBean.class.getSimpleName() + " ID: " + UUID.randomUUID());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna: " + StationBean.class.getSimpleName());
    }

    @PersistenceContext(unitName = "postajalisca-jpa")
    private EntityManager em;

    @BeleziKlice
    public List<Station> getStations() {
        Query namedQuery = em.createNamedQuery(Station.GET_ALL);
        return namedQuery.getResultList();
    }

    public List<Station> getByOwner(String owner) {
        Query namedQuery = em.createNamedQuery(Station.GET_BY_OWNER);
        namedQuery.setParameter("owner", owner);
        return namedQuery.getResultList();
    }

    @BeleziKlice
    public Station getStation(int id) {
        return em.find(Station.class, id);
    }

    @BeleziKlice
    @Transactional
    public void updateStation(int id, StationDTO stationDTO) {
        Station update = getStation(id);
        update.setPrice(stationDTO.getPrice());
        update.setWorkHours(stationDTO.getWorkhour());
        update.setOwner(stationDTO.getOwner());
        return;
    }

    /*@Transactional
    public void updateWorkHours(int id, String workhours) {
        Station update = getStation(id);
        update.setWorkHours(workhours);
        return;
    }

    @Transactional
    public void updateOwner(int id, String owner) {
        Station update = getStation(id);
        update.setOwner(owner);
        return;
    }*/

    @BeleziKlice
    @Transactional
    public void addStation(Station toadd) {
        /*Station toadd = new Station();
        toadd.setLocation(location);
        toadd.setOwner(owner);
        toadd.setPrice(price);
        toadd.setWorkHours(work_hours);*/
        em.persist(toadd);
    }

    @BeleziKlice
    @Transactional
    public void deleteStation(int id) {
        Station todelete = getStation(id);
        if (todelete != null) {
            em.remove(todelete);
        }
        return;
    }

    /*public List<Station> getStationsC() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Station> cq = cb.createQuery(Station.class);
        List<Station> stations = em.createQuery(cq).getResultList();
        return stations;
    }*/
}
