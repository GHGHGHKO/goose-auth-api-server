package me.synology.gooseauthapiserver.dto.social.kakao;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfoResponseDto {

  private Long id;
  private LocalDateTime connectedAt;
  private ForPartner forPartner;
  private Properties properties;
  private KakaoAccount kakaoAccount;

  @Getter
  @Setter
  private static class ForPartner {

    private String uuid;
  }

  @Getter
  @Setter
  private static class Properties {

    private String nickName;
    private String profileImage;
    private String thumbnailImage;
  }

  @Getter
  @Setter
  private static class KakaoAccount {

    private Boolean profileNeedsAgreement;
    private Profile profile;
    private Boolean hasEmail;
    private Boolean emailNeedsAgreement;
    private Boolean isEmailValid;
    private Boolean isEmailVerified;
    private String email;
    private Boolean hasAgeRange;
    private Boolean ageRangeNeedsAgreement;
    private String ageRange;
    private Boolean hasBirthday;
    private Boolean birthdayNeedsAgreement;
    private String birthday;
    private String birthdayType;
    private Boolean hasGender;
    private Boolean genderNeedsAgreement;
    private String gender;

    @Getter
    @Setter
    private static class Profile {
      private String nickName;
      private String thumbnailImageUrl;
      private String profileImageUrl;
      private Boolean isDefaultImage;
    }
  }
}
