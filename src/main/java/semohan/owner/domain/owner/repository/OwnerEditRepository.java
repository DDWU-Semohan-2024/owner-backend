package semohan.owner.domain.owner.repository;

import org.springframework.stereotype.Repository;
import semohan.owner.domain.owner.domain.Owner;

@Repository
public class OwnerEditRepository{

    public void resetPassword(Owner owner, String password) {
        owner.setPassword(password);
    }

    public void resetPhoneNumber(Owner owner, String phoneNumber) {
        owner.setPhoneNumber(phoneNumber);
    }
}