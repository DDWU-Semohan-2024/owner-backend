package semohan.owner.domain.owner.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semohan.owner.domain.owner.domain.Owner;
import semohan.owner.domain.owner.dto.OwnerDto;
import semohan.owner.domain.owner.repository.OwnerRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerDto getOwnerInfo(long id) {
        return OwnerDto.toDto(ownerRepository.findOwnerById(id).orElseThrow());
    }

    public boolean updateOwnerInfo(Long userId, String phoneNumber, String password, String newPassword) {
        Owner owner = ownerRepository.findById(userId).orElse(null);
        if (owner != null) {
            if (password.equals(owner.getPassword())) {
                // phoneNumber와 newPassword가 빈 문자열이 아닌 경우에만 업데이트를 수행
                if (!phoneNumber.isEmpty()) {
                    owner.setPhoneNumber(phoneNumber);
                }
                if (!newPassword.isEmpty()) {
                    owner.setPassword(newPassword);
                }
                ownerRepository.save(owner);
                return true;
            }
        }   return false;
    }
}
