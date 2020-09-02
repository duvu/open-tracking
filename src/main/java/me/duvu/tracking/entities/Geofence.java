package me.duvu.tracking.entities;

import me.duvu.tracking.geo.Geometry;
import me.duvu.tracking.geo.GeometryFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author beou on 1/20/18 00:00
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Geofence {

    private static final long serialVersionUID = -911578626567049197L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "AccountGeofence", joinColumns = @JoinColumn(name = "geofenceId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "accountId", referencedColumnName = "id"))
    private Set<Account> accounts;

    @Column(length = 512)
    private String name;

    @Column(length = 512)
    private String address;

    @Column(length = 32)
    private String color;

    @Column
    private double maxSpeedKPH;

    @Column
    private boolean reverseGeocode;

    @Column boolean privateArea; //private road?

    @Column
    @Lob
    private String geojson;

    @Column(length = 32)
    private String createdBy;

    @Column(length = 32)
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Transient
    private Geometry geometry;

    //--
    @PostLoad
    private void postLoad() {
        this.geometry = GeometryFactory.getInstance().create(this.geojson);
    }
    @PrePersist
    private void prePersist() {
        this.createdOn = new Date();
    }

    @PreUpdate
    private void preUpdate () {
        this.updatedOn = new Date();
    }
}
