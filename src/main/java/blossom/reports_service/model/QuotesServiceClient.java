package blossom.reports_service.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "quotesClient", url = "https://api.api-ninjas.com/v1/")
public interface QuotesServiceClient {

  @GetMapping("/quotes")
  Quote[] getQuotes(@RequestHeader("X-Api-Key") String apiKey, @RequestParam("category") String category);
}
