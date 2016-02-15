package com.andela.voluminotesapp.config;


public enum Constants {
    SMALL(20),
    MEDIUM(30),
    LARGE(40),
    EXTRA_LARGE(50);

    private String constant;
    private int sizes;

    Constants(String constant){
        this.constant = constant;
    }

    Constants(int sizes){
        this.sizes = sizes;
    }

    public int getSizes() {
        return sizes;
    }

    @Override
    public String toString() {
        return this.constant;
    }
}
