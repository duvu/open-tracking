package me.duvu.tracking.storages;

public interface UploadResult {
    String getUrl();
    String getSecureUrl();
    Integer getBytes();
    String getFormat();
}
