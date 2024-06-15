package semohan.owner.domain.restaurant.domain;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import semohan.owner.global.s3.Image;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String businessHours;

    @NotNull
    private String price;

    @NotNull
    @Embedded
    private Address address;

    @NotNull
    private int likesRestaurant = 0;  // 좋아요 수

    @ManyToOne
    @JoinColumn(name="image")
    private Image image;
}
