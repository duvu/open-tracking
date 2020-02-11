package me.duvu.tracking.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beou on 12/7/19 21:46
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmtpProperties implements Serializable {

    private static final long serialVersionUID = 6659499897105044333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String protocol;

    @Column
    private String host;

    @Column
    private int port;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Boolean auth;

    @Column
    private Boolean startTls;

    @Column
    private Long maxSizeAttachment; // bytes

    @Column(length = 32)
    private String createdBy;

    @Column(length = 32)
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

}
