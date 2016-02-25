package com.andela.voluminotesapp.utilities;

/**
 * Created by andela on 2/18/16.
 */
public enum Pages {
    PAGES(0);

    public static final int WELCOME = 0;
    public static final int ALL_NOTES_GRID = 1;
    public static final int ALL_NOTES_LIST = 2;
    public static final int TRASH = 3;
    public static final int SEARCH = 4;

    public static final int TAKE_A_NOTE = 0;
    public static final int VIEW_ALL_NOTES = 1;
    public static final int VIEW_TRASH = 2;
    public static final int VIEW_SETTINGS = 3;

    public static int CURRENT;
    public static int PREVIOUS;

    private int pages;

    Pages(int pages) {
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }
}
