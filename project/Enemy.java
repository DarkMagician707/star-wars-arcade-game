public class Enemy {
    private double rx;
    private double ry;
    private double vx;
    private double vy;
    private double rad;
    private boolean active; // says if the enemy is alive or not
    private double frameCount = 0;

    public Enemy(double x, double y, double sx, double sy, double radius, boolean act) {
        rx = x;
        ry = y;
        vx = sx;
        vy = sy;
        rad = radius;
        active = act;
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

    public boolean isActive() {
        return active;
    }

    public void setRx(double rx) {
        this.rx = rx;
    }

    public void setRy(double ry) {
        this.ry = ry;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setRad(double rad) {
        this.rad = rad;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void moveLeft() {
        rx = rx - vx;
    }

    public void moveRight() {
        rx = rx + vx;
    }

    public void moveDown() {
        ry = ry - vy;
    }

    public double addX() {
        return rx + vx + rad;
    }

    public double subX() {
        return rx - vx - rad;
    }

    public double addY() {
        return ry + vy + rad;
    }

    public double subY() {
        return ry - vy - rad;
    }

    public void setAll(double x, double y, double radius, boolean act) {
        rx = x;
        ry = y;
        rad = radius;
        active = act;
    }
    public double getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(double frameCount) {
        this.frameCount = frameCount;
    }

    public void incFrameCount() {
        frameCount++;
    }

}
