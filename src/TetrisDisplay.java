/*
 * Display file for Tetris, responsible for showing the status of the game
 * Hafsah Khan Caitlyn Dionne 
 * 11.28.21
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel{
    
    private TetrisGame game;
    private int start_x = 50;
    private int start_y = 30;
    private int cellSize = 10;
    private Color[] colors = {Color.white, Color.black, Color.orange,
                                Color.green, Color.YELLOW, Color.CYAN,
                                Color.RED, Color.magenta, Color.BLUE};
    
    private int numSeg = 4;
    private String timerState = "play";
    
    Timer timer;
    private int delay = 300;
    
    public TetrisDisplay(TetrisGame gam)
    {
        game = gam;
        
        
        timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                cycleMove();
            }
        });
        
        this.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke)
            {
                translateKey(ke);
            }
        });
        
        timer.start();
        
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

    }
    

    public void gameOver(Graphics g)
    {
        if (game.boardFull())
        {
            timer.stop();
            
            g.setColor(Color.black);
            g.fillRect(35,110,170,50);
            
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            g.drawString("Game Over!", 35, 140);
        }
    }
    public void translateKey(KeyEvent ke)
    {
        int code = ke.getKeyCode();
        String direction;
        switch(code)
        {
            case KeyEvent.VK_DOWN:
                direction = "D";
                game.makeMove(direction);
                break;
            case KeyEvent.VK_RIGHT:
                direction = "R";
                game.makeMove(direction);
                break;
            case KeyEvent.VK_LEFT:
                direction = "L";
                game.makeMove(direction);
                break;  
            case KeyEvent.VK_SPACE:
                if (timerState.equals("play"))
                {
                    timer.stop();
                    timerState = "pause";
                    break; 
                }
                else
                {
                    timer.start();
                    timerState = "play";
                    break;
                } 
            case KeyEvent.VK_UP:
                direction = "F";
                game.makeMove(direction);
                break;
            
            case KeyEvent.VK_N:
                game.newGame();
                timer.start();
                break;
        }
        repaint();
    }
    
    public void cycleMove()
    {
        String direction = "D";
        game.makeMove(direction);
        repaint();
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));
        drawWell(g);
        drawBackground(g);
        drawBrick(g);
        drawScore(g);
        gameOver(g);
    }
    
    public void drawWell(Graphics g)
    {
        //Left Wall
        g.fillRect(start_x - cellSize, start_y , 
                cellSize, cellSize * game.getRows() + cellSize);
        
        //Right Wall
        g.fillRect(start_x + cellSize*game.getCols(), 
                start_y , cellSize, 
                cellSize* game.getRows() + cellSize);
        
        //Bottom Wall
        g.fillRect(start_x - cellSize, start_y + cellSize * game.getRows(), 
                 cellSize*game.getCols()+2*cellSize, cellSize);
    }
    
    public void drawBrick(Graphics g)
    {
        for (int seg = 0; seg < numSeg; seg++)
        {
            int col = game.getBrickPosition(seg, 0);
            int row = game.getBrickPosition(seg, 1); 

            g.setColor(colors[game.getBlockColor()]);
            g.fillRect(start_x+col*cellSize,
                (start_y ) + row*cellSize, cellSize, cellSize); 
            g.setColor(colors[1]);
            g.drawRect(start_x+col*cellSize,
                (start_y )+row*cellSize, cellSize, cellSize);
            
        }
    }
    
    public void drawBackground(Graphics g)
    {
       for(int row = 0; row < game.getRows(); row++)
       {
           for (int col = 0; col < game.getCols(); col++)
           {
               if (game.fetchBoardPosition(row, col) == 0)
               {
                    g.setColor(colors[0]);
                    g.fillRect(start_x+col*cellSize,
                        start_y  + row*cellSize, cellSize, cellSize); 

               }
               else
               {
                   g.setColor(colors[game.fetchBoardPosition(row, col)]);
                    g.fillRect(start_x+col*cellSize,
                        start_y  + row*cellSize, cellSize, cellSize); 
                    g.setColor(colors[1]);
                    g.drawRect(start_x+col*cellSize,
                        start_y  +row*cellSize, cellSize, cellSize);
               }
           }
       }
    }
    
    public void drawScore(Graphics g)
    {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
        g.drawString("Score: "+game.getScore(), 25, 25);
    }

    public void stopTimer()
    {
        timer.stop();
    }
    
    public void startTimer()
    {
        timer.start();
    }
}
