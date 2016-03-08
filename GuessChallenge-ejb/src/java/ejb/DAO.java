package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
import models.Achivment;
import models.User;

@Stateless
public class DAO implements DAOLocal {

    @PersistenceContext(unitName = "GuessChallenge-ejbPU")
    private EntityManager em;

    @Override
    public User addUser(User user) {
        try {
            em.persist(user);
            return user;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }

    @Override
    public User getUserById(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByName(String name) {
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.name = ?1", User.class);
            query.setParameter(1, name);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        Query query = em.createQuery("SELECT u FROM User u ORDER BY u.count DESC, u.maxCombo DESC", User.class);
        return query.getResultList();
    }

    @Override
    public User updateUser(User user) {
        try {
            return em.merge(user);
        } catch (ConstraintViolationException e) {
            return null;
        }
    }

    @Override
    public boolean removeUser(User user) {
        User userToRemove = em.getReference(User.class, user.getId());
        em.remove(userToRemove);
        return true;
    }

    @Override
    public List<Achivment> getAllAchivments() {
        Query query = em.createQuery("SELECT a FROM Achivment a", Achivment.class);
        return query.getResultList();
    }

    @Override
    public Achivment getAchivment(int count, int maxCombo) {
        try {
            Query query = em.createQuery("SELECT a FROM Achivment a WHERE a.count = ?1 OR a.maxCombo = ?2", Achivment.class);
            query.setParameter(1, count);
            query.setParameter(2, maxCombo);
            return (Achivment) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
