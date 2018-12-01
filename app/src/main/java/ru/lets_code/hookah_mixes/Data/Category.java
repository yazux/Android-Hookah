package ru.lets_code.hookah_mixes.Data;

public class Category {

    public int id;

    public String name;

    public String description;

    public MixPivotCategory pivot;

    private int marks;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}