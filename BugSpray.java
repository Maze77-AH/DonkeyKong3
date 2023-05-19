import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.io.IOException;

public class BugSpray extends Mario {
    private boolean extreme;
    private boolean registerHit = false;
    private boolean spraying = false;
    private int posX;
    private int posY;
    private int sizeX = 25;
    private int sizeY = 25;
    private int currentAnim = 0;
    
    public BugSpray(boolean extreme) {
        this.extreme = extreme;
        currentAnim = 0;
    }

    public void puff(int posXMario, int posYMario) {
        if (super.getDeath() == false) {
            spraying = true;
            System.out.println(super.getPosX());
            posX = posXMario;
            posY = posYMario;
        }
    }

    public void animSet(int dkPosX, int dkPosY) {
        if (currentAnim < 7) {
            currentAnim++;
        }
        else {
            currentAnim = 8;
            spraying = false;
            currentAnim = 0;
        }
    }
    
    public void movement() {
        if (spraying) {
            if(extreme)
                this.posY -= 35;
            else 
                this.posY -= 30;
        }
    }

    public boolean getRegisterHit() {
        return registerHit;
    }

    public boolean getSpraying() {
        return spraying;
    }

    public void forceSprayOff() {
        spraying = false;
        posY = 0;
        currentAnim = 0;
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
