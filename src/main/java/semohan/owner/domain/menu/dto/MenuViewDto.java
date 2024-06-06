package semohan.owner.domain.menu.dto;

import lombok.*;
import semohan.owner.domain.menu.domain.Menu;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
public class MenuViewDto {

    private List<MenuResponseDto> menuList;

}
