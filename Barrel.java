public class Barrel {
    private int posX;
    private int posY;
    private int sizeX = 50;
    private int sizeY = 50;
    private boolean thrown = false;;

    public Barrel() {

    }

    public void move() {
        this.posY += 70;
        if (posY >= 4000)
            thrown = false;
    }

    public void thrown(int posX, int posY) {
        thrown = true;
        this.posX = posX;
        this.posY = posY;
    }

    public boolean getThrown() {
        return thrown;
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
