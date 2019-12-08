package me.duvu.tracking.web.rest.model.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author beou on 12/4/18 08:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceGroupRequest extends AbstractRequest implements Serializable{
    private static final long serialVersionUID = 2929825357301149778L;

    private String name;
    private String description;
    private Set<Long> deviceIds; //a set of device
}
