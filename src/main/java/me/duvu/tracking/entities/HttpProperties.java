package me.duvu.tracking.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beou on 12/7/19 23:14
 * for sms and email via http/https
 */

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpProperties implements Serializable {

    private static final long serialVersionUID = 2289263370868340751L;

    @Column
    private String gatewayUrl;

    @Column
    private String gatewayApiApp;

    @Column
    private String gatewayApiKey;

    @Column
    private String gatewayApiSecret;
}
