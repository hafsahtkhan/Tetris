/*
 * Game file for Tetris, responsible for logic
 * Hafsah Khan Caitlyn Dionne 
 * 11.28.21
 */

import java.util.*;
import javax.swing.*;
import java.io.*;

public class TetrisGame {
    
    private TetrisBrick fallingBrick;
    private int[][] background;
    private int state;
    private Random randGen = new Random();
    
    private int numOfBricks = 7;
    
    private int rows;
    private int cols;
    
    private int score;
    private int maxScores = 10;
    
    private ArrayList<Integer> scoreList = new ArrayList<>();
    
    private String fileName = "HighScores.dat";
    private File checkScoresFile = new File (fileName);
    
    public TetrisGame(int rows, int cols)
    {
        background = new int [rows][cols];
        spawnBrick();
        if(checkScoresFile.exists())
        {
            getScoresFromFile();
            noScoreFile();
        }
  
    }
    
    public void noScoreFile()
    {
        scoreList.add(0);
    }

    public void clearAllScores()
    {
        for (int sc = 0; sc < scoreList.size(); sc++)
        {
            scoreList.set(sc,0);
        }
        score = 0;
        saveScoreToFile(fileName);
    }
    
    public int getScoresAmount()
    {
        return scoreList.size();
    }
    
    public int getAHighScore(int index)
    {
        return scoreList.get(index);
    }
    
    public void getScoresFromFile()
    {
        
        File inFile = new File(fileName);
        if (!inFile.exists())
        {
            String error = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"+
                           "Can not see file: "+fileName+"\n"+
                           "Check the spelling of the file"+
                           "This program is ending\n"+
                           "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
            System.err.print(error);
            System.exit(0);
        }
        try
        {
            Scanner fileScan = new Scanner(inFile);
    
            while(fileScan.hasNext())
            {
                scoreList.add(fileScan.nextInt());
            }
            fileScan.close();
        }
        catch(IOException ioe)
        {
             String error = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"+
                           "Trouble reading data from: "+fileName+"\n"+
                           "This program is ending\n"+
                           "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
            System.err.print(error);
            System.exit(0);
        }
    }
    
    public void sortScores()
    {
        scoreList.add(score);
        
        int tempScore = 0;
        for (int num = 0; num < scoreList.size(); num++)
        {
            for (int num2 = num+1; num2 < scoreList.size(); num2++)
            {
                int score1 = scoreList.get(num);
                int score2 = scoreList.get(num2);

                if (score1 < score2)
                {
                    tempScore = score1;
                    scoreList.set(num, score2);
                    scoreList.set(num2, tempScore);

                }
            }
        }
        
        if (scoreList.size() > 10)
        {
            for (int delScore = 10; delScore < scoreList.size(); delScore++)
            {
                scoreList.remove(delScore);
            }
        }
    }
    
    public String getHighScores()
    {
        
        String highScores = "";
        for (int i = 0; i < scoreList.size(); i++)
        {
            highScores += scoreList.get(i)+"\n";
        }
        return highScores;
    }
    
    public void saveScoreToFile(String file)
    {
        sortScores();
        
        String fileName = file;
        File outFile = new File(fileName);

        try
        {
            FileWriter outWriter = new FileWriter(outFile, false);
            outWriter.write(getHighScores());
            outWriter.close();
        }
        catch (IOException ioe)
        {
            String error = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"+
                           "Trouble writing data to: "+fileName+"\n"+
                           "This program is ending\n"+
                           "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
            System.err.print(error);
            System.exit(0);
        }
    }
    public void resetScore()
    {
        score = 0;
    }
    public int getScore()
    {
        return score;
    }
    public boolean rowHasSpace(int rowNum)
    {
        for (int col = 0; col < background[0].length; col++)
        {
            if (background[rowNum][col] == 0)
                return true;
        }
        return false;
    }
    
    public boolean rowHasColor(int rowNum)
    {
        for (int col = 0; col < background[0].length; col++)
        {
            if (background[rowNum][col] > 0)
                return true;
        }
        return false;
    }
    
    public void copyRow(int rowNum)
    {
        for (int col = 0; col < background[0].length; col++)
        {
            background[rowNum][col] = background[rowNum-1][col];
        }
        
    }
    
    public void copyAllRows(int rowNum)
    {
        for (int row = rowNum; row > 0; row--)
        {
            if (rowHasColor(row))
            {
                copyRow(row);
            }
        }
    }
    
    public void deleteRow()
    {
        int maxRow = fallingBrick.getMaxRow();
        int minRow = fallingBrick.getMinRow();
        
        int range = maxRow - minRow + 1;
        
        int lines = 0;
        
        for (int row = maxRow; row >= minRow; row--)
        {
            if (!rowHasSpace(row))
            {
                copyAllRows(row);
                row++;
                minRow--;
                lines++;
            }
        }
        
        if (lines == 1)
            score += 100;
        else if (lines == 2)
            score += 300;
        else if (lines == 3)
            score += 600;
        else if (lines == 4)
            score += 1000;
    }
    
