public class BugSpray extends Mario {
    private boolean extreme;
    private boolean registerHit = false;
    private boolean spraying = false;
    private int posX;
    private int posY;
    private int sizeX = 25;
    private int sizeY = 25;
    private int currentAnim = 0;

    public BugSpray() {
        super();
        currentAnim = 0;
    }

    public void setExtreme(boolean extreme) {
        this.extreme = extreme;
    }

    public void puff(int posXMario, int posYMario) {
        if (super.getDeath() == false) {
            spraying = true;
            posX = posXMario;
            posY = posYMario;
        }
    }

    public void animSet(int dkPosX, int dkPosY) {
        if (!extreme) {
            if (currentAnim < 7) {
                currentAnim++;
            } else {
                currentAnim = 8;
                spraying = false;
                currentAnim = 0;
            }
        } else {
            if (currentAnim < 17 && currentAnim > 10) {
                currentAnim++;
            } else if (currentAnim < 10) {
                currentAnim = 11;
            }
            else {
                spraying = false;
                currentAnim = 0;
            }
        }
    }

    public void movement() {
        if (spraying) {
            if (extreme)
                this.posY -= 40;
            else
                this.posY -= 30;
        }
    }

    public boolean getRegisterHit() {
        return registerHit;
    }

    public boolean getSpraying() {
        return spraying;
    }

    public void forceSprayOff() {
        spraying = false;
        posY = 0;
        currentAnim = 0;
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
