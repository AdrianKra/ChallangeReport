package blossom.reports_service.model;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class ClientTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientTest.class);

  @Autowired
  private RetryableServiceClient retryableQuotesClient;

  @MockBean
  private QuotesServiceClient quotesClient;

  @Test
  public void getQuotesTest() {
    LOGGER.info("Starting Mock Test for getQuotesTest...");
    LOGGER.info("Mocking fetch of quotes for category: happiness...");
    Quote[] mockQuotes = new Quote[] { new Quote("Happiness is a journey, not a destination.", "author") };
    when(quotesClient.getQuotes(anyString(), anyString())).thenReturn(mockQuotes);

    Quote[] quotes = retryableQuotesClient.getQuotes("happiness");
    for (Quote quote : quotes) {
      System.out.println(quote);
    }

    assert (quotes.length == 1);
    assert (quotes[0].getQuote().equals("Happiness is a journey, not a destination."));
    assert (quotes[0].getAuthor().equals("author"));
    LOGGER.info("Mock Test for getQuotesTest completed successfully.");
  }
}
