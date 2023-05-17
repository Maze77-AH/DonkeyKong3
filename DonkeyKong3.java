import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.io.IOException;

public class DonkeyKong3 extends JFrame implements KeyListener, ActionListener {

    private JFrame myFrame;
    private int highScores;
    private int currentScore = 0;
    private int lives;
    private int windowWidth = 1000;
    private int windowHeight = 865;
    private boolean up, down, left, right;

    int playerX = 0;
    int playerY = 75;
    int playerSpeed = 4;

    private Player p = new Player(this, null);

    public DonkeyKong3() {
        this(0, 0, 3);
    }

    public DonkeyKong3(int score, int level, int lives) {
        this.currentScore = score;
        myFrame = new JFrame("Donkey Kong 3");
        setLevel(level);
        addKeyListener(this);
        myFrame.setSize(windowWidth, windowHeight);
        myFrame.setAlwaysOnTop(false);
        myFrame.setUndecorated(false);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        this.lives = lives;
        loop();
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            setHighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        while(myFrame != null)
            update();
            repaint();
    }

    public void setHighScore() throws IOException {
        int ch;
        FileReader fr = null;

        try {
            fr = new FileReader("highscore.txt");
        } catch (FileNotFoundException fe) {
            System.out.println("File not found");
        }
        String get = "";
        while ((ch = fr.read()) != -1) {
            get += ((char) ch);
            highScores = Integer.parseInt(get);
        }
        fr.close();

    }

    public int scoreIncrease(int score) {
        this.currentScore += score;
        if (this.currentScore > highScores)
            this.highScores = currentScore;
        return currentScore;
    }

    public void setLevel(int level) {
        if (level == 0) {
            myFrame.add(new JLabel(new ImageIcon("dk3_level_0.png")));
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, 100, 100);
        g2.dispose();
    }

    public void death() {
        lives--;
    }
    
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_LEFT)
            left = true;
        else if(event.getKeyCode() == KeyEvent.VK_UP)
            up = true;
        else if(event.getKeyCode() == KeyEvent.VK_RIGHT)
            right = true;
        else if(event.getKeyCode() == KeyEvent.VK_DOWN)
            down = true;
    }

    public void keyReleased(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_LEFT)
            left = false;
        else if(event.getKeyCode() == KeyEvent.VK_UP)
            up = false;
        else if(event.getKeyCode() == KeyEvent.VK_RIGHT)
            right = false;
        else if(event.getKeyCode() == KeyEvent.VK_DOWN)
            down = false;
    }

    public void keyTyped(KeyEvent event) {

    }

    public void actionPerformed(ActionEvent event) {

    }
}
