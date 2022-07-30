package me.synology.gooseauthapiserver.controller.exception;

import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.advice.AuthenticationEntryPointExceptionCustom;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

  @GetMapping(value = "/entryPoint")
  public CommonResult entryPointException() {
    throw new AuthenticationEntryPointExceptionCustom();
  }
}
