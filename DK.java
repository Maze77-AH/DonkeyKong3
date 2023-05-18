public class DK extends Mario {

    private int posX;
    private int posY;
    private int sizeX = 210;
    private int sizeY = 150;
    private int anim = 5;
    private int level;
    private int aiLevel;

    public DK(int level, int aiLevel) {
        this.level = level;
        this.aiLevel = 5 + aiLevel;
        if (this.level == 0) {
            posX = 348;
            posY = 270;
        }
    }

    public void move() {
        if (posY >= 300) {
            super.setDeath();
            if(anim == 10)
                anim = 9;
            else if(anim != 9 || anim != 10)
                anim = 10;
            posY = super.getPosY();
        }
        else {
            posY += aiLevel;
            if(anim < 7)
                anim++;
            else
                anim = 5;
        }
    }

    public int getAnim() {
        return anim;
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
