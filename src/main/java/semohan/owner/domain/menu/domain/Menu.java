package semohan.owner.domain.menu.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import semohan.owner.domain.restaurant.domain.Restaurant;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private Date meal_date;

    @NotNull
    @Size(min = 1, max = 2)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> main_menu;

    @NotNull
    @Size(min = 1, max = 10)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> sub_menu;

    @NotNull
    private int meal_type;

    @NotNull
    @ManyToOne
    private Restaurant restaurant;
}