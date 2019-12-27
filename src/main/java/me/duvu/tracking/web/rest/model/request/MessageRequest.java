package me.duvu.tracking.web.rest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author beou on 11/2/18 19:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest implements Serializable {

    private static final long serialVersionUID = -5682174715394678671L;

    private String emailFrom;
    private String emailTo;
    private String cc;
    private String bcc;
    private String subject;
    private String body; //body
}
