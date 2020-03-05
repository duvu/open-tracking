package me.duvu.tracking.storages;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Qualifier("storageService")
public class CloudinaryServiceImpl implements StorageService {
    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String download(String uri) {
        return null;
    }

    @Override
    public UploadResult upload(String path, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Map params = ObjectUtils.asMap(
            "public_id", "vd5/" + path + "/" + fileName,
            "overwrite", true,
            "notification_url", "https://notify.gpshandle.com/notify_endpoint",
            "resource_type", "image"
        );
        Map result = cloudinary.uploader().upload(file.getBytes(), params);
        return CloudinaryResult.from(result);
    }
}
