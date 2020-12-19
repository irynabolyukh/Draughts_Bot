import java.io.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.Game;
import game.Move;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static serverCommunication.HttpRequests.*;

public class Main {

   public static void main(String[] args) throws IOException, ParseException {

      JSONObject dataConnect = connect();
      String myColor = String.valueOf(dataConnect.get("color"));
      String myToken = String.valueOf(dataConnect.get("token"));
      System.out.println("Color: " + myColor + " & Token: " + myToken);

      Game game = new Game(myColor);

      try{
         Thread.sleep(1000);
      }catch(InterruptedException e){
         System.out.println(e);
      }

      ArrayList<Integer> moves = new ArrayList<>();

      boolean isFinished = false;

      while (!isFinished) {
         JSONObject info = getGameInfo();
         isFinished = (boolean) info.get("is_finished");

         if (myColor.equals(info.get("whose_turn"))){
            JSONParser parser = new JSONParser();
            game.parseBoard((JSONArray) parser.parse(String.valueOf(info.get("board"))));

            ArrayList<Integer> move = game.getMove();

            if(move.size()>0){
               move(myToken, move);
            }
         }

         try{
            Thread.sleep(1000);
         }catch(InterruptedException e){
            System.out.println(e);
         }
      }
   }

   public static JSONObject getGameInfo() throws ParseException {
      String infoURL = "http://localhost:8081/game";
      JSONParser parser = new JSONParser();
      JSONObject infoAnswer = gameInfo(infoURL);
      JSONObject info = (JSONObject) parser.parse(String.valueOf(infoAnswer.get("data")));
      return info;
   }

   public static JSONObject connect() throws ParseException {
      String connectURL = "http://localhost:8081/game?team_name=";
      String teamName = "red";
      JSONParser parser = new JSONParser();
      JSONObject connectAnswer = connectToGame(connectURL+teamName);
      JSONObject dataConnect = (JSONObject) parser.parse(String.valueOf(connectAnswer.get("data")));
      return dataConnect;
   }

   public static String move(String  myToken,  ArrayList<Integer> moves) throws JsonProcessingException {
      Map<Object, Object> values = new HashMap<>();
      values.put("move", moves);
      String moveURL = "http://localhost:8081/move";
      var objectMapper = new ObjectMapper();
      String requestBody = objectMapper.writeValueAsString(values);

      JSONObject moveAnswer = makeMove(moveURL, requestBody, myToken);
      String dataMove = String.valueOf(moveAnswer.get("data"));

      return dataMove;
   }
}