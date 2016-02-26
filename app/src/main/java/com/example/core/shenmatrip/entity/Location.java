package com.example.core.shenmatrip.entity;

/**
 * Created by zhouyf on 16/2/26.
 */
public class Location {
    private static String location = "湖北";

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Location.location = location;
    }
}
