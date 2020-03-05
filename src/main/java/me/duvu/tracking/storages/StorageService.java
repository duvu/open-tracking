package me.duvu.tracking.storages;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author beou on 9/27/19 22:29
 */
public interface StorageService {
    String download(String uri);
    UploadResult upload(String path, MultipartFile file) throws IOException;
}
