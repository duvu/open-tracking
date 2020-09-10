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
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MailProperties implements Serializable {

    private static final long serialVersionUID = 6659499897105044333L;

    @Column
    private String mailProtocol;

    @Column
    private String mailHost;

    @Column
    private int mailPort;

    @Column
    private String mailUsername;

    @Column
    @JsonIgnore
    private String mailPassword;

    @Column
    private Boolean mailAuth;

    @Column
    private Boolean mailStartTls;

    @Column
    private Long mailMaxSizeAttachment; // bytes
}
