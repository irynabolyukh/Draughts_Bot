package game;

public class Draught {

    Color color;
    boolean king;

    public Draught(Color color, boolean king) {
        this.color = color;
        this.king = king;
    }

    public enum Color {
        RED, BLACK, NONE
    }

    @Override
    public String toString() {
        return "Draught{" +
                "color=" + color +
                ", king=" + king +
                '}';
    }
}
