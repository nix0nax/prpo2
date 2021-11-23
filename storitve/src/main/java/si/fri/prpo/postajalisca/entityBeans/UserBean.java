package si.fri.prpo.postajalisca.entityBeans;

import si.fri.prpo.postajalisca.annotations.BeleziKlice;
import si.fri.prpo.postajalisca.dtos.UserDTO;
import si.fri.prpo.postajalisca.entitete.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class UserBean {

    private Logger log = Logger.getLogger(UserBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna: " + UserBean.class.getSimpleName() + " ID: " + UUID.randomUUID());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna: " + UserBean.class.getSimpleName());
    }

    @PersistenceContext(unitName = "postajalisca-jpa")
    private EntityManager em;

    @BeleziKlice
    public List<User> getUsers() {
        Query namedQuery = em.createNamedQuery(User.GET_ALL);
        return namedQuery.getResultList();
    }

    @BeleziKlice
    public User getUser(int id) {
        return em.find(User.class, id);
    }

    @BeleziKlice
    @Transactional
    public void updateUser(int id, UserDTO userDTO) {
        User update = getUser(id);
        update.setUsername(userDTO.getUsername());
        update.setPassword(userDTO.getPassword());
        update.setName(userDTO.getName());
        return;
    }

    /*@Transactional
    public void updateName(int id, String name) {
        User update = getUser(id);
        update.setName(name);
        return;
    }

    @Transactional
    public void updatePassword(int id, String password) {
        User update = getUser(id);
        update.setPassword(password);
        return;
    }*/

    @BeleziKlice
    @Transactional
    public void addUser(User toadd) {
        /*User toadd = new User();
        toadd.setName(name);
        toadd.setPassword(password);
        toadd.setUsername(username);*/
        em.persist(toadd);
    }

    @BeleziKlice
    @Transactional
    public boolean deleteUser(int id) {
        User todelete = getUser(id);
        if (todelete != null) {
            em.remove(todelete);
            return true;
        }
        return false;
    }

}
