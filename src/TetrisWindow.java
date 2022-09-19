/*
 * Display file for Tetris, responsible for showing the status of the game
 * Hafsah Khan Caitlyn Dionne
 * 11.28.21
 */

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*; 
import java.io.*;

public class TetrisWindow extends JFrame{
    private TetrisGame game;
    private TetrisDisplay display;
    private int win_width = 400;
    private int win_height = 400;
    
    private int rows = 20;
    private int cols = 12;
    
    private int maxScores = 10; 
    
    private ArrayList<String> gameList = new ArrayList<>();
    private ArrayList<Integer> scoreList = new ArrayList<>();
    
    private String scoreFile = "HighScores.dat";
    private String nameFile = "PrevGames.dat";
    
    public TetrisWindow()
    {
        this.setTitle("Tetris project                Hafsah K., Caitlyn D.");
        this.setSize(win_width, win_height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game = new TetrisGame(rows, cols);
        display = new TetrisDisplay(game);
        
        initMenus();
        this.add(display);
        
        this.setVisible(true);

    }
    
    public void initMenus()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu GameOp = new JMenu("Game Options");

        menuBar.add(GameOp);
        
        JMenuItem saveOp = new JMenuItem("Save game");
        JMenu loadMenu = new JMenu("Retrieve game");
        JMenuItem newGame = new JMenuItem("New Game");
        
        File checkfile = new File(nameFile);
        if (checkfile.exists())
            getNamesFromFile();
        
        for (int names = 0; names < gameList.size(); names++)
        {
            String name = gameList.get(names);
            JMenuItem item1 = new JMenuItem(name);
                item1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ac) 
                    { 
                        game.resetScore();
                        display.repaint();
                        game.retrieveFromFile(name);
                    }
                });
                loadMenu.add(item1);
        }
        
        saveOp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ac) 
            { 
                display.stopTimer();
                game.saveScoreToFile(scoreFile);
                
                String gameName = game.saveToFile();

                gameList.add(gameName);
                
                JMenuItem item1 = new JMenuItem(gameName);
                item1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ac) 
                    { 
                        game.resetScore();
                        display.repaint();
                        game.retrieveFromFile(gameName);
                        display.startTimer();
                    }
                });
                loadMenu.add(item1);
                display.startTimer();
                
                String text = "";
                for (int i = 0; i < gameList.size(); i++)
                {
                    text += gameList.get(i)+"\n";
                }
                writeNamesToFile(text);
            }
         });
        
        GameOp.add(saveOp);
        GameOp.add(loadMenu);
        
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ac) 
            { 
                game.newGame();
                display.startTimer();
            }
         });
        
        GameOp.add(newGame);
        
        JMenu highScores = new JMenu("High Scores");
        
        menuBar.add(highScores);
        
        JMenuItem disScores = new JMenuItem("Display Scores");
        disScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ac)
            {
                int amOfScores = game.getScoresAmount();
                String scoreText = "High Scores: \n";
                for (int scores = 0; scores < maxScores; scores++)
                {
                    int highScore;
            
                    if (scores >= amOfScores)
                        highScore = 0;
                    else
                        highScore = game.getAHighScore(scores);
                    
                    scoreText += (scores+1)+".  "+highScore+"\n";
                }
                JOptionPane.showMessageDialog(null, scoreText, "High Scores Display  "
                        + "     Hafsah K. Caitlyn D", 1);
            }
        });
        
        highScores.add(disScores);
        
        JMenuItem clearScor = new JMenuItem("Clear All Scores");
        clearScor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ac)
            {
                game.clearAllScores();
            }
        });
        
        highScores.add(clearScor);
        this.setJMenuBar(menuBar);
    }
    
    public void writeNamesToFile(String names)
    {
        File outFile = new File(nameFile);
        
        try
        {
            FileWriter outWriter = new FileWriter(outFile, false);
            outWriter.write(names);
            outWriter.close();
        }
        catch (IOException ioe)
        {
            String error = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"+
                           "Trouble writing data to: "+nameFile+"\n"+
                           "This program is ending\n"+
                           "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
            System.err.print(error);
            System.exit(0);
        }
    }
    public void getNamesFromFile()
    {
        File inFile = new File(nameFile);
        if (!inFile.exists())
        {
            String error = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"+
                           "Can not see file: "+nameFile+"\n"+
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
                gameList.add(fileScan.next());
            }
            fileScan.close();
        }
        catch(IOException ioe)
        {
             String error = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"+
                           "Trouble reading data from: "+nameFile+"\n"+
                           "This program is ending\n"+
                           "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
            System.err.print(error);
            System.exit(0);
        }
    }
    
    public static void main(String[] args) 
    {
        new TetrisWindow();
    }
}
