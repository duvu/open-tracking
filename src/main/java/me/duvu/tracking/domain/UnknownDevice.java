package me.duvu.tracking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beou on 10/22/18 21:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UnknownDevice implements Serializable {

    private static final long serialVersionUID = 7997032292275407182L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String uniqueId;

    @Column
    private int port; //incoming or local port

    @Column
    private String remoteIpAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    //--
    @PrePersist
    private void prePersist() {
        this.createdOn = new Date();
    }


}
