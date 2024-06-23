package semohan.owner.domain.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import semohan.owner.domain.owner.repository.OwnerRepository;
import semohan.owner.domain.restaurant.domain.Address;
import semohan.owner.domain.restaurant.domain.Restaurant;
import semohan.owner.domain.restaurant.dto.RestaurantInfoDto;
import semohan.owner.domain.restaurant.dto.RestaurantInfoUpdateDto;
import semohan.owner.domain.restaurant.repository.RestaurantRepository;
import semohan.owner.global.s3.AwsS3Uploader;
import semohan.owner.global.s3.Image;
import semohan.owner.global.s3.ImageRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    private final ImageRepository imageRepository;

    private final AwsS3Uploader awsS3Uploader;

    public RestaurantInfoDto getRestaurantInfo(long ownerId) {
        long resId = ownerRepository.findOwnerById(ownerId).orElseThrow().getRestaurant().getId();
        return RestaurantInfoDto.toDto(restaurantRepository.findRestaurantById(resId).orElseThrow());
    }

    public Boolean updateRestaurantInfo(long ownerId, RestaurantInfoUpdateDto dto, MultipartFile imageFile) {
        long resId = ownerRepository.findOwnerById(ownerId).orElseThrow().getRestaurant().getId();
        Restaurant restaurant = restaurantRepository.findRestaurantById(resId).orElseThrow();

        restaurant.setPhoneNumber(dto.getPhoneNumber());
        restaurant.setBusinessHours(dto.getBusinessHours());
        restaurant.setPrice(dto.getPrice());

        if(dto.getAddress() != null && dto.getAddress() != "") {
            restaurant.setAddress(Address.builder()
                    .address(dto.getAddress())
                    .detailedAddress(dto.getDetailedAddress())
                    .postCode(dto.getPostCode())
                    .build());
        }

        // 이미지 업로드
        if(imageFile != null && !imageFile.isEmpty()){
            String imgPath = awsS3Uploader.imgPath("restaurant");
            String imgUrl = awsS3Uploader.putS3(imageFile, imgPath);
            String imgInputName = imageFile.getOriginalFilename();

            Image image = Image.builder()
                            .s3Url(imgUrl)
                            .fileName(imgInputName)
                            .build();

            imageRepository.save(image);
            restaurant.setImage(image);
        }

        return true;
    }
}
