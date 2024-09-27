package semohan.owner.domain.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import semohan.owner.domain.menu.domain.Menu;
import semohan.owner.domain.restaurant.domain.Restaurant;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String content;

    @CreationTimestamp
    private LocalDateTime writeTime;

    private boolean likeRestaurant;  // 식당 좋아요 여부

    private boolean likeMenu;        // 메뉴 좋아요 여부

    @NotNull
    @ManyToOne
    private Restaurant restaurant;

    @NotNull
    @ManyToOne
    private Menu menu;
}