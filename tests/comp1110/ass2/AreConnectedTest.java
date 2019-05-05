package comp1110.ass2;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class AreConnectedTest {

    @Test
    public void testInvalidEdge(){
        for(String test:INVALID_EDGE){
            String placement1 = test.substring(0,5);
            int index1 = (int)placement1.charAt(4)-48;
            String placement2 = test.substring(5);
            int index2 = (int)placement2.charAt(4)-48;
            assertFalse("Placement sequence " + test + " is invalid because they are not the same type, but passed", RailroadInk.areConnected(placement1,placement2,index1,index2));
        }

    }

    static final String[] INVALID_EDGE= {
            "A3A10A2A22",
            "A2A30A5A43"
    };

}