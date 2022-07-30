package me.synology.gooseauthapiserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.synology.gooseauthapiserver.dto.sign.SignInRequest;
import me.synology.gooseauthapiserver.dto.sign.SignUpRequest;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import me.synology.gooseauthapiserver.model.response.SingleResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import me.synology.gooseauthapiserver.service.SignService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "1. Sign")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

  private final SignService signService;

  private final ResponseService responseService;

  @Operation(summary = "로그인", description = "이메일 회원 로그인을 한다.")
  @PostMapping(value = "/signIn")
  public SingleResult<String> signIn(@RequestBody SignInRequest signInRequest) {
    return responseService.getSingleResult(
        signService.signIn(signInRequest.getUserEmail(), signInRequest.getUserPassword()));
  }

  @Operation(summary = "회원가입", description = "회원가입을 한다.")
  @PostMapping(value = "/signUp")
  public CommonResult signUp(@RequestBody SignUpRequest signUpRequest) {
    log.info("{} {} {}", signUpRequest.getUserEmail(), signUpRequest.getUserPassword(),
        signUpRequest.getUserNickname());
    signService.signUp(signUpRequest.getUserEmail(), signUpRequest.getUserPassword(),
        signUpRequest.getUserNickname());
    return responseService.getSuccessResult();
  }
}
