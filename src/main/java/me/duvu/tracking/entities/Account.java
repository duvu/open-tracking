package me.duvu.tracking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import me.duvu.tracking.entities.enumeration.AccountStatus;
import me.duvu.tracking.entities.enumeration.Roles;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author beou on 8/1/17 03:09
 */
@Entity
@Data
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "manager")
public class Account implements Serializable {

    private static final long serialVersionUID = -7003585213284904715L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String accountId;

    private String password;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne
    @JsonIgnoreProperties
    private Account manager;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "_account_device_group", joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "deviceGroupId", referencedColumnName = "id"))
    private Set<DeviceGroup> deviceGroups;

    @Enumerated(EnumType.ORDINAL)
    private Roles privilege;

    @Column(length = 25)
    private String firstName;

    @Column(length = 25)
    private String lastName;

    @Column(length = 20)
    private String phoneNumber;

    @Column()
    private String photoUrl;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Column(length = 128)
    private String addressLine1;

    @Column(length = 128)
    private String addressLine2;

    @Column(length = 512)
    private String notes;

    @Column(length = 32)
    private String language;

    @Column(length = 32)
    private String timeZoneStr;

    @Column
    private String firstPageUrl;

    @Column
    private Integer maxDevice;

    @Column
    private Integer maxStoredDataTime; // in days

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "_smtp_properties_account", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "smtp_properties_id", referencedColumnName = "id"))
    private Set<SmtpProperties> smtpProperties;


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
        if (StringUtils.isEmpty(this.language)) {
            this.language = "EN";
        }
    }

    @PreUpdate
    private void preUpdate () {
        this.updatedOn = new Date();
    }
}
