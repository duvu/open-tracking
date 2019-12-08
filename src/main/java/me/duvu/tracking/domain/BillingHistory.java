package me.duvu.tracking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author beou on 11/3/18 00:28
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BillingHistory implements Serializable {

    private static final long serialVersionUID = -6316573590144855343L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
