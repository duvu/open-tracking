package me.duvu.tracking.web.rest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToggleAlertProfileDTO implements Serializable {
    private static final long serialVersionUID = 541678767188890425L;
    Long deviceId;
    Long alertProfileId;
    String action;
}
