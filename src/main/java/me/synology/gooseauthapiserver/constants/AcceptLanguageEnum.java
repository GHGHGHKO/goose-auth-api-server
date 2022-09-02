package me.synology.gooseauthapiserver.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum AcceptLanguageEnum {

  KO_KR("ko_KR"),
  EN("en");

  private String locale;
}
