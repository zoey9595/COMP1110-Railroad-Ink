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
    
    Author: Jiang Huhan
    
Review:
  The best feature of this code is it is really readable and clearly determined 
  whether the provided placements are neighbours connected by at least one 
  validly connecting edge. The code is well-documented. The structure is logical.
  It does follow Java code conventions and the style is consistent throughout so 
  it is easy to understand. I do not find errors in these codes so I think it is 
  perfect I think.