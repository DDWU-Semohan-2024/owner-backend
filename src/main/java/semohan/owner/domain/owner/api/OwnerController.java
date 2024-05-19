package semohan.owner.domain.owner.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.owner.application.OwnerService;
import semohan.owner.domain.owner.dto.OwnerDto;
import semohan.owner.domain.owner.dto.OwnerUpdateDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping(value = "/info")
    public ResponseEntity<OwnerDto> ownerInfo(HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("id");
        return ResponseEntity.ok(ownerService.getOwnerInfo(id));
    }

    @PostMapping("/edit-info")
    public ResponseEntity<Boolean> updateOwnerInfo(HttpSession session, @RequestBody @Validated OwnerUpdateDto ownerUpdateDto) {
        Long id = (Long) session.getAttribute("id");
        return ResponseEntity.ok(ownerService.updateOwnerInfo(id, ownerUpdateDto));
    }
}
