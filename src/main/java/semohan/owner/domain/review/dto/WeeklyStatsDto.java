package semohan.owner.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyStatsDto {

    private String mainMenu; // '|'로 구분된 문자열

    private int reviewCount;

    private int likesMenu;

    private int preference;
}
