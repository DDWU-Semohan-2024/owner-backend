package semohan.owner.domain.restaurant.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Address {

    private String postCode;

    private String address;

    private String detailedAddress;

    public String getFullAddress() {
        return address + detailedAddress;
    }
}
