package semohan.owner.domain.owner.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
@Builder
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindIdVerificationDto {
        @Pattern(regexp= "\\d{3}-\\d{4}-\\d{4}", message="알맞은 형식의 휴대폰 번호를 입력해주세요.")
        @NotNull
        private String phoneNumber;

        @NotNull
        private String verificationCode;
}
