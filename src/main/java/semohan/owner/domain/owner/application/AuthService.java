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
}
