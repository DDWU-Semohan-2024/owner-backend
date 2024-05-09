package semohan.owner.domain.owner.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semohan.owner.domain.owner.domain.Owner;
import semohan.owner.domain.owner.dto.OwnerDto;
import semohan.owner.domain.owner.dto.OwnerEditDto;
import semohan.owner.domain.owner.repository.OwnerEditRepository;
import semohan.owner.domain.owner.repository.OwnerRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerEditRepository ownerEditRepository;

    public OwnerDto getOwnerInfo(long id) {
        return OwnerDto.toDto(ownerRepository.findOwnerById(id).orElseThrow());
    }

//    public boolean isPasswordMatch(Long id, String password) {
//        Owner owner = ownerRepository.findById(id).orElse(null);
//        return owner != null && owner.getPassword().equals(password);
//    }

    public boolean updateOwnerInfo(Long id, OwnerEditDto ownerEditDto) {
        // id로 owner 가져오기
        Owner owner = ownerRepository.findOwnerById(id).orElseThrow();

        // 사용자 입력 비밀번호가 사용자 정보에 저장된 비밀번호와 같은지 확인
        if (ownerEditDto.getPassword().equals(owner.getPassword())) {
            if (ownerEditDto.getPhoneNumber() != null && ownerEditDto.getNewPassword() != null) {
                // 핸드폰 번호와 비밀번호 모두 업데이트하는 경우
                ownerEditRepository.resetPassword(owner, ownerEditDto.getNewPassword());
                ownerEditRepository.resetPhoneNumber(owner, ownerEditDto.getPhoneNumber());
            } else if (ownerEditDto.getPhoneNumber() != null) {
                // 핸드폰 번호만 업데이트하는 경우
                ownerEditRepository.resetPhoneNumber(owner, ownerEditDto.getPhoneNumber());
            } else if (ownerEditDto.getNewPassword() != null) {
                // 비밀번호만 업데이트하는 경우
                ownerEditRepository.resetPassword(owner, ownerEditDto.getNewPassword());
            }
            ownerRepository.save(owner);
            return true;
        }
        return false;
    }
}
