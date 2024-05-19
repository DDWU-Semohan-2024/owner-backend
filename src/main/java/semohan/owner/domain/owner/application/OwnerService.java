package semohan.owner.domain.owner.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semohan.owner.domain.owner.domain.Owner;
import semohan.owner.domain.owner.dto.OwnerDto;
import semohan.owner.domain.owner.dto.OwnerUpdateDto;
import semohan.owner.domain.owner.repository.OwnerRepository;

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

        // entity에 변경된 핸드폰 번호 set
        owner.setPhoneNumber(ownerUpdateDto.getPhoneNumber());

        // entity에 변경된 비밀번호 set
        if(ownerUpdateDto.getPassword().equals(ownerUpdateDto.getNewPassword())) {
            owner.setPassword(ownerUpdateDto.getPassword());
        }

        return true;
    }
}
