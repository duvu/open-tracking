package me.duvu.tracking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author beou on 10/15/17 16:09
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle implements Serializable {
    private static final long serialVersionUID = 808433327662877116L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String name;

    @OneToOne
    @JoinColumn(name = "deviceId", referencedColumnName = "id")
    private Device device;

    private String address;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double velocity;
    private Double direction;

}
