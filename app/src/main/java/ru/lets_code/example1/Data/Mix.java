package ru.lets_code.example1.Data;

import java.util.ArrayList;

public class Mix {
    public int id;
    public String name;
    public String description;
    public String[] stowage;
    public String coal;
    public String liquid;
    public String additionally;
    public float rating;
    public Category[] category;
    public Tobacco[] tobacco;
    public ArrayList<String> tobacco_text;

    public void setTobacco_text(ArrayList<String> tobacco_text) {
        this.tobacco_text = tobacco_text;
    }
}
