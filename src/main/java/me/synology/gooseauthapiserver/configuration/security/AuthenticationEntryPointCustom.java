package me.synology.gooseauthapiserver.configuration.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component("authenticationEntryPointCustom")
@RequiredArgsConstructor
public class AuthenticationEntryPointCustom implements AuthenticationEntryPoint {

  @Qualifier("handlerExceptionResolver")
  private final HandlerExceptionResolver resolver;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {
    resolver.resolveException(request, response, null, exception);
  }
}
