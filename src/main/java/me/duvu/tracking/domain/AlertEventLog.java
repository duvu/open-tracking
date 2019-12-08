package me.duvu.tracking.domain;


import me.duvu.tracking.domain.enumeration.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beou on 9/9/18 18:39
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertEventLog implements Serializable {

    private static final long serialVersionUID = -6959691400510056129L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alertName;

    private String alertDescription;

    private Long accountId;
    private Long deviceId;
    private String uniqueId; // device imei
    private Long eventId;

    private Double latitude;
    private Double longitude;
    private String address;
    private Long timestamp;

    @Enumerated(EnumType.STRING)
    private AlertType type;

    private Double speedKph;
    private Long zoneId;

    private boolean alertEmail;
    private boolean alertSms;
    private boolean alertApp;
    private String cannedAction; // use to call to external URL
    private String receivers;

    private String subject;
    private String text;
    private String templateId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @PrePersist
    private void prePersist() {
        this.createdOn = new Date();
    }

    @Override
    public String toString() {
        return "AlertEventLog{" +
                "id=" + id +
                ", alertName='" + alertName + '\'' +
                ", alertDescription='" + alertDescription + '\'' +
                ", accountId=" + accountId +
                ", deviceId='" + deviceId + '\'' +
                ", eventId=" + eventId +
                ", type=" + type +
                ", speedKph=" + speedKph +
                ", zoneId=" + zoneId +
                ", alertEmail=" + alertEmail +
                ", alertSms=" + alertSms +
                ", alertApp=" + alertApp +
                ", cannedAction='" + cannedAction + '\'' +
                ", receivers='" + receivers + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", templateId='" + templateId + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
