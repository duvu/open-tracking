package me.duvu.tracking.internal;

import lombok.Data;

/**
 * @author beou on 10/8/18 13:16
 */
@Data
public class InternalDeviceModel {
    private Long id;
    private String deviceId;
    private String uniqueId;
    private String imei;

}
