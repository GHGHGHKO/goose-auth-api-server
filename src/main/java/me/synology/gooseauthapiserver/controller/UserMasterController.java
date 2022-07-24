package me.synology.gooseauthapiserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.model.response.ListResult;
import me.synology.gooseauthapiserver.model.response.SingleResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import me.synology.gooseauthapiserver.service.UserMasterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ListResult<UserMaster> findAllUsers() {
    return responseService.getListResult(userMasterService.findAllUsers());
  }

  @Operation(summary = "회원 조회", description = "회원을 조회한다.")
  @GetMapping(value = "/users/{userIdentity}")
  public SingleResult<UserMaster> findUserByUserIdentity(
      @Parameter(name = "회원의 userIdentity", required = true)
      @PathVariable Long userIdentity) {
    return responseService.getSingleResult(userMasterService.findUserByIdentity(userIdentity));
  }

  @Operation(summary = "회원 입력", description = "회원을 입력한다.")
  @PostMapping(value = "/users")
  public UserMaster saveUsers(
      @Parameter(name = "회원 이메일", required = true) @RequestParam String userEmail,
      @Parameter(name = "회원 패스워드", required = true) @RequestParam String userPassword,
      @Parameter(name = "회원 닉네임", required = true) @RequestParam String userNickname) {
    return userMasterService.saveUser(userEmail, userPassword, userNickname);
  }
}
