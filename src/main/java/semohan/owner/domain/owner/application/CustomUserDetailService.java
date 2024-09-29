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
        Owner owner = ownerRepository.findOwnerByUsername(username).get();

        if(owner != null) {
            return new CustomUserDetails(owner);
        }

        return null;
    }
}
