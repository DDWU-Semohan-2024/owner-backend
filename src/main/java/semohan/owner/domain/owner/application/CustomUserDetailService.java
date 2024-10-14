package semohan.owner.domain.owner.application;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import semohan.owner.domain.owner.domain.Owner;
import semohan.owner.domain.owner.dto.CustomUserDetails;
import semohan.owner.domain.owner.repository.OwnerRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    public CustomUserDetailService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 원래는 userId를 보내는게 맞지만... 우선 long type의 id로...
//        Owner owner = ownerRepository.findOwnerById(Long.getLong(id)).get();

        // username으로 다시 변경
        Owner owner = ownerRepository.findOwnerByUsername(username).get();

        if(owner != null) {
            return new CustomUserDetails(owner);
        }

        return null;
    }
}
