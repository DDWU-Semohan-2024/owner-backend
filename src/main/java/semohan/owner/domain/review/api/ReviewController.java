package semohan.owner.domain.review.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.review.dto.MenuLikesDto;
import semohan.owner.domain.review.application.ReviewService;
import semohan.owner.domain.review.dto.ReviewViewDto;

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
}