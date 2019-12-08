package me.duvu.tracking.geo;

/**
 * @author beou on 9/15/18 07:38
 */
public class Rectangle extends Polygon {
    private final String TYPE;

    public Rectangle() {
        TYPE = "Rectangle";
    }

    public Rectangle(String geoJson) {
        TYPE = "Rectangle";
        fromGeoJson(geoJson);
    }
//
//    @Override
//    public boolean containsPoint(double latitude, double longitude) {
//        return false;
//    }
//
//    @Override
//    public String toString() {
//        return toGeoJson();
//    }
//
//    @Override
//    public String toGeoJson() {
//        return null;
//    }
//
//    @Override
//    public void fromGeoJson(String geoJson) throws ParseException {
//        JsonObject json = Json.createReader(new StringReader(geoJson)).readObject();
//        JsonObject properties = json.getJsonObject("properties");
//
//        String type = properties.getString("type");
//        if (StringUtils.isEmpty(type) || !type.equalsIgnoreCase(TYPE)) {
//            throw new ParseException("Not a " + TYPE, 0);
//        }
//
//        JsonObject geometry = json.getJsonObject("geometry");
//        JsonArray jsonCoordinates = geometry.getJsonArray("coordinates");
//        coordinates = jsonCoordinates.stream().map(x -> {
//            Coordinate coordinate = new Coordinate();
//            coordinate.setLatitude(Double.parseDouble(x.asJsonArray().getString(0)));
//            coordinate.setLongitude(Double.parseDouble(x.asJsonArray().getString(1)));
//            return coordinate;
//        }).collect(Collectors.toList());
//    }

    @Override
    public String getTYPE() {
        return TYPE;
    }
}
