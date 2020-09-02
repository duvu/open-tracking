package me.duvu.tracking.geo;


import me.duvu.tracking.utils.DistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.Serializable;
import java.io.StringReader;

/**
 * @author beou on 3/6/18 00:54
 */
public class Circle extends Geometry {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private double centerLatitude;
    private double centerLongitude;
    private double radius;

    public Circle() {
    }

    public Circle(String wkt) {
        fromGeoJson(wkt);
    }

    public Circle(double centerLatitude, double centerLongitude, double radius) {
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.radius = radius;
    }

    public double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public boolean containsPoint(double latitude, double longitude) {
        logger.info("... calculating if contain point {}/{}", latitude, longitude);
        logger.info("... Circle {}/{} - {}", centerLatitude, centerLongitude, radius);
        double distance = DistanceCalculator.distance(centerLatitude, centerLongitude, latitude, longitude);
        logger.info("... Distance #{}", distance);
        return DistanceCalculator.distance(centerLatitude, centerLongitude, latitude, longitude) <= radius;
    }

    @Override
    public String toString() {
        return toGeoJson();
    }

    @Override
    public String toGeoJson() {
        JsonObject jsonObjectProperties = Json.createObjectBuilder()
                .add("type", "Circle")
                .add("radius", radius)
                .build();

        JsonArray coordinates = Json.createArrayBuilder()
                .add(centerLatitude)
                .add(centerLongitude)
                .build();

        JsonObject jsonObjectGeometry = Json.createObjectBuilder()
                .add("type", "Point")
                .add("coordinates", coordinates)
                .build();

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder(); // javax.json.JsonObjectBuilder
        JsonObject jsonObject = jsonObjectBuilder
                .add("type", "Feature")
                .add("properties", jsonObjectProperties)
                .add("geometry", jsonObjectGeometry)
                .build();
        return jsonObject.toString();
    }

    // {
    //      "type":"Feature",
    //      "properties":{
    //          "type":"Circle",
    //          "radius":4844.827350162072
    //      },
    //      "geometry":{
    //          "type":"Point",
    //          "coordinates":[106.06165,21.772602]
    //      }
    // }
    @Override
    public void fromGeoJson(String geoJson) {
        JsonObject jsonObject = Json.createReader(new StringReader(geoJson)).readObject();
        this.radius = jsonObject.getJsonObject("properties").getJsonNumber("radius").doubleValue();
        this.centerLongitude = jsonObject.getJsonObject("geometry").getJsonArray("coordinates").getJsonNumber(0).doubleValue();
        this.centerLatitude = jsonObject.getJsonObject("geometry").getJsonArray("coordinates").getJsonNumber(1).doubleValue();
    }

    //    @Override
//    public String toWkt() {
//        String wkt = "";
//        wkt = "CIRCLE (";
//        wkt += String.valueOf(centerLatitude);
//        wkt += " ";
//        wkt += String.valueOf(centerLongitude);
//        wkt += ", ";
//        DecimalFormat format = new DecimalFormat("0.#");
//        wkt += format.format(radius);
//        wkt += ")";
//        return wkt;
//    }
//
//    @Override
//    public void fromWkt(String wkt) throws ParseException {
//        if (!wkt.startsWith("CIRCLE")) {
//            throw new ParseException("Mismatch geo type", 0);
//        }
//        String content = wkt.substring(wkt.indexOf("(") + 1, wkt.indexOf(")"));
//        if (content == null || content.equals("")) {
//            throw new ParseException("No content", 0);
//        }
//        String[] commaTokens = content.split(",");
//        if (commaTokens.length != 2) {
//            throw new ParseException("Not valid content", 0);
//        }
//        String[] tokens = commaTokens[0].split("\\s");
//        if (tokens.length != 2) {
//            throw new ParseException("Too much or less coordinates", 0);
//        }
//        try {
//            centerLatitude = Double.parseDouble(tokens[0]);
//        } catch (NumberFormatException e) {
//            throw new ParseException(tokens[0] + " is not a double", 0);
//        }
//        try {
//            centerLongitude = Double.parseDouble(tokens[1]);
//        } catch (NumberFormatException e) {
//            throw new ParseException(tokens[1] + " is not a double", 0);
//        }
//        try {
//            radius = Double.parseDouble(commaTokens[1]);
//        } catch (NumberFormatException e) {
//            throw new ParseException(commaTokens[1] + " is not a double", 0);
//        }
//    }
}
