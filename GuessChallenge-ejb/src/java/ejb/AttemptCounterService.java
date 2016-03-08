package ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

@Singleton
@LocalBean
@ApplicationScoped
@Startup
public class AttemptCounterService implements AttemptCounterServiceRemote{
    @EJB
    private DAOLocal dao;
    private int attemptCounter;
    
    @PostConstruct
    private void onCreate() {
        attemptCounter = 0;
        dao.getAllUsers().forEach(user -> {
            attemptCounter += user.getCount();
        });
        dao = null;
    }

    @Override
    public int getCounter(){
        return attemptCounter;
    }
    
    @Override
    public void attempt() {
        attemptCounter++;
    }
}
