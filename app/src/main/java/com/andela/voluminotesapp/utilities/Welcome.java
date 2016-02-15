package com.andela.voluminotesapp.utilities;

/**
 * Created by andela on 2/15/16.
 */
public class Welcome {
    private int id;
    private int drawable;
    private int color;
    private String title;

    public Welcome() {
    }

    public Welcome(int id, int drawable, int color, String title) {
        this.id = id;
        this.drawable = drawable;
        this.color = color;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
