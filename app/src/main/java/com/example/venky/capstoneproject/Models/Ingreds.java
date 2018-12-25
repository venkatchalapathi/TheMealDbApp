package com.example.venky.capstoneproject.Models;

/**
 * Created by VENKY on 12/18/2018.
 */

public class Ingreds {
    String image;
    String name;
    String measure;



    public Ingreds(String image, String name, String measure) {
        this.image = image;
        this.name = name;
        this.measure=measure;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
    public String getMeasure() {
        return measure;
    }
}