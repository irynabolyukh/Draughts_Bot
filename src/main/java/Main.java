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

      JSONObject getTime = getGameInfo();
      double timeForMove = (double) getTime.get("available_time");
      System.out.println(timeForMove*1000/3);

      Game game = new Game(myColor);

      try{
         Thread.sleep((long)timeForMove);
      }catch(InterruptedException e){
         System.out.println(e);
      }

      boolean isFinished = false;

      while (!isFinished) {
         JSONObject info = getGameInfo();
         isFinished = (boolean) info.get("is_finished");

         if (myColor.equals(info.get("whose_turn"))){
            JSONParser parser = new JSONParser();
            game.parseBoard((JSONArray) parser.parse(String.valueOf(info.get("board"))));
            game.parseLastMove((JSONObject) parser.parse(String.valueOf(info.get("last_move"))));

            ArrayList<Integer> move;
            boolean moveMade = false;

            do{
               JSONObject checkIsFinished = getGameInfo();
               isFinished = (boolean) checkIsFinished.get("is_finished");
               move = game.getMove();
               if(move.size()>0) {
                  moveMade = move(myToken, move);
               }
               try{
                  Thread.sleep(700);
               }catch(InterruptedException e){
                  System.out.println(e);
               }
            }while(!moveMade && !isFinished);

         }

         try{
            Thread.sleep((long)timeForMove);
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

   public static JSONObject connect(){
      String connectURL = "http://localhost:8081/game?team_name=";
      String teamName = "red";
      JSONParser parser = new JSONParser();
      JSONObject connectAnswer = connectToGame(connectURL+teamName);
      try {
         JSONObject dataConnect = (JSONObject) parser.parse(String.valueOf(connectAnswer.get("data")));
         return dataConnect;
      }catch (Exception e){
         throw new RuntimeException("Can't connect");
      }
   }

   public static boolean move(String  myToken,  ArrayList<Integer> moves) throws JsonProcessingException {
      Map<Object, Object> values = new HashMap<>();
      values.put("move", moves);
      String moveURL = "http://localhost:8081/move";
      var objectMapper = new ObjectMapper();
      String requestBody = objectMapper.writeValueAsString(values);

      JSONObject moveAnswer = makeMove(moveURL, requestBody, myToken);

      try{
         moveAnswer.get("data");
         return true;
      } catch (Exception e){
         e.printStackTrace();
         return false;
      }

   }
}