    public String toString()
    {
        String stuff = ""+background.length+" "+background[0].length+"\n";
        for (int row = 0; row < background.length; row++)
        {
            for (int col = 0; col < background[0].length ; col++)
            {
                stuff += ""+background[row][col]+" ";
            }
            stuff += "\n";
        }
        stuff = stuff.substring(0, stuff.length()-1);
        return stuff;
    }
    
    public void retrieveFromFile(String gameName)
    {
        initBoard();
        File fileConnection = new File(""+gameName+".dat");
        try
        {
            Scanner inScan = new Scanner(fileConnection);
            rows = inScan.nextInt();
            cols = inScan.nextInt();
            
            background = new int[rows][cols];
            
            for(int row = 0; row < rows; row++)
            {
                for (int col = 0; col < cols; col++)
                {
                    background[row][col] = inScan.nextInt();
                }
            }
        }
        catch (Exception e)
        {
            System.err.print("error occurred during retrieve from file");
        }
        spawnBrick();
    }
    
    public String saveToFile()
    {
        String fileName = JOptionPane.showInputDialog(null, "Enter name of file: ", 
                "save to file", 1);
        File fileConnection = new File (""+fileName+".dat");
        try
        {
            FileWriter outWriter = new FileWriter(fileConnection);
            outWriter.write(this.toString());
            outWriter.close();
        }
        catch (IOException ioe)
        {
            System.err.println("   error save to file ");
        }
        return fileName;
    }
    
    public void initBoard()
    {
        for (int row = 0; row < background.length; row++)
        {   
            for (int col = 0; col < background[0].length; col++)
            {
                background[row][col] = 0;
            }
        }
    }
    
    public void newGame()
    {
        initBoard();
        spawnBrick();
        score = 0;
    }
    
    private void transferColor()
    {
        for (int seg = 0; seg < fallingBrick.getNumSeg(); seg++)
        {
            int col = getBrickPosition(seg, 0);
            int row = getBrickPosition(seg, 1)  ;
            
            background[row][col] = fallingBrick.getColorNumber();
        }
    }
    
    private void spawnBrick()
    {
        int randBrick = randGen.nextInt(numOfBricks);
        
        switch(randBrick){
            case 0:
              fallingBrick = new StackBrick();
              break;
            case 1:
              fallingBrick = new ElBrick();
              break;
            case 2:
              fallingBrick = new EssBrick();
              break;
            case 3:
              fallingBrick = new JayBrick();
              break;
            case 4:
             fallingBrick = new LongBrick();
              break;
            case 5:
              fallingBrick = new SquareBrick();
              break;
            case 6:
              fallingBrick = new ZeeBrick();
              break;
        }
        fallingBrick.initPosition();
    }
    
    public boolean boardFull()
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < background[0].length; col++)
            {
                if (background[0][col]!= 0)
                    return true;
            }
        }
        return false;
    }
    
    
    public void makeMove(String moveCode)
    {
        String moveDirection = moveCode;
        switch(moveDirection)
        {
            case "D":
                fallingBrick.moveDown();
                if (!validateMove())
                {
                    fallingBrick.moveUp();
                    transferColor();
                    deleteRow();
                    if (!boardFull())
                        spawnBrick();
                    break;
                }
                break;
                
            case "R":
                fallingBrick.moveRight();
                if(!validateMove())
                    fallingBrick.moveLeft();
                break;
                
            case "L":
                fallingBrick.moveLeft();
                if(!validateMove())
                    fallingBrick.moveRight();
                break;
                
            case "F":
                fallingBrick.rotate();
                if(!validateMove())
                    fallingBrick.unrotate();
                break;
        }
    }
    
    private boolean validateMove()
    {
        for (int seg = 0; seg < fallingBrick.getNumSeg(); seg++)
        {
            int row = fallingBrick.getPosition(seg, 1) ;
            int col = fallingBrick.getPosition(seg, 0);
            if (row  > background.length - 1 || col < 0 || col  > background[0].length - 1 )
            {
                return false;
            }
            
            else if (background[row][col] > 0)
            {
                return false;
            }
        }
        return true;
    }
    
    public int fetchBoardPosition(int rows, int cols)
    {
        return background[rows][cols];
    }
    
    public int getRows() 
    {
        return background.length;
    }

    public int getCols() 
    {
        return background[0].length;
    }
    
    public int getBrickPosition(int row, int col)
    {
        return fallingBrick.getPosition(row, col);
    }
    
    public int getBlockColor()
    {
        return fallingBrick.getColorNumber();
    }
    
}
    
