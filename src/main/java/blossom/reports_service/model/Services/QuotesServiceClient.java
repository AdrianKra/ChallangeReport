package blossom.reports_service.model.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import blossom.reports_service.model.Quote;

@FeignClient(name = "quotesClient", url = "https://api.api-ninjas.com/v1/")
public interface QuotesServiceClient {

  @GetMapping("/quotes")
  Quote[] getQuotes(@RequestHeader("X-Api-Key") String apiKey, @RequestParam("category") String category);
}
