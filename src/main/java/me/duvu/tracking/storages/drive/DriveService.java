package me.duvu.tracking.storages.drive;

/**
 * @author beou on 9/27/19 22:29
 */
public interface DriveService {
    String download(String uri);
    String upload(String path);
}
