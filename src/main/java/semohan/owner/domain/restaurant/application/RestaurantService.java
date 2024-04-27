package semohan.owner.domain.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import semohan.owner.domain.restaurant.dto.RestaurantInfoDto;
import semohan.owner.domain.restaurant.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantInfoDto getRestaurantInfo(long id) {
        return RestaurantInfoDto.toDto(restaurantRepository.findRestaurantById(id).orElseThrow());
    }
}
