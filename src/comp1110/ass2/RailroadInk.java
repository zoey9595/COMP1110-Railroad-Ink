package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RailroadInk {
    /**
     * Author: Yufan Zhou
     *
     * Determine whether a tile placement string is well-formed:
     * - it consists of exactly 5 characters;
     * - the first character represents a die A or B, or a special tile S
     * - the second character indicates which tile or face of the die (0-5 for die A and special tiles, or 0-2 for die B)
     * - the third character represents the placement row A-G
     * - the fourth character represents the placement column 0-6
     * - the fifth character represents the orientation 0-7
     *
     * @param tilePlacementString a candidate tile placement string
     * @return true if the tile placement is well formed
     */
    public static boolean isTilePlacementWellFormed(String tilePlacementString) {

        // whether it contains only 5 characters
        if(tilePlacementString.length()/5 !=1 || tilePlacementString.length()%5 !=0){
            return false;
        }

        // whether the first character represents a die A or B, or a special tile S
        if(tilePlacementString.charAt(0) != 'A' && tilePlacementString.charAt(0) != 'B' && tilePlacementString.charAt(0) != 'S'){
            return false;
        }

        // when the first character is A or S, whether the second character between 0-5
        if(tilePlacementString.charAt(0) == 'A' && tilePlacementString.charAt(1)>'5'){
            return false;
        }

        if(tilePlacementString.charAt(0) == 'S' && tilePlacementString.charAt(1)>'5'){
            return false;
        }

        // when the first character is B, whether the second character between 0-2
        if(tilePlacementString.charAt(0) == 'B' && tilePlacementString.charAt(1)>'2'){
            return false;
        }

        // whether the third character is between A and G
        if(tilePlacementString.charAt(2) > 'G' || tilePlacementString.charAt(2) < 'A'){
            return false;
        }

        // whether the fourth character is between 0 and 6
        if(!(tilePlacementString.charAt(3) == '0' || tilePlacementString.charAt(3) == '1'||tilePlacementString.charAt(3) == '2'||tilePlacementString.charAt(3) == '3'||tilePlacementString.charAt(3) == '4'||tilePlacementString.charAt(3) == '5'||tilePlacementString.charAt(3) == '6')){
            return false;
        }

        // whether the fifth character is between 0 and 7
        if(tilePlacementString.charAt(4) > '7'){
            return false;
        }
        // FIXME Task 2: determine whether a tile placement is well-formed
        return true;
    }

    /**
     * Author: Yufan Zhou
     *
     * Determine whether a board string is well-formed:
     * - it consists of exactly N five-character tile placements (where N = 1 .. 31);
     * - each piece placement is well-formed
     * - no more than three special tiles are included
     *
     * @param boardString a board string describing the placement of one or more pieces
     * @return true if the board string is well-formed
     */
    public static boolean isBoardStringWellFormed(String boardString) {

        // whether a board string is null or empty
        if(boardString == null || boardString == ""){
            return false;
        }

        // whether a board string has exactly N five-character tile placements, and not too long
        if(boardString.length()%5 != 0) {
            return false;
        }else if(boardString.length()/5 > 31){
            return false;
        }

        int count = 0;
        for(int i=0; i<boardString.length(); i+=5){
            // slice the board string into a single one
            String newString = boardString.substring(i,i+4);
            char margin = boardString.charAt(i+4);
            newString = newString + margin;
            // test whether it is well performed
            if(!isTilePlacementWellFormed(newString)){
                return false;
            }
            // count the number of special pieces
            if(newString.charAt(0) == 'S'){
                count++;
            }
        }

        // if special pieces is more than 3 then it is not valid
        if(count > 3){
            return false;
        }else{
            return true;
        }
        // FIXME Task 3: determine whether a board string is well-formed
    }

    /**
     * Author: Yuqing Zhai
     *
     * Check whether two tile placements are in the same row or column
     */
    public static int areNeighbours(String tilePlacementStringA, String tilePlacementStringB) {

        //A is top at B
        if((tilePlacementStringA.charAt(3) == tilePlacementStringB.charAt(3))&&(tilePlacementStringA.charAt(2) - tilePlacementStringB.charAt(2) == -1))
            return 0;
        //A is right at B
        if((tilePlacementStringA.charAt(2) == tilePlacementStringB.charAt(2))&&(tilePlacementStringA.charAt(3) - tilePlacementStringB.charAt(3) == 1))
            return 1;
        //A is down at B
        if((tilePlacementStringA.charAt(3) == tilePlacementStringB.charAt(3))&&(tilePlacementStringA.charAt(2) - tilePlacementStringB.charAt(2) == 1))
            return 2;
        //A is left at B
        if((tilePlacementStringA.charAt(2) == tilePlacementStringB.charAt(2))&&(tilePlacementStringA.charAt(3) - tilePlacementStringB.charAt(3) == -1))
            return 3;

        return -1;
    }

    // which exit can the four direction of tile connect - up,right,down,left
    // highway->1, railway->2, no exit->0
    static int S0[][]={{1,1,2,1},{1,1,1,2},{2,1,1,1},{1,2,1,1},{1,1,2,1},{1,1,1,2},{2,1,1,1},{1,2,1,1}};
    static int S1[][]={{1,2,2,2},{2,1,2,2},{2,2,1,2},{2,2,2,1},{1,2,2,2},{2,1,2,2},{2,2,1,2},{2,2,2,1}};
    static int S2[][]={{1,1,1,1},{1,1,1,1},{1,1,1,1},{1,1,1,1},{1,1,1,1},{1,1,1,1},{1,1,1,1},{1,1,1,1}};
    static int S3[][]={{2,2,2,2},{2,2,2,2},{2,2,2,2},{2,2,2,2},{2,2,2,2},{2,2,2,2},{2,2,2,2},{2,2,2,2}};
    static int S4[][]={{1,2,2,1},{1,1,2,2},{2,1,1,2},{2,2,1,1},{1,1,2,2},{2,1,1,2},{2,2,1,1},{1,2,2,1}};
    static int S5[][]={{1,2,1,2},{2,1,2,1},{1,2,1,2},{2,1,2,1},{1,2,1,2},{2,1,2,1},{1,2,1,2},{2,1,2,1}};
    static int A0[][]={{2,0,0,2},{2,2,0,0},{0,2,2,0},{0,0,2,2},{2,2,0,0},{0,2,2,0},{0,0,2,2},{2,0,0,2}};
    static int A1[][]={{2,0,2,0},{0,2,0,2},{2,0,2,0},{0,2,0,2},{2,0,2,0},{0,2,0,2},{2,0,2,0},{0,2,0,2}};
    static int A2[][]={{2,2,2,0},{0,2,2,2},{2,0,2,2},{2,2,0,2},{2,0,2,2},{2,2,0,2},{2,2,2,0},{0,2,2,2}};
    static int A3[][]={{1,1,1,0},{0,1,1,1},{1,0,1,1},{1,1,0,1},{1,0,1,1},{1,1,0,1},{1,1,1,0},{0,1,1,1}};
    static int A4[][]={{1,0,1,0},{0,1,0,1},{1,0,1,0},{0,1,0,1},{1,0,1,0},{0,1,0,1},{1,0,1,0},{0,1,0,1}};
    static int A5[][]={{1,0,0,1},{1,1,0,0},{0,1,1,0},{0,0,1,1},{1,1,0,0},{0,1,1,0},{0,0,1,1},{1,0,0,1}};
    static int B0[][]={{1,0,2,0},{0,1,0,2},{2,0,1,0},{0,2,0,1},{1,0,2,0},{0,1,0,2},{2,0,1,0},{0,2,0,1}};
    static int B1[][]={{1,2,0,0},{0,1,2,0},{0,0,1,2},{2,0,0,1},{1,0,0,2},{2,1,0,0},{0,2,1,0},{0,0,2,1}};
    static int B2[][]={{1,2,1,2},{2,1,2,1},{1,2,1,2},{2,1,2,1},{1,2,1,2},{2,1,2,1},{1,2,1,2},{2,1,2,1}};

    // To get the connection type
    // Author: Yuqing Zhai
    public static int getvalue(String tilePlacementStringA,int x,int y)
    {
        int ans=0;
        if(tilePlacementStringA.charAt(0)=='S'&&tilePlacementStringA.charAt(1)=='0') ans=S0[x][y];
        if(tilePlacementStringA.charAt(0)=='S'&&tilePlacementStringA.charAt(1)=='1') ans=S1[x][y];
        if(tilePlacementStringA.charAt(0)=='S'&&tilePlacementStringA.charAt(1)=='2') ans=S2[x][y];
        if(tilePlacementStringA.charAt(0)=='S'&&tilePlacementStringA.charAt(1)=='3') ans=S3[x][y];
        if(tilePlacementStringA.charAt(0)=='S'&&tilePlacementStringA.charAt(1)=='4') ans=S4[x][y];
        if(tilePlacementStringA.charAt(0)=='S'&&tilePlacementStringA.charAt(1)=='5') ans=S5[x][y];
        if(tilePlacementStringA.charAt(0)=='A'&&tilePlacementStringA.charAt(1)=='0') ans=A0[x][y];
        if(tilePlacementStringA.charAt(0)=='A'&&tilePlacementStringA.charAt(1)=='1') ans=A1[x][y];
        if(tilePlacementStringA.charAt(0)=='A'&&tilePlacementStringA.charAt(1)=='2') ans=A2[x][y];
        if(tilePlacementStringA.charAt(0)=='A'&&tilePlacementStringA.charAt(1)=='3') ans=A3[x][y];
        if(tilePlacementStringA.charAt(0)=='A'&&tilePlacementStringA.charAt(1)=='4') ans=A4[x][y];
        if(tilePlacementStringA.charAt(0)=='A'&&tilePlacementStringA.charAt(1)=='5') ans=A5[x][y];
        if(tilePlacementStringA.charAt(0)=='B'&&tilePlacementStringA.charAt(1)=='0') ans=B0[x][y];
        if(tilePlacementStringA.charAt(0)=='B'&&tilePlacementStringA.charAt(1)=='1') ans=B1[x][y];
        if(tilePlacementStringA.charAt(0)=='B'&&tilePlacementStringA.charAt(1)=='2') ans=B2[x][y];
        return ans;
    }

    // Test whether two placement can be connected
    // Author: Yuqing Zhai
    public static boolean areConnected(String tilePlacementStringA, String tilePlacementStringB, int indexA, int indexB)
    {
        int valueA=getvalue(tilePlacementStringA,tilePlacementStringA.charAt(4)-'0',indexA);
        int valueB=getvalue(tilePlacementStringB,tilePlacementStringB.charAt(4)-'0',indexB);
        // whether the connection exists, and the connection type is the same one
        if(valueA==0 || valueB==0) return false;
        return (valueA == valueB);
    }

    /**
     * Author: Yuqing Zhai
     *
     * Check whether there is a connection between two tiles
     * @param tilePlacementStringA the first tile
     * @param tilePlacementStringB the second tile
     * @return true if two tiles are neighbour but have no connection
     */
    public static boolean validNeighbourNoConnection(String tilePlacementStringA, String tilePlacementStringB){

        int indexA = 0;
        int indexB = 0;

        if(tilePlacementStringA.charAt(2) - tilePlacementStringB.charAt(2) == 1){
            indexA = 0;
            indexB = 2;
        }
        if(tilePlacementStringA.charAt(2)-tilePlacementStringB.charAt(2) == -1) {
            indexA = 2;
            indexB = 0;
        }
        if(tilePlacementStringA.charAt(3)-tilePlacementStringB.charAt(3) == 1) {
            indexA = 3;
            indexB = 1;
        }
        if(tilePlacementStringA.charAt(3)-tilePlacementStringB.charAt(3) == -1) {
            indexA = 1;
            indexB = 3;
        }

        int valueA=getvalue(tilePlacementStringA,tilePlacementStringA.charAt(4)-'0',indexA);
        int valueB=getvalue(tilePlacementStringB,tilePlacementStringB.charAt(4)-'0',indexB);

        return (valueA==0 || valueB==0);
    }

    /**
     * Author: Huhan Jiang
     * Determine whether the provided placements are neighbours connected by at least one validly connecting edge.
     * For example,
     * - areConnectedNeighbours("A3C10", "A3C23") would return true as these tiles are connected by a highway edge;
     * - areConnectedNeighbours("A3C23", "B1B20") would return false as these neighbouring tiles are disconnected;
     * - areConnectedNeighbours("A0B30", "A3B23") would return false as these neighbouring tiles have an
     * invalid connection between highway and railway; and
     * areConnectedNeighbours("A0B30", "A3C23") would return false as these tiles are not neighbours.
     *
     * @return true if the placements are connected neighbours
     */
    public static boolean areConnectedNeighbours(String tilePlacementStringA, String tilePlacementStringB) {

        // first judge neighbour
        if (areNeighbours(tilePlacementStringA, tilePlacementStringB) == -1) {
            return false;
        }
        // If they are neighbours, whether they can successfully connect.
        if(tilePlacementStringA.charAt(2) - tilePlacementStringB.charAt(2) == 1) {
            if(areConnected(tilePlacementStringA,tilePlacementStringB,0,2))
                return true;
        }

        if(tilePlacementStringA.charAt(2)-tilePlacementStringB.charAt(2) == -1) {
            if(areConnected(tilePlacementStringA,tilePlacementStringB,2,0))
                return true;
        }

        if(tilePlacementStringA.charAt(3)-tilePlacementStringB.charAt(3) == 1) {
            if(areConnected(tilePlacementStringA,tilePlacementStringB,3,1))
                return true;
        }

        if(tilePlacementStringA.charAt(3)-tilePlacementStringB.charAt(3) == -1) {
            if(areConnected(tilePlacementStringA,tilePlacementStringB,1,3))
                return true;
        }
        // FIXME Task 5: determine whether neighbouring placements are connected
        return false;
    }


    // Author: Huhan Jiang, Yuqing Zhai
    // Check whether a placement is connect to an exit

    public static int connectToAnExit(String tilePlacementString) {
        int exits = -1;

        //top 3 exits
        if ((tilePlacementString.charAt(2) == 'A' && tilePlacementString.charAt(3) == '1')
                || (tilePlacementString.charAt(2) == 'A' && tilePlacementString.charAt(3) == '5')) {
            exits = -3;
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 0) == 1) {
                exits=0;
            }else if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 0) == 0){
                exits=-2;
            }
        }
        if (tilePlacementString.charAt(2) == 'A' && tilePlacementString.charAt(3) == '3') {
            exits = -3;
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 0) == 2) {
                exits=0;
            }else if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 0) == 0){
                exits=-2;
            }
        }

        //right 3 exits
        if ((tilePlacementString.charAt(2) == 'B' && tilePlacementString.charAt(3) == '6')
                || (tilePlacementString.charAt(2) == 'F' && tilePlacementString.charAt(3) == '6')) {
            exits = -3;
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 1) == 2) {
                exits=1;
            }else if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 1) == 0){
                exits=-2;
            }
        }
        if (tilePlacementString.charAt(2) == 'D' && tilePlacementString.charAt(3) == '6') {
            exits = -3;
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 1) == 1) {
                exits=1;
            }else if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 1) == 0){
                exits=-2;
            }
        }

        //down 3 exits
        if ((tilePlacementString.charAt(2) == 'G' && tilePlacementString.charAt(3) == '1')
                || (tilePlacementString.charAt(2) == 'G' && tilePlacementString.charAt(3) == '5')) {
            exits = -3;
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 2) == 1) {
                exits=2;
            }else if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 2) == 0){
                exits=-2;
            }
        }
        if (tilePlacementString.charAt(2) == 'G' && tilePlacementString.charAt(3) == '3') {
            exits = -3;
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 2) == 2) {
                exits=2;
            }else if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 2) == 0){
                exits=-2;
            }
        }

        // left 3 exits
        if ((tilePlacementString.charAt(2) == 'B' && tilePlacementString.charAt(3) == '0')
                || (tilePlacementString.charAt(2) == 'F' && tilePlacementString.charAt(3) == '0')) {
            exits = -3;
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 3) == 2) {
                exits=3;
            } else if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 2) == 0){
                exits=-2;
            }
        }
        if (tilePlacementString.charAt(2) == 'D' && tilePlacementString.charAt(3) == '0') {
            exits = -3;
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 3) == 1) {
                exits=3;
            } else if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 2) == 0){
                exits=-2;
            }
        }
        return exits;
    }
    /**
     *  Author: Huhan Jiang, Yuqing Zhai
     *
     *  If two strings are not in the same place, return false.
     *  Otherwise return true
     */
    public static boolean findDuplicate(String tileStringA, String tileStringB){
        return (tileStringA.charAt(2) == tileStringB.charAt(2)) && (tileStringA.charAt(3) == tileStringB.charAt(3));
    }

    /**
     * Author: Huhan Jiang, Yuqing Zhai
     *
     * Given a well-formed board string representing an ordered list of placements,
     * determine whether the board string is valid.
     * A board string is valid if each tile placement is legal with respect to all previous tile
     * placements in the string, according to the rules for legal placements:
     * - A tile must be placed such that at least one edge connects to either an exit or a pre-existing route.
     *   Such a connection is called a valid connection.
     * - Tiles may not be placed such that a highway edge connects to a railway edge;
     *   this is referred to as an invalid connection.
     *   Highways and railways may only join at station tiles.
     * - A tile may have one or more edges touching a blank edge of another tile;
     *   this is referred to as disconnected, but the placement is still legal.
     *
     * @param boardString a board string representing some placement sequence
     * @return true if placement sequence is valid
     */

    public static boolean isValidPlacementSequence(String boardString) {

        // Test if the board string is well formed
        if(!isBoardStringWellFormed(boardString)){
            return false;
        }

        // The first String must connect to an exit
        if (connectToAnExit(boardString.substring(0,5)) < 0) {
            return false;
        }

        int i=5;

        loop:
        while (i < boardString.length()) {

            boolean status = false;
            // Check whether there are two tiles in the same grid
            int k=0;
            while(k<i) {
                if (findDuplicate(boardString.substring(k,k+5), boardString.substring(i,i+5))) {
                    return false;
                }else{
                    k+=5;
                }
            }

            // if it connect to an exit, then continue loop
            if (connectToAnExit(boardString.substring(i,i+5)) >=0) {
                i=i+5;
                if(i == boardString.length()){
                    return true;
                }
                continue;
            } else if (connectToAnExit(boardString.substring(i,i+5)) == -3){
                return false;
            }

            // Check whether the tile can connect with any other tile before
            int j=0;
            while(j<i) {
                if(areNeighbours(boardString.substring(j,j+5), boardString.substring(i,i+5))>=0) {
                    if (!areConnectedNeighbours(boardString.substring(j,j+5), boardString.substring(i,i+5))) {
                        // Check whether two tiles have valid placement but no connection
                        if(!validNeighbourNoConnection(boardString.substring(j,j+5), boardString.substring(i,i+5))){
                            return false;
                        }
                    }else{
                        status = true;
                    }
                }
                j=j+5;
                if(j==i){
                    if(status) {
                        i = i + 5;
                        // If the tile is all checked, return true
                        if (i == boardString.length()) {
                            return status;
                        }
                        continue loop;
                    }
                    return false;
                }
            }
            // A tile is invalid if neither connect to an exit nor a tile
        }
        return true;
    }

    /**
     * Author: Huhan Jiang
     *
     * Generate a random dice roll as a string of eight characters.
     * Dice A should be rolled three times, dice B should be rolled once.
     * Die A has faces numbered 0-5.
     * Die B has faces numbered 0-2.
     * Each die roll is composed of a character 'A' or 'B' representing the dice,
     * followed by a digit character representing the face.
     *
     * @return a String representing the die roll e.g. A0A4A3B2
     */
    public static String generateDiceRoll() {

        String roll = "";
        Random r = new Random();
        roll = "A" + r.nextInt(6) + "A" + r.nextInt(6) + "A" + r.nextInt(6) + "B" + r.nextInt(3);
        // FIXME Task 7: generate a dice roll
        return roll;
    }

    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     *
     * Get the Basic game score.
     *
     * @param boardString A well-formed whole board string.
     * @return
     */
    public static int getBasicScore(String boardString) {
        getGroups(boardString);

        int ans = 0;
        // Number of Exits connected to route | 2 | 3 | 4  | 5 |  6 |  7 |  8 |  9 |  10 | 11 | 12 |
        // Points Awarded                     | 4 | 8 | 12 | 16 | 20 | 24 | 28 | 32 | 36 | 40 | 45 |
        for(int i=0;i<group.size();i++){
            int exits=0;
            int error=0;
            for(int j=0;j+4<group.get(i).length();j=j+5){
                String temp=group.get(i).substring(j,j+5);
                for(int k=0;k<4;k++) {
                    if(mapNode.get(temp).direction[k]==-1) exits++;
                    if(mapNode.get(temp).direction[k]==-2){
                        error++;
                    }
                }
            }
            if(exits==2) ans+=4;
            if(exits==3) ans+=8;
            if(exits==4) ans+=12;
            if(exits==5) ans+=16;
            if(exits==6) ans+=20;
            if(exits==7) ans+=24;
            if(exits==8) ans+=28;
            if(exits==9) ans+=32;
            if(exits==10) ans+=36;
            if(exits==11) ans+=40;
            if(exits==12) ans+=45;

            ans-=error;
        }
        //centre grid that are covered
        ans += getCentreGrids(boardString);
        // FIXME Task 8: compute the basic score
        return ans;
    }

    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     * @param boardString
     * @return
     */
    static int getCentreGrids(String boardString)
    {
        int nums=0;
        int i=0;
        while(i+4<boardString.length())
        {
            if(boardString.charAt(i+2)=='C'&&boardString.charAt(i+3)=='2')nums++;
            if(boardString.charAt(i+2)=='C'&&boardString.charAt(i+3)=='3')nums++;
            if(boardString.charAt(i+2)=='C'&&boardString.charAt(i+3)=='4')nums++;
            if(boardString.charAt(i+2)=='D'&&boardString.charAt(i+3)=='2')nums++;
            if(boardString.charAt(i+2)=='D'&&boardString.charAt(i+3)=='3')nums++;
            if(boardString.charAt(i+2)=='D'&&boardString.charAt(i+3)=='4')nums++;
            if(boardString.charAt(i+2)=='E'&&boardString.charAt(i+3)=='2')nums++;
            if(boardString.charAt(i+2)=='E'&&boardString.charAt(i+3)=='3')nums++;
            if(boardString.charAt(i+2)=='E'&&boardString.charAt(i+3)=='4')nums++;


            i=i+5;
        }
        return nums;
    }

    // Store different groups of routes
    static ArrayList<String> group=new ArrayList<String>();
    // Each group's connection number
    static HashMap<String, Node> mapNode = new HashMap<String, Node>();

    /**
     * Set Node class, set default directions to be -2
     */
    static class Node{
        int[] direction=new int[4];
        public Node() {
            for(int i=0;i<4;i++){
                direction[i]=-2;
            }
        }
    }
    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     *
     * Divide different nodes into different groups.
     *
     */
    static void getGroups(String boardString)
    {
        mapNode.clear();
        group.clear();
        int i=0;

        while(i+4<boardString.length())
        {
            Node node=new Node();
            String temp=boardString.substring(i,i+5);

            for(int j=0;j<4;j++){
                // Set the no connection direction as 0
                if(getvalue(temp,temp.charAt(4)-'0',j)==0) node.direction[j]=0;
            }

            // If the tile is in the first row, set its up direction as 0
            if(temp.charAt(2)=='A')node.direction[0]=0;
            // If the tile is in the last column, set its right direction as 0
            if(temp.charAt(3)=='6')node.direction[1]=0;
            // If the tile is in the last row, set its down direction as 0
            if(temp.charAt(2)=='G')node.direction[2]=0;
            // If the tile is in the first column, set its left direction as 0
            if(temp.charAt(3)=='0')node.direction[3]=0;
            // Whether there are connecting groups
            int find=0;
            // The first group
            int findGroup=-1;
            // Whether it connects to an exit
            int nodeExits=-1;
            // The neighbor is in which direction of the note
            int neighbour=-1;
            // Check whether the node connects to an exit
            nodeExits=connectToAnExit(temp);
            // If the node connects to an exit, set the direction -1
            if(nodeExits>=0) node.direction[nodeExits]=-1;

            for(int j=0;j<group.size();j++){

                find=0;
                String neighPlace="";

                for(int k=0;k+4<group.get(j).length();k=k+5){
                    // Check the connection condition of the temp
                    neighbour=areNeighbours(temp, group.get(j).substring(k,k+5));

                    if(neighbour>=0&&areConnectedNeighbours(temp,group.get(j).substring(k,k+5))) {
                        neighPlace= group.get(j).substring(k,k+5);
                        find=1;
                        // Stop when finding the first connecting neighbour
                        break;
                    }
                }
                if(find==1){
                    if(findGroup==-1){
                        // If the connecting tile is B2
                        if(neighPlace.charAt(0)=='B'&&neighPlace.charAt(1)=='2'){
                            // If no connection group on the opposite direction 
                            if(mapNode.get(neighPlace).direction[(neighbour+2)%4]==-2){
                                findGroup=j;
                                group.add(temp+neighPlace);
                                // Set the different connection condition
                                node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                                mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);
                                j++;
                            }
                            // If there is a group in the opposite direction
                            else{
                                // Whether the neighbour node belongs to the group
                                for(int qq=0;qq+4<group.get(j).length();qq=qq+5){
                                    if(areNeighbours(group.get(j).substring(qq,qq+5),neighPlace)==(neighbour+2)%4){
                                        // Move into the group
                                        findGroup=j;
                                        group.set(j,group.get(j).concat(temp));
                                        // Set the connection direction
                                        node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                                        mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);
                                        j=group.size();
                                        break;
                                    }
                                }
                            }
                        }
                        // If the tile is not B2
                        else
                        {
                            //move into the group
                            findGroup=j;
                            group.set(j,group.get(j).concat(temp));
                            // Set the connection direction
                            node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                            mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);
                        }
                    }
                    // If findgroup is not equal to -1
                    else{
                        // If temp is B2
                        if(temp.charAt(0)=='B'&&temp.charAt(1)=='2'){
                            //move into the group
                            group.set(j,group.get(j).concat(temp));
                            // Set the connection direction
                            node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                            mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);
                        }else{
                            group.set(findGroup,group.get(findGroup)+group.get(j));
                            // Set the connection direction
                            node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                            mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);
                            group.remove(j);
                        }
                    }
                }
            }
            if(findGroup==-1){
                group.add(temp);
            }

            i=i+5;
            mapNode.put(temp, node);
        }
    }

    static int[][] map = new int[7][7];

    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     *
     * Set up the map.
     *
     * @param boardString A five-character board string
     */
    static void setMap(String boardString) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                map[i][j] = 0;
            }
        }
        int i = 0;
        while (i < boardString.length()) {
            map[boardString.charAt(i + 2) - 'A'][boardString.charAt(i + 3) - '0'] = 1;
            i = i + 5;
        }
    }

    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     *
     * Get valid place to put the tile
     * @return the valid position
     */
    static String getValidPlace() {

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (map[i][j] == 0) {
                    map[i][j] = -1;
                    char x=(char)('A'+i);
                    char y=(char)('0'+j);
                    return "" + x+ y;
                }
            }
        }
        return null;
    }

    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     *
     * Given a valid boardString and a dice roll for the round, return a String representing an ordered sequence of valid piece placements for the round.
     *
     * @param boardString a board string representing the current state of the game as at the start of the round
     * @param diceRoll a String representing a dice roll for the round
     * @return a String representing an ordered sequence of valid piece placements for the current round
     * @see RailroadInk#generateDiceRoll()
     */
    public static String generateMove(String boardString, String diceRoll) {
        // FIXME Task 10: compute the basic score
        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            setMap(boardString);
            String temp ="";
            String place=getValidPlace();
            flag=false;
            while (place != null) {
                for (int j = 0; j < 8; j++) {
                    char qqq=(char)('0'+j);
                    temp = diceRoll.substring(2 * i, 2 * i + 2) + place + qqq;
                    if (isValidPlacementSequence(boardString + temp) == true) {
                        flag = true;
                        break;
                    }
                }
                if(flag) break;
                place = getValidPlace();
            }
            boardString += temp;
        }
        return boardString;
    }

    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     *
     * Given the current state of a game board, output an integer representing the sum of all the factors contributing to `getBasicScore`, as well as those attributed to:
     * <p>
     * * Longest railroad * Longest highway
     *
     * @param boardString a board string representing a completed game
     * @return integer (positive or negative) for final score (not counting expansion packs)
     */
    public static int getAdvancedScore(String boardString) {
        // FIXME Task 12: compute the total score including bonus points
        int ans=getBasicScore(boardString);
        //分析groups的多个路线最长路径
        int longrailway=0;
        int longhighway=0;
        for(int i=0;i<group.size();i++)
        {
            int temp=getLongWay(0,i,0,2);
            System.out.println();
            if(temp>longrailway) longrailway=temp;
            temp=getLongWay(0,i,0,1);
            System.out.println();
            if(temp>longhighway)longhighway=temp;
        }
        return ans+longrailway+longhighway;
    }

    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     * @param ans
     * @param groupid
     * @param place
     * @param find
     * @return
     */
    public static int getLongWay(int ans,int groupid,int place,int find)
    {
         int ans1=0,ans2=0,ans3=0,ans4=0,flag=0;
        if(place+4>group.get(groupid).length())return 0;

        //top
        int temp=mapNode.get(group.get(groupid).substring(place,place+5)).direction[0];
        if(temp==1||temp==2)
        {
            if(temp==find)
            {
                int k=place+5;
                while(k<group.get(groupid).length())
                {
                    if(areNeighbours(group.get(groupid).substring(k,k+5),group.get(groupid).substring(place,place+5))==0
                            &&areConnectedNeighbours(group.get(groupid).substring(k,k+5),group.get(groupid).substring(place,place+5)))
                    {
                        flag=1;break;
                    }
                    k=k+5;
                }

                if(flag==1) {
                    ans1=1+getLongWay(ans,groupid,k,find);
                    // System.out.println(ans1 +" "+group.get(groupid).substring(place,place+5));
                }
            }
            else
            {
                ans1=0+getLongWay(0,groupid,place+5,find);
            }
        }
        //right
        temp=mapNode.get(group.get(groupid).substring(place,place+5)).direction[1];
        if(temp==1||temp==2)
        {
            if(temp==find)
            {
                int k=place+5;
                while(k+4<group.get(groupid).length())
                {
                    if(areNeighbours(group.get(groupid).substring(k,k+5),group.get(groupid).substring(place,place+5))==1
                            &&areConnectedNeighbours(group.get(groupid).substring(k,k+5),group.get(groupid).substring(place,place+5)))
                    {
                        flag=1;break;
                    }
                    k=k+5;
                }
                if(flag==1)
                {
                    ans2=1+getLongWay(ans,groupid,k,find);
                    // System.out.println(ans2 +" "+group.get(groupid).substring(place,place+5));
                }

            }
            else
            {
                ans2=0+getLongWay(0,groupid,place+5,find);
            }
        }
        //down
        temp=mapNode.get(group.get(groupid).substring(place,place+5)).direction[2];
        if(temp==1||temp==2)
        {
            if(temp==find)
            {
                int k=place+5;
                while(k<group.get(groupid).length())
                {
                    if(areNeighbours(group.get(groupid).substring(k,k+5),group.get(groupid).substring(place,place+5))==2
                            &&areConnectedNeighbours(group.get(groupid).substring(k,k+5),group.get(groupid).substring(place,place+5)))
                    {
                        flag=1;break;
                    }
                    k=k+5;
                }
                if(flag==1)
                {
                    ans3=1+getLongWay(ans,groupid,k,find);
                    //  System.out.println(ans3 +" "+group.get(groupid).substring(place,place+5));
                }
            }
            else
            {
                ans3=0+getLongWay(0,groupid,place+5,find);
            }
        }

        //left
        temp=mapNode.get(group.get(groupid).substring(place,place+5)).direction[3];
        if(temp==1||temp==2)
        {
            if(temp==find)
            {
                int k=place+5;
                while(k<group.get(groupid).length())
                {
                    if(areNeighbours(group.get(groupid).substring(k,k+5),group.get(groupid).substring(place,place+5))==3
                            &&areConnectedNeighbours(group.get(groupid).substring(k,k+5),group.get(groupid).substring(place,place+5)))
                    {
                        flag=1;break;
                    }
                    k=k+5;
                }
                if(flag==1)
                {
                    ans4=1+getLongWay(ans,groupid,k,find);
                    // System.out.println(ans4 +" "+group.get(groupid).substring(place,place+5));
                }
            }
            else
            {
                ans4=0+getLongWay(0,groupid,place+5,find);
            }
        }

        int max=ans1;
        if(ans2>max)max=ans2;
        if(ans3>max)max=ans3;
        if(ans4>max)max=ans4;

        return max;
    }

    public static void main(String[] args) {
        System.out.println(isValidPlacementSequence("A1A30A1B30A3D03B0D61A2C36A2D53A1C41B1B02A3C00A1D41B0D13A4C13S3D30A2C52B1A10A0A23A3C22A0B52S5D20B0B22A4E20A5F20A5F12A4G10A1B63A2E30B2F33S1G31"));
    }
}