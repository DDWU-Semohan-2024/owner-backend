package semohan.owner.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import semohan.owner.domain.menu.domain.Menu;
import semohan.owner.domain.restaurant.domain.Restaurant;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findMenuById(Long id);

    List<Menu> findAllByRestaurantAndMealDateBetween(Restaurant restaurant, Date start, Date end);
}