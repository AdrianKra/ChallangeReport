package blossom.reports_service.model;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class ClientTest {

  @Autowired
  private ReportsService reportsService;

  @MockBean
  private QuotesServiceClient quotesClient;

  // @Test
  // public void getQuotesTest() {
  // Quote[] quotes = reportsService.getQuotes("happiness");
  // for (Quote quote : quotes) {
  // System.out.println("!!!!!!" + quote);
  // }
  // }

  @Test
  public void getQuotesTest() {
    Quote[] mockQuotes = new Quote[] { new Quote("Happiness is a journey, not a destination.", "courage") };
    when(quotesClient.getQuotes(anyString(), anyString())).thenReturn(mockQuotes);

    Quote[] quotes = reportsService.getQuotes("happiness");
    for (Quote quote : quotes) {
      System.out.println("!!!!!!" + quote);
    }
  }
}
