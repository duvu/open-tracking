package me.duvu.tracking.web.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * @author beou on 8/16/18 04:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class HttpProperties {

    private String http_url;
    private HttpMethod http_method;
    private Boolean http_require_auth;
    private String http_user_name;
    private String http_password;
}
