package info.sigmaproject.ga.robotcontroller;

public class XYPair {
    private final int x;
    private final int y;

    public XYPair(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XYPair xyPair = (XYPair) o;

        if (x != xyPair.x) return false;
        return y == xyPair.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
