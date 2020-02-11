package me.duvu.tracking.entities.enumeration;

/**
 * @author beou on 9/30/19 00:23
 */
public enum AlertCatalog {
    EVENTUALLY("EVENTUALLY"),            // no param
    INTERVAL("INTERVAL");                // no param

    String catalog;

    AlertCatalog(String catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return catalog;
    }
}
