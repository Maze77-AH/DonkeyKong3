public class Flower {
    private int posX = 200;
    private int posY = 920;
    private int sizeX = 55;
    private int sizeY = 55;
    private boolean dragging = false;
    private boolean dropping = false;

    public void draggedAway(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        dragging = true;
        if (posY < 250) {
            posY = -100;
            dragging = true;
        }
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void dropped() {
        dragging = false;
        dropping = true;
        if (posY < 920) {
            this.posY += 15;
        }
        else {
            dropping = false;
        }
    }

    public boolean getDragged() {
        return dragging;
    }

    public boolean getDropping() {
        return dropping;
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

    public String toString() {
        return "Flower";
    }
}
