package me.duvu.tracking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author beou on 5/14/18 22:18
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Driver implements Serializable {

    private static final long serialVersionUID = -5302834275254159640L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    @Column
    private Date dateOfBirth;

    @Transient
    private int driverExperiencesMonths;

    @Column
    private String typeOfVehicleExperienced;

    @Column
    private String driverLicenseNumber;

    @Column
    private String driverLicenseType;

    @Column
    private String driverLicenseTypeDescription;

    @Column
    private Date driverLicenseIssueDate;

    @Column
    private Date driverLicenseExpiredDate;

    @Column(length = 32)
    private String createdBy;

    @Column(length = 32)
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @PostLoad
    private void postLoad() {
        //TODO: implement later
        driverExperiencesMonths = 1;
    }
}
