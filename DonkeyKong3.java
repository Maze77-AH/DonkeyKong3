import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

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
    private int fpsCounter;
    private boolean debug = false;
    private boolean gameOver = false;
    private boolean decending = false;
    private boolean marioWin = false;
    private boolean exception = false;
    private boolean stubbed = false;
    private boolean death = false;
    private boolean extreme = false;
    private boolean hasPowerUp = false;
    private boolean dropped = false;
    private boolean stopSpawn = false;
    private int countExtreme = 0;
    private ArrayList<Enemy> enemy = new ArrayList<Enemy>(aiLevel);
    private ArrayList<Flower> flowers = new ArrayList<Flower>();
    private ArrayList<String> debugConsole = new ArrayList<String>();
    private int[] enemyVariety = new int[8];
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private DK dk = new DK();
    private Mario mario = new Mario();
    private BugSpray bs = new BugSpray();
    private BugSpray bs2 = new BugSpray();
    private Pump pump = new Pump();
    private Barrel dkB = new Barrel();

    public DonkeyKong3() {
        this(0, 0, 3, 1, false, true, false);
    }

    public DonkeyKong3(int score, int level, int lives, int aiLevel, boolean debug, boolean hasPowerUp, boolean activePowerUp) {
        this.lives = lives;
        this.aiLevel = aiLevel;
        this.level = level;
        this.debug = debug;
        this.hasPowerUp = hasPowerUp;
        dk.setLevelandAI(level, aiLevel);
        myFrame = new JFrame("Donkey Kong 3");
        myFrame.setSize(windowWidth, windowHeight);
        myFrame.setAlwaysOnTop(false);
        myFrame.setUndecorated(false);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setFocusable(true);
        myFrame.add(this);
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.toFront();
        myFrame.requestFocus();
        ImageIcon favicon = new ImageIcon("DK3Title.png");
        myFrame.setIconImage(favicon.getImage());
        requestFocusInWindow();
        addKeyListener(this);
        setEnemyandFlowers(enemyVariety);
        try {
            setHighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scoreIncrease(score);
        setLevel(level);
        setPump();
        setAILevelBarrel();
        if (activePowerUp) {
            onExtreme();
        }
        setFPSandPaint();

    }

    public void setAILevelBarrel() {
        dkB.setAI(aiLevel);
    }

    public void setPump() {
        pump.setPump(hasPowerUp);
    }

    public void setEnemyandFlowers(int [] array) {
        for (int x = 0; x < array.length; x++) {
            array[x] = (int) (Math.random() * 4);
            if (array[0] == 0) {
                array[0] = 1;
            }
            enemy.add(new Enemy(array[x]));
        }

        for (int x = 0; x < 5; x++) {
            flowers.add(new Flower());
            dk.flowers(flowers);
        }
    }

    public void setFPSandPaint() {
        // Set FPS information modified extensively from Stack Overflow

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
            if (aiLevel > 30) {
                if (timer >= 1000000000 - (30 * 25000000)) {
                    fpsCounter = drawCount;
                    dk.move(playerX, playerY);
                    bonusTime -= 100;
                    drawCount = 0;
                    timer = 0;
                }
            }

            if (aiLevel <= 30) {
                if (timer >= 1000000000 - (aiLevel * 25000000)) {
                    fpsCounter = drawCount;
                    dk.move(playerX, playerY);
                    bonusTime -= 100;
                    drawCount = 0;
                    timer = 0;
                }
            }

            if (dk.getMarioWin() || enemy.size() <= 0)
                marioWinSetLevel(level);
            else if (bonusTime <= 0 || dk.getDeath() || death)
                death();

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
                if (!death) {
                    if(left || right)
                        mario.move();
                    if (dkB.getThrown())
                        dkB.move();
                    for (int x = 0; x < enemy.size(); x++) {
                        for (int y = 0; y < flowers.size(); y++) {
                            if (!enemy.get(x).getAttacker() && flowers.get(y).getPosX() >= enemy.get(x).getPosX() - 40
                                    && flowers.get(y).getPosX() <= enemy.get(x).getPosX() + 40 && flowers.get(y).getPosY() >= enemy.get(x).getPosY() - 40
                                    && flowers.get(y).getPosY() <= enemy.get(x).getPosY() + 40) {
                                        flowers.get(y).draggedAway(enemy.get(x).getPosX(), enemy.get(x).getPosY());
                            }
                            if (flowers.get(y).getPosY() <= 150) {
                                debugConsole.add("Flower Lost: " + flowers.get(y));
                                flowers.remove(y);
                                bonusScore -= 100;
                            }
                        }
                    }
                    for (int y = 0; y < flowers.size(); y++) {
                        if (flowers.get(y).getDragged() && bs.getPosX() >= flowers.get(y).getPosX() - 100
                        && bs.getPosX() <= flowers.get(y).getPosX() + 100 && bs.getPosY() >= flowers.get(y).getPosY() - 100
                        && bs.getPosY() <= flowers.get(y).getPosY() + 100) {
                            flowers.get(y).dropped();
                        }
                        if (flowers.get(y).getDragged() && bs2.getPosX() >= flowers.get(y).getPosX() - 100
                        && bs2.getPosX() <= flowers.get(y).getPosX() + 100 && bs2.getPosY() >= flowers.get(y).getPosY() - 100
                        && bs2.getPosY() <= flowers.get(y).getPosY() + 100 || flowers.get(y).getDropping()) {
                            flowers.get(y).dropped();
                        }
                    }
                    if (hasPowerUp && dk.getPosY() <= 185) {
                        dropped = true;
                    }
                    if (hasPowerUp && dropped)
                        pump.dropped();
                    if (countExtreme == 250) {
                        offExtreme();
                        countExtreme++;
                    }
                    else if (countExtreme < 250 && extreme) {
                        countExtreme++;
                    }
                }
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
                } else {
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
            debugConsole.add("File not found");
        }
        String get = "";
        while ((ch = fr.read()) != -1) {
            get += ((char) ch);
            highScores = Integer.parseInt(get);
        }
        debugConsole.add("File found");
        fr.close();

    }

    public int scoreIncrease(int score) {
        this.currentScore += score;
        if (this.currentScore > highScores) {
            this.highScores = currentScore;
            Writer writer = null;

            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("highscore.txt"), "utf-8"));
                writer.write(Integer.toString(highScores));
            } catch (IOException ex) {

            } finally {
                try {
                    writer.close();
                } catch (Exception ex) {
                }
            }
        }
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
        myFrame.setVisible(false);
        myFrame.dispose();
        new DonkeyKong3(currentScore, level + 1, lives, aiLevel + 2, debug, false, extreme);
        System.exit(0);
    }

    public void setLevel(int level) {
        if (level > 1) {
            myFrame.setVisible(false);
            myFrame.dispose();
            new DonkeyKong3(currentScore, 0, lives, aiLevel, debug, false, extreme);
            System.exit(0);
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
        myFrame.setVisible(false);
        myFrame.dispose();
        new DonkeyKong3(currentScore, level, lives, aiLevel, debug, true, false);
        System.exit(0);
    }

    public void gameOver() {
        gameOver = true;
        if (!stubbed) {
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
            if (enemy.size() == 1 && !stopSpawn) {
                stopSpawn = false;
                setEnemyandFlowers(enemyVariety);
            }
            mario.updatePosX(playerX);
            mario.updatePosY(playerY);
            if (left && !(playerX < 200)) {
                playerX -= perPixel;
            } else if (right && !(playerX > 650)) {
                playerX += perPixel;
            }
        }
    }
    
    public void onExtreme() {
        debugConsole.add("Extreme On");
        extreme = true;
        pump.forceOff();
        bs.setExtreme(true);
        bs2.setExtreme(true);
        mario.setExtreme(true);
    }

    public void offExtreme() {
        debugConsole.add("Extreme Off");
        extreme = false;
        bs.setExtreme(false);
        bs2.setExtreme(false);
        mario.setExtreme(false);
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

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Draw Bug Spray
        if (bs.getPosY() >= dk.getPosY() && bs.getPosY() <= dk.getPosY() + 50 && bs.getPosX() >= dk.getPosX()
                && bs.getPosX() <= dk.getPosX() + 150) {
            if(dk.getIframe())
                debugConsole.add(sdf.format(timestamp) + " HIT (1): Inv");
            else 
                debugConsole.add(sdf.format(timestamp) + " HIT (1)");
            bs.forceSprayOff();
            dk.hit();
        }
        g2.drawImage(tool.getImage("sprites/smoke/" + bs.getAnim() + ".png"), bs.getPosX(), bs.getPosY(), bs.getSizeX(),
                bs.getSizeY(), this);

        if (bs2.getPosY() >= dk.getPosY() && bs2.getPosY() <= dk.getPosY() + 50 && bs2.getPosX() >= dk.getPosX()
                && bs2.getPosX() <= dk.getPosX() + 150) {
            if(dk.getIframe())
                debugConsole.add(sdf.format(timestamp) + " HIT (2): Inv");
            else 
                debugConsole.add(sdf.format(timestamp) + " HIT (2)");
            bs2.forceSprayOff();
            dk.hit();
        }
        g2.drawImage(tool.getImage("sprites/smoke/" + bs2.getAnim() + ".png"), bs2.getPosX(), bs2.getPosY(),
                bs2.getSizeX(), bs2.getSizeY(), this);

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Monocraft.ttf")));
        } catch (IOException|FontFormatException e) { 
            debugConsole.add("Font not loaded");
        }
        // Score Display
        g2.setFont(new Font("Monocraft", Font.BOLD, 40));
        g2.setColor(Color.cyan);
        g2.drawString(Integer.toString(currentScore), 125, 30);
        g2.setColor(Color.red);
        g2.drawString(Integer.toString(highScores), 450, 30);
        if (level == 1)
            g2.setColor(Color.orange);
        g2.drawString(Integer.toString(bonusTime), 760, 100);
        g2.setColor(Color.white);
        g2.setFont(new Font("Monocraft", Font.BOLD, 20));

        if (marioWin)
            g2.drawString(Integer.toString(bonusScore) + " bonus points", 350, 500);

        if (gameOver) {
            g2.setColor(Color.black);
            g2.drawRect(0, 0, 1920, 1080);
            g2.fillRect(0, 0, 1920, 1080);
            g2.setColor(Color.orange);
            g2.drawString("GAME OVER", 350, 300);
            g2.drawString("Current Score: " + Integer.toString(currentScore), 300, 700);
        }

        if (hasPowerUp)
            g2.drawImage(tool.getImage("sprites/misc/Pump.png"), pump.getPosX(), pump.getPosY(), 
        pump.getSizeX(), pump.getSizeY(), this);

        for (Enemy x : enemy) {
            if (playerX >= x.getPosX() - 30 && playerX <= x.getPosX() + 30 && playerY >= x.getPosY() - 30
                    && playerY <= x.getPosY() + 30 && !death)
                death = true;
        }

        if (!extreme && playerX >= pump.getPosX() - 30 && playerX <= pump.getPosX() + 30 && playerY >= pump.getPosY() - 50 && playerY <= pump.getPosY() + 50 && !death) {
            onExtreme();
        }
                

        for (int x = 0; x < enemy.size(); x++) {
            if (enemy.get(x).getPursue() && bs.getPosX() >= enemy.get(x).getPosX() - 50
                    && bs.getPosX() <= enemy.get(x).getPosX() + 50 && bs.getPosY() >= enemy.get(x).getPosY() - 50
                    && bs.getPosY() <= enemy.get(x).getPosY() + 50) {
                enemy.remove(x);
                bs.forceSprayOff();
                scoreIncrease(100);
            }
        }

        for (int x = 0; x < enemy.size(); x++) {
            if (enemy.get(x).getPursue() && bs2.getPosX() >= enemy.get(x).getPosX() - 50
                    && bs2.getPosX() <= enemy.get(x).getPosX() + 50 && bs2.getPosY() >= enemy.get(x).getPosY() - 50
                    && bs2.getPosY() <= enemy.get(x).getPosY() + 50) {
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

        int count2 = 200;
        for (Flower f : flowers) {
            if (!f.getDragged()) {
                f.setPosX(count2);
                count2 += 150;
            }
        }

        for (Flower f : flowers) {
            g2.drawImage(tool.getImage("sprites/misc/Flower.png"), f.getPosX(), f.getPosY(), f.getSizeX(), f.getSizeY(), this);
        }

        if (dk.getBarrel() && !dkB.getThrown()) {
            dkB.reset(dk.getPosY());
            dkB.thrown(dk.getPosX(), dk.getPosY());
        }

        if (dkB.getThrown()) {
            g2.drawImage(tool.getImage("sprites/barrel/0.png"), dkB.getPosX(), dkB.getPosY(), dkB.getSizeX(),
                    dkB.getSizeY(), this);
        }

        if (playerX >= dkB.getPosX() - 50 && playerX <= dkB.getPosX() + 50 && playerY >= dkB.getPosY() - 50
                && playerY <= dkB.getPosY() + 50 && !death)
            death = true;

        if (debug) {
            int PositionConsoleY = 0;

            g2.drawString(String.format("GAME.fps: (%d)", fpsCounter), 10, 60);
            g2.drawString(String.format("GAME.entities: (%d)", enemy.size() + 5), 10, 80);
            g2.drawString(String.format("MARIO.lives: (%d)", lives), 10, 100);
            g2.drawString(String.format("DK.aiLevel: (%d)", dk.getAILevel()), 10, 120);
            g2.drawString(String.format("MARIO.powerUpPercent: (%d)", Math.round((countExtreme/250.0) * 100.0)), 10, 140);

            g2.drawRect(0, 600, 350, 350);
            Color transParent = new Color(0f, 0f, 0f, .5f);
            g2.setColor(transParent);
            g2.fillRect(0, 600, 350, 350);

            g2.setColor(Color.white);

            for (int items = 0; items < debugConsole.size(); items++) {
                String item = debugConsole.get(items);

                if (items == 17)
                    debugConsole.remove(0);
                else
                    PositionConsoleY += 20;

                g2.drawString(item, 10, PositionConsoleY + 605);
            }
        }

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
                left = true;
            } else if (event.getKeyCode() == KeyEvent.VK_W && jumpCount < 25 && !decending && !up && !down) {
                up = true;
                previousYLoc = playerY;
            } else if (event.getKeyCode() == KeyEvent.VK_D && !(playerX > 650) && !decending && !up && !down) {
                right = true;
            } else if (event.getKeyCode() == KeyEvent.VK_S && playerY < 860 && !decending && !up && !down) {
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
        if (event.getKeyCode() == KeyEvent.VK_L && debug)
            dk.setMarioWin();
        if (event.getKeyCode() == KeyEvent.VK_K && debug) {
            dk.setLevelandAI(level, 30);
            aiLevel = 30;
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN && debug)
            dk.move(playerX, playerY);
        if (event.getKeyCode() == KeyEvent.VK_RIGHT && debug)
            dk.forceThrow();
        if (event.getKeyCode() == KeyEvent.VK_LEFT && debug)
            enemy.get((int)Math.random() * enemy.size()).forcePursue();
        if (event.getKeyCode() == KeyEvent.VK_F3)
            debug = !debug;
    }

    public void keyTyped(KeyEvent event) {

    }

    public void actionPerformed(ActionEvent event) {

    }
}
