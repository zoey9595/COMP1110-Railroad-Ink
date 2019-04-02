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

        if(tilePlacementString.length()/5 !=1 || tilePlacementString.length()%5 !=0){
            return false;
        }

        if(tilePlacementString.charAt(0) != 'A' && tilePlacementString.charAt(0) != 'B' && tilePlacementString.charAt(0) != 'S'){
            return false;
        }

        if(tilePlacementString.charAt(0) == 'A' && tilePlacementString.charAt(1)>'5'){
            return false;
        }

        if(tilePlacementString.charAt(0) == 'S' && tilePlacementString.charAt(1)>'5'){
            return false;
        }

        if(tilePlacementString.charAt(0) == 'B' && tilePlacementString.charAt(1)>'2'){
            return false;
        }

        if(tilePlacementString.charAt(2) > 'G' || tilePlacementString.charAt(2) < 'A'){
            return false;
        }

        if(!(tilePlacementString.charAt(3) == '0' || tilePlacementString.charAt(3) == '1'||tilePlacementString.charAt(3) == '2'||tilePlacementString.charAt(3) == '3'||tilePlacementString.charAt(3) == '4'||tilePlacementString.charAt(3) == '5'||tilePlacementString.charAt(3) == '6')){
            return false;
        }

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

        if(boardString == null || boardString == ""){
            return false;
        }

        if(boardString.length()%5 != 0) {
            return false;
        }else if(boardString.length()/5 > 31){
            return false;
        }

        int count = 0;
        for(int i=0; i<boardString.length(); i+=5){
            String newString = boardString.substring(i,i+4);
            char margin = boardString.charAt(i+4);
            newString = newString + margin;
            if(!isTilePlacementWellFormed(newString)){
                return false;
            }
            if(newString.charAt(0) == 'S'){
                count++;
            }
        }

        if(count >= 3){
            return false;
        }else{
            return true;
        }
        // FIXME Task 3: determine whether a board string is well-formed
    }


    /**
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

        /* if two tiles are not neighbours then they are invalid;
         * so if they are in the same rol, the difference between two column numbers should be exactly 1;
         * if they are in the same column, the difference between two row numbers should also be exactly 1;
         * if not, return false.
         */
        if(!((tilePlacementStringA.charAt(2) == tilePlacementStringA.charAt(2) && Math.abs(tilePlacementStringA.charAt(3) - tilePlacementStringB.charAt(3)) == 1)||(tilePlacementStringA.charAt(3) == tilePlacementStringA.charAt(3) && Math.abs((int)tilePlacementStringA.charAt(2) - (int)tilePlacementStringB.charAt(2)) == 1))) {
            return false;
        }
        String tileAType = tilePlacementStringA.substring(0,3);
        String tileBType = tilePlacementStringB.substring(0,3);
        //if(tileAType == "S2"|| tileAType == "A3"|| tileAType == "A4" || tileAType == "A5")&&(tileBType == "S3"|| tileBType == "A0"|| tileBType == "A1" || tileBType == "A2")
        // FIXME Task 5: determine whether neighbouring placements are connected
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
        // FIXME Task 6: determine whether the given placement sequence is valid
        return false;
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

