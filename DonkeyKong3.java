import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.io.IOException;

public class DonkeyKong3 extends JFrame implements ActionListener, KeyListener {

    private JFrame myFrame;
    private int highScores;
    private int currentScore = 0;
    private int lives;
    private int windowWidth = 1000;
    private int windowHeight = 865;
    private boolean up, down, left, right;
    private int fps = 60;
    private int currentAnim = 0;

    int playerX = 0;
    int playerY = 75;
    int perPixel = 4;

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
        myFrame.setFocusable(true);
        requestFocusInWindow();
        this.lives = lives;
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setFPSandPaint();
        try {
            setHighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFPSandPaint() {

        // Set FPS (information found on Stack Overflow)

        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime = System.nanoTime();
        int timer = 0;
        int drawCount = 0;

        while(myFrame != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
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
        if (level == 0 || level == 1 || level == 2) {
            myFrame.add(new JLabel(new ImageIcon("dk3_level_" + level + ".png")));
        }
        else {
            new DonkeyKong3(currentScore, 0, lives);
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
        }
            
    }

    public void update() {
        if(up)
            playerY -= perPixel;
        else if(left)
            playerX -= perPixel;
        else if(right)
            playerX += perPixel;
        else if(down)
            playerY += perPixel;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("MOVING");
        Graphics2D g2 = (Graphics2D) g;
        Toolkit tool = Toolkit.getDefaultToolkit();

        g2.setColor(Color.red);

        g2.drawImage(tool.getImage("bm/" + currentAnim + ".png"), playerX, playerY, 60, 60, this);

        // Score Display
        g2.drawString(Integer.toString(currentScore), 10, 10);

        g2.fillRect(playerX, playerY, 800, 800);
        g2.dispose();
    }

    public void death() {
        lives--;
    }
    
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
            if (currentAnim < 4)
                currentAnim++;
            else
                currentAnim = 0;
        }
        else if(event.getKeyCode() == KeyEvent.VK_UP)
            up = true;
        else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
            if (currentAnim < 4)
                currentAnim++;
            else
                currentAnim = 0;
        }
        else if(event.getKeyCode() == KeyEvent.VK_DOWN)
            down = true;
    }

    public void keyReleased(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_LEFT)
            left = false;
        else if(event.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
            if (currentAnim < 4)
                currentAnim++;
            else
                currentAnim = 0;
        }
        else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
            if (currentAnim < 4)
                currentAnim++;
            else
                currentAnim = 0;
        }
        else if(event.getKeyCode() == KeyEvent.VK_DOWN)
            down = false;
    }

    public void keyTyped(KeyEvent event) {

    }

    public void actionPerformed(ActionEvent event) {

    }
}
