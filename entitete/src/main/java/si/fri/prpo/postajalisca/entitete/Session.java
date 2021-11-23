package si.fri.prpo.postajalisca.entitete;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "session")
@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Session.getAll", query = "SELECT s FROM Session s"),
                @NamedQuery(name = "Session.getByStationID", query = "SELECT s FROM Session s WHERE s.station.station_id = :station_id"),
                @NamedQuery(name = "Session.getByStationIDAndStart", query = "SELECT s FROM Session s WHERE s.station.station_id = :station_id AND s.startTime LIKE :start_time"),
                @NamedQuery(name = "Session.getByUserID", query = "SELECT s FROM Session s WHERE s.user.user_id = :user_id"),

        })
public class Session {

    public static final String GET_ALL = "Session.getAll";
    public static final String GET_BY_STATION_ID = "Session.getByStationID";
    public static final String GET_BY_USER_ID = "Session.getByUserID";
    public static final String GET_BY_STATION_ID_AND_START = "Session.getByStationIDAndStart";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false)
    private Integer session_id;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getId() {
        return session_id;
    }

    public void setId(Integer id) {
        this.session_id = id;
    }

    @Override
    public String toString() {
        return "Session{" +
                "session_id=" + session_id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", station=" + station +
                ", user=" + user +
                '}';
    }
}