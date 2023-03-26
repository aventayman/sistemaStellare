package it.unibs.fp.sistemaStellare;

public class Posizione {
    private float x, y;

    public Posizione () {}

    public Posizione (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%7.2f,%7.2f)", x, y);
    }
}
