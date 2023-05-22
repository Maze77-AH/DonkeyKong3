public class Enemy extends DK {

    public int variety;
    private int posX;
    private int posY;
    private int sizeX = 15;
    private int sizeY = 15;
    private int currentAnim = 6;
    private boolean pursue;
    private boolean right;

    public Enemy(int variety) {
        super();
        pursue = false;
        this.variety = variety;
        posY = 120;
        if ((int)(Math.random() * 10) > 5) {
            right = false;
            posX = 200;
        }
        else {
            right = true;
            posX = 700;
        }
    }

    public void move() {
        if (!getDeath()) {
            if (currentAnim < 2 + variety + 1) {
                currentAnim += 1 + variety + 1;
            }
            else {
                currentAnim = 0;
            }
        }
        if (!getDeath() && !pursue) {
            // Deal with X
            if (posX > 220 && !right && !pursue) {
                posX -= 5;
            }
            else {
                if ((int)(Math.random() * 10) > 5) {
                    posX += 5;
                }
                else {
                    posX -= 5;
                }
            }
            if (posX < 190 && !right && !pursue) {
                posX += 5;
            }
            else {
                if ((int)(Math.random() * 10) > 5) {
                    posX += 5;
                }
                else {
                    posX -= 5;
                }
            }
            if (posX > 720 && right && !pursue) {
                posX -= 5;
            }
            else {
                if ((int)(Math.random() * 10) > 5) {
                    posX += 5;
                }
                else {
                    posX -= 5;
                }
            }
            if (posX < 690 && right && !pursue) {
                posX += 5;
            }
            else {
                if ((int)(Math.random() * 10) > 5) {
                    posX += 5;
                }
                else {
                    posX -= 5;
                }
            }

            // Deal with Y

            if (posY > 220 && !pursue) {
                posY -= 5;
            }
            else {
                if ((int)(Math.random() * 10) > 5) {
                    posY += 5;
                }
                else {
                    posY -= 5;
                }
            }
            if (posY < 125 && !pursue) {
                posY += 5;
            }
            else {
                if ((int)(Math.random() * 10) > 5) {
                    posY += 5;
                }
                else {
                    posY -= 5;
                }
            }

            // Recurssion Method named checkingSpace
            if (checkingSpace(0, (int)(Math.random() * 1000)) == (int)(Math.random() * 1000)) {
                pursue();
            }
        }

        if (!getDeath() && pursue) {
            // Deal with Y

            if (posY > 220 && !pursue) {
                posY -= 5;
            }
            else {
                if ((int)(Math.random() * 10) > 5) {
                    posY += 5;
                }
                else {
                    posY -= 5;
                }
            }
            if (posY < 125 && !pursue) {
                posY += 5;
            }
            else {
                if ((int)(Math.random() * 10) > 5) {
                    posY += 5;
                }
                else {
                    posY -= 5;
                }
            }
        }
    }

    public void pursue() {
        System.out.println("PURSUE");
        pursue = true;
    }

    public int checkingSpace(int index, int y) {
        if (index == 75 && !getDeath() || index == y) {
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
