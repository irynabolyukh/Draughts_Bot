package serverCommunication;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequests {
   static HttpURLConnection connection;
   private static final JSONParser parser = new JSONParser();

   public static JSONObject gameInfo(String targetURL) {

      try {
         //Create connection
         URL url = new URL(targetURL);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("GET");
         connection.setRequestProperty("Content-Language", "en-US");

         connection.setUseCaches(false);
         connection.setDoOutput(true);

         //Get Response
         InputStream is = connection.getInputStream();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
         String line;
         while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
         }
         rd.close();
         return (JSONObject) parser.parse(response.toString());
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      } finally {
         if (connection != null) {
            connection.disconnect();
         }
      }
   }

   public static JSONObject connectToGame(String targetURL) {

      try {
         //Create connection
         URL url = new URL(targetURL);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("POST");

         connection.setRequestProperty("Content-Language", "en-US");

         connection.setUseCaches(false);
         connection.setDoOutput(true);

         //Send request
         DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
         wr.close();

         //Get Response
         InputStream is = connection.getInputStream();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
         String line;
         while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
         }
         rd.close();
         return (JSONObject) parser.parse(response.toString());
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      } finally {
         if (connection != null) {
            connection.disconnect();
         }
      }
   }

   public static JSONObject makeMove(String targetURL, String urlParameters, String my_token) {

      try {
         //Create connection
         URL url = new URL(targetURL);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("POST");
         connection.setRequestProperty("Content-Type", "application/json");
         connection.setRequestProperty("Authorization", "Token " + my_token);

         connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
         connection.setRequestProperty("Content-Language", "en-US");

         connection.setUseCaches(false);
         connection.setDoOutput(true);

         //Send request
         DataOutputStream wr = new DataOutputStream (
                 connection.getOutputStream());
         wr.writeBytes(urlParameters);
         wr.close();

         //Get Response
         InputStream is = connection.getInputStream();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
         String line;
         while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
         }
         rd.close();
         return (JSONObject) parser.parse(response.toString());
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      } finally {
         if (connection != null) {
            connection.disconnect();
         }
      }
   }
}
