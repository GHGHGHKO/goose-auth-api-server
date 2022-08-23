package me.synology.gooseauthapiserver.controller;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.synology.gooseauthapiserver.advice.CommunicationExceptionCustom;
import me.synology.gooseauthapiserver.dto.social.kakao.KakaoUserInfoResponseDto;
import me.synology.gooseauthapiserver.model.response.SingleResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import me.synology.gooseauthapiserver.service.social.KakaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "3. Social")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/social")
public class SocialController {

  private final KakaoService kakaoService;

  private final ResponseService responseService;

  @Operation(summary = "카카오 회원 정보 조회", description = "한 회원의 정보를 조회한다.")
  @PostMapping(value = "/user")
  public ResponseEntity<SingleResult<KakaoUserInfoResponseDto>> findUser(
      @Parameter(name = "access_token", required = true, in = HEADER) String accessToken
  ) throws IOException {
    return ResponseEntity.ok()
        .body(responseService.getSingleResult(kakaoService.getUserInfo(accessToken).orElseThrow(
            CommunicationExceptionCustom::new)));
  }
}
