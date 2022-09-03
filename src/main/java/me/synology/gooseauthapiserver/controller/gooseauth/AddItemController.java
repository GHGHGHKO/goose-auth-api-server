package me.synology.gooseauthapiserver.controller.gooseauth;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.synology.gooseauthapiserver.constants.AcceptLanguageEnum;
import me.synology.gooseauthapiserver.dto.sign.gooseauth.AddItemRequestDto;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import me.synology.gooseauthapiserver.service.gooseauth.AddItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "4. GooseAuth Add Item")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/gooseAuth")
public class AddItemController {

  private final AddItemService addItemService;

  private final ResponseService responseService;

  @Operation(summary = "GooseAuth Add Item", description = "접속 정보를 저장한다.")
  @PostMapping(value = "/addItems")
  public ResponseEntity<CommonResult> gooseAuthAddItem(
      @Parameter(name = "X-AUTH-TOKEN", required = true, in = HEADER) String token,
      @Parameter(name = "Accept-Language", in = HEADER) AcceptLanguageEnum language,
      @Validated @RequestBody AddItemRequestDto addItemRequestDto) {
    addItemService.gooseAuthAddItem(addItemRequestDto);
    return ResponseEntity.ok()
        .body(responseService.getSuccessResult());
  }
}
