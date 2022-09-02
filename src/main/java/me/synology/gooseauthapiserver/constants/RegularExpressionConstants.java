package me.synology.gooseauthapiserver.constants;

public class RegularExpressionConstants {

  private RegularExpressionConstants() { }

  public static final String EMAIL_VALIDATION = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

  public static final String PASSWORD_VALIDATION = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}";
}
