package semohan.owner.domain.menu.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.menu.application.MenuService;

import semohan.owner.domain.menu.dto.MenuDto;
import semohan.owner.domain.menu.dto.MenuViewDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<MenuViewDto> getMenuInfo(@PathVariable("id") long id) {
        return ResponseEntity.ok(menuService.getMenuInfo(id));
    }

    @PostMapping(value = "/new-menu")
    public ResponseEntity<Boolean> createMenu(@RequestBody @Validated MenuDto menuDto) {
        return ResponseEntity.ok(menuService.createMenu(menuDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateMenu(@PathVariable("id") long id, @RequestBody @Validated MenuDto menuDto) {
        if (menuService.getMenuInfo(id) == null) {
            return ResponseEntity.notFound().build();   // 해당 메뉴 없을 때
        }
        return ResponseEntity.ok(menuService.updateMenu(id, menuDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteMenu(@PathVariable("id") long id) {
        if (menuService.getMenuInfo(id) == null) {
            return ResponseEntity.notFound().build();   // 해당 메뉴 없을 때
        }
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }
}
