package semohan.owner.domain.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import semohan.owner.global.s3.Image;
import semohan.owner.domain.restaurant.domain.Restaurant;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantInfoDto {
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    private String businessHours;

    private String price;

    @NotNull
    private String address;

    private Image image;

    public static RestaurantInfoDto toDto (Restaurant entity) {
        return new RestaurantInfoDto(entity.getId(), entity.getName(), entity.getPhoneNumber(),
                entity.getBusinessHours(), entity.getPrice(), entity.getAddress().getFullAddress(), entity.getImage());
    }
}
