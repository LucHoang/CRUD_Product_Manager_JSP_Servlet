package com.demoqlsp.model;

public class Category {
    private int cateId;
    private String name;

    public Category() {

    }

    public Category(int cateId, String name) {
        this.cateId = cateId;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
