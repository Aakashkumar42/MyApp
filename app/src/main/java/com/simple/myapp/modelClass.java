package com.simple.myapp;

public class modelClass {

    private static  String name;
    private static float lon,lat,temp_c,temp_f;


    public modelClass() {
    }

    public static float getLon() {
        return lon;
    }

    public static float getLat() {
        return lat;
    }

    public static float getTemp_c() {
        return temp_c;
    }

    public static float getTemp_f() {
        return temp_f;
    }

    public static String getName() {
        return name;
    }



}
