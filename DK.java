public class DK extends Mario {
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
    private int[] selectPositionX = {6, 2, -2, 8, -8, 9};
    private int[] selectPositionY = {1, 1, 1, 1, 1, -1};

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
        if (!(posY >= 340 && posY < 350)) {
            if (stopHarm && globalCount < 3 && level == 0 && aiLevel < aiThreshold)
                whackBugs();
            if (stopHarm && globalCount < 3 && level == 0 && aiLevel >= aiThreshold)
                throwBarrel();
            if (stopHarm && globalCount < 3 && level == 1)
                throwBarrel();
        }
        if (!stopHarm) {
            globalCount = 0;
            if (posY <= 50 && level == 0 || posY <= 110 && level == 1) {
                marioWin = true;
                currentAnim = 5;
                sizeY = 150;
            }
            else if (posY >= 350) {
                marioDeath = true;
                if (currentAnim == 10)
                    currentAnim = 9;
                else if (currentAnim != 9 || currentAnim != 10)
                    currentAnim = 10;
                posX = posXMario - 100;
                posY = posYMario;
                sizeY = 150;
            }
            else if (posY >= 340 && posY < 350) {
                posY += aiLevel;
                currentAnim = 14;
                sizeY = 200;
            }
            else {
                sizeY = 150;
                posY += aiLevel;
                if (currentAnim < 7)
                    currentAnim++;
                else
                    currentAnim = 6;
            }
            if (!(posY >= 340 && posY < 350)) {
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
    }

    public int randomX() {
        return selectPositionX[(int)(Math.random() * selectPositionX.length)];
    }

    public int donkeyThrow(int currValue) {
        if(currValue >= 70) {
            return 0;
        }else {
            return selectPositionY[(int)(Math.random() * selectPositionY.length)] + donkeyThrow(currValue + 1);
        }
    }

    public int getMarioPosX() {
        return super.getPosX();
    }

    public void forceThrow() {
        throwBarrel();
    }

    public void whackBugs() {
        if (!marioWin) {
            sizeY = 150;
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
            sizeY = 150;
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
            sizeY = 150;
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
