package me.duvu.tracking.storages;

import com.cloudinary.utils.ObjectUtils;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class CloudinaryResult implements UploadResult, Serializable {
    private static final long serialVersionUID = 2162528909118861437L;

    private String publicId;
    private Long version;
    private String signature;
    private int height;
    private int width;
    private String format;
    private String resourceType;
    private String createdAt;
    private Integer bytes;
    private String type;
    private String url;
    private String secureUrl;

    public static CloudinaryResult from(Map<String, Object> map) {
        CloudinaryResult result = new CloudinaryResult();
        result.publicId = ObjectUtils.asString(map.get("public_id"));
        result.version = ObjectUtils.asLong(map.get("version"), 0L);
        result.signature = ObjectUtils.asString(map.get("signature"));
        result.width = ObjectUtils.asInteger(map.get("width"), 0);
        result.height = ObjectUtils.asInteger(map.get("height"), 0);
        result.format = ObjectUtils.asString(map.get("format"));
        result.resourceType = ObjectUtils.asString(map.get("resource_type"));
        result.createdAt = ObjectUtils.asString(map.get("created_at"));
        result.bytes = ObjectUtils.asInteger(map.get("bytes"), 0);
        result.type = ObjectUtils.asString(map.get("type"));
        result.url = ObjectUtils.asString(map.get("url"));
        result.secureUrl = ObjectUtils.asString(map.get("secure_url"));
        return result;
    }
}
