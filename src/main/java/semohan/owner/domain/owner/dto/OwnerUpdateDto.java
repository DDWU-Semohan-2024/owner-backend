package semohan.owner.domain.owner.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerUpdateDto {

    @Length(max = 30, message = "비밀번호는 최대 30자까지 입력 가능합니다.")
    private String password;

    @Pattern(regexp= "\\d{3}-\\d{4}-\\d{4}", message="알맞은 형식의 휴대폰 번호를 입력해주세요.")
    private String phoneNumber;
}