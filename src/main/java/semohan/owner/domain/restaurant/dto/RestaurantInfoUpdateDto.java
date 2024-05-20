package semohan.owner.domain.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RestaurantInfoUpdateDto {

    @Pattern(regexp= "\\d{2}-\\d{3}-\\d{4}", message="알맞은 형식의 전화번호를 입력해주세요.")
    private String phoneNumber;

    @NotBlank(message="필수 항목을 모두 입력해주세요.")
    private String businessHours;

    @NotBlank(message="필수 항목을 모두 입력해주세요.")
    private String price;

    private String postCode;

    private String address;

    private String detailedAddress;
}
