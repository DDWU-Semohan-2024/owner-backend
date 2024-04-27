package semohan.owner.domain.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semohan.owner.domain.restaurant.domain.Restaurant;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findRestaurantById(Long id);
}
