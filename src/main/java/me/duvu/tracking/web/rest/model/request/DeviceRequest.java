package me.duvu.tracking.web.rest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author beou on 9/21/17 04:40
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequest implements Serializable {

    private static final long serialVersionUID = 7294250623424629286L;

    @Size(max = 32)
    private String name;

    private String description;

    @NotNull
    @Size(max = 32)
    private String deviceId;

    @Size(max = 15)
    private String imei;

    private Set<Long> accountIds;
    private Set<Long> alertProfileIds;

    private Long vehicleId;

    @Size(max = 32)
    private String protocol;
    private String ipAddress;
    private int port;

    private String timeZoneStr;

    private int maxStoredDataTime;

    private Date expiredOn;

    private String status;

    @Size(max = 32)
    private String serivalNumber;

    @Size(max = 32)
    private String modelName;

    private String manufacturerName;

    private String firmwareVerison;

    private String originalCountry;
}
