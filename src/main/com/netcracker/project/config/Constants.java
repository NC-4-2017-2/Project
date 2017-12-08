package main.com.netcracker.project.config;

public final class Constants {

  public static class DB {
    public static final String ERP_URL = System.getenv("ERP_URL");
    public static final String ERP_USERNAME = System.getenv("ERP_USERNAME");
    public static final String ERP_PASS = System.getenv("ERP_PASS");

  }
}
