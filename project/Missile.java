import java.awt.Color;

public class Missile {
    private double rx;
    private double ry;
    private double vy;
    private double vx;
    private Color color;
    private double rad = 0.01;
    private boolean playerMissile;
            // indicates if it was the player or an enemy that shot the missile

    public Missile(double x, double y, double sx, double sy, Color col, boolean status) {
        rx = x;
        ry = y;
        vx = sx;
        vy = sy;
        color = col;
        playerMissile = status;
    }

    public double getRx() {
        return rx;
    }

    public double getRy() {
        return ry;
    }

    public double getRad() {
        return rad;
    }

    public boolean isPlayerMissile() {
        return playerMissile;
    }

    public void moveUp() {
        ry = ry + vy;
    }

    public void moveX() {
        rx = rx + vx;
    }

    // used to predict the y-position of the missile (with its hitbox include) in the next frame
    public double addY() {
        return ry + vy + rad;
    }

    // used to predict the x-position of the missile (with its hitbox include) in the next frame
    public double addX() {
        return rx + vx + rad;
    }

    public Color getColor() {
        return color;
    }

    // checks if a missile has reached a boundry
    public boolean checkMissile() {
        if ((addY() > 1.0 || addY() < -1.0) && (addX() > 1.0 || addX() < -1.0)) {
            return true;
        }
        else {
            return false;
        }
    }
}
