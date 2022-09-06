package me.synology.gooseauthapiserver.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtils {

  private CommonUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String getAuthenticationUserEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }
}
