package semohan.owner.domain.restaurant.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import semohan.owner.domain.restaurant.application.RestaurantService;
import semohan.owner.domain.restaurant.dto.RestaurantInfoDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping(value = "/info")
    public ResponseEntity<RestaurantInfoDto> restaurantInfo(HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(restaurantService.getRestaurantInfo(id));
    }

}
