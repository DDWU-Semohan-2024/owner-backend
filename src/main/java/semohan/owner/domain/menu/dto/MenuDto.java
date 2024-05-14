package semohan.owner.domain.menu.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import semohan.owner.domain.restaurant.domain.Restaurant;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    @NotNull
    @Size(min = 1, max = 2)
    private List<String> main_menu;

    @NotNull
    @Size(min = 1, max = 10)
    private List<String> sub_menu;

    @NotNull
    private int meal_type;

    @NotNull
    private Restaurant restaurant;

    @NotNull
    private Date meal_date;
}
