package me.duvu.tracking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author beou on 10/5/19 00:23
 */

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Configuration implements Serializable {

    private static final long serialVersionUID = -1150400522580476842L;

    @Id
    @Size(max = 128)
    String configKey;

    @Size(max = 512)
    @NotNull
    String configValue;
}
