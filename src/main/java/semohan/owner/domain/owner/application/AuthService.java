package semohan.owner.domain.owner.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semohan.owner.domain.owner.domain.Owner;
import semohan.owner.domain.owner.dto.SignInDto;
import semohan.owner.domain.owner.repository.OwnerRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final OwnerRepository ownerRepository;

    public boolean signIn(SignInDto signInDto, HttpServletRequest httpServletRequest) {
        // username으로 owner 가져오기
        Owner owner = ownerRepository.findOwnerByUsername(signInDto.getUsername()).orElseThrow();

        // 비밀번호 확인
        if(signInDto.getPassword().equals(owner.getPassword())) {
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("id", owner.getId());
            return true;
        }

        return false;
    }

    public String findUserName(String phoneNumber) {

        // phoneNumber로 owner 가져오기
        Owner owner = ownerRepository.findOwnerByPhoneNumber(phoneNumber).orElse(null);
        if (owner == null) {
            return "사용자를 찾을 수 없습니다. ";
        } else return owner.getUsername();
    }

    public String resetPassword(String userName, String phoneNumber, String newPassword) {
        // 아이디로 사용자 조회
        Owner owner = ownerRepository.findOwnerByUsername(userName).orElse(null);

        // 입력한 아이디로 찾은 owner의 휴대전화 번호와 입력한 휴대전화 번호가 일치하는지 확인
        if (owner != null) {
            if (owner.getPhoneNumber().equals(phoneNumber)) {
                // 비밀번호 재설정
                owner.setPassword(newPassword);
                ownerRepository.save(owner);
                return "비밀번호가 성공적으로 재설정되었습니다.";
            } else {
                return "아이디와 휴대전화 번호가 일치하는 사용자를 찾을 수 없습니다.";
            }
        } else {
            return "해당 아이디로 등록된 사용자를 찾을 수 없습니다.";
        }
    }
}
