public class Enemy extends Mario {

    // Enemy is a vital part of this game; moreso than Donkey Kong. Almost all recursion and inheritance is done in here

    public int variety;
    private int posX;
    private int posY;
    private int sizeX = 15;
    private int sizeY = 15;
    private int currentAnim = variety;
    private boolean goBack = false;
    private boolean pursue = false;
    private boolean attacker = false;
    private boolean right;
    public boolean death = false;

    public Enemy(int variety) {
        super();
        this.variety = variety;
        posY = 120;
        if ((int) (Math.random() * 10) > 5) {
            right = false;
            posX = 200;
        } else {
            right = true;
            posX = 700;
        }
    }

    public void move() {
        if (!getDeath() && !attacker) {
            if (currentAnim != variety + 1) {
                currentAnim++;
            } else {
                currentAnim = variety;
            }
        }
        if (!getDeath() && attacker) {
            if (currentAnim < 7) {
                currentAnim++;
            } else {
                currentAnim = 6;
            }
        }
        if (!getDeath() && !pursue && !attacker) {
            // Deal with X
            if (posX > 220 && !right && !pursue) {
                posX -= 5;
            } else {
                if ((int) (Math.random() * 10) > 5) {
                    posX += 5;
                } else {
                    posX -= 5;
                }
            }
            if (posX < 190 && !right && !pursue) {
                posX += 5;
            } else {
                if ((int) (Math.random() * 10) > 5) {
                    posX += 5;
                } else {
                    posX -= 5;
                }
            }
            if (posX > 720 && right && !pursue) {
                posX -= 5;
            } else {
                if ((int) (Math.random() * 10) > 5) {
                    posX += 5;
                } else {
                    posX -= 5;
                }
            }
            if (posX < 690 && right && !pursue) {
                posX += 5;
            } else {
                if ((int) (Math.random() * 10) > 5) {
                    posX += 5;
                } else {
                    posX -= 5;
                }
            }

            // Deal with Y

            if (posY > 220 && !pursue) {
                posY -= 5;
            } else {
                if ((int) (Math.random() * 10) > 5) {
                    posY += 5;
                } else {
                    posY -= 5;
                }
            }
            if (posY < 125 && !pursue) {
                posY += 5;
            } else {
                if ((int) (Math.random() * 10) > 5) {
                    posY += 5;
                } else {
                    posY -= 5;
                }
            }

            // Recurssion Method named checkingSpace
            if (checkingSpace(0, (int) (Math.random() * 1000)) == (int) (Math.random() * 1000)) {
                pursue = true;
                sizeX = 50;
                sizeY = 50;
            }
        }

        // Transfer Bees Nests

        if (!getDeath() && pursue && (int) (Math.random() * 10) == 5) {

            if (posY > 900) {
                posY -= 5;
            } else {
                posY += 5;
            }
            if (posY < 100) {
                posY += 5;
            } else {
                posY -= 5;
            }
        }

        // Deal with Y pursue

        if (!getDeath() && pursue && !goBack && !attacker) {

            if (!(posY > 925)) {
                posY += 5;
            }
            if (posY < 900) {
                posY += 5;
            } else {
                posY -= 5;
            }
            if (posY >= 899) {
                goBack = true;
            }
        }
        if (goBack && pursue && !attacker) {
            if (posY >= 50) {
                posY -= 3;
                if ((int) (Math.random() * 10) > 5) {
                    posX += 5;
                } else {
                    posX -= 5;
                }
            } else {
                goBack = false;
                attacker = true;
            }
        }

        // Deal with X

        if (!getDeath() && pursue && !attacker) {

            if (posX > super.getPosX() + 150) {
                posX -= 5;
            } else {
                posX += 5;
            }
            if (posY < super.getPosX() - 150) {
                posX += 5;
            } else {
                posX -= 5;
            }
        }

        if (attacker) {
            if ((int) (Math.random() * 100) < 50) {
                flyAttack();
            }
        }

        if (!pursue) {
            sizeX = 15;
            sizeY = 15;
        }
    }

    public void flyAttack() {
        posX = super.getPosX();
        posY += 70;
    }

    public int checkingSpace(int index, int y) {
        if (index == 75 && !getDeath() || index == y) {
            return 0;
        } else {
            return 1 + checkingSpace(index + 1, y);
        }
    }

    public boolean getDeath() {
        return death;
    }

    public boolean getPursue() {
        return pursue;
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
