package semohan.owner.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semohan.owner.domain.menu.domain.Menu;
import semohan.owner.domain.restaurant.domain.Restaurant;
import semohan.owner.domain.review.domain.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByRestaurant(Restaurant restaurant);
    int countByMenu(Menu menu);
}