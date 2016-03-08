package controllers;

import ejb.AttemptCounterServiceRemote;
import ejb.DAOLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import models.Achivment;
import models.User;

@Named
@SessionScoped
public class Controller implements Serializable {

    @EJB
    private DAOLocal dao;
    @EJB
    private AttemptCounterServiceRemote counterServiceRemote;
    private User currentUser;
    private String userName;
    private Integer userNumber;
    private int computerNum;
    private int maxCombo;
    private String message;
    private Random random;

    public Controller() {
        maxCombo = 0;
        random = new Random();
        computerNum = random.nextInt(10) + 1;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public int getMaxCombo() {
        return maxCombo;
    }

    public int getAttemptCounter() {
        return counterServiceRemote.getCounter();
    }

    public String getMessage() {
        String tmpMsg = message;
        message = "";
        return tmpMsg;
    }

    public void represent() {
        User user = dao.getUserByName(userName);
        if (user != null) {
            currentUser = user;
        } else {
            currentUser = new User();
            currentUser.setName(userName);
            currentUser.setCount(0);
            currentUser.setMaxCombo(0);
            dao.addUser(currentUser);
        }
    }

    public List<User> listUsers() {
        return dao.getAllUsers();
    }

    public boolean isRepresented() {
        return currentUser != null;
    }

    public void exit() {
        currentUser = null;
    }

    public void removeUser() {
        dao.removeUser(currentUser);
        currentUser = null;
        userName = null;
    }

    public void turn() {
        if (userNumber.intValue() == computerNum) {
            maxCombo++;
            message = "Вы угадали, так держать!";
            if (currentUser.getMaxCombo() < maxCombo) {
                currentUser.setMaxCombo(maxCombo);
            }
            currentUser.setCount(currentUser.getCount() + 1);
            computerNum = random.nextInt(10) + 1;
            Achivment achivment = dao.getAchivment(currentUser.getCount(), maxCombo);
            if (achivment != null) {
                currentUser.getAchivments().add(achivment);
                message += " А ещё вы заработали новое достижение!";
            }
            currentUser = dao.updateUser(currentUser);
        } else {
            maxCombo = 0;
            message = "К сожалению, вы не угадали, попробуйте ещё раз!";
        }
        counterServiceRemote.attempt();
        userNumber = null;
    }
}
