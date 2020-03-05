package me.duvu.tracking.storages;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GridFSServiceImpl implements StorageService {
    @Override
    public String download(String uri) {
        return null;
    }

    @Override
    public UploadResult upload(String path, MultipartFile file) throws IOException {
        return null;
    }
}
