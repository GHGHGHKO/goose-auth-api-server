package me.synology.gooseauthapiserver.configuration.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component("accessDeniedHandlerCustom")
@RequiredArgsConstructor
public class AccessDeniedHandlerCustom implements AccessDeniedHandler {

  @Qualifier("handlerExceptionResolver")
  private final HandlerExceptionResolver resolver;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    resolver.resolveException(request, response, null, accessDeniedException);
  }
}
