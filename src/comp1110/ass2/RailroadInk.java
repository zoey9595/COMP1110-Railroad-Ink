package comp1110.ass2;

public class RailroadInk {
    /**
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
        if(count >= 3){
            return false;
        }else{
            return true;
        }
        // FIXME Task 3: determine whether a board string is well-formed
    }

    /*
     * Check whether two tile placements are in the same row or column
     */
    public static boolean areNeighbours(String tilePlacementStringA, String tilePlacementStringB)
    {
        // whether they are in the same column
        if(((tilePlacementStringA.charAt(2)-tilePlacementStringB.charAt(2)==1)
                ||(tilePlacementStringA.charAt(2)-tilePlacementStringB.charAt(2)==-1))
                &&(tilePlacementStringA.charAt(3)==tilePlacementStringB.charAt(3)))
            return true;
        // whether they are in the same row
        if(((tilePlacementStringA.charAt(3)-tilePlacementStringB.charAt(3)==1)
                ||(tilePlacementStringA.charAt(3)-tilePlacementStringB.charAt(3)==-1))
                &&(tilePlacementStringA.charAt(2)==tilePlacementStringB.charAt(2)))
            return true;

        return false;
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
    public static boolean areConnected(String tilePlacementStringA, String tilePlacementStringB,int indexA,int indexB)
    {
        int valueA=getvalue(tilePlacementStringA,tilePlacementStringA.charAt(4)-'0',indexA);
        int valueB=getvalue(tilePlacementStringB,tilePlacementStringB.charAt(4)-'0',indexB);
        // whether the connection exists, and the connection type is the same one
        if(valueA==0||valueB==0)return false;
        if(valueA==valueB) return true;

        return false;
    }


    /*
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
        if(!areNeighbours(tilePlacementStringA,tilePlacementStringB))return false;

        // If they are neighbours, whether they can successfully connect.
        if(tilePlacementStringA.charAt(2)-tilePlacementStringB.charAt(2)==1) {
            if(areConnected(tilePlacementStringA,tilePlacementStringB,0,2))
                return true;
        }

        if(tilePlacementStringA.charAt(2)-tilePlacementStringB.charAt(2)==-1) {
            if(areConnected(tilePlacementStringA,tilePlacementStringB,2,0))
                return true;
        }

        if(tilePlacementStringA.charAt(3)-tilePlacementStringB.charAt(3)==1) {
            if(areConnected(tilePlacementStringA,tilePlacementStringB,3,1))
                return true;
        }

        if(tilePlacementStringA.charAt(3)-tilePlacementStringB.charAt(3)==-1) {
            if(areConnected(tilePlacementStringA,tilePlacementStringB,1,3))
                return true;
        }
        // FIXME Task 5: determine whether neighbouring placements are connected
        return false;
    }


    // Check whether a placement is connect to an exit
    public static boolean connectToAnExit(String tilePlacementString)
    {
        int exits=0;

        // left 3 exits
        if((tilePlacementString.charAt(2)=='B'&&tilePlacementString.charAt(3)=='0')
                ||(tilePlacementString.charAt(2)=='F'&&tilePlacementString.charAt(3)=='0'))
        {
            if(getvalue(tilePlacementString,tilePlacementString.charAt(4)-'0',3)==2)
                exits++;
        }
        if(tilePlacementString.charAt(2)=='D'&&tilePlacementString.charAt(3)=='0')
        {
            if(getvalue(tilePlacementString,tilePlacementString.charAt(4)-'0',3)==1)
                exits++;
        }
        //top 3 exits
        if((tilePlacementString.charAt(2)=='A'&&tilePlacementString.charAt(3)=='1')
                ||(tilePlacementString.charAt(2)=='A'&&tilePlacementString.charAt(3)=='5'))
        {
            if(getvalue(tilePlacementString,tilePlacementString.charAt(4)-'0',0)==1)
                exits++;
        }
        if(tilePlacementString.charAt(2)=='A'&&tilePlacementString.charAt(3)=='3')
        {
            if(getvalue(tilePlacementString,tilePlacementString.charAt(4)-'0',0)==2)
                exits++;
        }
        //right 3 exits
        if((tilePlacementString.charAt(2)=='B'&&tilePlacementString.charAt(3)=='6')
                ||(tilePlacementString.charAt(2)=='F'&&tilePlacementString.charAt(3)=='6'))
        {
            if(getvalue(tilePlacementString,tilePlacementString.charAt(4)-'0',1)==2)
                exits++;
        }
        if(tilePlacementString.charAt(2)=='D'&&tilePlacementString.charAt(3)=='6')
        {
            if(getvalue(tilePlacementString,tilePlacementString.charAt(4)-'0',1)==1)
                exits++;
        }
        //down 3 exits
        if((tilePlacementString.charAt(2)=='G'&&tilePlacementString.charAt(3)=='1')
                ||(tilePlacementString.charAt(2)=='G'&&tilePlacementString.charAt(3)=='5'))
        {
            if(getvalue(tilePlacementString,tilePlacementString.charAt(4)-'0',2)==1)
                exits++;
        }
        if(tilePlacementString.charAt(2)=='G'&&tilePlacementString.charAt(3)=='3')
        {
            if(getvalue(tilePlacementString,tilePlacementString.charAt(4)-'0',2)==2)
                exits++;
        }

        if(exits==0){
            return false;
        }

        return true;
    }

    /**
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

        int i = 0;
        loop:
        while (i < boardString.length()) {

            /*if (!isTilePlacementWellFormed(boardString.substring(i,i+5))){
                return false;
            }*/

            // The first String must connect to an exit
            if(!connectToAnExit(boardString.substring(0,5))) return false;

            // if it connect to an exit, then continue loop
            if(connectToAnExit(boardString.substring(i,i+5)))
            {
                i=i+5;
                if(i == boardString.length()){
                    return true;
                }
                continue;
            }

            // Check whether the tile can connect with any other tile before
            int j=0;
            while(j<i) {
                if(areConnectedNeighbours(boardString.substring(j,j+5),boardString.substring(i,i+5))){
                    i=i+5;
                    if(i == boardString.length()){
                        return true;
                    }
                    continue loop;
                }
                j=j+5;
            }
            // A tile is invalid if neither connect to an exit nor a tile
            return false;
        }
        return true;
        // FIXME Task 6: determine whether the given placement sequence is valid
    }

    /**
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
        // FIXME Task 7: generate a dice roll
        return "";
    }

    /**
     * Given the current state of a game board, output an integer representing the sum of all the following factors
     * that contribute to the player's final score.
     * <p>
     * * Number of exits mapped
     * * Number of centre tiles used
     * * Number of dead ends in the network
     *
     * @param boardString a board string representing a completed game
     * @return integer (positive or negative) for score *not* considering longest rail/highway
     */
    public static int getBasicScore(String boardString) {
        // FIXME Task 8: compute the basic score
        return -1;
    }

    /**
     * Given a valid boardString and a dice roll for the round,
     * return a String representing an ordered sequence of valid piece placements for the round.
     * @param boardString a board string representing the current state of the game as at the start of the round
     * @param diceRoll a String representing a dice roll for the round
     * @return a String representing an ordered sequence of valid piece placements for the current round
     * @see RailroadInk#generateDiceRoll()
     */
    public static String generateMove(String boardString, String diceRoll) {
        // FIXME Task 10: generate a valid move
        return null;
    }

    /**
     * Given the current state of a game board, output an integer representing the sum of all the factors contributing
     * to `getBasicScore`, as well as those attributed to:
     * <p>
     * * Longest railroad
     * * Longest highway
     *
     * @param boardString a board string representing a completed game
     * @return integer (positive or negative) for final score (not counting expansion packs)
     */
    public static int getAdvancedScore(String boardString) {
        // FIXME Task 12: compute the total score including bonus points
        return -1;
    }
}

