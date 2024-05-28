package blossom.reports_service.model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiTest {
  public static void main(String[] args) {
    try {
      // API endpoint
      String category = "happiness";
      URI uri = new URI("https://api.api-ninjas.com/v1/quotes?category=" + category);
      URL url = uri.toURL();

      // Establish connection
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("X-Api-Key", "nEoiuHE++sApITJEJkZyyQ==0O7c8zWfpTUONcKJ"); // Replace with your API
                                                                                              // key
      connection.setRequestProperty("accept", "application/json");

      // Read response
      InputStream responseStream = connection.getInputStream();
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(responseStream);

      // Print the response (quotes array)
      System.out.println(root.toString());

      // Iterate and print individual quotes
      for (JsonNode quoteNode : root) {
        String quote = quoteNode.path("quote").asText();
        String author = quoteNode.path("author").asText();
        System.out.println("Quote: " + quote);
        System.out.println("Author: " + author);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
