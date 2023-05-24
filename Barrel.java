public class Barrel {
    private int posX;
    private int posY;
    private int sizeX = 15;
    private int sizeY = 15;
    private boolean thrown = false;;

    public Barrel() {

    }

    public void thrown(int posX, int posY) {
        thrown = true;
    }

    public boolean getThrown() {
        return thrown;
    }
}
