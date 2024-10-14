package semohan.owner.domain.owner.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public OwnerDto getOwnerInfo(long id) {
        return OwnerDto.toDto(ownerRepository.findOwnerById(id).orElseThrow());
    }

    @Transactional
    public boolean updateOwnerInfo(Long id, OwnerUpdateDto ownerUpdateDto) {

        // id로 owner 가져오기
        Owner owner = ownerRepository.findOwnerById(id).orElseThrow();

        // entity에 변경된 비밀번호 set
        if(ownerUpdateDto.getPassword() != null && !ownerUpdateDto.getPassword().equals("")) {
            owner.setPassword(bCryptPasswordEncoder.encode(ownerUpdateDto.getPassword()));
        }

        // entity에 변경된 핸드폰 번호 set
        owner.setPhoneNumber(ownerUpdateDto.getPhoneNumber());

        ownerRepository.save(owner);

        return true;
    }
}
