package semohan.owner.domain.menu.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semohan.owner.domain.menu.domain.Menu;
import semohan.owner.domain.menu.dto.MenuRequestDto;
import semohan.owner.domain.menu.dto.MenuResponseDto;
import semohan.owner.domain.menu.repository.MenuRepository;
import semohan.owner.domain.owner.repository.OwnerRepository;
import semohan.owner.domain.restaurant.domain.Restaurant;
import semohan.owner.domain.restaurant.repository.RestaurantRepository;
import semohan.owner.global.exception.CustomException;
import semohan.owner.global.exception.ErrorCode;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final OwnerRepository ownerRepository;

    public MenuResponseDto getMenu(long id) {
        return MenuResponseDto.toDto(menuRepository.findMenuById(id).get());
    }

    private java.sql.Date convertToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    // 오늘: 0 / 전주: -1 / 다음주: 1
    public List<MenuResponseDto> getMenuList(long ownerId, int weekIndex) {
        Restaurant restaurant = ownerRepository.findOwnerById(ownerId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_MEMBER)).getRestaurant();

        LocalDate startDate = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .plusWeeks(weekIndex);
        LocalDate endDate = startDate.plusDays(6); // 한 주 뒤의 날짜

        java.sql.Date start = convertToSqlDate(startDate);
        java.sql.Date end = convertToSqlDate(endDate);

        List<Menu> menuList = menuRepository.findAllByRestaurantAndMealDateBetween(restaurant, start, end);
        return menuList.stream()
                .map(MenuResponseDto::toDto)
                .sorted(Comparator.comparing(MenuResponseDto::getMealDate))
                .collect(Collectors.toList());

    }

    public boolean createMenu(MenuRequestDto menuDto, long ownerId) {
        Restaurant restaurant = ownerRepository.findOwnerById(ownerId).get().getRestaurant();
        Menu menu = convertToEntity(menuDto);
        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
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
                .mealDate(menuDto.getMealDate())
                .build();
    }
}
