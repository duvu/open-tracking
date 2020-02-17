package me.duvu.tracking.entities;

import java.io.Serializable;

public interface MultiTenantInf extends Serializable {
    String getTenantId();
}
