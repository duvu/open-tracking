package me.duvu.tracking.web.rest.model.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author beou on 12/9/18 22:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceGroupModel implements Serializable {
    private static final long serialVersionUID = -270443053130197945L;

    private Long deviceId;
    private Long groupdId;
}
