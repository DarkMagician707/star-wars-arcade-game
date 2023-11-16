public class Player {
    private double rx;
    private double ry;
    private double vx;
    private double vy;
    private double rad;
    private int theta = 0; // player starts on the y-axis by default therefore
    // 0 degrees rotated from the y-axis

    public Player(double x, double y, double v_x, double v_y, double radius) {
        rx = x;
        ry = y;
        vx = v_x;
        vy = v_y;
        rad = radius;
    }

    public double getRx() {
        return rx;
    }

    public double getRy() {
        return ry;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getRad() {
        return rad;
    }

    public int getTheta() {
        return theta;
    }

    public void setTheta(int theta) {
        this.theta = theta;
    }

    public void moveLeft() {
        rx = rx - vx;
    }

    public void moveRight() {
        rx = rx + vx;
    }

    public void rotateLeft() {
        if (theta > -90) {
            theta = theta - 5;
        }
    }

    public void rotateRight() {
        if (theta < 90) {
            theta = theta + 5;
        }
    }

    // used to predict the x-position of the player (with its hit box include) in the next frame
    // if the player were to move right
    public double addX() {
        return rx + vx + rad;
    }

    // used to predict the x-position of the player (with its hit box include) in the next frame
    // if the player were to move left
    public double subX() {
        return rx - vx - rad;
    }

    // Same logic as x-coordinate
    public double addY() {
        return ry + vy + rad;
    }

    public double subY() {
        return ry - vy - rad;
    }

    // checks if player pressed a movement key and that the player still be in the boundries
    public void checkPlayer() {
        if (StdDraw.isKeyPressed(68) && !(addX() > 1.0)) { // when D is pressed
            moveRight();
        }
        if (StdDraw.isKeyPressed(65) && !(subX() < -1.0)) { // when A is pressed
            moveLeft();
        }
        if (StdDraw.isKeyPressed(37)) { // when Left Arrow key is pressed
            rotateLeft();
        }
        if (StdDraw.isKeyPressed(39)) { // when Right Arrow key is pressed
            rotateRight();
        }
    }
}
