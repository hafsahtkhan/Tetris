/*
 * One of the subclasses to TetrisBrick: EssBrick
 * Hafsah Khan Caitlyn Dionne
 * 11.9.21
 */


public class EssBrick extends TetrisBrick {
    
    public EssBrick()
    {
        colorNum = 4;
    }
    
    public void initPosition()
    {
         //segment 0:
        position[0][0] = 4;
        position[0][1] = 1;
        
        //segment 1:
        position[1][0] = 5;
        position[1][1] = 1;
        
        //segment 2:
        position[2][0] = 5;
        position[2][1] = 0;
        
        //segment 3:
        position[3][0] = 6;
        position[3][1] = 0;
    }
    
    public void rotate()
    {
        if (orientation == 0)
        {
            position[0][0]++;
            position[0][1]--;

            position[2][0]++;
            position[2][1]++;

            position[3][1] += 2;

            orientation = 1;  
        }
        else if (orientation == 1)
        {
            position[0][0]--;
            position[0][1]++;

            position[2][0]--;
            position[2][1]--;

            position[3][1] -= 2;

            orientation = 0;
        }
    }
    
    public void unrotate()
    {
        rotate();
    }
}
