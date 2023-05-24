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
    private int playerX = 450;
    private int playerY = 860;
    private int perPixel = 4;
    private int previousYLoc = 860;
    private int bonusTime = 8000;
    private int jumpCount = 0;
    private int bonusScore = 500;
    private boolean gameOver = false;
    private boolean decending = false;
    private boolean marioWin = false;
    private boolean exception = false;
    private boolean stubbed = false;
    private boolean death = false;
    private ArrayList<Enemy> enemy = new ArrayList<Enemy>(aiLevel);
    private int[] enemyVariety = new int[8];

    private DK dk = new DK();
    private Mario mario = new Mario();
    private BugSpray bs = new BugSpray(false);
    private BugSpray bs2 = new BugSpray(false);
    private Barrel dkB = new Barrel();

    public DonkeyKong3() {
        this(0, 0, 3, 1);
    }

    public DonkeyKong3(int score, int level, int lives, int aiLevel) {
        this.lives = lives;
        this.aiLevel = aiLevel;
        this.level = level;
        dk.setLevelandAI(level, aiLevel);
        scoreIncrease(score);
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
        setEnemy();
        setFPSandPaint();
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            setHighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setEnemy() {
        for (int x = 0; x < enemyVariety.length; x++) {
            enemyVariety[x] = (int) (Math.random() * 4);
            enemy.add(new Enemy(enemyVariety[x]));
        }
    }

    public void setFPSandPaint() {
        // Set FPS information modified from Stack Overflow

        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime = System.nanoTime();
        int timer = 0;
        int drawCount = 0;

        double drawInterval2 = 1000000000 / fps;
        double delta2 = 0;
        long lastTime2 = System.nanoTime();
        int timer2 = 0;

        double drawInterval3 = 1000000000 / fps;
        double delta3 = 0;
        long lastTime3 = System.nanoTime();
        int timer3 = 0;

        while (myFrame != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            delta2 += (currentTime - lastTime2) / drawInterval2;
            timer2 += (currentTime - lastTime2);
            lastTime2 = currentTime;

            delta3 += (currentTime - lastTime3) / drawInterval3;
            timer3 += (currentTime - lastTime3);
            lastTime3 = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS " + drawCount);
                if (dk.getMarioWin())
                    marioWinSetLevel(level);
                else if (bonusTime <= 0 || dk.getDeath() || death)
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
                if (bs.getSpraying()) {
                    bs.animSet(dk.getPosX(), dk.getPosY());
                }
                if (bs2.getSpraying()) {
                    bs2.animSet(dk.getPosX(), dk.getPosY());
                }
                timer2 = 0;
            }

            if (delta3 >= 1) {
                delta3--;
            }
            if (timer3 >= 40000000) {
                if (level == 0) {
                    if (up && playerY > previousYLoc - 120 && !decending && jumpCount < 24) {
                        playerY -= 15;
                        jumpCount++;
                    }
                    if (up && playerY > previousYLoc - 120 && !decending && jumpCount < 32 && jumpCount >= 24) {
                        playerY -= 15;
                        jumpCount++;
                    }
                    if (jumpCount == 32) {
                        exception = true;
                        down = true;
                    }
                    if (up && playerY == previousYLoc - 120 && !decending && !exception) {
                        decending = true;
                    }
                    if (decending) {
                        playerY += 15;
                    }
                    if (up && playerY > previousYLoc - 100 && decending) {
                        up = false;
                        decending = false;
                    } else if (down && playerY < 860 && !decending) {
                        decending = true;
                    }
                    if (down && playerY > previousYLoc - 10 && decending && exception) {
                        down = false;
                        up = false;
                        decending = false;
                        exception = false;
                        if (jumpCount >= 0)
                            jumpCount -= 8;
                    }
                    if (down && playerY > previousYLoc + 80 && decending) {
                        down = false;
                        decending = false;
                        exception = false;
                        if (jumpCount >= 0)
                            jumpCount -= 8;
                    }
                    if (bs.getSpraying()) {
                        bs.movement();
                    }
                    if (bs2.getSpraying()) {
                        bs2.movement();
                    }
                    for (Enemy x : enemy) {
                        x.move();
                    }
                }
                else {
                    if (up && playerY > previousYLoc - 120 && !decending && jumpCount < 16) {
                        playerY -= 15;
                        jumpCount++;
                    }
                    if (up && playerY > previousYLoc - 120 && !decending && jumpCount < 24 && jumpCount >= 16) {
                        playerY -= 15;
                        jumpCount++;
                    }
                    if (jumpCount == 24) {
                        exception = true;
                        down = true;
                    }
                    if (up && playerY == previousYLoc - 120 && !decending && !exception) {
                        decending = true;
                    }
                    if (decending) {
                        playerY += 15;
                    }
                    if (up && playerY > previousYLoc - 100 && decending) {
                        up = false;
                        decending = false;
                    } else if (down && playerY < 860 && !decending) {
                        decending = true;
                    }
                    if (down && playerY > previousYLoc - 10 && decending && exception) {
                        down = false;
                        up = false;
                        decending = false;
                        exception = false;
                        if (jumpCount >= 0)
                            jumpCount -= 8;
                    }
                    if (down && playerY > previousYLoc + 80 && decending) {
                        down = false;
                        decending = false;
                        exception = false;
                        if (jumpCount >= 0)
                            jumpCount -= 8;
                    }
                    if (bs.getSpraying()) {
                        bs.movement();
                    }
                    if (bs2.getSpraying()) {
                        bs2.movement();
                    }
                    for (Enemy x : enemy) {
                        x.move();
                    }
                    if (dkB.getThrown())
                        dkB.move();
                }
                timer3 = 0;
            }
        }
    }

    // Reads high score from file

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

    public void marioWinSetLevel(int level) {
        repaint();
        marioWin = true;
        mario.setMarioWin(level);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        new DonkeyKong3(currentScore + bonusScore + bonusTime, level + 1, lives, aiLevel + 1);
        System.exit(0);
        myFrame.dispose();
        myFrame.setVisible(false);
    }

    public void setLevel(int level) {
        if (level > 1) {
            new DonkeyKong3(currentScore, 0, lives, aiLevel);
            System.exit(0);
            myFrame.dispose();
            myFrame.setVisible(false);
        }
    }

    // Level restart in-case of death

    public void restartLevel() {
        repaint();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        new DonkeyKong3(currentScore, level, lives, aiLevel);
        System.exit(0);
        myFrame.dispose();
        myFrame.setVisible(false);
    }

    public void gameOver() {
        gameOver = true;
        if(!stubbed) {
            stubbed = true;
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update() {
        if (mario.getDeath() == false) {
            mario.updatePosX(playerX);
            mario.updatePosY(playerY);
            if (left && !(playerX < 200)) {
                playerX -= perPixel;
            }
            else if (right && !(playerX > 650)) {
                playerX += perPixel;
            }
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

        g2.drawImage(tool.getImage("sprites/dk/" + dk.getAnim() + ".png"), dk.getPosX(), dk.getPosY(), dk.getSizeX(),
                dk.getSizeY(), this);

        // Draw Enemy (bugs)

        for (Enemy x : enemy) {
            g2.drawImage(tool.getImage("sprites/bugs/" + x.getAnim() + ".png"), x.getPosX(), x.getPosY(), x.getSizeX(),
                    x.getSizeY(), this);
        }

        // Draw Bug Spray

        if (bs.getPosY() >= dk.getPosY() && bs.getPosY() <= dk.getPosY() + 50 && bs.getPosX() >= dk.getPosX()
                && bs.getPosX() <= dk.getPosX() + 150) {
            System.out.println("HIT");
            bs.forceSprayOff();
            dk.hit();
        }
        g2.drawImage(tool.getImage("sprites/smoke/" + bs.getAnim() + ".png"), bs.getPosX(), bs.getPosY(), bs.getSizeX(),
                bs.getSizeY(), this);

        if (bs2.getPosY() >= dk.getPosY() && bs2.getPosY() <= dk.getPosY() + 50 && bs2.getPosX() >= dk.getPosX()
                && bs2.getPosX() <= dk.getPosX() + 150) {
            System.out.println("HIT2");
            bs2.forceSprayOff();
            dk.hit();
        }
        g2.drawImage(tool.getImage("sprites/smoke/" + bs2.getAnim() + ".png"), bs2.getPosX(), bs2.getPosY(),
                bs2.getSizeX(), bs2.getSizeY(), this);

        // Score Display
        g2.setColor(Color.orange);
        g2.setFont(new Font("Monospace", Font.PLAIN, 40));
        g2.drawString(Integer.toString(currentScore), 125, 30);
        g2.drawString(Integer.toString(highScores), 450, 30);
        g2.drawString(Integer.toString(bonusTime), 760, 100);

        if (marioWin)
            g2.drawString(Integer.toString(bonusScore) + " bonus points", 350, 500);
        
        if(gameOver) {
            g2.setColor(Color.black);
            g2.drawRect(0, 0, 1920, 1080);
            g2.fillRect(0, 0, 1920, 1080);
            g2.setColor(Color.orange);
            g2.drawString("GAME OVER", 350, 300);
            g2.drawString("Current Score: " + Integer.toString(currentScore), 300, 700);
        }
        
        for (Enemy x : enemy) {
            if (playerX >= x.getPosX() - 15 && playerX <= x.getPosX() + 15 && playerY >= x.getPosY() - 15 && playerY <= x.getPosY() + 15 && !death)
                death = true;
        }

        for (int x = 0; x < enemy.size(); x++) {
            if (enemy.get(x).getPursue() && bs.getPosX() >= enemy.get(x).getPosX() - 50 && bs.getPosX() <= enemy.get(x).getPosX() + 50 && bs.getPosY() >= enemy.get(x).getPosY() - 50 && bs.getPosY() <= enemy.get(x).getPosY() + 50) {
                enemy.remove(x);
                bs.forceSprayOff();
                scoreIncrease(100);
            }
        }

        for (int x = 0; x < enemy.size(); x++) {
            if (enemy.get(x).getPursue() && bs2.getPosX() >= enemy.get(x).getPosX() - 50 && bs2.getPosX() <= enemy.get(x).getPosX() + 50 && bs2.getPosY() >= enemy.get(x).getPosY() - 50 && bs2.getPosY() <= enemy.get(x).getPosY() + 50) {
                enemy.remove(x);
                bs2.forceSprayOff();
                scoreIncrease(100);
            }
        }

        int count = 0;
        for (int x = 0; x < lives; x++) {
            g2.drawImage(tool.getImage("sprites/misc/Lives.png"), count, 950, 30, 40, this);
            count += 50;
        }

        if (dk.getBarrel() && !dkB.getThrown()) {
            dkB.thrown(dk.getPosX(), dk.getPosY());
        }

        if (dkB.getThrown()) {
            g2.drawImage(tool.getImage("sprites/barrel/0.png"), dkB.getPosX(), dkB.getPosY(), dkB.getSizeX(), dkB.getSizeY(), this);
        }

        if (playerX >= dkB.getPosX() - 50 && playerX <= dkB.getPosX() + 50 && playerY >= dkB.getPosY() - 50 && playerY <= dkB.getPosY() + 50 && !death)
            death = true;

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
            if (event.getKeyCode() == KeyEvent.VK_A && !(playerX < 200) && !decending && !up && !down) {
                mario.move();
                left = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_W && jumpCount < 25 && !decending && !up && !down) {
                up = true;
                previousYLoc = playerY;
            }
            if (event.getKeyCode() == KeyEvent.VK_D && !(playerX > 650) && !decending && !up && !down) {
                mario.move();
                right = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_S && playerY < 860 && !decending && !up && !down) {
                down = true;
                previousYLoc = playerY;
            }
        }
    }

    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_A)
            left = false;
        else if (event.getKeyCode() == KeyEvent.VK_D)
            right = false;
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!bs2.getSpraying() && bs.getSpraying())
                bs2.puff(playerX, playerY);
            if (!bs.getSpraying())
                bs.puff(playerX, playerY);
        }
    }

    public void keyTyped(KeyEvent event) {

    }

    public void actionPerformed(ActionEvent event) {

    }
}
