package game;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;

public class Game {

//    Draught[][] DASHBOARD = {{Draught.Color.RED, Draught.RED, Draught.RED, Draught.RED},
//            {Draught.RED,Draught.RED,Draught.RED, Draught.RED},
//            {Draught.RED,Draught.RED,Draught.RED, Draught.RED},
//            {Draught.NONE,Draught.NONE,Draught.NONE, Draught.NONE},
//            {Draught.NONE,Draught.NONE,Draught.NONE, Draught.NONE},
//            {Draught.BLACK,Draught.BLACK,Draught.BLACK, Draught.BLACK},
//            {Draught.BLACK,Draught.BLACK,Draught.BLACK, Draught.BLACK},
//            {Draught.BLACK,Draught.BLACK,Draught.BLACK, Draught.BLACK}};

    Draught[][] DASHBOARD = new Draught[8][4];

    int[][] positions = {{1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16},
            {17, 18, 19, 20},
            {21, 22, 23, 24},
            {25, 26, 27, 28},
            {29, 30, 31, 32}};

    Draught.Color MY_COLOR;
    Draught.Color THEIR_COLOR;

    public Game(String myColor) {
        if(myColor.equals("BLACK")) {
            this.MY_COLOR = Draught.Color.BLACK;
            this.THEIR_COLOR = Draught.Color.RED;
        }else{
            this.MY_COLOR = Draught.Color.RED;
            this.THEIR_COLOR = Draught.Color.BLACK;
        }
    }

    public ArrayList<Integer> getMove() {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Move> allMoves = new ArrayList<>();
//        list.add(21);
//        list.add(17);
        ArrayList<Move> captures = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 4; j++){
                if(DASHBOARD[i][j].color == MY_COLOR){
                    captures.addAll(checkCaptures(i,j));
                }
            }
        }

        if(captures.size() > 0){
            captures.sort(null);
            list.add(captures.get(0).getFrom());
            list.add(captures.get(0).getTo());
        }
        else{
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 4; j++){
                    if(DASHBOARD[i][j].color == MY_COLOR){
                        allMoves.addAll(moveWherever(i,j));
                    }
                }
            }
            System.out.println(allMoves);
            if(allMoves.size() > 0){
                Random rand = new Random();
                int n = rand.nextInt(allMoves.size());
                list.add(allMoves.get(n).getFrom());
                list.add(allMoves.get(n).getTo());
            }
        }

        System.out.println("MOVE "+list);

        return list;
    }

    private ArrayList<Move> checkCaptures(int row, int column) {
        ArrayList<Move> captureMoves = new ArrayList<>();
        Draught.Color color = DASHBOARD[row][column].color;
        ArrayList<Move> possibleMoves = possibleMoves(row, column);
        for (Move move : possibleMoves) {
            if (DASHBOARD[move.getToRow()][move.getToColumn()].color != color
                    && DASHBOARD[move.getToRow()][move.getToColumn()].color != Draught.Color.NONE) {
                Move toCapture = checkToCapture(move, row, column);
                if(toCapture != null){
                    captureMoves.add(toCapture);
                }
            }
        }
        return captureMoves;
    }

    public Move checkToCapture(Move move, int row, int column) {
        int r = move.getToRow();
        int c = move.getToColumn();
        if (r > row) {
            if (c == column && row % 2 == 0 && c - 1 > -1 && r + 1 < 8 && DASHBOARD[r + 1][c - 1].color == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r + 1][c - 1], 2, r + 1, c - 1);
            }
            if (c == column && row % 2 == 1 && c + 1 < 4 && r + 1 < 8 && DASHBOARD[r + 1][c + 1].color == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r + 1][c + 1], 2, r + 1, c + 1);
            }
//            } else if (r + 1 < 8 && DASHBOARD[r + 1][c].color == Draught.Color.NONE) {
//                return new Move(positions[row][column], positions[r + 1][c], 2, r + 1, c);
//            }
            if (c>column && r + 1 < 8 && DASHBOARD[r + 1][c].color == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r + 1][c], 2, r + 1, c);
            }if (c<column && r + 1 < 8 && DASHBOARD[r + 1][c].color == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r + 1][c], 2, r + 1, c);
            }
        } else {
            if (c == column && row % 2 == 0 && c - 1 > -1 && r - 1 > -1 && DASHBOARD[r - 1][c - 1].color == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r - 1][c - 1], 2, r - 1, c - 1);
            }
            if (c == column && row % 2 == 1 && c + 1 < 4 && r - 1 > -1 && DASHBOARD[r - 1][c + 1].color == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r - 1][c + 1], 2, r - 1, c + 1);

            }if (c>column && r - 1 > -1 && DASHBOARD[r - 1][c].color == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r - 1][c], 2, r - 1, c);
            }if (c<column && r - 1 > -1 && DASHBOARD[r - 1][c].color == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r - 1][c], 2, r - 1, c);
            }
        }
        return null;
    }

    public ArrayList<Move> possibleMoves(int row, int column) {
        ArrayList<Move> moves = new ArrayList<>();
        Draught.Color color = DASHBOARD[row][column].color;

        int r1, r2, c2;
        r1 = row + 1;
        r2 = row - 1;

        if (row % 2 == 1) {
            c2 = column - 1;
        } else {
            c2 = column + 1;
        }

        if (DASHBOARD[row][column].king) {
            if (r1 < 8) {
                moves.add(new Move(positions[row][column], positions[r1][column], 1, r1, column));
                if (c2 > -1 && c2 < 4) {
                    moves.add(new Move(positions[row][column], positions[r1][c2], 1, r1, c2));
                }
            }
            if (r2 > -1) {
                moves.add(new Move(positions[row][column], positions[r2][column], 1, r2, column));
                if (c2 > -1 && c2 < 4) {
                    moves.add(new Move(positions[row][column], positions[r2][c2], 1, r2, c2));
                }
            }
        } else {
            if (color == Draught.Color.RED) {
                if (r1 < 8) {
                    moves.add(new Move(positions[row][column], positions[r1][column], 1, r1, column));
                    if (c2 > -1 && c2 < 4) {
                        moves.add(new Move(positions[row][column], positions[r1][c2], 1, r1, c2));
                    }
                }
            } else {
                if (r2 > -1) {
                    moves.add(new Move(positions[row][column], positions[r2][column], 1, r2, column));
                    if (c2 > -1 && c2 < 4) {
                        moves.add(new Move(positions[row][column], positions[r2][c2], 1, r2, c2));
                    }
                }
            }
        }
        return moves;
    }

    public ArrayList<Move> moveWherever(int row, int column) {
        ArrayList<Move> possible = possibleMoves(row,column);

//        System.out.println(possible);

        return (ArrayList<Move>) possible.stream()
                .filter(x-> DASHBOARD[x.getToRow()][x.getToColumn()].color == Draught.Color.NONE)
                .collect(Collectors.toList());
    }

    public void parseBoard(JSONArray board) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                DASHBOARD[i][j] = new Draught(Draught.Color.NONE, false);
            }
        }

        Iterator<JSONObject> iterator = board.iterator();

        while (iterator.hasNext()) {
            JSONObject next = iterator.next();

            long row = (Long) next.get("row");
            long column = (Long) next.get("column");
            String color = (String) next.get("color");
            boolean king = (boolean) next.get("king");

            DASHBOARD[(int) row][(int) column] = color.equals("RED") ? new Draught(Draught.Color.RED, king) : new Draught(Draught.Color.BLACK, king);
        }

    }
}
