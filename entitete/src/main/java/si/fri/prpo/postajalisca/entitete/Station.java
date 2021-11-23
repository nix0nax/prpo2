package si.fri.prpo.postajalisca.entitete;

import javax.persistence.*;


@Table(name = "station")
@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Station.getAll", query = "SELECT s FROM Station s"),
                @NamedQuery(name = "Station.getByOwner", query = "SELECT s FROM Station s WHERE s.owner = :owner"),
        })
public class Station {
    public static final String GET_BY_OWNER = "Station.getByOwner";
    public static final String GET_ALL = "Station.getAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id", nullable = false)
    private Integer station_id;

    @Lob
    @Column(name = "owner", nullable = false)
    private String owner;

    @Lob
    @Column(name = "location", nullable = false)
    private String location;

    @Lob
    @Column(name = "work_hours", nullable = false)
    private String workHours;

    @Column(name = "price", nullable = false)
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return station_id;
    }

    public void setId(Integer id) {
        this.station_id = id;
    }

    @Override
    public String toString() {
        return "Station{" +
                "station_id=" + station_id +
                ", owner='" + owner + '\'' +
                ", location='" + location + '\'' +
                ", workHours='" + workHours + '\'' +
                ", price=" + price +
                '}';
    }
}