package me.synology.gooseauthapiserver.advice;

public class CommunicationExceptionCustom extends RuntimeException {

  public CommunicationExceptionCustom(String message, Throwable throwable) {
    super(message, throwable);
  }

  public CommunicationExceptionCustom(String message) {
    super(message);
  }

  public CommunicationExceptionCustom() {
    super();
  }
}
