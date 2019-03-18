package comp1110.ass2;

import java.util.Random;

public class SampleGames {
    String exampleInBook = "A4A12B2B16A1B01A1B23S1B32A1A32B1B44B2A44A4C16A3D15A4D01A5D23A4E20B1F24A2F17A1F01B0G16A5C34A4C43A5C53A3D50A4D61S4E50A0F51A1F67S2E46B1E31A1F30A2G36A1G41B1G52";
    static final char[] pieceDice = {'A', 'B', 'S'};

    static final String[] COMPLETED_GAMES = {
            "A3D61A3D53B0C52A0B52A2B63A4D41B0E60A0F61A3D31A3D23A2G30B0F34A3E32A1B01B2B10A1B21A0A63A4D01A1G41B0G12S2D10A4C10B2A10A2B33A1A30S4E11A4E21A3C21A3C31S5F11",
            "A3A10A3A52A3G10B2F10S1B50A2B61A0C60A1B41B1A35A4A41A2B31A1C30B0D32A2C50A4E10A3D12B2B10A2F01A0G00A4D01B1A27S3B20A4C10A1D50A0F23B2G25A3E30A4E41",
            "A2A30A0A43A3A50B2B50A4C50A3D50A2B43B0G12B0A14A2B33A0B11A4E50A3D61A2B21A3G52B1G45A3F52B2F41A3F33A1E40A1D40A3E32A3E27B0F10S0E12B1D17A4D01A1B61A0C43",
            "A4A50A1F61A0B61S5F50B1F46A1F01S1F12A2F23A1E20B2D21A3D03A1C20A0B22B1A61A4D11A4G10B1G44A2G30A3C01A3C12B0B31A1B01A4B50B0C50A2F32A0E32A0E40A4D31B1D47A1B11"
    };

    static String randomPiece(Random r) {
        return "" + pieceDice[r.nextInt(3)] + (char) (r.nextInt(6) + '0');
    }

    static String randomLocation(Random r) {
        return "" + (char) (r.nextInt(7) + 'A') + (char) (r.nextInt(7) + '0');
    }

    static int randomOrientation(Random r) {
        return r.nextInt(8);
    }

    static String badlyFormedPiecePlacement(Random r) {
        String piece = randomPiece(r);
        char a = piece.charAt(0);
        char b = piece.charAt(1);
        char bada = (char) ('C' + r.nextInt(26));
        if (bada == 'S') bada = '!';
        int badb = r.nextInt(5) + 8;

        String loc = randomLocation(r);
        int c = loc.charAt(0);
        int d = loc.charAt(1);
        int badc = r.nextInt(10) + 7 + 'A';
        int badd = r.nextInt(10) + 7 + '0';
        int e = randomOrientation(r);
        char bade = (char) ('A' + 8 + r.nextInt(10));

        String test = "";
        switch (r.nextInt(7)) {
            case 0:
                test += "" + bada + b + c + d + e;
                break;
            case 1:
                test += "" + a + badb + c + d + e;
                break;
            case 2:
                test += "" + a + b + badc + d + e;
                break;
            case 3:
                test += "" + a + b + badc + d + e;
                break;
            case 4:
                test += "" + a + b + c + badd + e;
                break;
            case 5:
                test += "" + a + b + c + d + bade;
                break;
            case 6:
                test += "" + bada + b + badc + d + e;
                break;
            default:
                test += "" + a + badb + c + badd + bade;
        }
        return test;
    }
}