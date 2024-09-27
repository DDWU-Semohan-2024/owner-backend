package semohan.owner.domain.review.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semohan.owner.domain.menu.domain.Menu;
import semohan.owner.domain.owner.repository.OwnerRepository;
import semohan.owner.domain.review.dto.MenuLikesDto;
import semohan.owner.domain.menu.repository.MenuRepository;
import semohan.owner.domain.restaurant.domain.Restaurant;
import semohan.owner.domain.review.domain.Review;
import semohan.owner.domain.review.dto.ReviewViewDto;
import semohan.owner.domain.review.repository.ReviewRepository;
import semohan.owner.global.exception.CustomException;
import semohan.owner.global.exception.ErrorCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;
    private final OwnerRepository ownerRepository;

    private java.sql.Date convertToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    public List<ReviewViewDto> getMyReviews(long ownerId) {
        Restaurant restaurant = ownerRepository.findOwnerById(ownerId).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER)).getRestaurant();
        List<Review> reviews = reviewRepository.findReviewsByRestaurant(restaurant);
        return reviews.stream().map(ReviewViewDto::toDto).collect(Collectors.toList());
    }

    public List<MenuLikesDto> getWeeklyLikesAndReviews(long ownerId) {
        Restaurant restaurant = ownerRepository.findOwnerById(ownerId).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER)).getRestaurant();

        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(7);

        java.sql.Date start = convertToSqlDate(oneWeekAgo);
        java.sql.Date end = convertToSqlDate(today);

        List<Menu> menuList = menuRepository.findAllByRestaurantAndMealDateBetween(restaurant, start, end);

        // 결과 담을 리스트
        List<MenuLikesDto> result = new ArrayList<>();

        // 각 메뉴에 대해 좋아요 수와 리뷰 수 계산
        for (Menu menu : menuList) {
            int likesCount = menu.getLikesMenu(); // 메뉴에 대한 좋아요 수
            int reviewsCount = reviewRepository.countByMenu(menu); // 메뉴에 대한 리뷰 수

            LocalDate mealDate = new java.sql.Date(menu.getMealDate().getTime()).toLocalDate();

            // 리뷰 수가 0일 경우 나누기 에러를 방지
            double ratio = (reviewsCount != 0) ? (double) likesCount / reviewsCount * 100 : 0;

            // DTO에 담아 결과 리스트에 추가
            result.add(new MenuLikesDto(mealDate, (int) ratio));
        }

        return result;
    }
}
