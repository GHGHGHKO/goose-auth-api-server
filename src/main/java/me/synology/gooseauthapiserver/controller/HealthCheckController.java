package me.synology.gooseauthapiserver.controller;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.model.response.SingleResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health-check")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class HealthCheckController {

  private final ResponseService responseService;

  @Operation(summary = "Health check", description = "api 서버, 토큰 상태 체크")
  @GetMapping(value = "/health")
  public ResponseEntity<SingleResult<String>> healthCheck(
      @Parameter(name = "X-AUTH-TOKEN", required = true, in = HEADER) String token
  ) {
    return ResponseEntity.ok()
        .body(responseService.getSingleResult("Healthy"));
  }
}
