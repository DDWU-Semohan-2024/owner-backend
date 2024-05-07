package semohan.owner.domain.owner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import semohan.owner.domain.owner.domain.Owner;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
    @NotNull
    private String name;

    @NotNull
    private String userName;

    @NotNull
    private String phoneNumber;

    public static OwnerDto toDto (Owner entity) {
        return new OwnerDto(entity.getName(), entity.getUsername(), entity.getPhoneNumber());
    }
}