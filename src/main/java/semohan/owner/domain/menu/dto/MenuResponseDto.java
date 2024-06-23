package semohan.owner.domain.menu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import semohan.owner.domain.menu.domain.Menu;

import java.util.Date;

@Data
@NoArgsConstructor
public class MenuResponseDto {

    private long id;

    private String mainMenu;

    private String subMenu;

    private int mealType;

    private Date mealDate;

    public static MenuResponseDto toDto(Menu entity) {
        MenuResponseDto dto = new MenuResponseDto();
        dto.setId(entity.getId());
        dto.setMainMenu(entity.getMainMenu().replaceAll("\\|", "\n"));
        dto.setSubMenu(entity.getSubMenu().replaceAll("\\|", "\n"));
        dto.setMealType(entity.getMealType());
        dto.setMealDate(entity.getMealDate());
        return dto;
    }
}
