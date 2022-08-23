package me.synology.gooseauthapiserver.retrofit;

import me.synology.gooseauthapiserver.dto.social.kakao.KakaoUserInfoResponseDto;
import org.springframework.http.HttpHeaders;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface KakaoApi {

  @GET("/v2/user/me")
  Call<KakaoUserInfoResponseDto> getUserInfo(
      @Header(HttpHeaders.AUTHORIZATION) String accessToken);
}
