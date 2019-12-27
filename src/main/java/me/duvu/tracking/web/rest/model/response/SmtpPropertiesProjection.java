package me.duvu.tracking.web.rest.model.response;

/**
 * @author beou on 8/16/18 05:16
 */
public interface SmtpPropertiesProjection {
    Long getId();
    String getProtocol();
    String getHost();
    Integer getPort();
    String getUsername();
    String getPassword();
    Boolean getAuth();
    Boolean getStartTls();
    Long getMaxSizeAttachment();
}
