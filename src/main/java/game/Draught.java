package game;

public class Draught {

    private Color color;
    private boolean king;

    public Draught(Color color, boolean king) {
        this.color = color;
        this.king = king;
    }

    public enum Color {
        RED, BLACK, NONE
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isKing() {
        return king;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    @Override
    public String toString() {
        return "Draught{" +
                "color=" + color +
                ", king=" + king +
                '}';
    }
}
