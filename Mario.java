public class Mario {
    public int posX, posY;
    public int perPixel = 0;
    public int playerX = 150;
    public int playerY = 100;
    public int totalPlayerY = 0;
    
    public void move(double speed, boolean up, boolean down, boolean left, boolean right) {
      if(left && !right){
        if(speed > (0 - perPixel)) speed -= 1;
      }
      else if(right && !left){
        if(speed < perPixel) speed += 1;
      }
      else {
          if (speed > 0) speed -= 0.5;
          else if (speed < perPixel) speed += 0.5;
      }
    }

    public void pos(){
        playerX += perPixel;
        if(playerY > totalPlayerY)
            totalPlayerY = playerY;
    }

    public void death() {
        
    }
}
