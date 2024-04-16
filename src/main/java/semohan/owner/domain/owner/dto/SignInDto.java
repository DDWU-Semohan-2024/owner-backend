package semohan.owner.domain.owner.dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
