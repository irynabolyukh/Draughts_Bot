import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class Main {

   public static void main(String[] args) throws IOException, ParseException {

      JSONParser parser = new JSONParser();

      HttpClient client = HttpClient.newHttpClient();
//

//      GET INFO
//      HttpRequest infoRequest = HttpRequest.newBuilder()
//              .uri(URI.create("http://localhost:8081/game"))
//              .build();
//
//      String infoAnswer = client.sendAsync(infoRequest, BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
//
//      JSONObject json_info = (JSONObject) parser.parse(infoAnswer);
//      JSONObject data_info = (JSONObject) parser.parse(String.valueOf(json_info.get("data")));
//
//      int[] last = new int[2];
//      last = (int[]) data_info.get("last_move");
//
//      System.out.println(data_info.get("available_time"));




//      CONNECT TO GAME
//      HttpRequest connectRequest = HttpRequest.newBuilder()
//              .POST(HttpRequest.BodyPublishers.ofString(""))
//              .uri(URI.create("http://localhost:8081/game?team_name=jgh"))
//              .build();
//
//      String connect_answer = client.sendAsync(connectRequest, BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
//
//      JSONObject json_connect = (JSONObject) parser.parse(connect_answer);
//      JSONObject data_connect = (JSONObject) parser.parse(String.valueOf(json_connect.get("data")));
//
//      System.out.println(json_connect);
//
//      String my_color = String.valueOf(data_connect.get("color"));
//      String my_token = String.valueOf(data_connect.get("token"));
//
//      System.out.println("Color: " + my_color + " & Token: " + my_token);


//      MAKE MOVE TEXT
//      HttpRequest moveRequest = HttpRequest.newBuilder()
//              .header("Authorization", "Token 606f4d445b7f1c20926ccd17b68b0e54")
//              .POST(HttpRequest.BodyPublishers.ofString(" \"move\": [9, 13]\n"))
//              .uri(URI.create("http://localhost:8081/move"))
//              .build();
//
//      String move_answer = client.sendAsync(moveRequest, BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
//
//      JSONObject json_move = (JSONObject) parser.parse(move_answer);
//      String data_move = String.valueOf(json_move.get("data"));
//
//      System.out.println(data_move);

   }

}