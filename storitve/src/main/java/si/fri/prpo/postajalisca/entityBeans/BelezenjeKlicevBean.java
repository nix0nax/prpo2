package si.fri.prpo.postajalisca.entityBeans;

import si.fri.prpo.postajalisca.interceptors.BelezenjeKlicevInterceptor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicevBean {
    Logger log = Logger.getLogger(BelezenjeKlicevBean.class.getName());
    int stklicev = 0;

    public void beleziKlic() {
        stklicev++;
        log.info("Št. klicov:" + stklicev);
    }


}
