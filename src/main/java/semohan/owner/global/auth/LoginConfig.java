package semohan.owner.global.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()) // LogInterceptor 등록
                .order(1)    // 적용할 필터 순서 설정
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/sign-in"); // 인터셉터에서 제외할 패턴
    }
}
