package si.fri.prpo.postajalisca.entitete;

import javax.persistence.*;

@Table(name = "\"user\"")
@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "User.getAll", query = "SELECT u FROM User u"),
                @NamedQuery(name = "User.getByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),

        })
public class User {
    public static final String GET_ALL = "User.getAll";
    public static final String GET_BY_USERNAME = "User.getByUsername";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer user_id;

    @Lob
    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return user_id;
    }

    public void setId(Integer id) {
        this.user_id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}