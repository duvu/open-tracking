package me.duvu.tracking.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author beou on 6/6/18 16:34
 */

@Data
@Entity
public class ActivityLog implements Serializable {

    private static final long serialVersionUID = -5141807460451377913L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;
}
