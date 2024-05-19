package semohan.owner.domain.restaurant.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import semohan.owner.domain.restaurant.application.RestaurantService;
import semohan.owner.domain.restaurant.dto.RestaurantInfoDto;
import semohan.owner.domain.restaurant.dto.RestaurantInfoUpdateDto;

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

    @PostMapping(value = "/updateInfo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateRestaurantInfo(HttpServletRequest request, @RequestPart RestaurantInfoUpdateDto restaurantInfoUpdateDto, @RequestPart MultipartFile imageFile) {
        long id = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(restaurantService.updateRestaurantInfo(id, restaurantInfoUpdateDto, imageFile));
    }
}
