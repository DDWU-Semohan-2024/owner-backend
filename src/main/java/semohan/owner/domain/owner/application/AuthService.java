package semohan.owner.domain.owner.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semohan.owner.domain.owner.domain.Owner;
import semohan.owner.domain.owner.dto.ResetPasswordRequestDto;
import semohan.owner.domain.owner.dto.SignInDto;
import semohan.owner.domain.owner.repository.OwnerRepository;
import semohan.owner.global.exception.CustomException;

import static semohan.owner.global.exception.ErrorCode.INVALID_MEMBER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final OwnerRepository ownerRepository;

    public long signIn(SignInDto signInDto) {
        // username으로 owner 가져오기
        Owner owner = ownerRepository.findOwnerByUsername(signInDto.getUsername())
                .orElseThrow(() -> new CustomException(INVALID_MEMBER));


        // 비밀번호 확인
        if(!signInDto.getPassword().equals(owner.getPassword())) {
            throw new CustomException(INVALID_MEMBER);
        }
        return owner.getId();
    }


    public String findUserName(String phoneNumber) {
        // phoneNumber로 owner 가져오기
        Owner owner = (Owner) ownerRepository.findOwnerByPhoneNumber(phoneNumber).orElse(null);
        if (owner == null) {
            return "사용자를 찾을 수 없습니다. ";
        } else {
            return owner.getUsername();
        }
    }

    public boolean resetPassword(ResetPasswordRequestDto request) {
        // 아이디로 사용자 조회
        Owner owner = ownerRepository.findOwnerByUsername(request.getUsername()).orElse(null);

        // 입력한 아이디로 찾은 owner의 휴대전화 번호와 입력한 휴대전화 번호가 일치하는지 확인
        if (owner != null) {
            if (owner.getPhoneNumber().equals(request.getPhoneNumber())) {
                // 비밀번호 재설정
                owner.setPassword(request.getPassword());
                ownerRepository.save(owner); // 변경된 비밀번호 저장
                return true; // 비밀번호 변경 성공
            }
        }
        return false;   // 변경 실패
    }
}
