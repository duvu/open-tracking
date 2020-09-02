package me.duvu.tracking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author beou on 11/29/18 16:01
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceGroup {

    private static final long serialVersionUID = -519846339911883186L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column
    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "_device_group_device", joinColumns = @JoinColumn(name = "deviceGroupId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "deviceId", referencedColumnName = "id"))
    private Set<Device> devices;

    @Column(length = 32)
    private String createdBy;

    @Column(length = 32)
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Transient
    private int totalDevices;

    @Transient
    private int liveDevices; // last message in 10 minutes

    @PrePersist
    private void prePersist() {
        this.createdOn = new Date();
    }

    @PreUpdate
    private void preUpdate () {
        this.updatedOn = new Date();
    }

    @PostLoad
    private void postLoad() {
        if (devices == null) {
            totalDevices = 0;
            liveDevices = 0;
        } else {
            this.totalDevices = devices.size();
            devices.forEach(device -> {
                //
            });
        }
    }

}
