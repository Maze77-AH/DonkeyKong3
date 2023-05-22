public class Enemy extends DK {

    public int aiLevel;
    public int[] enemyVariety = new int[8];
    private int posX;
    private int posY;
    private int sizeX = 15;
    private int sizeY = 15;
    private int currentAnim = 6;

    public Enemy(int aiLevel) {
        super();
        this.aiLevel = aiLevel;
        for (int x = 0; x < enemyVariety.length; x++) {
            enemyVariety[x] = (int) (Math.random() * 4);
        }
    }

    public int checkingSpace(int index, int y) {
        if (y == 75 && !super.getDeath()) {
            return 0;
        } else {
            return 1 + checkingSpace(index + 1, y);
        }
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
