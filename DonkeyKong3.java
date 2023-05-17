import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.io.IOException;

public class DonkeyKong3 extends JPanel implements ActionListener, KeyListener {

    public JFrame myFrame = new JFrame();
    private int highScores;
    private int currentScore = 0;
    private int lives;
    private int level;
    private int windowWidth = 1000;
    private int windowHeight = 865;
    private boolean up, down, left, right;
    private int fps = 60;
    private int currentAnim = 0;
    private int aiLevel;
    private int bonusTime = 700;

    int playerX = 700;
    int playerY = 700;
    int perPixel = 4;

    private Mario mario = new Mario();
    private DK dk = new DK();

    public DonkeyKong3() {
        this(0, 0, 3, 0);
    }

    public DonkeyKong3(int score, int level, int lives, int aiLevel) {
        this.currentScore = score;
        this.lives = lives;
        this.aiLevel = aiLevel;
        this.level = level;
        myFrame = new JFrame("Donkey Kong 3");
        myFrame.setSize(windowWidth, windowHeight);
        myFrame.setAlwaysOnTop(false);
        myFrame.setUndecorated(false);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setFocusable(true);
        myFrame.add(this);
        myFrame.setVisible(true);
        requestFocusInWindow();
        addKeyListener(this);
        setLevel(level);
        setFPSandPaint();
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            setHighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setFPSandPaint() {

        // Set FPS (information found on Stack Overflow)

        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime = System.nanoTime();
        int timer = 0;
        int drawCount = 0;

        while (myFrame != null) {
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
                if (bonusTime <= 0)
                    death();
                else {
                    bonusTime -= 10;
                    drawCount = 0;
                    timer = 0;
                }
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
        if (level >= 3 ) {
            new DonkeyKong3(currentScore, 0, lives, aiLevel);
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void update() {
        if (up)
            playerY -= perPixel;
        else if (left)
            playerX -= perPixel;
        else if (right)
            playerX += perPixel;
        else if (down)
            playerY += perPixel;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        Toolkit tool = Toolkit.getDefaultToolkit();

        // Draw Level

        g2.drawImage(tool.getImage("dk3_level_" + level + ".png"), 0, 0, 1000, 850, this);

        // Draw Mario

        g2.drawImage(tool.getImage("bm/" + currentAnim + ".png"), playerX, playerY, 55, 65, this);

        // Draw Donkey Kong

        g2.drawImage(tool.getImage("sprites/dk/1.png"), dk.getPosX(), dk.getPosY(), 55, 65, this);

        // Score Display
        g2.setColor(Color.orange);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 24)); 
        g2.drawString(Integer.toString(currentScore), 165, 79);
        g2.drawString(Integer.toString(bonusTime), 845, 140);
        g2.drawString(Integer.toString(highScores), 470, 85);

        // // Movement of Mario
        // mario.move(perPixel, up, down, left, right);
        // mario.pos();

        // g2.fillRect(playerX, playerY, 800, 800);
        g2.dispose();
    }

    public void death() {
        lives--;
        mario.death();
    }

    // Movement Booleans

    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_A) {
            left = true;
            if (currentAnim < 3)
                currentAnim++;
            else
                currentAnim = 0;
        } else if (event.getKeyCode() == KeyEvent.VK_W)
            up = true;
        else if (event.getKeyCode() == KeyEvent.VK_D) {
            right = true;
            if (currentAnim < 3)
                currentAnim++;
            else
                currentAnim = 0;
        } else if (event.getKeyCode() == KeyEvent.VK_S)
            down = true;
    }

    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_A)
            left = false;
        else if (event.getKeyCode() == KeyEvent.VK_W)
            up = false;
        else if (event.getKeyCode() == KeyEvent.VK_D)
            right = false;
        else if (event.getKeyCode() == KeyEvent.VK_S)
            down = false;
    }

    public void keyTyped(KeyEvent event) {

    }

    public void actionPerformed(ActionEvent event) {

    }
}
