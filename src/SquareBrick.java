/*
 * One of the subclasses to TetrisBrick: SquareBrick
 * Hafsah Khan Caitlyn Dionne 
 * 11.9.21
 */

public class SquareBrick extends TetrisBrick {
    
    public SquareBrick()
    {
        colorNum = 8;
    }
    
    public void initPosition()
    {
        //segment 0:
        position[0][0] = 5;
        position[0][1] = 0;
        
        //segment 1:
        position[1][0] = 5;
        position[1][1] = 1;
        
        //segment 2:
        position[2][0] = 6;
        position[2][1] = 0;
        
        //segment 3:
        position[3][0] = 6;
        position[3][1] = 1;
    }
    
    public void rotate()
    {
        
    }
    
    public void unrotate()
    {
        
    }
}
