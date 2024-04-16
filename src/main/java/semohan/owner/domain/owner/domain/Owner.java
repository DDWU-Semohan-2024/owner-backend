package semohan.owner.domain.owner.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;
import semohan.owner.domain.restaurant.domain.Restaurant;

@Getter
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY) /*FetchType.LAZY: 해당 엔티티가 필요할 때까지 로딩 지연시키는 옵션*/
    private Restaurant restaurant;
}
