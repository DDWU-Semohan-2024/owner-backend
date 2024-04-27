package semohan.owner.domain.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import semohan.owner.domain.restaurant.domain.Restaurant;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantInfoDto {

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    private String businessHours;

    private String prices;

    @NotNull
    private String address;

    public static RestaurantInfoDto toDto (Restaurant entity) {
        return new RestaurantInfoDto(entity.getName(), entity.getPhoneNumber(),
                entity.getBusinessHours(), entity.getPrice(), entity.getAddress().getFullAddress());
    }
}
