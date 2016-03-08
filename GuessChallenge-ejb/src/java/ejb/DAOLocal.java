package ejb;

import java.util.List;
import javax.ejb.Local;
import models.Achivment;
import models.User;

@Local
public interface DAOLocal {
    User addUser(User user);
    User getUserById(Integer id);
    User getUserByName(String name);
    List<User> getAllUsers();
    User updateUser(User user);
    boolean removeUser(User user);
    List<Achivment> getAllAchivments();
    Achivment getAchivment(int count, int maxCombo);
}
