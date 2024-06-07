package semohan.owner.domain.menu.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semohan.owner.domain.menu.domain.Menu;
import semohan.owner.domain.menu.dto.MenuRequestDto;
import semohan.owner.domain.menu.dto.MenuResponseDto;
import semohan.owner.domain.menu.repository.MenuRepository;
import semohan.owner.domain.restaurant.domain.Restaurant;
import semohan.owner.domain.restaurant.repository.RestaurantRepository;
import semohan.owner.global.exception.CustomException;
import semohan.owner.global.exception.ErrorCode;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuResponseDto getMenu(long id) {
        return MenuResponseDto.toDto(menuRepository.findMenuById(id).get());
    }

    private java.sql.Date convertToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    // 오늘: 0 / 전주: -1 / 다음주: 1
    public List<MenuResponseDto> getMenuList(long ownerId, int weekIndex) {
        Restaurant restaurant = restaurantRepository.findRestaurantById(ownerId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_MEMBER));
        
        // TODO: 월요일부터 시작하도록 수정

        LocalDate startDate = LocalDate.now().plusWeeks(weekIndex);
        LocalDate endDate = startDate.plusDays(6); // One week later

        java.sql.Date start = convertToSqlDate(startDate);
        java.sql.Date end = convertToSqlDate(endDate);

        List<Menu> menuList = menuRepository.findAllByRestaurantAndMealDateBetween(restaurant, start, end);
        return menuList.stream()
                .map(entity ->
                    MenuResponseDto.toDto(entity)
                )
                .collect(Collectors.toList());
    }

    public boolean createMenu(MenuRequestDto menuDto) {
        menuRepository.save(convertToEntity(menuDto));
        return true;
    }

    public boolean updateMenu(Long id, MenuRequestDto menuDto) {
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

    private Menu convertToEntity(MenuRequestDto menuDto) {
        return Menu.builder()
                .mainMenu(menuDto.getMainMenuAsString())
                .subMenu(menuDto.getSubMenuAsString())
                .mealType(menuDto.getMealType())
                .restaurant(menuDto.getRestaurant())
                .mealDate(menuDto.getMealDate())
                .build();
    }
}
