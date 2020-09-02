package me.duvu.tracking.geo;

import org.apache.commons.lang3.StringUtils;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beou on 3/6/18 00:55
 */
public class Polygon extends Geometry {

    private final String TYPE;

    public Polygon() {
        TYPE = "Polygon";
    }

    public Polygon(String wkt) {
        TYPE = "Polygon";
        fromGeoJson(wkt);
    }

    private List<Coordinate> coordinates;

    private double[] constant;
    private double[] multiple;

    private boolean needNormalize = false;

    private void precalc() {
        if (coordinates == null) {
            return;
        }

        int polyCorners = coordinates.size();
        int i;
        int j = polyCorners - 1;

        if (constant != null) {
            constant = null;
        }
        if (multiple != null) {
            multiple = null;
        }

        constant = new double[polyCorners];
        multiple = new double[polyCorners];

        boolean hasNegative = false;
        boolean hasPositive = false;
        for (i = 0; i < polyCorners; i++) {
            if (coordinates.get(i).getLongitude() > 90) {
                hasPositive = true;
            } else if (coordinates.get(i).getLongitude() < -90) {
                hasNegative = true;
            }
        }
        needNormalize = hasPositive && hasNegative;

        for (i = 0; i < polyCorners; j = i++) {
            if (normalizeLon(coordinates.get(j).getLongitude()) == normalizeLon(coordinates.get(i).getLongitude())) {
                constant[i] = coordinates.get(i).getLatitude();
                multiple[i] = 0;
            } else {
                constant[i] = coordinates.get(i).getLatitude()
                        - (normalizeLon(coordinates.get(i).getLongitude()) * coordinates.get(j).getLatitude())
                        / (normalizeLon(coordinates.get(j).getLongitude()) - normalizeLon(coordinates.get(i).getLongitude()))
                        + (normalizeLon(coordinates.get(i).getLongitude()) * coordinates.get(i).getLatitude())
                        / (normalizeLon(coordinates.get(j).getLongitude()) - normalizeLon(coordinates.get(i).getLongitude()));
                multiple[i] = (coordinates.get(j).getLatitude() - coordinates.get(i).getLatitude())
                        / (normalizeLon(coordinates.get(j).getLongitude()) - normalizeLon(coordinates.get(i).getLongitude()));
            }
        }
    }

    private double normalizeLon(double lon) {
        if (needNormalize && lon < -90) {
            return lon + 360;
        }
        return lon;
    }

    @Override
    public boolean containsPoint(double latitude, double longitude) {

        int polyCorners = coordinates.size();
        int i;
        int j = polyCorners - 1;
        double longitudeNorm = normalizeLon(longitude);
        boolean oddNodes = false;

        for (i = 0; i < polyCorners; j = i++) {
            if (normalizeLon(coordinates.get(i).getLongitude()) < longitudeNorm
                    && normalizeLon(coordinates.get(j).getLongitude()) >= longitudeNorm
                    || normalizeLon(coordinates.get(j).getLongitude()) < longitudeNorm
                    && normalizeLon(coordinates.get(i).getLongitude()) >= longitudeNorm) {
                oddNodes ^= longitudeNorm * multiple[i] + constant[i] < latitude;
            }
        }
        return oddNodes;
    }

    @Override
    public String toString() {
        return toGeoJson();
    }

    @Override
    public String toGeoJson() {
        JsonObject propertiesObject = Json.createObjectBuilder()
                .add("type", getTYPE())
                .build();

        JsonArrayBuilder coorBuilder = Json.createArrayBuilder();
        for (Coordinate coordinate : coordinates) {
            JsonArray coorJson = Json.createArrayBuilder()
                    .add(coordinate.getLatitude())
                    .add(coordinate.getLongitude())
                    .build();

            coorBuilder.add(coorJson);
        }

        JsonObject geometryObject = Json.createObjectBuilder()
                .add("type", "Polygon")
                .build();

        //--
        JsonObject json = Json.createObjectBuilder()
                .add("type", "Feature")
                .add("properties", propertiesObject)
                .add("geometry", coorBuilder.build())
                .build();
        return json.toString();
    }

    // {
    //      "type":"Feature",
    //      "properties": {
    //          "type":"Rectangle"
    //      },
    //      "geometry": {
    //          "type":"Polygon",
    //          "coordinates":[
    //              [
    //                  [106.016359,21.704393],
    //                  [105.960009,21.704393],
    //                  [105.960009,21.667977],
    //                  [106.01635,21.667958],
    //                  [106.016359,21.704393]
    //              ]
    //          ]}
    //      }
    @Override
    public void fromGeoJson(String geoJson) {
        JsonObject json = Json.createReader(new StringReader(geoJson)).readObject();
        JsonObject properties = json.getJsonObject("properties");

        String type = properties.getString("type");
        if (StringUtils.isEmpty(type) || !type.equalsIgnoreCase(getTYPE())) {
            throw new RuntimeException("Not a " + TYPE);
        }

        JsonObject geometry = json.getJsonObject("geometry");
        JsonArray jsonCoordinates = geometry.getJsonArray("coordinates").getJsonArray(0);
        coordinates = jsonCoordinates.stream().map(x -> {
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(x.asJsonArray().getJsonNumber(1).doubleValue());
            coordinate.setLongitude(x.asJsonArray().getJsonNumber(0).doubleValue());
            return coordinate;
        }).collect(Collectors.toList());

        precalc();
    }

    public String getTYPE() {
        return TYPE;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public double[] getConstant() {
        return constant;
    }

    public void setConstant(double[] constant) {
        this.constant = constant;
    }

    public double[] getMultiple() {
        return multiple;
    }

    public void setMultiple(double[] multiple) {
        this.multiple = multiple;
    }

    public boolean isNeedNormalize() {
        return needNormalize;
    }

    public void setNeedNormalize(boolean needNormalize) {
        this.needNormalize = needNormalize;
    }

    //    @Override
//    public String toWkt() {
//        StringBuilder buf = new StringBuilder();
//        buf.append("POLYGON ((");
//        for (Coordinate coordinate : coordinates) {
//            buf.append(String.valueOf(coordinate.getLatitude()));
//            buf.append(" ");
//            buf.append(String.valueOf(coordinate.getLongitude()));
//            buf.append(", ");
//        }
//        return buf.substring(0, buf.length() - 2) + "))";
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
//        if (!wkt.startsWith("POLYGON")) {
//            throw new ParseException("Mismatch geo type", 0);
//        }
//        String content = wkt.substring(wkt.indexOf("((") + 2, wkt.indexOf("))"));
//        if (content.isEmpty()) {
//            throw new ParseException("No content", 0);
//        }
//        String[] commaTokens = content.split(",");
//        if (commaTokens.length < 3) {
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
//        precalc();
//    }
}
