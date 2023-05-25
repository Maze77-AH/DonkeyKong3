public class Pump {
    private int posX = 517;
    private int posY = 120;
    private int sizeX = 25;
    private int sizeY = 75;
    private boolean dropping = false;

    public void setPump(boolean pump) {
        if (!pump)
            forceOff();
    }

    public void forceOff() {
        posY = 1050;
    }

    public void dropped() {
        dropping = true;
        if (posY < 750) {
            this.posY += 15;
        }
        else {
            dropping = false;
        }
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
}
