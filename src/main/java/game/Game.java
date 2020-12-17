package game;

import org.json.simple.JSONObject;

public class Game {

   Draught[][] DASHBOARD = {{Draught.RED, Draught.RED, Draught.RED, Draught.RED},
           {Draught.RED,Draught.RED,Draught.RED, Draught.RED},
           {Draught.RED,Draught.RED,Draught.RED, Draught.RED},
           {Draught.NONE,Draught.NONE,Draught.NONE, Draught.NONE},
           {Draught.NONE,Draught.NONE,Draught.NONE, Draught.NONE},
           {Draught.BLACK,Draught.BLACK,Draught.BLACK, Draught.BLACK},
           {Draught.BLACK,Draught.BLACK,Draught.BLACK, Draught.BLACK},
           {Draught.BLACK,Draught.BLACK,Draught.BLACK, Draught.BLACK}};

   Draught MY_COLOR;
   Draught THEIR_COLOR;

   public Game(String myColor) {
      switch (myColor){
         case "RED":
            this.MY_COLOR = Draught.RED;
         default:
            this.MY_COLOR = Draught.BLACK;
      }

   }

   public enum Draught {
      RED, BLACK, NONE
   }

   public void parseBoard(JSONObject board){

   }

}
