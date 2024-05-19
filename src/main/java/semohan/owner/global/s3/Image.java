package semohan.owner.global.s3;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    @NotNull
    private String s3Url;

    @Builder
    public Image(String fileName, String s3Url) {
        this.fileName = fileName;
        this.s3Url = s3Url;
    }
}
