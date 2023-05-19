public class DK {

    private int posX;
    private int posY;
    private int sizeX = 210;
    private int sizeY = 150;
    private int currentAnim = 5;
    private int level;
    private int aiLevel;
    private boolean marioDeath = false;

    public DK(int level, int aiLevel) {
        this.level = level;
        this.aiLevel = 5 + aiLevel;
        if (this.level == 0) {
            posX = 348;
            posY = 270;
        }
    }

    public void move(int posXMario, int posYMario) {
        if (posY >= 350) {
            marioDeath = true;
            if(currentAnim == 10)
                currentAnim = 9;
            else if(currentAnim != 9 || currentAnim != 10)
                currentAnim = 10;
            posX = posXMario - 100;
            posY = posYMario;
        }
        else {
            posY += aiLevel;
            if(currentAnim < 7)
                currentAnim++;
            else
                currentAnim = 5;
        }
    }

    public void hit() {
        posY -= 10;
    }

    public boolean getDeath() {
        return marioDeath;
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
