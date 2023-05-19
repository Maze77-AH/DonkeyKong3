public class Mario {
    private int playerX = 700;
    private int playerY = 700;
    private int perPixel = 4;
    private int totalPlayerY = 0;
    private int currentAnim = 0;
    private boolean bugSpray = false;
    private boolean death = false;

    public void move() {
      if (death == false) {
        if (currentAnim < 3)
            currentAnim++;
        else
            currentAnim = 0;
      }
      else 
        currentAnim = 4;
    }


    public void setBugSpray(boolean b) {
      bugSpray = b;
    }

    public boolean getBugSpray() {
      return bugSpray;
    }

    public int getAnimMario() {
      return currentAnim;
    }

    public void setDeath() {
      death = true;
      currentAnim = 4;
    }

    public boolean getDeath() {
        return death;
    }

    public void updatePosX(int posX) {
      playerX = posX;
    }

    public void updatePosY(int posY) {
      playerY = posY;
    }

    public int getPosX() {
      return playerX;
    }

    public int getPosY() {
      return playerY;
    }

    public int getPerPixel() {
      return perPixel;
    }

}
