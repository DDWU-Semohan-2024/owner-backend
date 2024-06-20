package semohan.owner.domain.menu.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {

    @NotNull
    @Size(min = 1, max = 2)
    private List<String> mainMenu;

    @NotNull
    @Size(min = 1, max = 10)
    private List<String> subMenu;

    @NotNull
    private int mealType;

    @NotNull
    private Date mealDate;

    // mainMnu를 '|'로 구분된 문자열로 반환
    public String getMainMenuAsString() {
        return String.join("|", mainMenu);
    }

    // subMenu를 '|'로 구분된 문자열로 반환
    public String getSubMenuAsString() {
        return String.join("|", subMenu);
    }
}
