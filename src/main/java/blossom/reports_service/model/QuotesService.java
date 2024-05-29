package blossom.reports_service.model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class QuotesService {

  private String apiKey = "nEoiuHE++sApITJEJkZyyQ==0O7c8zWfpTUONcKJ"; // Replace with your API key

  public Quote getQuote(String category) {
    try {
      // API endpoint
      URI uri = new URI("https://api.api-ninjas.com/v1/quotes?category=" + category);
      URL url = uri.toURL();

      // Establish connection
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("X-Api-Key", apiKey);
      connection.setRequestProperty("accept", "application/json");

      // Read response
      InputStream responseStream = connection.getInputStream();
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(responseStream);

      // Get the first quote from the response
      if (root.isArray() && root.size() > 0) {
        JsonNode quoteNode = root.get(0);
        String quoteText = quoteNode.path("quote").asText();
        String authorText = quoteNode.path("author").asText();
        return new Quote(quoteText, authorText);
      } else {
        throw new RuntimeException("No quotes found for the given category");
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to fetch quote", e);
    }
  }
}
