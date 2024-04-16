package semohan.owner.domain.owner.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;
import semohan.owner.domain.owner.domain.Owner;
import semohan.owner.domain.owner.dto.SignInDto;
import semohan.owner.domain.owner.repository.OwnerRepository;

@Slf4j
@Service
public class AuthService {

    @Autowired
    OwnerRepository ownerRepository;

    public boolean signIn(SignInDto signInDto, HttpServletRequest httpServletRequest) {
        // TODO: 로그인 실패 처리

        // username으로 owner 가져오기
        Owner owner = ownerRepository.findOwnerByUsername(signInDto.getUsername()).get();

        // 비밀번호 확인
        if(signInDto.getPassword().equals(owner.getPassword())) {
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("ownerId", owner.getId());
            return true;
        }

        return false;
    }
}
