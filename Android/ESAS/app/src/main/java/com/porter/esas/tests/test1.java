package com.porter.esas.tests;
import android.test.InstrumentationTestCase;
public class test1 extends InstrumentationTestCase {
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
    }
}
