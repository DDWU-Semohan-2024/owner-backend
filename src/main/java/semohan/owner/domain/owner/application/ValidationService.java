package semohan.owner.domain.owner.application;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ValidationService {
    private static final String CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//    private static final String CODE_CHARS = "0123456789"; // 숫자만 포함하려면 이거 써야함
    private static final SecureRandom RANDOM = new SecureRandom();

    // 인증 번호 생성
    public String createCode() {
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            code.append(CODE_CHARS.charAt(RANDOM.nextInt(CODE_CHARS.length())));
        }
        return code.toString();
    }

    // 인증 번호 검증
    public boolean verifyCode(String inputCode, String generatedCode) {
        // 입력한 인증 번호와 생성된 인증 번호 비교
        return inputCode.equals(generatedCode);
    }
}
