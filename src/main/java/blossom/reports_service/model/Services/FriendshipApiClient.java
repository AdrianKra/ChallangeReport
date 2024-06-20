package blossom.reports_service.model.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "http://localhost:8080/rest/users/friendship", name = "FriendshipAPI")
public interface FriendshipApiClient {

    @GetMapping("/checkFriendship/{userEmail1}/{userEmail2}")
    public Boolean isFriendWith(@PathVariable("userEmail1") String userEmail1,
            @PathVariable("userEmail2") String userEmail2);
}
