package semohan.owner.domain.owner.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.owner.application.AuthService;
import semohan.owner.domain.owner.application.CustomUserDetailService;
import semohan.owner.domain.owner.dto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CustomUserDetailService customUserDetailService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInDto signInDto, HttpServletRequest httpServletRequest) {
        // TODO: jwt 적용
        HttpSession session = httpServletRequest.getSession(true);

        TokenDto tokenDto = authService.signIn(signInDto);

        session.setAttribute("token", tokenDto.getToken());
        session.setAttribute("id", tokenDto.getId());

        return ResponseEntity.ok(authService.signIn(signInDto).getToken());
    }

    @PostMapping(value = "/sign-out")
    public ResponseEntity<Boolean> signOut(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        log.info("로그아웃 완료");
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/find-id/send")
    public ResponseEntity<Boolean> sendSmsForFindId(@RequestBody @Pattern(regexp= "\\d{3}-\\d{4}-\\d{4}") String phoneNumber) {
        log.info(phoneNumber);
        return ResponseEntity.ok(authService.sendVerifySms(phoneNumber));
    }

    @PostMapping(value = "/find-id/confirm")
    public ResponseEntity<String> verifySmsForFindId(@RequestBody @Validated FindIdVerificationDto dto) {
        log.info(dto.toString());
        return ResponseEntity.ok(authService.verifySms(dto));
    }

    @PostMapping(value="/request-temporary-password/send")
    public ResponseEntity<Boolean> sendSmsForResetPassword(@RequestBody @Validated TemporaryPasswordRequestDto dto) {
        return ResponseEntity.ok(authService.sendSmsForResetPassword(dto));
    }

    @PostMapping(value="/request-temporary-password/confirm")
    public ResponseEntity<Boolean> sendTempPassword(@RequestBody @Validated TemporaryPasswordVerificationDto dto) {
        return ResponseEntity.ok(authService.sendTempPassword(dto));
    }
}
