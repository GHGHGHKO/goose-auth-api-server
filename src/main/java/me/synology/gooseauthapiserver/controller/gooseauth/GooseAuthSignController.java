package me.synology.gooseauthapiserver.controller.gooseauth;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.synology.gooseauthapiserver.dto.sign.GooseAuthSignUpRequestDto;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import me.synology.gooseauthapiserver.service.gooseauth.GooseAuthSignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "4. GooseAuth Sign")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/gooseAuth")
public class GooseAuthSignController {

  private final GooseAuthSignService gooseAuthSignService;

  private final ResponseService responseService;

  @Operation(summary = "GooseAuth 유저 회원가입", description = "회원가입을 한다.")
  @PostMapping(value = "/signUp")
  public ResponseEntity<CommonResult> gooseAuthSignUp(
      @Parameter(name = "X-AUTH-TOKEN", required = true, in = HEADER) String token,
      @RequestBody GooseAuthSignUpRequestDto gooseAuthSignUpRequestDto) {
    gooseAuthSignService.signUp(gooseAuthSignUpRequestDto);
    return ResponseEntity.ok()
        .body(responseService.getSuccessResult());
  }
}
