/*
 * Abstract TetrisBrick superclass for all other brick subclasses
 * Hafsah Khan Caitlyn Dionne 
 * 11.9.21
 */


public abstract class TetrisBrick {
    
    protected int[][] position = new int[4][2];
    protected int orientation;
    protected int numSegments = 4;
    protected int colorNum;
    
    protected int minRow = 0;
    protected int maxRow = 0; 
    
    public TetrisBrick()
    {
        
    }
    
    public void moveDown()
    {
        for (int seg = 0; seg < numSegments; seg++)
        {
            position[seg][1]++;
        }
    }
    
    public void moveUp()
    {
        for (int seg = 0; seg < numSegments; seg++)
        {
            position[seg][1]--;
        } 
    }
    
    public void moveLeft()
    {
        for (int seg = 0; seg < numSegments; seg++)
        {
            position[seg][0]--;
        } 
    }
    
    public void moveRight()
    {
        for (int seg = 0; seg < numSegments; seg++)
        {
            position[seg][0]++;
        } 
    }
    
    public int getColorNumber()
    {
        return colorNum;
    }

    public int getPosition(int row, int col)
    {
        return position[row][col];
    }
    
    public int getNumSeg()
    {
        return numSegments;
    }
    
    public int getMinRow()
    {
        minRow = position[0][1];
        
        for (int seg = 0; seg < numSegments; seg++)
        {
            if (position[seg][1] < minRow)
            {
                minRow = position[seg][1];
            }
        }
        
        return minRow;
    }
    
    public int getMaxRow()
    {
        maxRow = position[0][1];
        
        for (int seg = 0; seg < numSegments; seg++)
        {
            if (position[seg][1] > maxRow)
            {
                maxRow = position[seg][1];
            }
        }
        
        return maxRow;
    }
    
    public abstract void initPosition();
    
    public abstract void rotate();
    
    public abstract void unrotate();
}
