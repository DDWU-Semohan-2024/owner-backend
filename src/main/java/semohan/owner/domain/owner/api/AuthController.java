package semohan.owner.domain.owner.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.owner.application.AuthService;
import semohan.owner.domain.owner.dto.TemporaryPasswordRequestDto;
import semohan.owner.domain.owner.dto.SignInDto;
import semohan.owner.domain.owner.dto.FindIdVerificationDto;
import semohan.owner.domain.owner.dto.TemporaryPasswordVerificationDto;

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
    public ResponseEntity<Boolean> sendSmsForFindId(@RequestParam("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(authService.sendVerifySms(phoneNumber));
    }

    @PostMapping(value = "/find-id/confirm")
    public ResponseEntity<String> verifySmsForFindId(@RequestBody @Validated FindIdVerificationDto request) {
        return ResponseEntity.ok(authService.verifySms(request));
    }

    @PostMapping(value="/request-temporary-password/send")
    public ResponseEntity<Boolean> sendSmsForResetPassword(@RequestBody @Validated TemporaryPasswordRequestDto request) {
        return ResponseEntity.ok(authService.sendSmsForResetPassword(request));
    }

    @PostMapping(value="/request-temporary-password/confirm")
    public ResponseEntity<Boolean> sendTempPassword(@RequestBody @Validated TemporaryPasswordVerificationDto request) {
        return ResponseEntity.ok(authService.sendTempPassword(request));
    }
}
