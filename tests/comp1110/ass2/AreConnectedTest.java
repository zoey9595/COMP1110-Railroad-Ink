package comp1110.ass2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AreConnectedTest {

    @Test
    public void testNoConnction(){
        for(String test: NO_CONNECTION){
            String placement1 = test.substring(0,5);
            int index1 = (int)placement1.charAt(4)-48;
            String placement2 = test.substring(5);
            int index2 = (int)placement2.charAt(4)-48;
            assertFalse("Placement sequence " + test + " is invalid because they are not connected, but passed", RailroadInk.areConnected(placement1,placement2,index1,index2));
        }
    }

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

    // Special case
    // Because sometimes this type should pass so I wrote a seperate method validNeighbourNoConnection to work on it
    @Test
    public void testValidNeighbourNoConnection(){
        for(String test: VALID_NEIBOUR_NO_CONNECTION){
            String placement1 = test.substring(0,5);
            int index1 = (int)placement1.charAt(4)-48;
            String placement2 = test.substring(5);
            int index2 = (int)placement2.charAt(4)-48;
            assertTrue("Placement sequence " + test + " is invalid connection, but passed", RailroadInk.areConnected(placement1,placement2,index1,index2));
        }
    }

    static final String[] NO_CONNECTION= {
            "A3A10A2A32A3D61A3D53B0C52A0B52A2B63A4D41",
            "A3D10A2D32A3D61A3D53B0C52A0B52A2B63A4D41",
            "A2B30A5A43A3D61A3D53B0C52A0B52A2B63A4D41"
    };

    static final String[] INVALID_EDGE= {
            "A3A10A2A22B0E60A0F61A3D31A3D23A2G30",
            "A2A30A5A43B0E60A0F61A3D31A3D23A2G30"
    };

    static final String[] VALID_NEIBOUR_NO_CONNECTION= {
            "A3A10A3A20A3D61A3D53B0C52A0B52A2B63A4D41",
            "A1D10A2D20A3D61A3D53B0C52A0B52A2B63A4D41"
    };
}