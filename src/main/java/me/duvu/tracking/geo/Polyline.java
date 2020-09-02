package me.duvu.tracking.geo;

import me.duvu.tracking.utils.DistanceCalculator;

import java.util.ArrayList;

/**
 * @author beou on 3/6/18 00:55
 */
public class Polyline extends Geometry {

    private ArrayList<Coordinate> coordinates;
    private double distance;

    public Polyline() {
    }

    public Polyline(String wkt) {
        fromGeoJson(wkt);
        //this.distance = distance;
    }

    @Override
    public boolean containsPoint(double latitude, double longitude) {
        for (int i = 1; i < coordinates.size(); i++) {
            if (DistanceCalculator.distanceToLine(
                    latitude, longitude, coordinates.get(i - 1).getLatitude(), coordinates.get(i - 1).getLongitude(),
                    coordinates.get(i).getLatitude(), coordinates.get(i).getLongitude()) <= distance) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return toGeoJson();
    }

    @Override
    public String toGeoJson() {
        return null;
    }

    @Override
    public void fromGeoJson(String geoJson) {

    }

    //    @Override
//    public String toWkt() {
//        StringBuilder buf = new StringBuilder();
//        buf.append("LINESTRING (");
//        for (Coordinate coordinate : coordinates) {
//            buf.append(String.valueOf(coordinate.getLatitude()));
//            buf.append(" ");
//            buf.append(String.valueOf(coordinate.getLongitude()));
//            buf.append(", ");
//        }
//        return buf.substring(0, buf.length() - 2) + ")";
//    }
//
//    @Override
//    public String toString() {
//        return toWkt();
//    }
//
//    @Override
//    public void fromWkt(String wkt) throws ParseException {
//        if (coordinates == null) {
//            coordinates = new ArrayList<>();
//        } else {
//            coordinates.clear();
//        }
//
//        if (!wkt.startsWith("LINESTRING")) {
//            throw new ParseException("Mismatch geo type", 0);
//        }
//        String content = wkt.substring(wkt.indexOf("(") + 1, wkt.indexOf(")"));
//        if (content.isEmpty()) {
//            throw new ParseException("No content", 0);
//        }
//        String[] commaTokens = content.split(",");
//        if (commaTokens.length < 2) {
//            throw new ParseException("Not valid content", 0);
//        }
//
//        for (String commaToken : commaTokens) {
//            String[] tokens = commaToken.trim().split("\\s");
//            if (tokens.length != 2) {
//                throw new ParseException("Here must be two coordinates: " + commaToken, 0);
//            }
//            Coordinate coordinate = new Coordinate();
//            try {
//                coordinate.setLatitude(Double.parseDouble(tokens[0]));
//            } catch (NumberFormatException e) {
//                throw new ParseException(tokens[0] + " is not a double", 0);
//            }
//            try {
//                coordinate.setLongitude(Double.parseDouble(tokens[1]));
//            } catch (NumberFormatException e) {
//                throw new ParseException(tokens[1] + " is not a double", 0);
//            }
//            coordinates.add(coordinate);
//        }
//
//    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public double getDistance() {
        return distance;
    }
}
