package semohan.owner.domain.owner.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semohan.owner.domain.owner.application.AuthService;
import semohan.owner.domain.owner.dto.SignInDto;

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
}
