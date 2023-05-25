public class DK {
    // Contains recursion

    private int posX;
    private int posY;
    private int sizeX = 210;
    private int sizeY = 150;
    private int currentAnim = 6;
    private int level;
    private int aiLevel;
    private int globalCount = 0;
    private int aiThreshold = 8;
    private boolean stopHarm = false;
    private boolean marioDeath = false;
    private boolean marioWin = false;
    private boolean barrel = false;

    public void setLevelandAI(int level, int aiLevel) {
        this.level = level;
        this.aiLevel = 5 + aiLevel;
        if (this.level == 0) {
            posX = 348;
            posY = 270;
        }
        if (this.level == 1) {
            posX = 356;
            posY = 270;
        }
    }

    public void move(int posXMario, int posYMario) {
        if (stopHarm && globalCount < 3 && level == 0 && aiLevel < aiThreshold)
            whackBugs();
        if (stopHarm && globalCount < 3 && level == 0 && aiLevel >= aiThreshold)
            throwBarrel();
        if (stopHarm && globalCount < 3 && level == 1)
            throwBarrel();
        if (!stopHarm) {
            globalCount = 0;
            if (posY <= 50 && level == 0 || posY <= 110 && level == 1) {
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
                if (level == 0 && aiLevel < aiThreshold)
                    whackBugs();
                if (level == 0 && aiLevel >= aiThreshold)
                    throwBarrel();
                if (level == 1)
                    throwBarrel();
            }
        }
    }

    public int recycle(int currValue) {
        if(currValue >= 70) {
            return 70;
        }else {
            return (int)(Math.random() * 10) * 18;
        }
    }

    public int donkeyThrow(int currValue) {
        return (int)(Math.random() * 10) * 5 + recycle(currValue);
    }

    public void forceThrow() {
        throwBarrel();
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

    public void throwBarrel() {
        if (!marioWin) {
            if (globalCount == 0) {
                stopHarm = true;
                currentAnim = 7;
                barrel = false;
            }
            if (globalCount == 1) {
                stopHarm = true;
                currentAnim = 12;
                barrel = true;
            }
            if (globalCount == 2) {
                stopHarm = false;
                currentAnim = 6;
                barrel = false;
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

    public int getThreshold() {
        return aiThreshold;
    }

    public void setMarioWin() {
        marioWin = true;
    }

    public boolean getBarrel() {
        return barrel;
    }

    public boolean getIframe() {
        return stopHarm;
    }

    public int getAILevel() {
        return aiLevel;
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
