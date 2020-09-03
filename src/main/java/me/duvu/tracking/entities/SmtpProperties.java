package me.duvu.tracking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SmtpProperties implements Serializable {

    private static final long serialVersionUID = 6659499897105044333L;

    @Column
    private String smtpProtocol;

    @Column
    private String smtpHost;

    @Column
    private int smtpPort;

    @Column
    private String smtpUsername;

    @Column
    @JsonIgnore
    private String smtpPassword;

    @Column
    private Boolean smtpAuth;

    @Column
    private Boolean smtpStartTls;

    @Column
    private Long smtpMaxSizeAttachment; // bytes
}
