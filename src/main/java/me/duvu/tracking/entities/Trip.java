package me.duvu.tracking.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author beou on 9/18/18 23:09
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    private static final long serialVersionUID = -6634383605192536330L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
