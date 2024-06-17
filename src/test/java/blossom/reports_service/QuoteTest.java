package blossom.reports_service;

import org.junit.jupiter.api.Test;

import blossom.reports_service.model.Quote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QuoteTest {

  @Test
  public void testDefaultConstructor() {
    // Arrange
    Quote quote = new Quote();

    // Act & Assert
    assertNull(quote.getQuote());
    assertNull(quote.getAuthor());
  }

  @Test
  public void testParameterizedConstructor() {
    // Arrange
    String expectedQuote = "To be or not to be";
    String expectedAuthor = "William Shakespeare";
    Quote quote = new Quote(expectedQuote, expectedAuthor);

    // Act & Assert
    assertEquals(expectedQuote, quote.getQuote());
    assertEquals(expectedAuthor, quote.getAuthor());
  }

  @Test
  public void testSettersAndGetters() {
    // Arrange
    String expectedQuote = "The only thing we have to fear is fear itself.";
    String expectedAuthor = "Franklin D. Roosevelt";
    Quote quote = new Quote();

    // Act
    quote.setQuote(expectedQuote);
    quote.setAuthor(expectedAuthor);

    // Assert
    assertEquals(expectedQuote, quote.getQuote());
    assertEquals(expectedAuthor, quote.getAuthor());
  }

  @Test
  public void testToString() {
    // Arrange
    String expectedQuote = "I think, therefore I am.";
    String expectedAuthor = "René Descartes";
    Quote quote = new Quote(expectedQuote, expectedAuthor);
    String expectedString = "Quote{quote='I think, therefore I am.', author='René Descartes'}";

    // Act
    String actualString = quote.toString();

    // Assert
    assertEquals(expectedString, actualString);
  }
}
