package semohan.owner.domain.restaurant.domain;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import semohan.owner.domain.restaurant.dto.RestaurantInfoDto;

@Getter
@Setter
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
}
