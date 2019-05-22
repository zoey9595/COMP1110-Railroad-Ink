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
        if(count >= 3){
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
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 0) == 1) {
                exits=0;
            }
        }
        if (tilePlacementString.charAt(2) == 'A' && tilePlacementString.charAt(3) == '3') {
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 0) == 2) {
                exits=0;
            }
        }

        //right 3 exits
        if ((tilePlacementString.charAt(2) == 'B' && tilePlacementString.charAt(3) == '6')
                || (tilePlacementString.charAt(2) == 'F' && tilePlacementString.charAt(3) == '6')) {
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 1) == 2) {
                exits=1;
            }
        }
        if (tilePlacementString.charAt(2) == 'D' && tilePlacementString.charAt(3) == '6') {
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 1) == 1) {
                exits=1;
            }
        }

        //down 3 exits
        if ((tilePlacementString.charAt(2) == 'G' && tilePlacementString.charAt(3) == '1')
                || (tilePlacementString.charAt(2) == 'G' && tilePlacementString.charAt(3) == '5')) {
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 2) == 1) {
                exits=2;
            }
        }
        if (tilePlacementString.charAt(2) == 'G' && tilePlacementString.charAt(3) == '3') {
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 2) == 2) {
                exits=2;
            }
        }

        // left 3 exits
        if ((tilePlacementString.charAt(2) == 'B' && tilePlacementString.charAt(3) == '0')
                || (tilePlacementString.charAt(2) == 'F' && tilePlacementString.charAt(3) == '0')) {
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 3) == 2) {
                exits=3;
            }
        }
        if (tilePlacementString.charAt(2) == 'D' && tilePlacementString.charAt(3) == '0') {
            if (getvalue(tilePlacementString, tilePlacementString.charAt(4) - '0', 3) == 1) {
                exits=3;
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
                    }
                }
                j=j+5;
                if(j==i){
                    i=i+5;
                    // If the tile is all checked, return true
                    if (i == boardString.length()) {
                        return true;
                    }
                    continue loop;
                }
            }
            // A tile is invalid if neither connect to an exit nor a tile
            return false;
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
     * @param boardString
     * @return
     */
    public static int getBasicScore(String boardString) {
        // FIXME Task 8: compute the basic score
        getGroups(boardString);

        int ans = 0;
        // Number of Exits connected to route | 2 | 3 | 4  | 5 |  6 |  7 |  8 |  9 |  10 | 11 | 12 |
        // Points Awarded                     | 4 | 8 | 12 | 16 | 20 | 24 | 28 | 32 | 36 | 40 | 45 |
        for(int i=0;i<group.size();i++)
        {
            int exits=0;
            int error=0;
            for(int j=0;j+4<group.get(i).length();j=j+5)
            {
                String temp=group.get(i).substring(j,j+5);
                for(int k=0;k<4;k++)
                {
                    if(mapNode.get(temp).direction[k]==-1) exits++;
                    if(mapNode.get(temp).direction[k]==-2) error++;

                }
            }
            if(exits==2)ans+=4;
            if(exits==3)ans+=8;
            if(exits==4)ans+=12;
            if(exits==5)ans+=16;
            if(exits==6)ans+=20;
            if(exits==7)ans+=24;
            if(exits==8)ans+=28;
            if(exits==9)ans+=32;
            if(exits==10)ans+=36;
            if(exits==11)ans+=40;
            if(exits==12)ans+=45;
            ans-=error;
        }
        //centre grid that are covered
        ans+=getCentreGrids(boardString);

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

    // several groups
    static ArrayList<String> group=new ArrayList<String>();
    //exists of every groups
    //static int[] exits=new int[64];
    static HashMap<String, Node> mapNode = new HashMap<String, Node>();

    static class Node
    {
        int[] direction=new int[4];
        public Node()
        {
            for(int i=0;i<4;i++)
                direction[i]=-2;
        }
    }
    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     *
     * All nodes are grouped according to whether or not they form a route
     */
    static void getGroups(String boardString)
    {
        mapNode.clear();
        group.clear();
        int i=0;
        //for(i=0;i<64;i++)
        //{
        //    exits[i]=0;
        //}
        i=0;
        while(i+4<boardString.length())
        {
            Node  node=new Node();
            String temp=boardString.substring(i,i+5);
            for(int j=0;j<4;j++)
            {
                node.direction[j]=-2;
                if(getvalue(temp,temp.charAt(4)-'0',j)==0)node.direction[j]=0;
            }
            if(temp.charAt(2)=='A')node.direction[0]=0;
            if(temp.charAt(3)=='6')node.direction[1]=0;
            if(temp.charAt(2)=='G')node.direction[2]=0;
            if(temp.charAt(3)=='0')node.direction[3]=0;
            int find=0;//是否找到相邻的组wether find the neighbour group
            int findGroup=-1;//找到的第一个组find the first group
            int nodeExits=-1;//是否连接出口wehter conect exist
            int neighbour=-1;//在已存在节点的哪个方向which direction of the node
            nodeExits=connectToAnExit(temp);
            if(nodeExits>=0)node.direction[nodeExits]=-1;
            for(int j=0;j<group.size();j++)
            {
                find=0;
                String neighPlace="";
                for(int k=0;k+4<group.get(j).length();k=k+5)
                {
                    neighbour=areNeighbours(temp, group.get(j).substring(k,k+5));
                    if(neighbour>=0&&areConnectedNeighbours(temp,group.get(j).substring(k,k+5)))
                    {
                        neighPlace= group.get(j).substring(k,k+5);
                        find=1;break;
                    }
                }
                if(find==1)
                {
                    if(findGroup==-1)
                    {
                        //邻近B2放入，put in Near B2 
                        if(neighPlace.charAt(0)=='B'&&neighPlace.charAt(1)=='2')
                        {
                            //判断一个路径上是否存在已有节点，如果没有新建groupDetermines whether an existing node exists on a path, if no new group is created
                            if(mapNode.get(neighPlace).direction[(neighbour+2)%4]==-2)
                            {
                                findGroup=j;
                                group.add(temp+neighPlace);
                                //设置两个节点四周连接状态Sets the connection state around two nodes
                                node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                                mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);
                                j++;
                            }
                            else
                            {
                                //判断与B2相邻的节点是否属于改组Determine whether the node adjacent to B2 belongs to reorganization
                                for(int qq=0;qq+4<group.get(j).length();qq=qq+5)
                                {
                                    if(areNeighbours(group.get(j).substring(qq,qq+5),neighPlace)==(neighbour+2)%4)
                                    {
                                        //移入该组move to this group
                                        findGroup=j;
                                        group.set(j,group.get(j).concat(temp));
                                        //设置两个节点四周连接状态Sets the connection state around two nodes
                                        node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                                        mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);
                                        j=group.size();
                                        break;
                                    }
                                }
                            }
                        }
                        else
                        {
                            //移入该组move to this group 
                            findGroup=j;
                            group.set(j,group.get(j).concat(temp));
                            //设置两个节点四周连接状态Sets the connection state around two nodes
                            node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                            mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);
                            //exits[j]+=nodeExits;
                        }
                    }
                    else
                    {
                        //B2 放入，各个组都需要添加put in b2 for everyfroup
                        if(temp.charAt(0)=='B'&&temp.charAt(1)=='2')
                        {
                            //移入该组
                            group.set(j,group.get(j).concat(temp));
                            //设置两个节点四周连接状态Sets the connection state around two nodes
                            node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                            mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);
                        }
                        else
                        {
                            // exits[findGroup]+=nodeExits;
                            group.set(findGroup,group.get(findGroup)+group.get(j));
                            // for(int p=j;p<groupLength-1;p++)
                            {
                                //      exits[p]=exits[p+1];
                            }
                            //设置两个节点四周连接状态Sets the connection state around two nodes
                            node.direction[(neighbour+2)%4]=getvalue(temp,temp.charAt(4)-'0',(neighbour+2)%4);
                            mapNode.get(neighPlace).direction[neighbour]=getvalue(neighPlace,neighPlace.charAt(4)-'0',neighbour);

                            group.remove(j);
                        }
                    }
                }
            }
            if(findGroup==-1)
            {
                group.add(temp);
            }
            i=i+5;

            // for(int q=0;q<group.size();q++)
            // {
            //     System.out.println("group:"+group.get(q));
            // }
            //  System.out.println();


            mapNode.put(temp, node);
        }
    }

    static int[][] map = new int[7][7];

    /**
     * Author: Huhan Jiang, Yuqing Zhai, Yufan Zhou
     *
     * 设置地图set map
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
     * @return
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
                        flag=true;break;
                    }
                }
                if(flag==true)break;
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
        //分析groups的多个路线最长路径Analyze the longest paths of multiple paths of groups
        int longrailway=0;
        int longhighway=0;
        for(int i=0;i<group.size();i++)
        {
            int temp=getLongWay(0,i,0,2);
            // System.out.println();
            if(temp>longrailway) longrailway=temp;
            temp=getLongWay(0,i,0,1);
            //    System.out.println();
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
        if(place>=group.get(groupid).length())return 1;

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
    }}