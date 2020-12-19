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
    int[] lastPosition = new int[2];
    boolean isLastMoveMine = false;

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

        if(isLastMoveMine){
            captures.addAll(checkCaptures(lastPosition[0],lastPosition[1], DASHBOARD));
            list.add(captures.get(0).getFrom());
            list.add(captures.get(0).getTo());

            lastPosition[0] = captures.get(0).getToRow();
            lastPosition[1] = captures.get(0).getToColumn();
            return list;
        }

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 4; j++){
                if(DASHBOARD[i][j].getColor() == MY_COLOR){
                    captures.addAll(checkCaptures(i,j, DASHBOARD));
                }
            }
        }

        if(captures.size() > 0){
            captures.sort(null);
            list.add(captures.get(0).getFrom());
            list.add(captures.get(0).getTo());

            lastPosition[0] = captures.get(0).getToRow();
            lastPosition[1] = captures.get(0).getToColumn();
        }
        else{
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 4; j++){
                    if(DASHBOARD[i][j].getColor() == MY_COLOR){
                        allMoves.addAll(moveWherever(i, j, DASHBOARD));
                    }
                }
            }
            System.out.println(allMoves);
            if(allMoves.size() > 0){
                Random rand = new Random();
                int n = rand.nextInt(allMoves.size());
                list.add(allMoves.get(n).getFrom());
                list.add(allMoves.get(n).getTo());

                lastPosition[0] = allMoves.get(n).getToRow();
                lastPosition[1] = allMoves.get(n).getToColumn();
            }
        }

        System.out.println("MOVE "+list);


        return list;
    }

    private int getPossibleLosses(int from, int to){

        Draught [][] updatedBoard = new Draught[8][4];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 4; j++){
                updatedBoard[i][j] = DASHBOARD[i][j];
            }
        }

        int fromRow, fromColumn, toRow, toColumn;
        fromRow = from/4;
        fromColumn = from%4==0 ? 3 : from%4 - 1;
        toRow = to/4;
        toColumn = to%4==0 ? 3 : to%4 - 1;


        updatedBoard[fromRow][fromColumn] = new Draught(Draught.Color.NONE, false);
        updatedBoard[toRow][toColumn] = new Draught(MY_COLOR, false);

        if( Math.abs(from - to) > 5 ){
            int removedPosition = (from + to + 1)/2;
            int removedRow, removedColumn;
            removedRow = removedPosition/4;
            removedColumn = removedPosition%4==0 ? 3 : removedPosition%4 - 1;

            updatedBoard[removedRow][removedColumn] = new Draught(Draught.Color.NONE, false);
        }

        int possibleLosses = 0;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 4; j++){
                if(updatedBoard[i][j].getColor() == THEIR_COLOR){
                    possibleLosses += checkCaptures(i, j, updatedBoard).size();
                }
            }
        }
        return possibleLosses;
    }

    private ArrayList<Move> checkCaptures(int row, int column, Draught[][] dashboard) {
        ArrayList<Move> captureMoves = new ArrayList<>();
        Draught.Color color = dashboard[row][column].getColor();
        ArrayList<Move> possibleMoves = possibleMoves(row, column, dashboard);
        for (Move move : possibleMoves) {
            if (dashboard[move.getToRow()][move.getToColumn()].getColor() != color
                    && dashboard[move.getToRow()][move.getToColumn()].getColor() != Draught.Color.NONE) {
                Move toCapture = checkToCapture(move, row, column, dashboard);
                if(toCapture != null){
                    toCapture.setHeuristic(toCapture.getHeuristic()-getPossibleLosses(toCapture.getFrom(), toCapture.getTo()));
                    captureMoves.add(toCapture);
                }
            }
        }
        return captureMoves;
    }

    public Move checkToCapture(Move move, int row, int column, Draught[][] dashboard) {
        int r = move.getToRow();
        int c = move.getToColumn();
        if (r > row) {
            if (c == column && row % 2 == 0 && c - 1 > -1 && r + 1 < 8 && dashboard[r + 1][c - 1].getColor() == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r + 1][c - 1], 2, r + 1, c - 1);
            }
            if (c == column && row % 2 == 1 && c + 1 < 4 && r + 1 < 8 && dashboard[r + 1][c + 1].getColor() == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r + 1][c + 1], 2, r + 1, c + 1);
            }
//            } else if (r + 1 < 8 && DASHBOARD[r + 1][c].color == Draught.Color.NONE) {
//                return new Move(positions[row][column], positions[r + 1][c], 2, r + 1, c);
//            }
            if (c>column && r + 1 < 8 && dashboard[r + 1][c].getColor() == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r + 1][c], 2, r + 1, c);
            }if (c<column && r + 1 < 8 && dashboard[r + 1][c].getColor() == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r + 1][c], 2, r + 1, c);
            }
        } else {
            if (c == column && row % 2 == 0 && c - 1 > -1 && r - 1 > -1 && dashboard[r - 1][c - 1].getColor() == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r - 1][c - 1], 2, r - 1, c - 1);
            }
            if (c == column && row % 2 == 1 && c + 1 < 4 && r - 1 > -1 && dashboard[r - 1][c + 1].getColor() == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r - 1][c + 1], 2, r - 1, c + 1);

            }if (c>column && r - 1 > -1 && dashboard[r - 1][c].getColor() == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r - 1][c], 2, r - 1, c);
            }if (c<column && r - 1 > -1 && dashboard[r - 1][c].getColor() == Draught.Color.NONE) {
                return new Move(positions[row][column], positions[r - 1][c], 2, r - 1, c);
            }
        }
        return null;
    }

    public ArrayList<Move> possibleMoves(int row, int column, Draught[][] dashboard) {
        ArrayList<Move> moves = new ArrayList<>();
        Draught.Color color = dashboard[row][column].getColor();

        int r1, r2, c2;
        r1 = row + 1;
        r2 = row - 1;

        if (row % 2 == 1) {
            c2 = column - 1;
        } else {
            c2 = column + 1;
        }

        if (dashboard[row][column].isKing()) {
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

    public ArrayList<Move> moveWherever(int row, int column, Draught[][] dashboard) {
        ArrayList<Move> possible = possibleMoves(row,column,dashboard);

        return (ArrayList<Move>) possible.stream()
                .filter(x-> dashboard[x.getToRow()][x.getToColumn()].getColor() == Draught.Color.NONE)
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

    public void parseLastMove(JSONObject last_move) {
        if(last_move != null){
            String player = String.valueOf(last_move.get("player"));
            isLastMoveMine = player.equals(MY_COLOR.toString());
        }
    }
}
