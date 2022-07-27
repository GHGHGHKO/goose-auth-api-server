package me.synology.gooseauthapiserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import me.synology.gooseauthapiserver.model.response.SingleResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import me.synology.gooseauthapiserver.service.SignService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "1. Sign")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

  private final SignService signService;

  private final ResponseService responseService;

  @Operation(summary = "로그인", description = "이메일 회원 로그인을 한다.")
  @PostMapping(value = "/signIn")
  public SingleResult<String> signIn(
      @Parameter(name = "회원ID : 이메일", required = true) @RequestParam String userEmail,
      @Parameter(name = "비밀번호", required = true) @RequestParam String userPassword) {
    return responseService.getSingleResult(signService.signIn(userEmail, userPassword));
  }

  @Operation(summary = "회원가입", description = "회원가입을 한다.")
  @PostMapping(value = "/signUp")
  public CommonResult signUp(
      @Parameter(name = "회원ID : 이메일", required = true) @RequestParam String userEmail,
      @Parameter(name = "비밀번호", required = true) @RequestParam String userPassword,
      @Parameter(name = "이름", required = true) @RequestParam String userNickname) {
    signService.signUp(userEmail, userPassword, userNickname);
    return responseService.getSuccessResult();
  }
}
