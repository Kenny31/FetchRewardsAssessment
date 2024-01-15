package com.example.codingexcercisefetch.model;

public class Item {
    private int Id;
    private int listId;
    private String name;

    public Item(int id, int listId, String name) {
        Id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
