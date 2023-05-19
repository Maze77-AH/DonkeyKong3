public class Enemy extends DonkeyKong3 {
    public int rarity;
    public int[] enemyVariety = new int[8] {0, 2, 4, 1, 2, 3, 1, 0};

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
