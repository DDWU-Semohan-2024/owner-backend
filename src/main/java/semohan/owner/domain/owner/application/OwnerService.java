package semohan.owner.domain.owner.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import semohan.owner.domain.owner.domain.Owner;
import semohan.owner.domain.owner.dto.OwnerDto;
import semohan.owner.domain.owner.dto.OwnerUpdateDto;
import semohan.owner.domain.owner.repository.OwnerRepository;
import semohan.owner.global.exception.CustomException;
import semohan.owner.global.exception.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerDto getOwnerInfo(long id) {
        return OwnerDto.toDto(ownerRepository.findOwnerById(id).orElseThrow());
    }

    @Transactional
    public boolean updateOwnerInfo(Long id, OwnerUpdateDto ownerUpdateDto) {

        // id로 owner 가져오기
        Owner owner = ownerRepository.findOwnerById(id).orElseThrow();

        // entity에 변경된 비밀번호 set
        if(ownerUpdateDto.getPassword().equals(ownerUpdateDto.getRepeatedPassword())) {
            owner.setPassword(ownerUpdateDto.getPassword());
        } else {
            throw new CustomException(ErrorCode.INVALID_REPEATED_PASSWORD);
        }

        // entity에 변경된 핸드폰 번호 set
        owner.setPhoneNumber(ownerUpdateDto.getPhoneNumber());

        return true;
    }
}
