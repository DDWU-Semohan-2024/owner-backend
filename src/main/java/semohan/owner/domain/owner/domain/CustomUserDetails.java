package semohan.owner.domain.owner.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import semohan.owner.domain.restaurant.domain.Restaurant;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private Owner owner;

    public CustomUserDetails(Owner owner) {
        this.owner = owner;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Owner의 role을 GrantedAuthority로 변환하여 반환
        return Collections.singleton(new SimpleGrantedAuthority(owner.getRole()));
    }

    @Override
    public String getPassword() {
        return owner.getPassword();
    }

    @Override
    public String getUsername() {
        return owner.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료되지 않음을 의미
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠기지 않음을 의미
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호가 만료되지 않음을 의미
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }

    public String getName() {
        return owner.getName();
    }

    public String getPhoneNumber() {
        return owner.getPhoneNumber();
    }

    public Restaurant getRestaurant() {
        return owner.getRestaurant();
    }
}
