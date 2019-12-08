package me.duvu.tracking.geo;

import me.duvu.tracking.utils.GeoJsonUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author beou on 9/15/18 13:10
 */
public class GeometryFactory {

    private static GeometryFactory instance;

    public GeometryFactory() {
    }

    public static GeometryFactory getInstance() {
        if (instance == null) {
            instance = new GeometryFactory();
        }
        return instance;
    }

    public Geometry create(String geoJson) {
        String type = GeoJsonUtils.getType(geoJson);

        if (StringUtils.isNotEmpty(type)) {
            switch (type) {
                case "Circle":
                    return createCircle(geoJson);
                case "Polygon":
                    return createPolygon(geoJson);
                case "Rectangle":
                    return createRectangle(geoJson);
                case "Polyline":
                    return createPolyline(geoJson);
            }
        }
        return null;
    }

    public Polygon createPolygon(String geoJson) {
        return new Polygon(geoJson);
    }

    public Circle createCircle(String geoJson) {
        return new Circle(geoJson);
    }

    public Rectangle createRectangle(String geoJson) {
        return new Rectangle(geoJson);
    }

    public Polyline createPolyline(String geoJson) {
        return new Polyline(geoJson);
    }
}
