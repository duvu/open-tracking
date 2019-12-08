package me.duvu.tracking.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author beou on 8/16/18 08:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WeekDays implements Serializable {

    private static final long serialVersionUID = 1817692965901204396L;

    private boolean sunday;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Monday: ").append(monday);
        sb.append("Tuesday: ").append(tuesday);
        sb.append("Wednesday: ").append(wednesday);
        sb.append("Thursday: ").append(thursday);
        sb.append("Friday: ").append(friday);
        sb.append("Saturday: ").append(saturday);
        sb.append("Sunday: ").append(sunday);
        return sb.toString();
    }
}
