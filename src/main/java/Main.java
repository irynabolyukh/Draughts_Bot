import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static httpConnection.HttpRequests.*;

public class Main {

   public static void main(String[] args) throws IOException, ParseException {

      JSONParser parser = new JSONParser();

      //GET INFO
      String infoURL = "http://localhost:8081/game";
      JSONObject infoAnswer = gameInfo(infoURL);
      JSONObject gameInfo = (JSONObject) parser.parse(String.valueOf(infoAnswer.get("data")));
      System.out.println(gameInfo);



      //CONNECT TO GAME
      String connectURL = "http://localhost:8081/game?team_name=";
      String teamName = "Black";
      JSONObject connectAnswer = connectToGame(connectURL+teamName);

      JSONObject data_connect = (JSONObject) parser.parse(String.valueOf(connectAnswer.get("data")));
      String my_color = String.valueOf(data_connect.get("color"));
      String my_token = String.valueOf(data_connect.get("token"));
      System.out.println("Color: " + my_color + " & Token: " + my_token);



      //MAKE MOVE
      ArrayList<Integer> moves = new ArrayList<>();
      moves.add(22);
      moves.add(17);
      Map<Object, Object> values = new HashMap<>();
      values.put("move", moves);
      String myToken = "a9272b80bb19b79a1ce0a84820db4a46";
      String moveURL = "http://localhost:8081/move";
      var objectMapper = new ObjectMapper();
      String requestBody = objectMapper.writeValueAsString(values);

      JSONObject moveAnswer = makeMove(moveURL, requestBody, myToken);
      String dataMove = String.valueOf(moveAnswer.get("data"));
      System.out.println(dataMove);

   }
}