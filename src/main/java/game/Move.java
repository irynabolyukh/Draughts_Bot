package game;

public class Move {

    int from, to, heuristic, toRow, toColumn;

    public Move(int from, int to, int heuristic, int toRow, int toColumn) {
        this.from = from;
        this.to = to;
        this.heuristic = heuristic;
        this.toRow = toRow;
        this.toColumn = toColumn;
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

    public int getToRow() {
        return toRow;
    }

    public void setToRow(int toRow) {
        this.toRow = toRow;
    }

    public int getToColumn() {
        return toColumn;
    }

    public void setToColumn(int toColumn) {
        this.toColumn = toColumn;
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
