package me.duvu.tracking.geo;

import java.text.ParseException;

/**
 * @author beou on 2/20/18 01:15
 */
public abstract class Geometry {

    public abstract boolean containsPoint(double latitude, double longitude);
    public abstract String toString();

    public abstract String toGeoJson();
    public abstract void fromGeoJson(String geoJson) throws ParseException;
}
