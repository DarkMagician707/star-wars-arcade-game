import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class Game {
    public static int FRAME_COUNT = 0; // used to prevent from shooting infinite stream of missiles
    public static final int FRAME_CAP = 10;
    public static int FRAME_CAP_ENEMY = 80;
    public static final double PLAYER_RAD = 0.1;
    public static final double PLAYER_X = 0.0;
    public static final double PLAYER_Y = -1.0 + PLAYER_RAD + 0.05;
    public static final double PLAYER_VX = 0.04;

    public static final double ENEMY_RAD = 0.065;
    public static final double ENEMY_X = -1.0 + ENEMY_RAD;
    public static final double ENEMY_Y = 1.0 - ENEMY_RAD * 3;
    public static final double ENEMY_VX = 0.015;
    public static final double ENEMY_VY = 0.055;

    public static final double ENEMY_MISSILE_VY = 0.04;

    public static final int ENEMY_NUM = 7;
    public static final int ENEMY_ROWS = 3;
    public static int ENEMY_TOTAL = ENEMY_NUM * ENEMY_ROWS;
    public static boolean RIGHT = true; // says if enemies are moving right or left
    public static boolean DOWN = false; // says if enemies should move down

    public static double XSPACE = 0.0; // is the amount of space between each enemy on x-axis
    public static double YSPACE = 0.0; // is the amount of space between each enemy on y-axis

    public static int LIVES = 3;
    public static int POINTS = 0;

    public Game() {

    }

    public void play() { // Simulate the motion of player, enemies and missiles.
        StdDraw.setXscale(-1.0, 1.0);
        StdDraw.setYscale(-1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        ArrayList<Missile> missiles = new ArrayList<Missile>();
        Enemy[][] enemies = new Enemy[ENEMY_ROWS][ENEMY_NUM]; // array of enemies
        for (int i = 0; i < ENEMY_ROWS; i++) { // instantiates enemies in the lists
            for (int j = 0; j < ENEMY_NUM; j++) {
                enemies[i][j] = new Enemy(ENEMY_X + XSPACE, ENEMY_Y - YSPACE, ENEMY_VX, ENEMY_VY,
                                          ENEMY_RAD, true);
                XSPACE = XSPACE + 2 * ENEMY_RAD + 0.08;
            }
            XSPACE = 0;
            YSPACE = YSPACE + 2 * ENEMY_RAD + 0.02;
        }
        Player player = new Player(PLAYER_X, PLAYER_Y, PLAYER_VX, PLAYER_VX, PLAYER_RAD);

        while (LIVES > 0) {  // Update player's, enemies' and missiles' positions and draw it.
            if (StdDraw.isKeyPressed(27)) { // when ESC is pressed, close program
                System.exit(0);
            }
            if (StdDraw.isKeyPressed(81)) { // when Q is pressed
                LIVES = 0;
            }
            // when SPACE is pressed and only shoots every 10 frames
            if (StdDraw.isKeyPressed(32) && FRAME_COUNT > FRAME_CAP) {
                int theta = 90 - player
                        .getTheta(); // we minus 90 because our theta in player is angle from y-axis and trig functions work from the x-axis
                double vx = ENEMY_MISSILE_VY * Math.cos(Math.toRadians(theta));
                double vy = ENEMY_MISSILE_VY * Math.sin(Math.toRadians(theta));
                missiles.add(new Missile(player.getRx(), player.getRy() + player.getRad(), vx, vy,
                                         Color.GREEN, true));
                FRAME_COUNT = 0;
                StdAudio.playInBackground("audio/Blaster.wav"); // Source: https://freesound.org/
            }
            for (int i = 0; i < ENEMY_ROWS; i++) { // detects if enemy is hit by player missile
                for (int j = 0; j < ENEMY_NUM; j++) {
                    for (int k = 0; k < missiles.size(); k++) {
                        Enemy test = enemies[i][j];
                        Missile missile = missiles.get(k);
                        if (test.isActive() && missile
                                .isPlayerMissile()) { // only active enemies may be destroyed
                            if ((missile.getRx() > test.subX()) && (missile.getRx() < test.addX())
                                    && (missile.getRy() > test.subY()) && (missile.getRy() < test
                                    .addY())) { // checks if missile is in hit box of enemy
                                enemies[i][j].setActive(false);
                                missiles.remove(k);
                                ENEMY_TOTAL--;
                                POINTS = POINTS + 50;
                                StdAudio.playInBackground(
                                        "audio/Explode.wav"); // Source: https://freesound.org/
                            }
                        }
                    }
                }
            }
            if (ENEMY_TOTAL == 0) { // all enemies are dead, reset the enemies
                restart(enemies);
                missiles.clear();
            }
            checkPoints(enemies);
            player.checkPlayer();
            checkEnemy(enemies, player, missiles);
            for (int i = 0; i < ENEMY_ROWS; i++) { // moves each enemy
                for (int j = 0; j < ENEMY_NUM; j++) {
                    if (RIGHT) {
                        enemies[i][j].moveRight();
                        if (DOWN) {
                            enemies[i][j].moveDown();
                        }
                    }
                    else if (!RIGHT) {
                        enemies[i][j].moveLeft();
                        if (DOWN) {
                            enemies[i][j].moveDown();
                        }
                    }

                }
            }
            DOWN = false;
            enemyShoot(enemies, missiles);
            for (int j = 0; j < missiles.size(); j++) {
                if (missiles.get(j).checkMissile()) { // if missile is at a boundary, remove it
                    missiles.remove(j);
                }
                else { // moves missiles to new postions
                    missiles.get(j).moveUp();
                    missiles.get(j).moveX();
                }
            }
            checkPlayer(missiles, player, enemies);
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(-1.0, -1.0, 2.0);
            StdDraw.picture(player.getRx(), player.getRy(), "images/Tie.png", 0.2, 0.2,
                            (-1) * player.getTheta());
            // original image source: http://pixelartmaker.com/art/8ebedec01a2d550
            StdDraw.setPenColor(Color.white);

            // original image source used: http://pixelartmaker.com/art/8b48c8141c504dd
            for (int i = 0; i < ENEMY_ROWS; i++) { // draws each enemy
                for (int j = 0; j < ENEMY_NUM; j++) {
                    if (enemies[i][j].isActive()) {
                        StdDraw.picture(enemies[i][j].getRx(), enemies[i][j].getRy(),
                                        "images/Rebel fighter.png");
                    }
                }
            }
            for (int i = 0; i < missiles.size(); i++) {
                StdDraw.setPenColor(missiles.get(i).getColor());
                StdDraw.filledCircle(missiles.get(i).getRx(), missiles.get(i).getRy(),
                                     missiles.get(i).getRad());
            }
            StdDraw.setPenColor(Color.white);
            FRAME_COUNT++;
            updateEnemyFrame(enemies);
            StdDraw.setFont(new Font("Broadway", Font.BOLD, 24));
            StdDraw.text(-0.7, 1 - ENEMY_RAD - 0.03, "Lives: " + LIVES);
            StdDraw.text(0.5, 1 - ENEMY_RAD - 0.03, "Score: " + POINTS);
            StdDraw.show();
            StdDraw.pause(20);
        }
        reset();
    }

    // checks if any furthermost side enemies have reached the boundary
    // enemies must be alive and be the either furthermost right or left enemy in the grid
    public static void checkEnemy(Enemy[][] enemies, Player player, ArrayList<Missile> missiles) {
        int row = 0;
        int start = ENEMY_NUM - 1;
        int end = 0;
        int bottomRow = 0;
        for (int i = 0; i < ENEMY_ROWS; i++) {
            for (int j = 0; j < ENEMY_NUM; j++) {
                if (enemies[i][j].isActive()) {
                    bottomRow = i;
                    if (j < start) {
                        start = j;
                        row = i;
                    }
                    if (j > end) {
                        end = j;
                        row = i;
                    }
                }
            }
        }
        if (enemies[row][end].addX()
                > 1.0) { // if the right most enemy in reaches boundary
            RIGHT = false;
            DOWN = true;
        }
        if (enemies[row][start].subX()
                < -1.0) { // if the left most enemy reaches boundary
            RIGHT = true;
            DOWN = true;
        }
        for (int i = 0; i < ENEMY_NUM;
             i++) { // tests if an enemy reaches the bottom or touches the player
            Enemy test = enemies[bottomRow][i];
            if (test.subY() <= -1 || (((test.getRx() + test.getRad()) >= (player.getRx() - player
                    .getRad())
                    || (test.getRx() - test.getRad()) <= (player.getRx() + player.getRad())) &&
                    (test.getRy() - test.getRad()) <= (player.getRy() + player.getRad()))) {
                LIVES--;
                restart(enemies);
                missiles.clear();
                ENEMY_TOTAL = ENEMY_NUM * ENEMY_ROWS;
            }
        }
    }

    // resets the grid of enemies to default starting positions and resets any killed enemies
    public static void restart(Enemy[][] enemies) {
        XSPACE = 0;
        YSPACE = 0;
        for (int i = 0; i < ENEMY_ROWS; i++) { // instantiates enemies in the lists
            for (int j = 0; j < ENEMY_NUM; j++) {
                enemies[i][j].setAll(ENEMY_X + XSPACE, ENEMY_Y - YSPACE, ENEMY_RAD, true);
                XSPACE = XSPACE + 2 * ENEMY_RAD + 0.08;
            }
            XSPACE = 0;
            YSPACE = YSPACE + 2 * ENEMY_RAD + 0.02;
        }
        ENEMY_TOTAL = ENEMY_NUM * ENEMY_ROWS;
    }

    // resets the game variables to default values for when a new game instances is called
    public static void reset() {
        FRAME_COUNT = 0;
        ENEMY_TOTAL = ENEMY_NUM * ENEMY_ROWS;
        RIGHT = true;
        DOWN = false;
        XSPACE = 0.0;
        YSPACE = 0.0;
        LIVES = 3;
        POINTS = 0;
    }

    // lets enemies shoot back, by using a frame cap (i.e. shooting recharge)
    // and a probability that the enemy can shoot on this frame
    public static void enemyShoot(Enemy[][] enemies, ArrayList<Missile> missiles) {
        for (int i = 0; i < ENEMY_ROWS; i++) {
            for (int j = 0; j < ENEMY_NUM; j++) {
                if (enemies[i][j].isActive()) {
                    double random = Math.random();
                    if (random <= 0.005 && enemies[i][j].getFrameCount() > FRAME_CAP_ENEMY) {
                        missiles.add(new Missile(enemies[i][j].getRx(),
                                                 enemies[i][j].getRy() - enemies[i][j].getRad(), 0,
                                                 -ENEMY_MISSILE_VY, Color.red, false));
                        enemies[i][j].setFrameCount(0);
                    }
                }
            }
        }
    }

    // updates every enemy's frame counter so that they can shoot
    public void updateEnemyFrame(Enemy[][] enemies) {
        for (int i = 0; i < ENEMY_ROWS; i++) {
            for (int j = 0; j < ENEMY_NUM; j++) {
                enemies[i][j].incFrameCount();
            }
        }
    }

    // checks if enemy missile is in hit box of player
    public void checkPlayer(ArrayList<Missile> missiles, Player player, Enemy[][] enemies) {
        for (int i = 0; i < missiles.size(); i++) {
            if (!missiles.get(i).isPlayerMissile()) {
                Missile missile = missiles.get(i);
                if ((missile.getRx() > player.subX()) && (missile.getRx() < player.addX())
                        && (missile.getRy() > player.subY()) && (missile.getRy() < player.addY())) {
                    LIVES--;
                    missiles.remove(i);
                    restart(enemies);
                    missiles.clear();
                }
            }
        }
    }

    // checks the total points and based on that increases enemy difficulty
    public static void checkPoints(Enemy[][] enemies) {
        if (POINTS >= ENEMY_NUM * ENEMY_ROWS * 50 * 5) { // if player has points worth 5 waves
            FRAME_CAP_ENEMY = 73;
            for (int i = 0; i < ENEMY_ROWS; i++) {
                for (int j = 0; j < ENEMY_NUM; j++) {
                    enemies[i][j].setVx(0.03);
                    enemies[i][j].setVy(0.06);
                }
            }
        }
        else if (POINTS >= ENEMY_NUM * ENEMY_ROWS * 50 * 3) { // if player has points worth 3 waves
            FRAME_CAP_ENEMY = 77;
            for (int i = 0; i < ENEMY_ROWS; i++) {
                for (int j = 0; j < ENEMY_NUM; j++) {
                    enemies[i][j].setVx(0.025);
                    enemies[i][j].setVy(0.06);
                }
            }
        }
        else if (POINTS >= ENEMY_NUM * ENEMY_ROWS * 50) { // if player has points worth 1 wave
            for (int i = 0; i < ENEMY_ROWS; i++) {
                for (int j = 0; j < ENEMY_NUM; j++) {
                    enemies[i][j].setVx(0.02);
                    enemies[i][j].setVy(0.06);
                }
            }
        }
    }
}
