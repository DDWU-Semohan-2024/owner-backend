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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
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

    public Map<String, List<MenuLikesDto>> getWeeklyLikesAndReviews(long ownerId, int weekOffset) {
        Restaurant restaurant = ownerRepository.findOwnerById(ownerId)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER))
                .getRestaurant();

        // 현재 날짜로부터 weekOffset(전 주 -1, 이번 주 0, 다음 주 1)만큼 이전/이후 주로 이동
        LocalDate today = LocalDate.now().plusWeeks(weekOffset);

        // 해당 주의 월요일과 일요일 계산
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);

        java.sql.Date start = convertToSqlDate(monday);
        java.sql.Date end = convertToSqlDate(sunday);

        List<Menu> menuList = menuRepository.findAllByRestaurantAndMealDateBetween(restaurant, start, end);

        // 결과를 담을 Map 생성 (한글 요일을 key로 사용)
        Map<String, List<MenuLikesDto>> resultMap = new HashMap<>();

        // 요일 변환용 배열
        String[] koreanDays = {"월", "화", "수", "목", "금", "토", "일"};

        // 각 요일에 해당하는 빈 리스트를 미리 초기화
        for (String day : koreanDays) {
            resultMap.put(day, new ArrayList<>());
        }

        // 각 메뉴에 대해 좋아요 수와 리뷰 수 계산
        for (Menu menu : menuList) {
            int likesCount = menu.getLikesMenu();
            int reviewsCount = reviewRepository.countByMenu(menu);

            LocalDate mealDate = new java.sql.Date(menu.getMealDate().getTime()).toLocalDate();
            DayOfWeek dayOfWeek = mealDate.getDayOfWeek(); // 요일 가져오기
            String koreanDay = koreanDays[dayOfWeek.getValue() - 1]; // 한글 요일 변환

            double ratio = (reviewsCount != 0) ? (double) likesCount / reviewsCount * 100 : 0;

            // 한글 요일에 맞는 리스트에 MenuLikesDto 추가
            resultMap.get(koreanDay).add(new MenuLikesDto(mealDate, (int) ratio));
        }

        return resultMap;
    }

    public List<Top3MenuDto> getTop3LikedMenus(long ownerId, int weekOffset) {
        Restaurant restaurant = ownerRepository.findOwnerById(ownerId)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER))
                .getRestaurant();

        // 현재 날짜로부터 weekOffset(전 주 -1, 이번 주 0, 다음 주 1)만큼 이전/이후 주로 이동
        LocalDate today = LocalDate.now().plusWeeks(weekOffset);

        // 해당 주의 월요일과 일요일 계산
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);

        java.sql.Date start = convertToSqlDate(monday);
        java.sql.Date end = convertToSqlDate(sunday);

        // 해당 주간의 메뉴 목록 가져오기
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

    public Map<String, List<WeeklyStatsDto>> getWeeklyMainMenu(long ownerId, int weekOffset) {
        Restaurant restaurant = ownerRepository.findOwnerById(ownerId).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER)).getRestaurant();

        // 현재 날짜로부터 weekOffset(전 주 -1, 이번 주 0, 다음 주 1)만큼 이전/이후 주로 이동
        LocalDate today = LocalDate.now().plusWeeks(weekOffset);

        // 해당 주의 월요일과 일요일 계산
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);

        java.sql.Date start = convertToSqlDate(monday);
        java.sql.Date end = convertToSqlDate(sunday);

        List<Menu> menus = menuRepository.findAllByRestaurantAndMealDateBetween(restaurant, start, end);

        // 결과를 담을 Map 생성 (한글 요일을 key로 사용)
        Map<String, List<WeeklyStatsDto>> resultMap = new HashMap<>();

        // 요일 변환용 배열
        String[] koreanDays = {"월", "화", "수", "목", "금", "토", "일"};

        // 각 요일에 해당하는 빈 리스트를 미리 초기화
        for (String day : koreanDays) {
            resultMap.put(day, new ArrayList<>());
        }

        for (Menu menu : menus) {
            int reviewsCount = reviewRepository.countByMenu(menu); // 메뉴에 대한 리뷰 수
            int likesCount = menu.getLikesMenu(); // 메뉴의 좋아요 개수 가져오기

            LocalDate mealDate = new java.sql.Date(menu.getMealDate().getTime()).toLocalDate();
            DayOfWeek dayOfWeek = mealDate.getDayOfWeek(); // 요일 가져오기
            String koreanDay = koreanDays[dayOfWeek.getValue() - 1]; // 한글 요일 변환

            // 선호도 계산 ( 좋아요 수 / 총 리뷰 수)
            double preference = (reviewsCount > 0) ? (double) likesCount / reviewsCount * 100 : 0;

            // '|'로 구분된 메뉴를 리스트로 변환
            List<String> menuList = Arrays.asList(menu.getMainMenu().split("\\|"));

            WeeklyStatsDto statsDto = new WeeklyStatsDto(menuList, reviewsCount, likesCount, (int) preference);
            // 한글 요일에 맞는 리스트에 MenuLikesDto 추가
            resultMap.get(koreanDay).add(statsDto);
        }

        return resultMap;
    }

    public List<String> getWeeklyTop3Menus() {
        LocalDate lastWeek = LocalDate.now().plusWeeks(-1);

        // 해당 주의 월요일과 일요일 계산
        LocalDate monday = lastWeek.with(DayOfWeek.MONDAY);
        LocalDate sunday = lastWeek.with(DayOfWeek.SUNDAY);

        java.sql.Date start = convertToSqlDate(monday);
        java.sql.Date end = convertToSqlDate(sunday);

        // 일주일간 등록된 모든 메뉴를 가져옴
        List<Menu> allMenus = menuRepository.findAllByMealDateBetween(start, end);

        // 메뉴명과 그에 대한 좋아요 개수를 저장할 Map
        Map<String, Integer> menuLikesMap = new HashMap<>();

        for (Menu menu : allMenus) {
            // 메뉴를 '|'로 분리
            String[] mainMenus = menu.getMainMenu().split("\\|");

            // 각 메인 메뉴의 좋아요 개수를 가져옴
            int likesCount = menu.getLikesMenu();

            // 메인 메뉴별로 좋아요를 분리해서 저장
            for (String mainMenu : mainMenus) {
                menuLikesMap.put(mainMenu, menuLikesMap.getOrDefault(mainMenu, 0) + likesCount);
            }
        }

        // 좋아요가 많은 상위 3개의 메뉴를 바로 반환
        return menuLikesMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // 좋아요 수 기준 내림차순 정렬
                .limit(3) // 상위 3개만 선택
                .map(Map.Entry::getKey) // 메뉴 이름만 가져옴
                .collect(Collectors.toList());
    }
}