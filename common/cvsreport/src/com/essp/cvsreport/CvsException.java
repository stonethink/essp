package com.essp.cvsreport;

public class CvsException extends RuntimeException{
  public static String PACKAGE_INVALID = "package invalid";
  public static String SYMBOL_UNKNOWN = "symbol unknown";
  public static String DATA_INVALID = "data unknown";

  String type;
  public CvsException(String type, String message) {
    super(message);
    this.type = type;
  }
  public String getType() {
    return type;
  }
}
