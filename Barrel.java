public class Barrel extends DK {
    // Contains inheritance from a recursion method

    private int posX;
    private int posY;
    private int velocity = 70;
    private int sizeX = 50;
    private int sizeY = 50;
    private int ranX;
    private int aiLevel;
    private boolean thrown = false;

    public Barrel() {
        super();
    }

    public void setAI(int aiLevel) {
        this.aiLevel = aiLevel;
    }

    public void setVelocity() {
        ranX = super.randomX();
        velocity = super.donkeyThrow(ranX, false);
    }

    public void setVelocityConstant() {
        velocity = super.donkeyThrow(50, true);
    }

    public void reset(int posY) {
        this.posY = posY;
    }

    public void move() {
        if (aiLevel <= 15)
            setVelocityConstant();
        else
            setVelocity();
        this.posY += velocity;
        this.posX += ranX;
        if (posY >= 1050)
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
