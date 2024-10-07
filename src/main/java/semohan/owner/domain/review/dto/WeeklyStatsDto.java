package semohan.owner.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyStatsDto {

    private List<String> mainMenu;

    private int reviewCount;

    private int likesMenu;

    private int preference;
}
