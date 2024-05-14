package semohan.owner.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import semohan.owner.domain.menu.domain.Menu;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuViewDto {
    private List<String> main_menu;

    private List<String> sub_menu;

    public static MenuViewDto toDto (Menu entity) {
        return new MenuViewDto(entity.getMain_menu(), entity.getSub_menu());
    }
}
