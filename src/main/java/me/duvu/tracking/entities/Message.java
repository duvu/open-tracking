package me.duvu.tracking.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beou on 10/29/18 01:50
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private static final long serialVersionUID = -3169521879441899118L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column
    private String emailFrom;

    @Column
    private String emailTo;

    @Column
    private String cc;

    @Column
    private String bcc;

    @Column
    private String subject;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] body; //body

    @Column
    private String attachFileId; // attachment

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

}
