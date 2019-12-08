package me.duvu.tracking.email;

/**
 * @author beou on 5/17/18 00:29
 */
public interface EmailService {
    public void send(String to, String subject, String text);
    public void send(String to, String cc, String subject, String text);
    public void send(String to, String cc, String subject, String text, String pathToAttach);
}
