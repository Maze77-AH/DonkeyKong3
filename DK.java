public class DK extends Mario {

    private int posX;
    private int posY;
    private int sizeX = 210;
    private int sizeY = 150;
    private int anim = 5;
    private int aiLevel;

    public DK(int aiLevel) {
        this.aiLevel = aiLevel;
        //if (getLevel() == 0) {
            posX = 348;
            posY = 270;
        //}
    }

    public void move() {
        posY += aiLevel;
        if(anim < 7)
            anim++;
        else
            anim = 5;
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
