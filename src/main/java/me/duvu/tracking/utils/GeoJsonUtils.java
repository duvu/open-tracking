package me.duvu.tracking.utils;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringReader;

/**
 * @author beou on 9/15/18 08:12
 */
public class GeoJsonUtils {
    public static String getType(String geoJson) {
        //{"type":"Feature","properties":{"type":"Circle","radius":1281.1099216359025},"geometry":{"type":"Point","coordinates":[105.944514,21.741324]}}
        JsonObject jsonObject = Json.createReader(new StringReader(geoJson)).readObject();
        JsonObject properties = jsonObject.getJsonObject("properties");
        return properties.getString("type");
    }
}
