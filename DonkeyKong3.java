import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.io.IOException;

public class DonkeyKong3 extends JFrame implements KeyListener, ActionListener {

    private int highScores;
    private JFrame myFrame;
    private int currentScore = 0;
    private int windowWidth = 1000;
    private int windowHeight = 1000;

    public DonkeyKong3() {
        this(0);
    }

    public DonkeyKong3(int score) {
        this.currentScore = score;
        myFrame = new JFrame("Donkey Kong 3");
        myFrame.add(this);
        addKeyListener(this);
        myFrame.setSize(windowWidth, windowHeight);
        myFrame.setAlwaysOnTop(false);
        myFrame.setUndecorated(false);
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            setHighScore();
        } catch (IOException e) {
            e.printStackTrace();
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

    public void setLevel(int rot) {
        if (rot == 0) {
            //myFrame.add(new JLabel(new ImageIcon("dk3_level_0.png")));
        }
    }
    
    public void keyPressed(KeyEvent event) {

    }

    public void keyTyped(KeyEvent event) {

    }

    public void keyReleased(KeyEvent event) {

    }

    public void actionPerformed(ActionEvent e) {

    }
}
