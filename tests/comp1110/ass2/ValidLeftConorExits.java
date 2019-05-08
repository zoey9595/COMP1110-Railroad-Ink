package comp1110.ass2;



import org.junit.*;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class ValidLeftConorExits {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testJavadocExamples() {
        testExpected("S0B01",  true);
        testExpected("S1B03",  false);

    }
    @Test
    public void testValidLeftConorExits() {
        for (String[] invalid : VALID) {
            testExpected(invalid[0], true);
        }
    }

    @Test
    public void testInvalidLeftConorExits() {
        testTrivialCorrect();
        for (String[] invalid : INVALID) {
            testExpected(invalid[0],  false);
        }
    }



    static final String[][] VALID = {
            {"S0B01"}, {"S0B05"},
            {"S1B00"}, {"S1B01"}, {"S1B02"}, {"S1B04"}, {"S1B05"}, {"S1B06"},
            {"S3B00"}, {"S3B01"}, {"S3B02"}, {"S3B03"}, {"S3B04"}, {"S3B05"}, {"S3B06"}, {"S3B07"},
            {"S4B01"}, {"S4B02"}, {"S4B04"}, {"S4B05"},
            {"S5B00"}, {"S5B02"}, {"S5B04"}, {"S5B06"},
            {"A0B00"}, {"A0B03"}, {"A0B06"}, {"A0B07"},
            {"A1B01"}, {"A1B03"}, {"A1B05"}, {"A1B07"},
            {"A2B01"}, {"A2B03"}, {"A2B05"}, {"A2B07"},{"A2B04"},{"A2B02"},
            {"B0B01"}, {"B0B05"},
            {"B1B02"}, {"B1B04"},
            {"B2B00"}, {"B2B02"}, {"B2B04"}, {"B2B06"},

    };

    static final String[][] INVALID = {
            {"S0B00"},{"S0B02"},{"S0B03"},{"S0B04"},{"S0B06"},{"S0B07"},
            {"S1B03"},{"S1B07"},
            {"S2BO0"},{"S2BO1"},{"S2BO2"},{"S2BO3"},{"S2BO4"},{"S2BO5"},{"S2BO6"},{"S2BO7"},
            {"S4B00"},{"S4B03"},{"S4B06"},{"S4B07"},
            {"S5B01"},{"S5B03"},{"S5B05"},{"S5B07"},
            {"A0B01"},{"A0B02"},{"A0B04"},{"A0B05"},
            {"A1B00"},{"A1B02"},{"A1B04"},{"A1B06"},
            {"A2B00"},{"A2B06"},
            {"B0B00"},{"B0B02"},{"B0B03"},{"B0B04"},{"B0B06"},{"B0B07"},
            {"B1B00"},{"B1B01"},{"B1B03"},{"B1B05"},{"B1B06"},{"B1B07"},
            {"B2B01"},{"B2B03"},{"B2B05"},{"B2B07"},

    };


    private void testTrivialCorrect() {
        testExpected("S0B01",  true);
    }
    private void testExpected(String tilePlacementString, boolean expected) {
        boolean result = RailroadInk.connectToAnExit(tilePlacementString);
        assertTrue("RailroadInk.connectToAnExit(" + tilePlacementString + ") expected " + expected + " but returned " + result, result == expected);

    }
}

