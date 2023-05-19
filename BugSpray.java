import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.io.IOException;

public class BugSpray extends Mario {
    private boolean extreme;
    private int posX;
    private int posY;
    private int sizeX = 210;
    private int sizeY = 150;
    private int currentAnim = 8;
    
    public BugSpray(boolean extreme) {
        this.extreme = extreme;
        currentAnim = 8;
    }

    public void puff() {
        if (super.getDeath() == false) {
            link();
            if (currentAnim < 7)
                currentAnim++;
            else
                currentAnim = 0;
          }
    }

    public void link() {
        while(super.getBugSpray() == false) {
            posX = getPosX();
            super.setBugSpray(false);
        }
    }
    
    public void movement() {
        
            
    }

    public int getAnim() {
        return currentAnim;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
}
