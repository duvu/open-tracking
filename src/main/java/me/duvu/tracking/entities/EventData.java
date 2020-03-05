/*
 * Copyright (c) 2017. by Vu.Du
 */

package me.duvu.tracking.entities;

import me.duvu.tracking.config.StatusCodeUtils;
import me.duvu.tracking.entities.enumeration.CellTower;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beou on 11/21/17 15:43
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "EventData", indexes = {
        @Index(name = "_index_1", columnList = "deviceId"),
        @Index(name = "_index_2", columnList = "deviceId, timestamp"),
        @Index(name = "_index_3", columnList = "deviceId, status, timestamp")
})
public class EventData implements MultiTenantInf, Cloneable {

    private static final long serialVersionUID = 2557053954443138542L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenantId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "deviceId", referencedColumnName = "id")
    private Device device;


    @Column(name = "status")
    private int status;

    @Transient
    private String statusCode;

    @Column(name = "timestamp")
    private long timestamp;


    @Column
    private long fixedtime;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column
    private double altitude;

    @Column
    private double speedKPH;

    @Column
    private double heading;

    @Column
    private double accuracy;

    @Column(length = 512)
    private String address;

    @Column
    private double distanceKM;

    @Column
    private double odometerKM;

    @Column
    private double odometterOffsetKM;

    @Column
    private long geozoneId;

    //-- gps information
    @Column
    private int gpsFixType; // fix type 0/1 NONE, 2 = 2D, 3 = 3D

    @Column
    private long gpsFixStatus; //

    @Column
    private long gpsAge;

    @Column
    private double horzAccuracy; // horizontal accuracy (metters)

    @Column
    private double vertAccuracy; // vertical accuracy (metters)

    @Column
    private double HDOP;

    @Column
    private int satelliteCount;

    @Column
    private double batteryLevel;

    @Column
    private double batteryVolts;

    @Column
    private double batteryTemp;

    @Column
    private double signalStrength;

    @Column
    private String driverId;

    @Column
    private long driverStatus;

    @Column
    private String driverMessage;

    @Column
    private long jobNumber;

    @Column
    private String rfidTag;

    //-- OBD fields;
    @Column
    private double fuelPressure;

    @Column
    private double fuelUsage;

    @Column
    private double fuelTemp;

    @Column
    private double fuelLevel;

    @Column
    private double fuelLevel2;

    @Column
    private double fuelRemain;

    @Column
    private long engineRpm;

    @Column
    private double engineHours;

    @Column
    private double engineOnHours;

    @Column
    private double engineLoad;

    @Column
    private double idleHours;

    @Column
    private double workHours;

    @Column
    private double tirePressure;

    @Column
    private double tireTemp;

    @Embedded
    private CellTower cellTower;

    @Column
    private String remoteAddress;

    @Column
    private int remotePort;

    @Column
    private String rawData;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @PreRemove
    private void preRemove() {
        this.device = null;
    }

    @PostLoad
    private void postLoad() {
        this.statusCode = StatusCodeUtils.getInstance().statusCodeToString(status);
    }
}
