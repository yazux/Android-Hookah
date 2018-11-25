package ru.lets_code.example1.Data;

public class Vendor {
    public int id;
    public String name;
    public String country;
    public String description;
    public int hero_image_id;
    public float rating;
    public Image hero_image;

    public String getCountry() {
        return country;
    }

    public int getHero_image_id() {
        return hero_image_id;
    }

    public Image getHero_image() {
        return hero_image;
    }

    public float getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}