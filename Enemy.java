public class Enemy extends DonkeyKong3 {
    public int rarity;

    public Enemy(int rarity) {
        this.rarity = rarity;
    }

    public int checkingSpace(int index, int y) {
        if (y == 75) {
            return 0;
        }
        else {
            return 1 + checkingSpace(index + 1, y);
        }
    }
}
