package me.duvu.tracking.web.rest.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmtpPropertiesModel implements Serializable {

    private static final long serialVersionUID = -4094802726827054009L;

    private Long accountId;

    private String protocol;
    
    private String host;

    private int port;

    private String username;

    private String password;

    private Boolean auth;

    private Boolean startTls;

    private Long maxSizeAttachment; // bytes
}
