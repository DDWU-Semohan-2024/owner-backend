package semohan.owner.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import semohan.owner.global.exception.CustomException;

import static semohan.owner.global.exception.ErrorCode.UNAUTHORIZED_USER;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestURI = request.getRequestURI();
        System.out.println("[interceptor] : " + requestURI);
        HttpSession session = request.getSession(false);

        // 로그인 X
        if(session == null || session.getAttribute("id") == null) {
            throw new CustomException(UNAUTHORIZED_USER);
        }
        
        log.info("로그인 상태 확인 완료");

        // 로그인 O
        return true;
    }
}
