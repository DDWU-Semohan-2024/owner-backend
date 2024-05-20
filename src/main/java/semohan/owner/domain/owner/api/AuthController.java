package semohan.owner.domain.owner.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.owner.application.AuthService;
import semohan.owner.domain.owner.dto.ResetPasswordRequestDto;
import semohan.owner.domain.owner.dto.SignInDto;
import semohan.owner.domain.owner.dto.SmsCertificationDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<Boolean> signIn(@RequestBody SignInDto signInDto, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("id", authService.signIn(signInDto));
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/sign-out")
    public ResponseEntity<Boolean> signOut(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/find-id/send")
    public ResponseEntity<Boolean> sendSms(@RequestParam("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(authService.sendSms(phoneNumber));
    }

    @PostMapping(value = "/find-id/confirm")
    public ResponseEntity<String> verifySms(@RequestBody @Validated SmsCertificationDto request) {
        return ResponseEntity.ok(authService.verifySms(request));
    }

    @PostMapping(value="/reset-password")
    public ResponseEntity<Boolean> resetPassword(@RequestBody @Validated ResetPasswordRequestDto request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }
}
