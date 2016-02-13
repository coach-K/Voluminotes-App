package com.andela.notelib.database;

/**
 * This class stores a MySQL credential constants
 */
public enum Config {

    /**
     * Username to MySQL database
     */
    USERNAME(""),

    /**
     * Password to MySQL database
     */
    PASSWORD(""),

    /**
     * Location to MySQL database
     */
    URL("jdbc:sqldroid:/data/data/com.andela.note/main.sqlite"),

    /**
     * MySQL database to establish connection
     */
    DATABASE(""),

    /**
     * MySQL table to query
     */
    TABLE(""),
    /**
     * Sqlite Driver
     */
    DRIVER("");

    private String CONSTANT;

    /**
     * Construct this class
     *
     * @param CONSTANT to be set
     */
    private Config(String CONSTANT) {
        this.CONSTANT = CONSTANT;
    }

    @Override
    public String toString() {
        return CONSTANT;
    }
}
