package semohan.owner.domain.owner.dto;


import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResetPasswordRequest {
    @NotNull
    private String username;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String password;
}
