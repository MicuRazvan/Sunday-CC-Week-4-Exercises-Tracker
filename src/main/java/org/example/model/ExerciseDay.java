package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDay {
    private String name;
    private List<Item> items;

    public ExerciseDay(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public void addItem(Item item) {
        this.items.add(item);
    }
    public void removeItem(Item item) {
        this.items.remove(item);
    }
}