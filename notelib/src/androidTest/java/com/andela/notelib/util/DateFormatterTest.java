package com.andela.notelib.util;

import junit.framework.TestCase;

/**
 * Created by andela on 2/14/16.
 */
public class DateFormatterTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testGetReadableDate() throws Exception {
        String datetime = DateFormatter.getReadableDate(System.currentTimeMillis());
        assertNotNull(datetime);
    }
}