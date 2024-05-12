package semohan.owner.domain.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import semohan.owner.domain.owner.repository.OwnerRepository;
import semohan.owner.domain.restaurant.domain.Restaurant;
import semohan.owner.domain.restaurant.dto.RestaurantInfoDto;
import semohan.owner.domain.restaurant.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    public RestaurantInfoDto getRestaurantInfo(long ownerId) {
        long resId = ownerRepository.findOwnerById(ownerId).orElseThrow().getRestaurant().getId();
        return RestaurantInfoDto.toDto(restaurantRepository.findRestaurantById(resId).orElseThrow());
    }

    public Boolean updateRestaurantInfo(long ownerId, RestaurantInfoDto dto) {
        long resId = ownerRepository.findOwnerById(ownerId).orElseThrow().getRestaurant().getId();
        Restaurant restaurant = restaurantRepository.findRestaurantById(resId).orElseThrow();

        restaurant.setPhoneNumber(dto.getPhoneNumber());
        restaurant.setBusinessHours(dto.getBusinessHours());
        restaurant.setPrice(dto.getPrice());

        return true;
    }
}
