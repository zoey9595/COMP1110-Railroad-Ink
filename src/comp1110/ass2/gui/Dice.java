package comp1110.ass2.gui;

public enum Dice {

    //all types of dices
    S0(true,true,true,true,1,1,1,0),
    S1(true,true,true,true,0,1,0,0),
    S2(true,true,true,true,1,1,1,1),
    S3(true,true,true,true,0,0,0,0),
    S4(true,true,true,true,1,1,0,0),
    S5(true,true,true,true,0,1,0,1),
    A0(true,true,false,false,0,0,-1,-1),
    A1(false,true,false,true,-1,0,-1,0),
    A2(false,true,true,true,-1,0,0,0),
    A3(false,true,true,true,-1,1,1,1),
    A4(false,true,false,true,-1,1,-1,1),
    A5(true,true,false,false,1,1,-1,-1),
    B0(false,true,false,true,-1,1,-1,0),
    B1(false,true,true,false,-1,1,0,-1),
    B2(true,true,true,true,0,1,0,1);

    //whether a direction has a way out
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;

    //if it has a way out, which type is it.
    private int leftType;
    private int upType;
    private int rightType;
    private int downType;

    Dice(boolean left, boolean up, boolean right, boolean down, int leftType, int upType, int rightType, int downType){
        this.left = left;
        this.up = up;
        this.right = right;
        this.down = down;
        this.leftType = leftType;
        this.upType = upType;
        this.rightType = rightType;
        this.downType = downType;
    }

    public String rotate90Degree(){
        return null;
    }

    public String mirroryAxis(){
        return null;
    }
}
