package semohan.owner.domain.menu.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semohan.owner.domain.menu.domain.Menu;
import semohan.owner.domain.menu.dto.MenuDto;
import semohan.owner.domain.menu.dto.MenuViewDto;
import semohan.owner.domain.menu.repository.MenuRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuViewDto getMenuInfo(long id) {
        return MenuViewDto.toDto(menuRepository.findMenuById(id).orElseThrow());
    }

    public boolean createMenu(MenuDto menuDto) {
        menuRepository.save(convertToEntity(menuDto));
        return true;
    }

    public boolean updateMenu(Long id, MenuDto menuDto) {
        Menu menu = menuRepository.findMenuById(id).orElseThrow();
        menu.setMainMenu(menuDto.getMainMenuAsString());
        menu.setSubMenu(menuDto.getSubMenuAsString());
        menu.setMealType(menuDto.getMealType());
        menu.setMealDate(menuDto.getMealDate());
        menuRepository.save(menu);
        return true;
    }

    public boolean deleteMenu(Long id) {
        menuRepository.deleteById(id);
        return true;
    }

    private Menu convertToEntity(MenuDto menuDto) {
        return Menu.builder()
                .mainMenu(menuDto.getMainMenuAsString())
                .subMenu(menuDto.getSubMenuAsString())
                .mealType(menuDto.getMealType())
                .restaurant(menuDto.getRestaurant())
                .mealDate(menuDto.getMealDate())
                .build();
    }
}
