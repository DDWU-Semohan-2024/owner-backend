package semohan.owner.domain.restaurant.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.restaurant.application.RestaurantService;
import semohan.owner.domain.restaurant.dto.RestaurantInfoDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/info")
    public ResponseEntity<RestaurantInfoDto> restaurantInfo(HttpServletRequest request) {
        long id = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(restaurantService.getRestaurantInfo(id));
    }

    @PostMapping("/updateInfo")
    public ResponseEntity<Boolean> updateRestaurantInfo(HttpServletRequest request, @RequestBody RestaurantInfoDto restaurantInfoDto) {
        long id = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(restaurantService.updateRestaurantInfo(id, restaurantInfoDto));
    }
}
