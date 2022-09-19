/*
 * One of the subclasses to TetrisBrick: JayBrick
 * Hafsah Khan Caitlyn Dionne 
 * 11.9.21
 */


public class JayBrick extends TetrisBrick {
    
    public JayBrick()
    {
        colorNum = 3;
    }
    
    public void initPosition()
    {
        //segment 0:
        position[0][0] = 6;
        position[0][1] = 0;
        
        //segment 1:
        position[1][0] = 6;
        position[1][1] = 1;
        
        //segment 2:
        position[2][0] = 6;
        position[2][1] = 2;
        
        //segment 3:
        position[3][0] = 5;
        position[3][1] = 2;
    }
    
    public void rotate()
    {
        if (orientation == 0)
        {
            //segment 0:
            position[0][0] += 2;
            position[0][1] += 2;
        
            //segment 1:
            position[1][0]++;
            position[1][1]++;
        
            //segment 3:
            position[3][0]++;
            position[3][1]--;
            
            orientation = 1;
        }
        
        else if (orientation == 1)
        {
            //segment 0:
            position[0][0] -= 2;
            position[0][1] += 2;
        
            //segment 1:
            position[1][0]--;
            position[1][1]++;
        
            //segment 3:
            position[3][0]++;
            position[3][1]++;
            
            orientation = 2;
        }
        
        else if (orientation == 2)
        {
            //segment 0:
            position[0][0] -= 2;
            position[0][1] -= 2;
        
            //segment 1:
            position[1][0]--;
            position[1][1]--;
        
            //segment 3:
            position[3][0]--;
            position[3][1]++;
            
            orientation = 3;
        }
        
        else if (orientation == 3)
        {
            //segment 0:
            position[0][0] += 2;
            position[0][1] -= 2;
        
            //segment 1:
            position[1][0]++;
            position[1][1]--;
        
            //segment 3:
            position[3][0]--;
            position[3][1]--;
            
            orientation = 0;
        }
    }
    
    public void unrotate()
    {
        for (int orien = 0; orien < 3; orien++)
        {
            rotate();
        }
    }
}

