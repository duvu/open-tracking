package me.duvu.tracking.web.rest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author beou on 2/19/18 17:15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeofenceRequest implements Serializable {

    private static final long serialVersionUID = 6808925528701179348L;

    private Set<Long> accountIds;
    private String name;
    private String address;
    private String color;
    private double maxSpeedKPH;
    private boolean reverseGeocode;
    boolean privateArea; //private road?
    private String geojson;
}
