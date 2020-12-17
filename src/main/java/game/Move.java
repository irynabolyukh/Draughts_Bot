package game;

public class Move {

    int from, to, heuristic;

    public Move(int from, int to, int heuristic) {
        this.from = from;
        this.to = to;
        this.heuristic = heuristic;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                ", heuristic=" + heuristic +
                '}';
    }
}
