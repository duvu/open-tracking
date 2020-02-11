package me.duvu.tracking.entities;

import me.duvu.tracking.entities.enumeration.AlertCatalog;
import me.duvu.tracking.entities.enumeration.AlertType;
import me.duvu.tracking.entities.enumeration.DayTime;
import me.duvu.tracking.entities.enumeration.WeekDays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beou on 1/28/18 04:16
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertProfile implements Serializable {

    private static final long serialVersionUID = -7236216894794333965L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    @Enumerated(EnumType.STRING)
    private AlertCatalog catalog;

    //-- for interval
    


    //-- for eventually
    @Enumerated(EnumType.STRING)
    private AlertType type;

    private Boolean active;

    private Double speedKph;

    private Long zoneId;

    private Long params1;
    private String params2;

    private WeekDays weekDays; //Mon, Tue, Wed, Thu, Fri, Sat, Sun
    private DayTime dayTime;   // 22:32 - 23:59

    private boolean alertEmail;
    private boolean alertSms;
    private boolean alertApp;
    private String cannedAction; // use to call to external URL

    private String subject;
    private String text;
    private String templateId;

    @Column(length = 32)
    private String createdBy;

    @Column(length = 32)
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    //--
    @PrePersist
    private void prePersist() {
        this.createdOn = new Date();
    }

    @PreUpdate
    private void preUpdate () {
        this.updatedOn = new Date();
    }

}
