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
    private int windowWidth = 899;
    private int windowHeight = 1020;
    private boolean up, down, left, right;
    private int fps = 60;
    private int aiLevel;
    private int bonusTime = 8000;

    private Mario mario = new Mario();
    private DK dk = new DK(level, aiLevel);
    private BugSpray bs = new BugSpray(false);

    int playerX = 700;
    int playerY = 700;
    int perPixel = 4;

    public DonkeyKong3() {
        this(0, 0, 3, 1);
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

        double drawInterval2 = 1000000000 / 120;
        double delta2 = 0;
        long lastTime2 = System.nanoTime();
        int timer2 = 0;

        while (myFrame != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            delta2 += (currentTime - lastTime2) / drawInterval2;
            timer2 += (currentTime - lastTime2);
            lastTime2 = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS " + drawCount);
                if (bonusTime <= 0 || dk.getDeath())
                    death();
                else {
                    dk.move(playerX, playerY);
                    bonusTime -= 100;
                    drawCount = 0;
                    timer = 0;
                }
            }

            if (delta2 >= 1) {
                delta2--;
            }
            if (timer2 >= 99999999) {
                if(bs.getSpraying()) {
                    bs.animSet(dk.getPosX(), dk.getPosY());
                }
                timer2 = 0;
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
        if (level > 3) {
            new DonkeyKong3(currentScore, 0, lives, aiLevel);
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void restartLevel() {
        new DonkeyKong3(currentScore, level, lives, aiLevel);
        myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
    }

    public void gameOver() {
        myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
    }

    public void update() {
        if (mario.getDeath() == false) {
            if (up)
                playerY -= perPixel;
            else if (left)
                playerX -= perPixel;
            else if (right)
                playerX += perPixel;
            else if (down)
                playerY += perPixel;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        Toolkit tool = Toolkit.getDefaultToolkit();

        // Draw Level

        g2.drawImage(tool.getImage("dk3_level_" + level + ".png"), 0, 0, 899, 982, this);

        // Draw Mario

        g2.drawImage(tool.getImage("bm/" + mario.getAnimMario() + ".png"), playerX, playerY, 55, 65, this);

        // Draw Donkey Kong

        g2.drawImage(tool.getImage("sprites/dk/" + dk.getAnim() + ".png"), dk.getPosX(), dk.getPosY(), dk.getSizeX(), dk.getSizeY(), this);

        // Draw Bug Spray

        if (bs.getPosY() >= dk.getPosY() && bs.getPosY() <= dk.getPosY() + 50 && bs.getPosX() >= dk.getPosX() && bs.getPosX() <= dk.getPosX()) {
            System.out.println("HITHITHIHTIT");
            bs.forceSprayOff();
            dk.hit();
        }
        g2.drawImage(tool.getImage("sprites/smoke/" + bs.getAnim() + ".png"), bs.getPosX(), bs.getPosY(), bs.getSizeX(), bs.getSizeY(), this);

        // Score Display
        g2.setColor(Color.orange);
        g2.setFont(new Font("Monospace", Font.PLAIN, 40)); 
        g2.drawString(Integer.toString(currentScore), 125, 30);
        g2.drawString(Integer.toString(highScores), 450, 30);
        g2.drawString(Integer.toString(bonusTime),  760, 100);

        mario.updatePosX(playerX);
        mario.updatePosY(playerY);

        // g2.fillRect(dk.getPosX() + 50, dk.getPosY() + 100, 100, 50);
        g2.dispose();
    }

    public void death() {
        lives--;
        mario.setDeath(); 
        if (lives > 0)
            restartLevel();
        else
            gameOver();
    }

    // Movement Booleans

    public void keyPressed(KeyEvent event) {
        if (mario.getDeath() == false) {
            if (event.getKeyCode() == KeyEvent.VK_A) {
                mario.move();
                left = true;
            } else if (event.getKeyCode() == KeyEvent.VK_W) {
                up = true;
            }
            else if (event.getKeyCode() == KeyEvent.VK_D) {
                mario.move();
                right = true;
            } else if (event.getKeyCode() == KeyEvent.VK_S) {
                down = true;
            }
        }
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
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            bs.puff(playerX, playerY);
        }
    }

    public void keyTyped(KeyEvent event) {

    }

    public void actionPerformed(ActionEvent event) {

    }
}
