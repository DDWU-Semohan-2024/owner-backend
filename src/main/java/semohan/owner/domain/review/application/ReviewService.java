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
import semohan.owner.domain.review.dto.Top3MenuDto;
import semohan.owner.domain.review.dto.WeeklyStatsDto;
import semohan.owner.domain.review.repository.ReviewRepository;
import semohan.owner.global.exception.CustomException;
import semohan.owner.global.exception.ErrorCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Top3MenuDto> getTop3LikedMenus(long ownerId) {
        Restaurant restaurant = ownerRepository.findOwnerById(ownerId).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER)).getRestaurant();

        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(7);

        java.sql.Date start = convertToSqlDate(oneWeekAgo);
        java.sql.Date end = convertToSqlDate(today);

        List<Menu> menus = menuRepository.findAllByRestaurantAndMealDateBetween(restaurant, start, end);

        // 각 메뉴의 좋아요 수를 계산하기 위해 mainMenu 필드 분리
        Map<String, Integer> menuLikesMap = new HashMap<>();

        for (Menu menu : menus) {
            // mainMenu 필드를 '|'로 구분하여 각 메뉴 항목을 분리
            String[] menuItems = menu.getMainMenu().split("\\|");

            for (String menuItem : menuItems) {
                menuItem = menuItem.trim();  // 공백 제거
                int likes = menu.getLikesMenu();  // 해당 메뉴의 좋아요 수 가져오기

                // 메뉴 항목별로 좋아요 수를 합산
                menuLikesMap.put(menuItem, menuLikesMap.getOrDefault(menuItem, 0) + likes);
            }
        }

        // 좋아요 수가 가장 많은 상위 3개의 메뉴 추출
        List<Map.Entry<String, Integer>> top3Menus = menuLikesMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())  // 좋아요 수 내림차순 정렬
                .limit(3)  // 상위 3개의 메뉴 선택
                .toList();

        // 전체 좋아요 수 총합
        int totalLikes = top3Menus.stream().mapToInt(Map.Entry::getValue).sum();

        // 백분율 계산하여 반환
        return top3Menus.stream()
                .map(entry -> {
                    int likes = entry.getValue();

                    // 총 좋아요 수가 0인 경우 백분율을 0으로 설정
                    double percentage = (totalLikes == 0) ? 0 : (double) likes / totalLikes * 100;

                    return new Top3MenuDto(
                            entry.getKey(),  // 메뉴 이름
                            percentage  // 백분율 계산
                    );
                })
                .collect(Collectors.toList());
    }

    public List<WeeklyStatsDto> getWeeklyMainMenu(long ownerId) {
        Restaurant restaurant = ownerRepository.findOwnerById(ownerId).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER)).getRestaurant();

        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(7);

        java.sql.Date start = convertToSqlDate(oneWeekAgo);
        java.sql.Date end = convertToSqlDate(today);

        List<Menu> menus = menuRepository.findAllByRestaurantAndMealDateBetween(restaurant, start, end);

        List<WeeklyStatsDto> weeklyStats = new ArrayList<>();

        for (Menu menu : menus) {
            int reviewsCount = reviewRepository.countByMenu(menu); // 메뉴에 대한 리뷰 수
            int likesCount = menu.getLikesMenu(); // 메뉴의 좋아요 개수 가져오기

            // 선호도 계산 ( 좋아요 수 / 총 리뷰 수)
            double preference = (reviewsCount > 0) ? (double) likesCount / reviewsCount * 100 : 0;

            WeeklyStatsDto statsDto = new WeeklyStatsDto(menu.getMainMenu(), reviewsCount, likesCount, (int) preference);
            weeklyStats.add(statsDto);
        }

        return weeklyStats;
    }
}