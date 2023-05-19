public class Mario {
    public int playerX = 700;
    public int playerY = 700;
    public int perPixel = 4;
    public int totalPlayerY = 0;
    private int currentAnim = 0;
    private boolean death = false;

    public void move() {
      if (death == false) {
        if (currentAnim < 3)
            currentAnim++;
        else
            currentAnim = 0;
      }
    }

    public int getAnimMario() {
      return currentAnim;
    }

    public void setDeath() {
      death = true;
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
