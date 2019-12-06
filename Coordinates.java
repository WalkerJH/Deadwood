public class Coordinates {
    private int x;
    private int y;
    private int w;
    private int h;
    private boolean occupied;

    public Coordinates(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Coordinates addXY (Coordinates other) {
        int x = this.x + other.getX();
        int y  = this.y + other.getY();
        return new Coordinates(x, y, w, h);
    }
}
