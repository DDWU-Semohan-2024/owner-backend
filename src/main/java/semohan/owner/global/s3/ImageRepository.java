package semohan.owner.global.s3;

import org.springframework.data.jpa.repository.JpaRepository;
import semohan.owner.domain.restaurant.domain.Restaurant;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
