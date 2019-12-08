package me.duvu.tracking.domain;

import me.duvu.tracking.domain.enumeration.DeviceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author beou on 8/1/17 03:10
 */
@Entity
@Data
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Device", indexes = {
        @Index(name = "device_index", columnList = "deviceId")
})
public class Device implements Serializable {

    private static final long serialVersionUID = -8426530978816745841L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, unique = true, length = 32)
    private String deviceId;

    @Column(unique = true, length = 15)
    private String imei;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "DeviceAccount", joinColumns = @JoinColumn(name = "deviceId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "accountId", referencedColumnName = "id"))
    private Set<Account> accounts;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "DeviceAlertRule", joinColumns = @JoinColumn(name = "deviceId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "alertRuleId", referencedColumnName = "id"))
    private Set<AlertProfile> alertProfiles;

    @OneToOne
    @JoinColumn(name = "vehicleId", referencedColumnName = "id")
    private Vehicle vehicle;

    private String ipAddress;

    private Integer port;

    @Column(length = 32)
    private String protocol;

    @Column
    private String timeZoneStr; // device timezone, default is UTC

    @Transient
    private TimeZone timeZone;

    @Column
    private Integer maxStoredDataTime; // in days

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredOn;

    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @Column(length = 32)
    private String serialNumber;

    @Column(length = 32)
    private String modelName;

    @Column(length = 128)
    private String manufacturerName;

    @Column(length = 32)
    private String firmwareVerison;

    @Column(length = 128)
    private String originalCountry;

    @Column
    private Double odoMeter;

    //--
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEventTime;

    @Column
    private Double lastSpeedKph;

    @Column
    private Double lastLatitude;

    @Column
    private Double lastLongitude;

    @Column
    private String lastAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date stayedTime;

    @Column(length = 32)
    private String createdBy;

    @Column(length = 32)
    private String updatedBy;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @PrePersist
    private void prePersist() {
        this.createdOn = new Date();
    }

    @PreUpdate
    private void preUpdate () {
        this.updatedOn = new Date();
    }

    @PreRemove
    private void preRemove() {
        this.accounts = null;
    }

    @PostLoad
    private void postLoad() {
        this.timeZone = getTz();
    }

    public void setLastEventTimestamp(Long timestamp) {
        this.lastEventTime = new Date(timestamp);
    }

    private TimeZone getTz() {
        if (StringUtils.isEmpty(timeZoneStr)) {
            return TimeZone.getDefault();
        } else {
            return TimeZone.getTimeZone(timeZoneStr);
        }
    }
}
