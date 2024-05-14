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
        menu.setMain_menu(menuDto.getMain_menu());
        menu.setSub_menu(menuDto.getSub_menu());
        menu.setMeal_type(menuDto.getMeal_type());
        menu.setMeal_date(menuDto.getMeal_date());
        menuRepository.save(menu);
        return true;
    }

    public boolean deleteMenu(Long id) {
        menuRepository.deleteById(id);
        return true;
    }

    private Menu convertToEntity(MenuDto menuDto) {
        return Menu.builder()
                .main_menu(menuDto.getMain_menu())
                .sub_menu(menuDto.getSub_menu())
                .meal_type(menuDto.getMeal_type())
                .restaurant(menuDto.getRestaurant())
                .meal_date(menuDto.getMeal_date())
                .build();
    }
}
