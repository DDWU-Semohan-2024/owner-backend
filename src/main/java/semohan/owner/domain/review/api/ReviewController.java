package semohan.owner.domain.review.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.review.dto.MenuLikesDto;
import semohan.owner.domain.review.application.ReviewService;
import semohan.owner.domain.review.dto.ReviewViewDto;
import semohan.owner.domain.review.dto.Top3MenuDto;
import semohan.owner.domain.review.dto.WeeklyStatsDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/my-reviews")
    public ResponseEntity<List<ReviewViewDto>> getMyReviews(HttpServletRequest request) {
        long ownerId = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(reviewService.getMyReviews(ownerId));
    }

    @GetMapping(value = "/line-graph")
    public ResponseEntity<List<MenuLikesDto>> getWeeklyLikesAndReviews(HttpServletRequest request) {
        long ownerId = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(reviewService.getWeeklyLikesAndReviews(ownerId));
    }

    @GetMapping("/pie-chart")
    public ResponseEntity<List<Top3MenuDto>> getTop3LikedMenus(HttpServletRequest request) {
        long ownerId = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(reviewService.getTop3LikedMenus(ownerId));
    }

    @GetMapping("/table-graph")
    public ResponseEntity<List<WeeklyStatsDto>> getWeeklyMainMenu(HttpServletRequest request) {
        long ownerId = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(reviewService.getWeeklyMainMenu(ownerId));
    }

    @GetMapping("/top3")
    public ResponseEntity<List<String>> getWeeklyTop3Menus() {
        return ResponseEntity.ok(reviewService.getWeeklyTop3Menus());
    }
}