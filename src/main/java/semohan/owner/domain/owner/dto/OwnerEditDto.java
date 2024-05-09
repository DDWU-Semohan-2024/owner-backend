package semohan.owner.domain.owner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerEditDto {
    @NotNull
    private String password;

    private String newPassword;

    private String phoneNumber;
}