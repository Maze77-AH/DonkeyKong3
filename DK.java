public class DK {

    private int posX;
    private int posY;
    private int sizeX = 210;
    private int sizeY = 150;
    private int currentAnim = 6;
    private int level;
    private int aiLevel;
    private int globalCount = 0;
    private boolean stopHarm = false;
    private boolean marioDeath = false;
    private boolean marioWin = false;

    public DK() {};

    public DK(int level, int aiLevel) {
        this.level = level;
        this.aiLevel = 5 + aiLevel;
        if (this.level == 0) {
            posX = 348;
            posY = 270;
        }
    }

    public void move(int posXMario, int posYMario) {
        if (stopHarm && globalCount < 3)
            whackBugs();
        if (!stopHarm) {
            globalCount = 0;
            if (posY <= 50) {
                marioWin = true;
                currentAnim = 5;
            } else if (posY >= 350) {
                marioDeath = true;
                if (currentAnim == 10)
                    currentAnim = 9;
                else if (currentAnim != 9 || currentAnim != 10)
                    currentAnim = 10;
                posX = posXMario - 100;
                posY = posYMario;
            } else {
                posY += aiLevel;
                if (currentAnim < 7)
                    currentAnim++;
                else
                    currentAnim = 6;
            }
            if ((int)(Math.random() * 5) == 2) {
                whackBugs();
            }
        }
    }

    public void whackBugs() {
        if (!marioWin) {
            if (globalCount == 0) {
                stopHarm = true;
                currentAnim = 2;
            }
            if (globalCount == 1) {
                stopHarm = true;
                currentAnim = 11;
            }
            if (globalCount == 2) {
                stopHarm = false;
                currentAnim = 6;
            }
            globalCount++;
        }
    }

    public void hit() {
        if (!stopHarm || !marioWin && !stopHarm) {
            currentAnim = 5;
            posY -= 10;
        }
    }

    public boolean getDeath() {
        return marioDeath;
    }

    public boolean getMarioWin() {
        return marioWin;
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
