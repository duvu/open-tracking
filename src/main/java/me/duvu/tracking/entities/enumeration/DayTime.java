package me.duvu.tracking.entities.enumeration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author beou on 8/16/18 08:33
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DayTime implements Serializable {

    private static final long serialVersionUID = -8368638052883727066L;

    private Integer fromHour;
    private Integer fromMinute;
    private Integer toHour;
    private Integer toMinute;
}
