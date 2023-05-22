public class Enemy extends DK {

    public int variety;
    private int posX;
    private int posY;
    private int sizeX = 15;
    private int sizeY = 15;
    private int currentAnim = 6;
    private boolean right;

    public Enemy(int variety) {
        super();
        this.variety = variety;
        posY = 120;
        if ((int)(Math.random() * 10) == 5) {
            right = false;
            posX = 200;
        }
        else {
            right = true;
            posX = 700;
        }
    }

    public void move() {
        if (posX > 220 && !right) {
            posX -= 5;
        }
        if (posX > 190 && !right) {
            posX += 5;
        }
        if (posX > 720 && right) {
            posX -= 5;
        }
        if (posX > 690 && right) {
            posX += 5;
        }
    }

    public int checkingSpace(int index, int y) {
        if (y == 75 && !super.getDeath()) {
            return 0;
        } else {
            return 1 + checkingSpace(index + 1, y);
        }
    }

    public int getVariety() {
        return variety;
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
