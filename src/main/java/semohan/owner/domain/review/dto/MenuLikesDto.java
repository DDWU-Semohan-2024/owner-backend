package semohan.owner.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuLikesDto {

    private LocalDate date;

    private int preference;
}