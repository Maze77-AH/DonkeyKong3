public class Flower {
    private int posX = 200;
    private int posY = 920;
    private int sizeX = 55;
    private int sizeY = 55;
    private boolean dragging = false;

    public void draggedAway(int posX, int posY) {
        this.posX = posX;
        this.posY -= 15;
        dragging = true;
        if (posY < 75) {
            posY = -50;
            dragging = true;
        }
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void dropped() {
        if (posY <= 900) {
            this.posY += 50;
            dragging = false;
        }
    }

    public boolean getDragged() {
        return dragging;
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
