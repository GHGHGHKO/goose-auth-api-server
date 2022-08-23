package me.synology.gooseauthapiserver.service.social;

import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.synology.gooseauthapiserver.dto.social.kakao.KakaoUserInfoResponseDto;
import me.synology.gooseauthapiserver.retrofit.KakaoApi;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiredArgsConstructor
@Slf4j
@Service
public class KakaoService {

  private final OkHttpClient okHttpClient;

  @Value("${social.kakao.kapi.url}")
  private String kakaoKapiUrl;

  public Optional<KakaoUserInfoResponseDto> getUserInfo(String accessToken) throws IOException {

    OkHttpClient.Builder httpClient = okHttpClient.newBuilder();

    Retrofit setRetrofit = new Retrofit.Builder()
        .baseUrl(kakaoKapiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build();

    KakaoApi service = setRetrofit.create(KakaoApi.class);
    Call<KakaoUserInfoResponseDto> request = service.getUserInfo(accessToken);

    try {
      Response<KakaoUserInfoResponseDto> response = request.execute();
      if (response.errorBody() != null) {
        log.error("kakaoService errorBody : {}", response.errorBody().string());
      }
      return Optional.ofNullable(response.body());
    } catch (Exception e) {
      log.error("kakao 로그인 실패 accessToken = {}", accessToken, e);
      throw new IOException();
    }
  }
}
