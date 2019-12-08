package me.duvu.tracking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beou on 5/24/18 01:04
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCommand implements Serializable {

    private static final long serialVersionUID = 5444803393409759366L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "deviceId", referencedColumnName = "id")
    private Device device;

    private String cmdText;

    private boolean sent;

    private String type; // ip, sms

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

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

}
