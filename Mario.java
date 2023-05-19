public class Mario {
    private int posX = 700;
    private int posY = 700;
    private int perPixel = 4;
    private int totalPlayerY = 0;
    private int currentAnim = 0;
    private boolean death;

    public Mario() {
      death = false;
    }

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
    
    public void setDeath() {
      death = true;
      currentAnim = 4;
      System.out.println("DEATH");
    }

    public void updatePosX(int posX) {
      this.posX = posX;
    }

    public void updatePosY(int posY) {
      this.posY = posY;
    }

    public int getAnimMario() {
      return currentAnim;
    }

    public boolean getDeath() {
        return death;
    }

    public int getPosX() {
      return this.posX;
    }

    public int getPosY() {
      return this.posY;
    }

    public int getPerPixel() {
      return perPixel;
    }

}
