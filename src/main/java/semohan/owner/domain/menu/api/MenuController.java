package semohan.owner.domain.menu.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.menu.application.MenuService;

import semohan.owner.domain.menu.dto.MenuRequestDto;
import semohan.owner.domain.menu.dto.MenuResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping(value = "/{weekIndex}")
    public ResponseEntity<List<MenuResponseDto>> getMenuView(HttpServletRequest request, @PathVariable("weekIndex") String index) {
        long id = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(menuService.getMenuList(id, Integer.parseInt(index)));
    }

    @PostMapping(value = "/new-menu")
    public ResponseEntity<Boolean> createMenu(@RequestBody @Validated MenuRequestDto menuDto) {
        return ResponseEntity.ok(menuService.createMenu(menuDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateMenu(@PathVariable("id") long id, @RequestBody @Validated MenuRequestDto menuDto) {
        if (menuService.getMenu(id) == null) {
            return ResponseEntity.notFound().build();   // 해당 메뉴 없을 때
        }
        return ResponseEntity.ok(menuService.updateMenu(id, menuDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteMenu(@PathVariable("id") long id) {
        if (menuService.getMenu(id) == null) {
            return ResponseEntity.notFound().build();   // 해당 메뉴 없을 때
        }
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }
}
