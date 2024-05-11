package semohan.owner.domain.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import semohan.owner.domain.restaurant.domain.Address;
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
    private Address address;

    public static RestaurantInfoDto toDto (Restaurant entity) {
        return new RestaurantInfoDto(entity.getId(), entity.getName(), entity.getPhoneNumber(),
                entity.getBusinessHours(), entity.getPrice(), entity.getAddress());
    }

    public Restaurant toEntity() {
        return new Restaurant(this.id, this.name, this.phoneNumber, this.businessHours, this.price, this.address);
    }
}
