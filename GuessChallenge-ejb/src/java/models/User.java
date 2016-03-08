package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    @Column(unique = true)
    @Size(min = 2)
    private String name;
    @NotNull
    private Integer count;
    @NotNull
    private Integer maxCombo;
    @ManyToMany
    @JoinTable(name = "achivments_users", joinColumns = @JoinColumn(name = "achivment_fk"), inverseJoinColumns = @JoinColumn(name = "user_fk"))
    private List<Achivment> achivments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMaxCombo() {
        return maxCombo;
    }

    public void setMaxCombo(Integer maxCombo) {
        this.maxCombo = maxCombo;
    }

    public List<Achivment> getAchivments() {
        return achivments;
    }

    public void setAchivments(List<Achivment> achivments) {
        this.achivments = achivments;
    }
}