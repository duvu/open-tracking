package me.duvu.tracking.web.rest.model.response;

/**
 * @author beou on 8/16/18 05:18
 */
public interface HttpPropertiesProjection {
    String getHttp_url();
    String getHttp_method();
    Boolean getHttp_require_auth();
    String getHttp_user_name();
    String getHttp_password();
}
