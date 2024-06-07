package semohan.owner.domain.owner.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.owner.application.OwnerService;
import semohan.owner.domain.owner.dto.OwnerDto;
import semohan.owner.domain.owner.dto.OwnerUpdateDto;
import semohan.owner.global.exception.CustomException;
import semohan.owner.global.exception.ErrorCode;


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
    public ResponseEntity<Boolean> updateOwnerInfo(HttpServletRequest request, @Valid @RequestBody OwnerUpdateDto ownerUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.INVALID_FORM_DATA, bindingResult.getFieldErrors().get(0).getDefaultMessage());
        } else {
            Long id = (Long) request.getSession().getAttribute("id");
            return ResponseEntity.ok(ownerService.updateOwnerInfo(id, ownerUpdateDto));
        }
    }
}
