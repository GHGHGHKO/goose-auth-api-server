package me.synology.gooseauthapiserver.controller;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.model.response.ListResult;
import me.synology.gooseauthapiserver.model.response.SingleResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import me.synology.gooseauthapiserver.service.UserMasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "2. UserMaster")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserMasterController {

  private final UserMasterService userMasterService;

  private final ResponseService responseService;

  @Operation(summary = "모든 회원 조회", description = "모든 회원을 조회한다.")
  @GetMapping(value = "/users")
  public ResponseEntity<ListResult<UserMaster>> findAllUsers(
      @Parameter(name = "X-AUTH-TOKEN", required = true, in = HEADER) String token
  ) {
    return ResponseEntity.ok()
        .body(responseService.getListResult(userMasterService.findAllUsers()));
  }

  @Operation(summary = "회원 조회", description = "회원을 조회한다.")
  @GetMapping(value = "/users/{userIdentity}")
  public ResponseEntity<SingleResult<UserMaster>> findUserByUserIdentity(
      @Parameter(name = "X-AUTH-TOKEN", required = true, in = HEADER) String token,
      @Parameter(name = "userIdentity", required = true)
      @PathVariable Long userIdentity) {
    return ResponseEntity.ok()
        .body(responseService.getSingleResult(userMasterService.findUserByIdentity(userIdentity)));
  }
}
