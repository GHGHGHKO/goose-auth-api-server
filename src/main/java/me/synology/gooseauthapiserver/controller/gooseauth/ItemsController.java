package me.synology.gooseauthapiserver.controller.gooseauth;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.synology.gooseauthapiserver.constants.AcceptLanguageEnum;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemsResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.AddItemRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.UpdateItemRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.UpdateItemResponseDto;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import me.synology.gooseauthapiserver.model.response.ListResult;
import me.synology.gooseauthapiserver.model.response.SingleResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import me.synology.gooseauthapiserver.service.gooseauth.ItemsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "4. GooseAuth Add Item")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/gooseAuth")
public class ItemsController {

  private final ItemsService itemsService;

  private final ResponseService responseService;

  @Operation(summary = "GooseAuth Add Item", description = "접속 정보를 저장한다.")
  @PostMapping(value = "/addItems")
  public ResponseEntity<CommonResult> gooseAuthAddItem(
      @Parameter(name = "X-AUTH-TOKEN", required = true, in = HEADER) String token,
      @Parameter(name = "Accept-Language", in = HEADER) AcceptLanguageEnum language,
      @Validated @RequestBody AddItemRequestDto addItemRequestDto) {
    itemsService.gooseAuthAddItem(addItemRequestDto);
    return ResponseEntity.ok()
        .body(responseService.getSuccessResult());
  }

  @Operation(summary = "GooseAuth get items", description = "모든 접속 정보 혹은 폴더 안의 정보를 가져온다.")
  @GetMapping(value = "/items")
  public ResponseEntity<ListResult<GooseAuthGetItemsResponseDto>> gooseAuthItems(
      @Parameter(name = "X-AUTH-TOKEN", required = true, in = HEADER) String token,
      @Parameter(name = "Accept-Language", in = HEADER) AcceptLanguageEnum language,
      @RequestParam(required = false) String folder) {
    return ResponseEntity.ok()
        .body(responseService.getListResult(itemsService.gooseAuthGetItems(folder)));
  }

  @Operation(summary = "GooseAuth get item by id", description = "id로 접속 정보를 가져온다.")
  @GetMapping(value = "/items/{itemIdentity}")
  public ResponseEntity<SingleResult<GooseAuthGetItemResponseDto>> gooseAuthItem(
      @Parameter(name = "X-AUTH-TOKEN", required = true, in = HEADER) String token,
      @Parameter(name = "Accept-Language", in = HEADER) AcceptLanguageEnum language,
      @PathVariable(required = false) Long itemIdentity) {
    return ResponseEntity.ok()
        .body(responseService.getSingleResult(itemsService.gooseAuthGetItem(itemIdentity)));
  }

  @Operation(summary = "GooseAuth update item", description = "id로 접속 정보를 업데이트 한다.")
  @PutMapping(value = "/items/{itemIdentity}")
  public ResponseEntity<SingleResult<UpdateItemResponseDto>> gooseAuthUpdateItme(
      @Parameter(name = "X-AUTH-TOKEN", required = true, in = HEADER) String token,
      @Parameter(name = "Accept-Language", in = HEADER) AcceptLanguageEnum language,
      @PathVariable(required = false) Long itemIdentity,
      @Validated @RequestBody UpdateItemRequestDto updateItemRequestDto) {
    return ResponseEntity.ok()
        .body(responseService.getSingleResult(
            itemsService.gooseAuthUpdateItem(itemIdentity, updateItemRequestDto)));
  }
}
