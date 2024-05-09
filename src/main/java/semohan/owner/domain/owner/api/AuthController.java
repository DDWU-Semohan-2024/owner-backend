package semohan.owner.domain.owner.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.owner.application.AuthService;
import semohan.owner.domain.owner.dto.ResetPasswordRequest;
import semohan.owner.domain.owner.dto.SignInDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<Boolean> signIn(@RequestBody SignInDto signInDto, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(authService.signIn(signInDto, httpServletRequest));
    }

//    @PostMapping(value = "/sign-in")
//    public ResponseEntity<Boolean> signIn(@RequestParam String username, @RequestParam String password, HttpServletRequest httpServletRequest) {
//        return ResponseEntity.ok(authService.signIn(new SignInDto(username, password), httpServletRequest));
//    }

    @PostMapping(value = "/findId")
    public ResponseEntity<String> findUserName(@RequestParam("phoneNumber") String phoneNumber) {
        String userName = authService.findUserName(phoneNumber);
        if (userName != null) {
            // Owner가 존재하는 경우
            return ResponseEntity.ok(userName);
        } else {
            // Owner가 존재하지 않는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자가 존재하지 않습니다.");
        }
    }

    @PostMapping(value="/reset-password")
    public ResponseEntity<Boolean> resetPassword(@RequestBody @Validated ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }
}
