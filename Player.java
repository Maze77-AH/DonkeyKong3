import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.io.IOException;

public class Player extends Mario {
    DonkeyKong3 dk;
    KeyEvent event;
    
    public Player(DonkeyKong3 dk, KeyEvent event) {
        this.dk = dk;
        this.event = event;
    }

    public void defPos() {
        posX = 100;
        posY = 100;
        speed = 4;
    }

    public void movement() {
        
            
    }
}
