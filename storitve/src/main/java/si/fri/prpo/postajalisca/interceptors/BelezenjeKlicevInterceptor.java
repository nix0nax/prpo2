package si.fri.prpo.postajalisca.interceptors;


import si.fri.prpo.postajalisca.annotations.BeleziKlice;
import si.fri.prpo.postajalisca.entityBeans.BelezenjeKlicevBean;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    @Inject
    BelezenjeKlicevBean belezenjeKlicevBean;

    @AroundInvoke
    public Object beleziKlic(InvocationContext context) throws Exception{
        belezenjeKlicevBean.beleziKlic();
        return context.proceed();
    }

}
