package me.duvu.tracking.web.rest.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author beou on 12/7/19 23:27
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmtpPropertiesRequest implements Serializable {

    private static final long serialVersionUID = 1470737793316262876L;
    private Long accountId;
    private String protocol;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Boolean auth;
    private Boolean startTls;
    private Long maxSizeAttachment;
}
