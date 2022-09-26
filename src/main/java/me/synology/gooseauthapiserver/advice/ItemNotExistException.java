package me.synology.gooseauthapiserver.advice;

public class ItemNotExistException extends RuntimeException {

  public ItemNotExistException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public ItemNotExistException(String message) {
    super(message);
  }

  public ItemNotExistException() {
    super();
  }
}
